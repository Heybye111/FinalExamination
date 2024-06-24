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

//    public static EmployeeEntity getEmployeeInfo(int employeeId) {
//        Response response = RestAssured.given()
//                .log().all()
//                .contentType(ContentType.JSON)
//                .when()
//                .get(url + "/" + employeeId)
//                .then()
//                .log().all()
//                .statusCode(200) //В свагере описано что статус код должен быть 201, по факту 200. так что тут баг в контракте
//                .extract().response();
//        return response.body().as(EmployeeEntity.class);
//    }

    public static CreateEmployeeError createNewEmployeeWithNonExistingCompany(Employee employee, String token) {
        CreateEmployeeError response = given()
                .log().all()
                .body(employee)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when().post(url + "/employee")
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
                .get(url + "/employee" + "?company=" + companyId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeForCompany[].class);
        return Arrays.asList(employees);
    }

}
