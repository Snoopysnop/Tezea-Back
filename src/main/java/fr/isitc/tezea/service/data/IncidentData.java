package fr.isitc.tezea.service.data;

import java.util.List;
import java.util.UUID;

import fr.isitc.tezea.model.Incident;
import fr.isitc.tezea.service.DTO.IncidentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class IncidentData extends IncidentDTO {

    private UUID id;
    private List<byte[]> evidences;

    public IncidentData(Incident incident){
        super(incident);
        this.id = incident.getId();
        this.evidences = incident.getEvidences();
    }
    
}
