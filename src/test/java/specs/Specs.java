package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Specs {
    public static RequestSpecification request = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);

    private static ResponseSpecification createResponseSpec(int statusCode, boolean logBody) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder()
                .log(STATUS)
                .expectStatusCode(statusCode);

        if (logBody) {
            builder.log(BODY);
        }

        return builder.build();
    }

    public static ResponseSpecification response200 = createResponseSpec(200, true);
    public static ResponseSpecification response201 = createResponseSpec(201, true);
    public static ResponseSpecification response204 = createResponseSpec(204, false);
    public static ResponseSpecification response404 = createResponseSpec(404, true);
}