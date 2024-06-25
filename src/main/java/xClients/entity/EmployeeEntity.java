package xClients.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class EmployeeEntity {
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


}
