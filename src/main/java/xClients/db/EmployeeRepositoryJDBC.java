package xClients.db;

import java.sql.*;

public class EmployeeRepositoryJDBC {

    private static final String SQL_INSERT_EMPLOYEE = "INSERT INTO employee(\"is_active\", \"first_name\", \"last_name\", \"middle_name\", \"phone\", \"email\", \"birthdate\", \"avatar_url\", \"company_id\" ) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM employee where id = ?";
    private final Connection connection;

    public EmployeeRepositoryJDBC(String connectionString, String user, String pass) throws SQLException {
        this.connection = DriverManager.getConnection(connectionString, user, pass);
    }

    @Override
    public int create(boolean is_active, String first_name, String last_name, String middle_name, String phone, String email,
                      String birthdate, String avatar_url, int company_id) throws SQLException {
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
        return keys.getInt(1);
    }

    @Override
    public EmployeeDB getById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new EmployeeDB(
                    resultSet.getInt("id"),
                    resultSet.getBoolean("is_active"),
                    resultSet.getString("create_timestamp"),
                    resultSet.getString("change_timestamp"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("middle_name"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("birthdate"),
                    resultSet.getString("avatar_url"),
                    resultSet.getInt("company_id"));
        } else return null;
    }

}
