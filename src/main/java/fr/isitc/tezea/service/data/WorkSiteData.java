package fr.isitc.tezea.service.data;

import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.ToolUsage;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.model.enums.Category;
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
    private Category category;

    public WorkSiteData(WorkSite workSite, Set<ToolUsage> equipments) {
        super(workSite, equipments);
        this.id = workSite.getId();
        this.satisfaction = workSite.getSatisfaction();
        this.status = workSite.getStatus();
        this.signature = workSite.getSignature();
        this.comment = workSite.getComment();
        this.category = workSite.getRequest().getCategory();

    }
}
