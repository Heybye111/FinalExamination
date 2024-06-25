package xClients.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class CompanyEntity {
    private int id;
    private Boolean is_active;
    private Timestamp create_timestamp;
    private Timestamp change_timestamp;
    private String name;
    private String description;
    private Timestamp deleted_at;
}


