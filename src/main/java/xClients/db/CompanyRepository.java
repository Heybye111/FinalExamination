package xClients.db;

import xClients.entity.CompanyEntity;

import java.sql.SQLException;

public interface CompanyRepository {
    CompanyEntity getCompanyById(int id) throws SQLException;
}