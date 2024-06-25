package xClients.api;

import io.restassured.http.ContentType;
import xClients.pojo.Company;
import xClients.helper.configHelper;
import xClients.pojo.CompanyResponse;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class companyApi {
    static String url = configHelper.getCompanyUrl();
    static String deleteCompanyUrl = configHelper.getDeleteCompanyUrl();

    public static int createCompany(Company company, String token) {
        {
            return given()
                    .log().all()
                    .body(company)
                    .header("x-client-token", token)
                    .contentType(ContentType.JSON)
                    .when().post(url)
                    .then()
                    .log().all()
                    .statusCode(201)
                    .body("id", greaterThan(0))
                    .extract().path("id");
        }
    }

    public static void changeCompanyActivity(int id, boolean activity, String token) {
        String body = "{\"isActive\": \"" + activity + "\"}";
        given()
                .log().all()
                .body(body)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when()
                .patch(url + id)
                .then()
                .log().all()
                .statusCode(200);
    }

    public static List<CompanyResponse> getListOfCompanies(boolean activity) {
        CompanyResponse[] companies = given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("active", activity)
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(CompanyResponse[].class);
        return Arrays.asList(companies);
    }

    public static void deleteCompany(int id, String token) {
        if (id <= 0) {
            return;
        }
        given()
                .log().all()
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when()
                .get(deleteCompanyUrl + id)
                .then()
                .log().all()
                .statusCode(200);
    }
}