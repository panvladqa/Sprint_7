package org.example.handlers.httpclient;

import io.restassured.response.Response;
import org.example.apitest.entities.request.Courier;
import org.example.ScooterUrls;

public class CourierHTTPClient extends BaseHTTPClient {

    /**
     * Регистрирует нового курьера в системе.
     */
    public Response registerCourier(Courier courier) {
        return sendPostRequest(
                ScooterUrls.BASE_URL + ScooterUrls.CREATE_COURIER_URL,
                courier,
                "application/json"
        );
    }

    /**
     * Авторизует существующего курьера в системе.
     */
    public Response loginCourier(Courier courier) {
        return sendPostRequest(
                ScooterUrls.BASE_URL + ScooterUrls.LOGIN_COURIER_URL,
                courier,
                "application/json"
        );
    }

    /**
     * Удаляет курьера по его идентификатору.
     */
    public Response deleteCourierById(Integer courierId) {
        String url = ScooterUrls.BASE_URL + ScooterUrls.DELETE_COURIER_URL + courierId;
        return sendDeleteRequest(url);
    }
}