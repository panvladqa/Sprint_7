package org.example.handlers.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.apitest.entities.request.Order;
import org.example.apitest.entities.response.OrderResponse;
import org.example.handlers.httpclient.OrdersHTTPClient;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersAPIHandler {

    private final OrdersHTTPClient ordersHttpClient = new OrdersHTTPClient();

    /**
     * Создаёт новый заказ.
     */
    @Step("Создание нового заказа")
    public Response createOrder(String firstName, String lastName, String address, String phone,
                                String rentTime, String deliveryDate, String comment, List<String> scooterColor) {
        return ordersHttpClient.createOrder(new Order(firstName, lastName, address, phone,
                rentTime, deliveryDate, comment, scooterColor));
    }

    /**
     * Отменяет заказ по его трек-номеру.
     */
    @Step("Отмена заказа по трек-номеру")
    public Response cancelOrder(Integer trackId) {
        return ordersHttpClient.cancelOrderByTrackId(trackId);
    }

    /**
     * Получает список всех заказов.
     */
    @Step("Получение списка всех заказов")
    public Response getAllOrders() {
        return ordersHttpClient.getOrdersList();
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
     * Проверяет, что указанное поле в теле ответа не равно null.
     */
    @Step("Проверка, что поле '{fieldName}' не null")
    public void verifyFieldIsNotNullInResponse(Response response, String fieldName) {
        Allure.addAttachment("Тело ответа", response.getBody().asInputStream());
        response.then().body(fieldName, notNullValue());
    }

    /**
     * Возвращает трек-номер созданного заказа из тела ответа.
     */
    @Step("Получение трек-номера заказа из ответа")
    public Integer getTrackFromResponse(Response response) {
        return response.body().as(OrderResponse.class).getTrack();
    }

    /**
     * Проверяет, что в теле ответа есть список заказов.
     */
    @Step("Проверка наличия списка заказов в ответе")
    public void verifyOrdersExistInResponse(Response response) {
        Allure.addAttachment("Список заказов", response.getBody().asInputStream());
        response.then().body("orders", notNullValue());
    }
}