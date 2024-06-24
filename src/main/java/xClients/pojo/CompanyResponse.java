package xClients.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CompanyResponse {

    private int id;
    private String createDateTime;
    private String lastChangedDateTime;
    private String name;
    private String description;
    private String deletedAt;
    private Boolean isActive;
}
