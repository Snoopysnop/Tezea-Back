package fr.isitc.tezea.service.DTO;

import java.io.Serializable;

import fr.isitc.tezea.model.enums.IncidentLevel;
import lombok.Data;

@Data
public class IncidentDTO implements Serializable {
    
    private IncidentLevel level;
    private String title;
    private String description;

    protected IncidentDTO() {
        
    }

}
