import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTests {

    @BeforeAll
    public static void beforeEach() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Tag("API")
    @Test
    public void checkUserTest() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"));
    }

    @Tag("API")
    @Test
    public void deleteUserTest() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Tag("API")
    @Test
    public void userNotFoundTest() {
        given()
                .when()
                .get("/users/900")
                .then()
                .statusCode(404);
    }

    @Tag("API")
    @Test
    public void getNonExistingResourceTest() {
        given()
                .when()
                .get("/unknown/999")
                .then()
                .statusCode(404);
    }

    @Tag("API")
    @Test
    public void createUserTest() {
        String requestBody = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Tag("API")
    @Test
    public void updateUserTest() {
        String requestBody = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";
        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"))
                .body("updatedAt", notNullValue());
    }
}