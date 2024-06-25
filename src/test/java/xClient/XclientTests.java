package xClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import xClients.api.authApi;
import xClients.api.companyApi;
import xClients.api.employeeApi;
import xClients.entity.CompanyEntity;
import xClients.entity.EmployeeEntity;
import xClients.pojo.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XclientTests {
    static String token;
    static int activeCompanyId;
    static int nonActiveCompanyId;
    ObjectMapper mapper = new ObjectMapper();


    @BeforeAll
    public static void setUp() {
        token = authApi.getToken();
    }

    @AfterEach
    public void tearDown() {
        companyApi.deleteCompany(activeCompanyId, token);
        companyApi.deleteCompany(nonActiveCompanyId, token);
    }

    @Test
    @DisplayName("Проверка фильтрации компаний по параметру active")
    public void checkActiveFilterParam() throws IOException {
        Company company = mapper.readValue
                (new File("src/test/java/xClient/resources/Company.json"), Company.class); //Создать компанию
        activeCompanyId = companyApi.createCompany(company, token);
        //#TODO Проверить что компания активная через БД
        Company secondCompany = mapper.readValue
                (new File("src/test/java/xClient/resources/Company.json"), Company.class); //Создать компанию
        secondCompany.setName("Credit-bank");
        nonActiveCompanyId = companyApi.createCompany(secondCompany, token);
        companyApi.changeCompanyActivity(nonActiveCompanyId, false, token); //Сделать ее неактиваной
        List<CompanyResponse> companies = companyApi.getListOfCompanies(true); //Вызвать /company с active true
        for (CompanyResponse comp : companies) {
            assertTrue(comp.getIsActive()); //Проверить что есть компании только активные
        }
    }
    @Test
    void test()  {

    }


    @Test
    @DisplayName("Проверка создания сотрудника в несуществующей компании")
    public void createUserInNonExistentCompany() throws IOException {
        int companyId = 4543;
        companyApi.deleteCompany(companyId, token);
        Employee employee = mapper.readValue
                (new File("src/test/java/xClient/resources/Employee.json"), Employee.class);
        employee.setCompanyId(companyId);
        CreateEmployeeError response = employeeApi.createNewEmployeeWithNonExistingCompany(employee, token);
        assertEquals("Internal server error", response.getMessage());
        assertEquals(500, response.getStatusCode());
    }

    @Test
    @DisplayName("Проверка, что неактивный сотрудник не отображается в списке сотрудников компании")
    public void checkInactiveEmployeeNotDisplayed() throws IOException {
        Company company = mapper.readValue
                (new File("src/test/java/xClient/resources/Company.json"), Company.class);
        activeCompanyId = companyApi.createCompany(company, token); //Создать компанию
        Employee employee = mapper.readValue
                (new File("src/test/java/xClient/resources/Employee.json"), Employee.class);
        employee.setCompanyId(activeCompanyId)
                .setIsActive(false);
        employeeApi.createNewEmployee(employee, token); //Поместить неактивного сотрудника #TODO Заменить на добавление сотрудника через БД из-за того что тут баг и не создается неактивный сотрудник
        Employee secondEmployee = mapper.readValue
                (new File("src/test/java/xClient/resources/Employee.json"), Employee.class);
        secondEmployee.setCompanyId(activeCompanyId)
                .setIsActive(true);
        employeeApi.createNewEmployee(secondEmployee, token); //Поместить активного сотрудника
        employeeApi.getListOfEmployees(activeCompanyId);  //Получить список сотрудников для компании
        for (EmployeeForCompany emp : employeeApi.getListOfEmployees(activeCompanyId)) {
            assertTrue(emp.getIsActive());
        } // Проверить что отображается только активные сотрудники
    }

    @Test
    @DisplayName("Проверка проставления в бд времени удаления у удаленной компании")
    public void checkDeletedTime() throws IOException {
        Company company = mapper.readValue
                (new File("src/test/java/xClient/resources/Company.json"), Company.class);
        activeCompanyId = companyApi.createCompany(company, token); //Создать компанию
        companyApi.deleteCompany(activeCompanyId, token); //Удалить компанию
        //Подключить к бд проверить что у удаленной компании поле deletedAt не пустое #TODO СДЕЛАТЬ ЭТО
    }
}
