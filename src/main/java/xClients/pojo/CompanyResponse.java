package xClients.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CompanyResponse {

    private int id;
    private boolean isActive;
    private String createDateTime;
    private String lastChangeDateTime;
    private String name;
    private String description;
    private String deleted_at;

}
