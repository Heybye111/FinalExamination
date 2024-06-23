package xClients.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import xClients.entity.Employee;
import xClients.helper.configHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class employeeApi {
    static String url = configHelper.getUrl();



    public static int createNewEmployee(Employee employee, String token) {
        return given()
                .log().all()
                .body(employee)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when().post(url + "/employee")
                .then()
                .log().all()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .body("id", greaterThan(1))
                .extract().path("id");
    }

    public static Employee getEmployeeInfo(int employeeId) {
        Response response = RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(url + "/" + employeeId)
                .then()
                .log().all()
                .statusCode(200) //В свагере описано что статус код должен быть 201, по факту 200. так что тут баг в контракте
                .extract().response();
        return response.body().as(Employee.class);
    }
}
