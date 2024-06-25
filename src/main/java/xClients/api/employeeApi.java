package xClients.api;


import io.restassured.http.ContentType;

import xClients.helper.configHelper;

import xClients.pojo.CreateEmployeeError;
import xClients.pojo.Employee;
import xClients.pojo.EmployeeForCompany;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class employeeApi {
    static String url = configHelper.getEmployeeUrl();

    public static int createNewEmployee(Employee employee, String token) {
        return given()
                .log().all()
                .body(employee)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when().post(url)
                .then()
                .log().all()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .body("id", greaterThan(1))
                .extract().path("id");
    }

    public static CreateEmployeeError createNewEmployeeWithNonExistingCompany(Employee employee, String token) {
        CreateEmployeeError response = given()
                .log().all()
                .body(employee)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when().post(url)
                .then()
                .log().all()
                .statusCode(500)
                .contentType("application/json; charset=utf-8")
                .extract()
                .as(CreateEmployeeError.class);
        return response;
    }

    public static List<EmployeeForCompany> getListOfEmployees(int companyId) {
        EmployeeForCompany[] employees = given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("company", companyId)
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeForCompany[].class);
        return Arrays.asList(employees);
    }

}