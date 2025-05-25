package org.example.handlers.httpclient;

import io.restassured.response.Response;
import org.example.apitest.entities.request.Order;
import org.example.ScooterUrls;

import java.util.HashMap;
import java.util.Map;

public class OrdersHTTPClient extends BaseHTTPClient {

    /**
     * Создаёт новый заказ.
     */
    public Response createOrder(Order order) {
        return sendPostRequest(
                ScooterUrls.BASE_URL + ScooterUrls.CREATE_ORDER_URL,
                order,
                "application/json"
        );
    }

    /**
     * Отменяет заказ по его номеру трекинга.
     */
    public Response cancelOrderByTrackId(Integer trackId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("track", trackId);

        return sendPutRequestWithParams(
                ScooterUrls.BASE_URL + ScooterUrls.CANCEL_ORDER_URL,
                queryParams
        );
    }

    /**
     * Получает список всех доступных заказов.
     */
    public Response getOrdersList() {
        return sendGetRequest(ScooterUrls.BASE_URL + ScooterUrls.ORDERS_LIST_URL);
    }
}