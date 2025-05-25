package org.example.handlers.httpclient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseHTTPClient {

    /**
     * Выполняет POST-запрос с указанным телом и типом контента.
     */
    public Response sendPostRequest(String url, Object requestBody, String contentType) {
        return given(baseRequestWithContentType(contentType))
                .body(requestBody)
                .when()
                .post(url);
    }

    /**
     * Выполняет GET-запрос без параметров.
     */
    public Response sendGetRequest(String url) {
        return given(baseRequest())
                .get(url);
    }

    /**
     * Выполняет GET-запрос с query-параметрами.
     */
    public Response sendGetRequestWithParams(String url, Map<String, Object> queryParams) {
        return given(baseRequest())
                .queryParams(queryParams)
                .when()
                .get(url);
    }

    /**
     * Выполняет DELETE-запрос.
     */
    public Response sendDeleteRequest(String url) {
        return given(baseRequest())
                .delete(url);
    }

    /**
     * Выполняет PUT-запрос с query-параметрами.
     */
    public Response sendPutRequestWithParams(String url, Map<String, Object> queryParams) {
        return given(baseRequest())
                .queryParams(queryParams)
                .when()
                .put(url);
    }

    /**
     * Возвращает базовую спецификацию запроса с фильтром Allure и отключенной проверкой HTTPS.
     */
    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    /**
     * Возвращает спецификацию запроса с указанным Content-Type.
     */
    private RequestSpecification baseRequestWithContentType(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-Type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
}