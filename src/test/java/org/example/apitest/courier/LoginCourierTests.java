package org.example.apitest.courier;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.handlers.api.CourierAPIHandler;
import org.junit.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;

import java.util.UUID;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/ #api-Courier-Login", name = "#api-Courier-Login")
@Tag("login-courier")
@Epic("Sprint 7")
@Feature("Группа тестов для API логина курьера")
@DisplayName("2. Логин курьера")
public class LoginCourierTests extends CourierAPIHandler {

    private String login;
    private String password;
    private String firstName;

    @Before
    @Step("Подготовка данных для тестирования")
    public void prepareTestData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();

        registerCourier(login, password, firstName);
    }

    @After
    @Step("Очистка данных после теста")
    public void clearAfterTests() {
        Response response = loginCourier(login, password);
        Integer courierId = getCourierIdFromResponse(response);

        if (courierId != null) {
            deleteCourierById(courierId);
        }
    }

    @Test
    @DisplayName("Успешный вход курьера в систему")
    @Description("Тест проверяет, что курьер может успешно войти в систему. Ожидается статус 200 и наличие ID курьера.")
    public void loginCourier_shouldBeSuccessful() {
        Response response = loginCourier(login, password);

        checkStatusCode(response, 200);
        verifyCourierIdIsNotNull(response);
    }

    @Test
    @DisplayName("Вход без логина и пароля")
    @Description("Тест проверяет, что вход невозможен без указания логина и пароля. Ожидается статус 400 и сообщение об ошибке.")
    public void loginWithoutCredentials_shouldFail() {
        Response response = loginCourier("", "");

        checkStatusCode(response, 400);
        checkResponseBodyField(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Вход без логина")
    @Description("Тест проверяет, что вход невозможен без указания логина. Ожидается статус 400 и сообщение об ошибке.")
    public void loginWithoutLogin_shouldFail() {
        Response response = loginCourier("", password);

        checkStatusCode(response, 400);
        checkResponseBodyField(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Вход без пароля")
    @Description("Тест проверяет, что вход невозможен без указания пароля. Ожидается статус 400 и сообщение об ошибке.")
    public void loginWithoutPassword_shouldFail() {
        Response response = loginCourier(login, "");

        checkStatusCode(response, 400);
        checkResponseBodyField(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Вход с неверным логином")
    @Description("Тест проверяет, что вход невозможен с неверным логином. Ожидается статус 404 и сообщение об ошибке.")
    public void loginWithInvalidLogin_shouldFail() {
        Response response = loginCourier(login + "1", password);

        checkStatusCode(response, 404);
        checkResponseBodyField(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    @Description("Тест проверяет, что вход невозможен с неверным паролем. Ожидается статус 404 и сообщение об ошибке.")
    public void loginWithInvalidPassword_shouldFail() {
        Response response = loginCourier(login, password + "1");

        checkStatusCode(response, 404);
        checkResponseBodyField(response, "message", "Учетная запись не найдена");
    }
}