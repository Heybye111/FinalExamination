package xClients.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class EmployeeForCompany {

    public int id;
    public Boolean isActive;
    public Date createDateTime;
    public Date lastChangedDateTime;
    public String firstName;
    public String lastName;
    public String middleName;
    public String phone;
    public Object email;
    public String birthdate;
    public String avatar_url;
    public int companyId;

}
