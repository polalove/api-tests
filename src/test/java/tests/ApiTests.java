package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import models.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.request;
import static specs.Specs.*;

@Feature("API тесты для reqres.in")
@Tag("API")
public class ApiTests {

    @Test
    @DisplayName("Получение информации о пользователе")
    @Story("Пользовательские операции")
    @Severity(SeverityLevel.BLOCKER)
    void checkUserTest() {
        step("Получить информацию о пользователе с id=2", () -> {
            UserResponse response = given(request)
                    .when()
                    .get("/users/2")
                    .then()
                    .spec(response200)
                    .extract().as(UserResponse.class);

            step("Проверить, что email пользователя соответствует ожидаемому", () -> {
                assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            });

            step("Проверить, что id пользователя равен 2", () -> {
                assertThat(response.getData().getId()).isEqualTo(2);
            });
        });
    }

    @Test
    @DisplayName("Удаление пользователя")
    @Story("Пользовательские операции")
    @Severity(SeverityLevel.CRITICAL)
    void deleteUserTest() {
        step("Удалить пользователя с id=2", () -> {
            given(request)
                    .when()
                    .delete("/users/2")
                    .then()
                    .spec(response204);
        });
    }

    @Test
    @DisplayName("Попытка получить несуществующего пользователя")
    @Story("Пользовательские операции")
    @Severity(SeverityLevel.NORMAL)
    void userNotFoundTest() {
        step("Запросить пользователя с id=900", () -> {
            given(request)
                    .when()
                    .get("/users/900")
                    .then()
                    .spec(response404);
        });
    }

    @Test
    @DisplayName("Попытка получить несуществующий ресурс")
    @Story("Ресурсные операции")
    @Severity(SeverityLevel.MINOR)
    void getNonExistingResourceTest() {
        step("Запросить несуществующий ресурс", () -> {
            given(request)
                    .when()
                    .get("/unknown/999")
                    .then()
                    .spec(response404);
        });
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Story("Пользовательские операции")
    @Severity(SeverityLevel.BLOCKER)
    void createUserTest() {
        CreateUserRequest requestBody = new CreateUserRequest();
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        step("Создать нового пользователя", () -> {
            CreateUserResponse response = given(request)
                    .body(requestBody)
                    .when()
                    .post("/users")
                    .then()
                    .spec(response201)
                    .extract().as(CreateUserResponse.class);

            step("Проверить, что имя пользователя соответствует отправленному", () -> {
                assertThat(response.getName()).isEqualTo(requestBody.getName());
            });

            step("Проверить, что работа пользователя соответствует отправленной", () -> {
                assertThat(response.getJob()).isEqualTo(requestBody.getJob());
            });

            step("Проверить, что id пользователя не пустой", () -> {
                assertThat(response.getId()).isNotNull();
            });

            step("Проверить, что дата создания не пустая", () -> {
                assertThat(response.getCreatedAt()).isNotNull();
            });
        });
    }

    @Test
    @DisplayName("Обновление информации о пользователе")
    @Story("Пользовательские операции")
    @Severity(SeverityLevel.CRITICAL)
    void updateUserTest() {
        CreateUserRequest requestBody = new CreateUserRequest();
        requestBody.setName("morpheus");
        requestBody.setJob("zion resident");

        step("Обновить информацию о пользователе с id=2", () -> {
            UpdateUserResponse response = given(request)
                    .body(requestBody)
                    .when()
                    .put("/users/2")
                    .then()
                    .spec(response200)
                    .extract().as(UpdateUserResponse.class);

            step("Проверить, что имя пользователя соответствует отправленному", () -> {
                assertThat(response.getName()).isEqualTo(requestBody.getName());
            });

            step("Проверить, что работа пользователя соответствует отправленной", () -> {
                assertThat(response.getJob()).isEqualTo(requestBody.getJob());
            });

            step("Проверить, что дата обновления не пустая", () -> {
                assertThat(response.getUpdatedAt()).isNotNull();
            });
        });
    }
}