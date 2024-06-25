package xClients.db;

import xClients.helper.configHelper;

import java.sql.*;

public class EmployeeRepositoryJDBC implements EmployeeRepository {
    String user = configHelper.getUserDb();
    String password = configHelper.getPasswordDb();
    String connectionString = configHelper.getConnectionString();
    Connection connection;
    private static final String SQL_INSERT_EMPLOYEE = "INSERT INTO employee(\"is_active\", \"first_name\", \"last_name\"," +
            " \"middle_name\", \"phone\", \"email\", \"birthdate\", \"avatar_url\", \"company_id\" ) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public int createEmployee(boolean is_active, String first_name, String last_name, String middle_name, String phone,
                              String email, String birthdate, String avatar_url, int company_id) throws SQLException {
        connection = DriverManager.getConnection(connectionString, user, password);
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);

        statement.setBoolean(1, is_active);
        statement.setString(2, first_name);
        statement.setString(3, last_name);
        statement.setString(4, middle_name);
        statement.setString(5, phone);
        statement.setString(6, email);
        statement.setDate(7, Date.valueOf(birthdate));
        statement.setString(8, avatar_url);
        statement.setInt(9, company_id);
        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        connection.close();
        return keys.getInt(1);
    }
}
