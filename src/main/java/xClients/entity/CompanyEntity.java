package xClients.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class CompanyEntity {
    private int id;
    private boolean is_active;
    private String create_timestamp;
    private String change_timestamp;
    private String name;
    private String description;
    private String deleted_at;
}
