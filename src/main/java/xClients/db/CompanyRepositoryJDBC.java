package xClients.db;

import xClients.entity.CompanyEntity;
import xClients.helper.configHelper;

import java.sql.*;

public class CompanyRepositoryJDBC implements CompanyRepository {

    String user = configHelper.getUserDb();
    String password = configHelper.getPasswordDb();
    String connectionString = configHelper.getConnectionString();

    private static final String SQL_INSERT_EMPLOYEE = "INSERT INTO employee(\"is_active\", \"first_name\", \"last_name\", \"middle_name\", \"phone\", \"email\", \"birthdate\", \"avatar_url\", \"company_id\" ) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    public static final String SQL_SELECT_BY_ID = "SELECT * FROM employee where id = ?";

    private static final String SQL_SELECT_COMPANY_BY_ID = configHelper.getCompanyById();

    Connection connection;

//    public CompanyRepositoryJDBC() throws SQLException {
//        this.connection = DriverManager.getConnection(connectionString, user, password);
//    }
//    public void closeConnection() throws SQLException {
//        if (this.connection != null) {
//            this.connection.close();
//        }
//    }

    @Override
    public CompanyEntity getCompanyById(int id) throws SQLException {
        connection = DriverManager.getConnection(connectionString, user, password);
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COMPANY_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        try {
            if (resultSet.next()) {
                return new CompanyEntity(
                        resultSet.getInt("id"),
                        resultSet.getBoolean("is_active"),
                        resultSet.getTimestamp("create_timestamp"),
                        resultSet.getTimestamp("change_timestamp"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("deleted_at"));
            }
        } finally {
            connection.close();
        }
        return null;

    }


//    @Override
//    public EmployeeEntity getById(int id) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
//        statement.setInt(1, id);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            return new EmployeeEntity(
//                    resultSet.getInt("id"),
//                    resultSet.getBoolean("is_active"),
//                    resultSet.getString("create_timestamp"),
//                    resultSet.getString("change_timestamp"),
//                    resultSet.getString("first_name"),
//                    resultSet.getString("last_name"),
//                    resultSet.getString("middle_name"),
//                    resultSet.getString("phone"),
//                    resultSet.getString("email"),
//                    resultSet.getString("birthdate"),
//                    resultSet.getString("avatar_url"),
//                    resultSet.getInt("company_id"));
//        } else return null;
//    }
//    @Override
//    public int create(boolean is_active, String first_name, String last_name, String middle_name, String phone, String email,
//                      String birthdate, String avatar_url, int company_id) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
//
//        statement.setBoolean(1, is_active);
//        statement.setString(2, first_name);
//        statement.setString(3, last_name);
//        statement.setString(4, middle_name);
//        statement.setString(5, phone);
//        statement.setString(6, email);
//        statement.setDate(7, Date.valueOf(birthdate));
//        statement.setString(8, avatar_url);
//        statement.setInt(9, company_id);
//
//
//        statement.executeUpdate();
//
//        ResultSet keys = statement.getGeneratedKeys();
//        keys.next();
//        return keys.getInt(1);
//    }

}
