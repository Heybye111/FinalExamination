package xClients.db;



import java.sql.SQLException;

public interface EmployeeRepository {

    int createEmployee(boolean is_active, String first_name, String last_name, String middle_name,
                       String phone, String email, String birthdate, String avatar_url, int company_id) throws SQLException;
}
