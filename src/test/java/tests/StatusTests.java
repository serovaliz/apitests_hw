package tests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class StatusTests {

    @Test
    public void totalAmountTest() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(25));
    }
    @Test
    public void statusCodeTest() {
        given()
                .log().uri()
                .log().headers()
                .log().method()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200);

    }
    @Test
    public void shemaTest() {
        given()
        .log().uri()
        .log().headers()
        .log().method()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_shema.json"));

    }
    @Test
    public void statusTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
//                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status") BAD PRACTICE
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200);
    }
}
