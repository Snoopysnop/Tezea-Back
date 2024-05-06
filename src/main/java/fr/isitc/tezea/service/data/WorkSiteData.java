package fr.isitc.tezea.service.data;

import java.util.UUID;

import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.model.enums.SatisfactionLevel;
import fr.isitc.tezea.model.enums.WorkSiteStatus;
import fr.isitc.tezea.service.DTO.WorkSiteDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkSiteData extends WorkSiteDTO {

    private UUID id;
    private SatisfactionLevel satisfaction;
    private WorkSiteStatus status;
    private String signature;
    private String comment;

    public WorkSiteData(WorkSite workSite) {
        super(workSite);
        this.id = workSite.getId();
        this.satisfaction = workSite.getSatisfaction();
        this.status = workSite.getStatus();
        this.signature = workSite.getSignature();
        this.comment = workSite.getComment();

    }
}
