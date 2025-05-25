package org.example.handlers.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.apitest.entities.request.Courier;
import org.example.apitest.entities.response.CourierResponse;
import org.example.handlers.httpclient.CourierHTTPClient;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierAPIHandler {

    private final CourierHTTPClient courierHttpClient = new CourierHTTPClient();
    private boolean isCourierCreated = false;

    /**
     * Регистрирует нового курьера.
     */
    @Step("Регистрация нового курьера")
    public Response registerCourier(String login, String password, String firstName) {
        return courierHttpClient.registerCourier(new Courier(login, password, firstName));
    }

    /**
     * Авторизует существующего курьера.
     */
    @Step("Авторизация курьера")
    public Response loginCourier(String login, String password) {
        return courierHttpClient.loginCourier(new Courier(login, password));
    }

    /**
     * Получает ID курьера из ответа API.
     */
    @Step("Получение ID курьера из ответа")
    public Integer getCourierIdFromResponse(Response response) {
        return response.body().as(CourierResponse.class).getId();
    }

    /**
     * Удаляет курьера по его ID.
     */
    @Step("Удаление курьера по ID")
    public Response deleteCourierById(Integer courierId) {
        return courierHttpClient.deleteCourierById(courierId);
    }

    /**
     * Проверяет статус-код ответа.
     */
    @Step("Проверка статус-кода: {expectedStatusCode}")
    public void checkStatusCode(Response response, int expectedStatusCode) {
        Allure.addAttachment("Статус ответа", response.getStatusLine());
        response.then().statusCode(expectedStatusCode);
    }

    /**
     * Проверяет поле в теле ответа на соответствие ожидаемому значению.
     */
    @Step("Проверка поля '{field}' на значение '{expectedValue}'")
    public void checkResponseBodyField(Response response, String field, Object expectedValue) {
        Allure.addAttachment("Тело ответа", response.getBody().asInputStream());
        response.then().body(field, equalTo(expectedValue));
    }

    /**
     * Проверяет, что в ответе присутствует ID курьера.
     */
    @Step("Проверка, что ID курьера не null")
    public void verifyCourierIdIsNotNull(Response response) {
        Allure.addAttachment("Тело ответа", response.getBody().asInputStream());
        response.then().body("id", notNullValue());
    }

    /**
     * Проверяет успешное создание курьера по статус-коду.
     */
    public boolean isCourierSuccessfullyCreated(Response response, int expectedStatusCode) {
        if (response.getStatusCode() != expectedStatusCode) return false;

        this.isCourierCreated = true;
        return true;
    }

    public void setCourierCreationStatus(boolean isCreated) {
        this.isCourierCreated = isCreated;
    }

    public boolean wasCourierCreated() {
        return this.isCourierCreated;
    }
}