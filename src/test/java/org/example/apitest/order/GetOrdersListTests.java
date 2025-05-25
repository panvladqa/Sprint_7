package org.example.apitest.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.example.handlers.api.OrdersAPIHandler;
import org.junit.Test;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/ #api-Orders-GetOrdersPageByPage", name = "#api-Orders-GetOrdersPageByPage")
@Tag("get-orders-list")
@Epic("Sprint 7")
@Feature("Группа тестов для API получения списка заказов")
@DisplayName("4. Получение списка заказов")
public class GetOrdersListTests extends OrdersAPIHandler {

    @Test
    @DisplayName("Получение списка всех заказов без параметров")
    @Description("Тест проверяет, что API возвращает список заказов при GET-запросе без параметров.")
    public void getAllOrders_shouldReturnOrdersList() {
        Response response = getAllOrders();
        checkStatusCode(response, 200);
        verifyOrdersExistInResponse(response);
    }
}