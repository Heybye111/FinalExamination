package xClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xClients.api.authApi;
import xClients.api.companyApi;
import xClients.api.employeeApi;
import xClients.entity.CompanyEntity;
import xClients.entity.EmployeeEntity;
import xClients.pojo.Company;
import xClients.pojo.CompanyResponse;
import xClients.pojo.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XclientTests {
    static String token;

    ObjectMapper mapper = new ObjectMapper();


    @BeforeAll
    public static void setUp() {
        token = authApi.getToken();
    }


    @Test
    void createNewEmployee() {
        Employee employee = new Employee();
        int newEmployeeId = employeeApi.createNewEmployee(employee, token);
        assertNotNull(newEmployeeId);
        assertNotEquals(0, newEmployeeId);
    }

    @Test
    void getListOfCompanies(){
        List<CompanyResponse> companies = companyApi.getListOfCompanies(true);
        System.out.println(companies);
    }

    @Test
    @DisplayName("Проверка фильтрации компаний по параметру active")
    public void checkActiveFilterParam() throws IOException {
        Company company = mapper.readValue(new File("src/test/java/xClient/resources/Company.json"), Company.class);
        int ActiveCompanyId = companyApi.createCompany(company, token);
        //Проверить что компания активная через БД

        //Создать компанию
        Company SecondCompany = mapper.readValue(new File("src/test/java/xClient/resources/Company.json"), Company.class);
        int NonActiveCompanyId = companyApi.createCompany(company, token);
        //Сделать ее неактиваной
        companyApi.changeCompanyActivity(NonActiveCompanyId, false, token);



        //Вызвать /company с active true
        //Проверить что есть компании только активные
    }


    @Test
    @DisplayName("Проверка создания сотрудника в несуществующей компании")
    public void createUserInNonExistentCompany(){
        //Попробовать тоесть через try удалить компанию с айди 0000
        //Попробовать создать сотрудника у которого companyId будет 0000
    }

    @Test
    @DisplayName("Проверка, что неактивный сотрудни не отображается в списке сотрудников компании")
    public void checkInactiveEmployeeNotDisplayed() {
        //Создать компанию
        //Поместить туда активного сотрудника
        //Поместить туда неактивного сотрудника
        //Получить список сотрудников для компании
        // Проверить что отображается только сотрудник с АКТИВ
    }


    @Test
    @DisplayName("Проверка проставления в бд времени удаления у удаленной компании")
    public void checkDeletedTime() {
        //Создать компанию
        //Удалить компанию
        //Подключить к бд проверить что у удаленной компании поле deletedAt не пустое
    }

}
