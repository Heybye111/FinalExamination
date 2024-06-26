package xClients.db;

import io.qameta.allure.Step;

import java.sql.SQLException;

public interface EmployeeRepository {
    @Step("Создать сотрудника в БД")
    int createEmployee(boolean is_active, String first_name, String last_name, String middle_name,
                       String phone, String email, String birthdate, String avatar_url, int company_id) throws SQLException;
}
