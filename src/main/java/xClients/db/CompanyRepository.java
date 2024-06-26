package xClients.db;

import io.qameta.allure.Step;
import xClients.entity.CompanyEntity;

import java.sql.SQLException;

public interface CompanyRepository {
    @Step("Получить компанию из БД")
    CompanyEntity getCompanyById(int id) throws SQLException;
}