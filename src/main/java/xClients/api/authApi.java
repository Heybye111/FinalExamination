package xClients.api;

import io.restassured.http.ContentType;
import xClients.helper.configHelper;

import static io.restassured.RestAssured.given;

public class authApi {
    static String url = configHelper.getAuthUrl();

    public static String getToken() {
        String body = "{\"username\": \"" + configHelper.getLogin() + "\",\"password\": \"" + configHelper.getPassword() + "\"}";
        String token = given()
                .log().all()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post(url)
                .then()
                .log().all()
                .statusCode(201)
                .extract().path("userToken");
        return token;
    }
}