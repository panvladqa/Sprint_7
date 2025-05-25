package org.example.apitest.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.example.handlers.api.OrdersAPIHandler;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
@Link(url = "https://qa-scooter.praktikum-services.ru/docs/ #api-Orders-CreateOrder", name = "Документация: Создание заказа")
@Tag("create-order")
@Epic("Sprint 7")
@Feature("Группа тестов для API создания заказа")
@DisplayName("3. Создание заказа")
public class CreateOrderTests extends OrdersAPIHandler {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private final List<String> scooterColor;
    private Integer trackId;

    public CreateOrderTests(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    /**
     * Возвращает набор тестовых данных для параметризации.
     */
    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] initParamsForTest() {
        return new Object[][]{
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.firstName = "testName";
        this.lastName = "testLastName";
        this.address = "Рязань, Тестовая ул., д. 27к4";
        this.phone = "+7 (921) 123-23-12";
        this.rentTime = "3";
        this.deliveryDate = "2025-05-27";
        this.comment = "Some comment";
    }

    @After
    @Step("Очистка после теста: удаление созданного заказа")
    public void clearAfterTests() {
        if (trackId != null) {
            cancelOrder(trackId);
        }
    }

    @Test
    @DisplayName("Создание заказа с указанным цветом самоката")
    @Description("Тест проверяет возможность создания заказа через API. Ожидается успешный ответ (201) и наличие track-номера.")
    public void createOrderWithDifferentColors_shouldReturnTrackNumber() {
        Allure.parameter("Цвет самоката", scooterColor);

        Response response = createOrder(firstName, lastName, address, phone,
                rentTime, deliveryDate, comment, scooterColor);

        checkStatusCode(response, 201);
        verifyFieldIsNotNullInResponse(response, "track");

        this.trackId = getTrackFromResponse(response);
    }
}