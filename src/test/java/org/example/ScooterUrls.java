package org.example;

public class ScooterUrls {

    /**
     * Базовый URL хоста тестируемого приложения.
     */
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    /**
     * Адрес для создания нового курьера.
     */
    public static final String CREATE_COURIER_URL = "/api/v1/courier";

    /**
     * Адрес для авторизации курьера.
     */
    public static final String LOGIN_COURIER_URL = "/api/v1/courier/login";

    /**
     * Базовый адрес для удаления курьера (требует указания ID курьера).
     */
    public static final String DELETE_COURIER_URL = "/api/v1/courier/";

    /**
     * Адрес для создания нового заказа.
     */
    public static final String CREATE_ORDER_URL = "/api/v1/orders";

    /**
     * Адрес для отмены текущего заказа.
     */
    public static final String CANCEL_ORDER_URL = "/api/v1/orders/cancel";

    /**
     * Адрес для получения списка всех заказов.
     */
    public static final String ORDERS_LIST_URL = "/api/v1/orders";
}