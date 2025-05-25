package org.example.apitest.courier;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.handlers.api.CourierAPIHandler;
import org.junit.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;

import java.util.UUID;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/ #api-Courier-CreateCourier", name = "#api-Courier-CreateCourier")
@Tag("create-courier")
@Epic("Sprint 7")
@Feature("Группа тестов для API создания курьера")
@DisplayName("1. Создание курьера")
public class CreateCourierTests extends CourierAPIHandler {

    private String login;
    private String password;
    private String firstName;
    private boolean isCreated = false;

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @After
    @Step("Очистка данных после теста")
    public void cleanAfterTests() {
        if (!wasCourierCreated()) return;

        Response response = loginCourier(login, password);
        Integer courierId = getCourierIdFromResponse(response);

        if (courierId != null) {
            deleteCourierById(courierId);
        }

        setCourierCreationStatus(false);
    }

    @Test
    @DisplayName("Создание нового курьера с уникальными данными")
    @Description("Тест проверяет, что новый курьер успешно создаётся. Ожидается статус 201 и ok: true.")
    public void createNewCourier_shouldBeSuccessful() {
        Response response = registerCourier(login, password, firstName);

        setCourierCreationStatus(isCourierSuccessfullyCreated(response, 201));

        checkStatusCode(response, 201);
        checkResponseBodyField(response, "ok", true);
    }

    @Test
    @DisplayName("Попытка создать двух курьеров с одинаковым логином")
    @Description("Тест проверяет, что нельзя создать двух курьеров с одинаковым логином. Ожидается статус 409 и сообщение об ошибке.")
    public void createDuplicateCourier_shouldReturnConflict() {
        // Первый запрос: успешное создание
        Response response = registerCourier(login, password, firstName);
        setCourierCreationStatus(isCourierSuccessfullyCreated(response, 201));

        // Второй запрос: попытка создать того же курьера
        response = registerCourier(login, password, firstName);

        checkStatusCode(response, 409);
        checkResponseBodyField(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Создание курьера без всех параметров")
    @Description("Тест проверяет, что курьер не создаётся без логина, пароля и имени. Ожидается статус 400 и сообщение о недостатке данных.")
    public void createCourierWithMissingAllFields_shouldFail() {
        Response response = registerCourier("", "", "");
        checkStatusCode(response, 400);
        checkResponseBodyField(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Тест проверяет, что курьер не создаётся без логина. Ожидается статус 400 и сообщение о недостатке данных.")
    public void createCourierWithMissingLogin_shouldFail() {
        Response response = registerCourier("", password, firstName);
        checkStatusCode(response, 400);
        checkResponseBodyField(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Тест проверяет, что курьер не создаётся без пароля. Ожидается статус 400 и сообщение о недостатке данных.")
    public void createCourierWithMissingPassword_shouldFail() {
        Response response = registerCourier(login, "", firstName);
        checkStatusCode(response, 400);
        checkResponseBodyField(response, "message", "Недостаточно данных для создания учетной записи");
    }
}