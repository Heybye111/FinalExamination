package xClients.db;

import xClients.entity.CompanyEntity;
import xClients.helper.configHelper;

import java.sql.*;

public class CompanyRepositoryJDBC implements CompanyRepository {
    String user = configHelper.getUserDb();
    String password = configHelper.getPasswordDb();
    String connectionString = configHelper.getConnectionString();
    private static final String SQL_SELECT_COMPANY_BY_ID = configHelper.getCompanyById();
    Connection connection;

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
}
