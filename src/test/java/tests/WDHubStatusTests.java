package tests;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class WDHubStatusTests extends TestBase {
    String login = System.getProperty("selenoid.login", "user1");
    String password = System.getProperty("selenoid.password", "1234");
    @Test
    public void wdHubstatusTest() {
        given()
                .log().all()
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString((login + ":" + password).getBytes()))
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    public void wdHubstatus401Test() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }
    @Test
    public void wdHubJsonSchemaTest() {
        given()
                .log().all()
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString((login + ":" + password).getBytes()))
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_schema.json"));
    }
    @Test
    public void wdHubBodyTest() {
        given()
                .log().all()
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString((login + ":" + password).getBytes()))
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .body("value.message", containsString("Selenoid"))
                .body("value.ready", is(true));

    }


}
