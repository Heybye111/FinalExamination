package xClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import xClients.api.authApi;
import xClients.api.companyApi;
import xClients.api.employeeApi;
import xClients.db.CompanyRepository;
import xClients.db.CompanyRepositoryJDBC;
import xClients.db.EmployeeRepository;
import xClients.db.EmployeeRepositoryJDBC;
import xClients.entity.CompanyEntity;
import xClients.pojo.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты на сервис xClients")
@Owner("Heybye")
public class XclientTests {
    static String token;
    static int activeCompanyId;
    static int nonActiveCompanyId;
    ObjectMapper mapper = new ObjectMapper();
    CompanyRepository companyRepo = new CompanyRepositoryJDBC();
    EmployeeRepository employeeRepo = new EmployeeRepositoryJDBC();


    @BeforeAll
    public static void setUp(){
        token = authApi.getToken();
    }

    @AfterEach
    public void tearDown() {
        companyApi.deleteCompany(activeCompanyId, token);
        companyApi.deleteCompany(nonActiveCompanyId, token);
    }

    @Test
    @DisplayName("Проверка фильтрации компаний")
    @Description("Сценарий 1. Проверка, что фильтрация компаний происходит по  по параметру active")
    @Severity(SeverityLevel.CRITICAL)
    public void checkActiveFilterParam() throws IOException, SQLException {
        Company company = mapper.readValue(new File("src/test/java/xClient/resources/Company.json"), Company.class);
        activeCompanyId = companyApi.createCompany(company, token);
        CompanyEntity activeCompany = companyRepo.getCompanyById(activeCompanyId);
        assertTrue(activeCompany.getIs_active());
        Company secondCompany = mapper.readValue(new File("src/test/java/xClient/resources/Company.json"), Company.class);
        secondCompany.setName("Credit-bank");
        nonActiveCompanyId = companyApi.createCompany(secondCompany, token);
        companyApi.changeCompanyActivity(nonActiveCompanyId, false, token);
        CompanyEntity nonActiveCompany = companyRepo.getCompanyById(nonActiveCompanyId);
        assertFalse(nonActiveCompany.getIs_active());
        List<CompanyResponse> companies = companyApi.getListOfCompanies(true);
        for (CompanyResponse comp : companies) {
            assertTrue(comp.getIsActive());
        }
    }

    @Test
    @DisplayName("Проверка создания сотрудника в несуществующей компании")
    @Description("Сценарий 2. Проверяем, что сотрудник не может быть создан в несуществующей компании")
    @Severity(SeverityLevel.CRITICAL)
    public void createUserInNonExistentCompany() throws IOException {
        int companyId = 4543;
        companyApi.deleteCompany(companyId, token);
        Employee employee = mapper.readValue(new File("src/test/java/xClient/resources/Employee.json"), Employee.class);
        employee.setCompanyId(companyId);
        CreateEmployeeError response = employeeApi.createNewEmployeeWithNonExistingCompany(employee, token);
        assertEquals("Internal server error", response.getMessage());
        assertEquals(500, response.getStatusCode());
    }

    @Test
    @DisplayName("Проверка, что неактивный сотрудник не отображается в списке сотрудников компании")
    @Description("Сценарий 3. ")
    @Severity(SeverityLevel.CRITICAL)
    public void checkInactiveEmployeeNotDisplayed() throws IOException, SQLException {
        Company company = mapper.readValue(new File("src/test/java/xClient/resources/Company.json"), Company.class);
        activeCompanyId = companyApi.createCompany(company, token);//Создать компанию
        employeeRepo.createEmployee(false, "Ivan", "Semenov", "Andreevich",
                "83213213", "tata@mail.ru", "2001-04-08", "http://photo.ru/asd", activeCompanyId);
        // Пришлось прибегнуть к созданию сотрудника через БД, так как в методе создания сотрудника баг
        Employee secondEmployee = mapper.readValue(new File("src/test/java/xClient/resources/Employee.json"), Employee.class);
        secondEmployee.setCompanyId(activeCompanyId).setIsActive(true);
        employeeApi.createNewEmployee(secondEmployee, token);
        employeeApi.getListOfEmployees(activeCompanyId);
        for (EmployeeForCompany emp : employeeApi.getListOfEmployees(activeCompanyId)) {
            assertTrue(emp.getIsActive());
        }
    }

    @Test
    @DisplayName("Проверка проставления в бд времени удаления у удаленной компании")
    @Description("Сценарий 4. ")
    @Severity(SeverityLevel.NORMAL)
    public void checkDeletedTime() throws IOException, SQLException {
        Company company = mapper.readValue(new File("src/test/java/xClient/resources/Company.json"), Company.class);
        activeCompanyId = companyApi.createCompany(company, token);
        companyApi.deleteCompany(activeCompanyId, token);
        CompanyEntity deletedCompany = companyRepo.getCompanyById(activeCompanyId);
        assertNotNull(deletedCompany.getDeleted_at());
    }
}
