package xClients.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class Employee {

    private int id;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private String birthdate;
    private String avatar_url;
    private int companyId;

    public Employee(boolean isActive, String firstName, String lastName, String middleName, String phone, String email, String birthdate, String avatar_url,  int companyId) {

        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
        this.email = email;
        this.birthdate = birthdate;
        this.avatar_url = avatar_url;
        this.companyId = companyId;
    }
}
