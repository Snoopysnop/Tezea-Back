package fr.isitc.tezea.service.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.enums.SatisfactionLevel;
import fr.isitc.tezea.model.enums.WorkSiteStatus;
import lombok.Data;

@Data
public class WorkSiteAndRequestData implements Serializable {

    private WorkSiteRequestData workSiteRequest;
    private UUID workSiteChief;
    private LocalDateTime begin;
    private LocalDateTime end;
    private Set<UUID> staff = new HashSet<>();
    private Map<String, Integer> equipments = new HashMap<>();
    private UUID id;
    private SatisfactionLevel satisfaction;
    private WorkSiteStatus status;
    private String signature;
    private boolean hasIncidents;

    
    public WorkSiteAndRequestData(WorkSiteData worksite, WorkSiteRequestData request, boolean hasIncidents) {
        this.workSiteRequest = request;

        this.workSiteChief = worksite.getWorkSiteChief();
        this.begin = worksite.getBegin();
        this.end = worksite.getEnd();
        this.staff = worksite.getStaff();
        this.equipments = worksite.getEquipments();
        this.id = worksite.getId();
        this.satisfaction = worksite.getSatisfaction();
        this.status = worksite.getStatus();
        this.signature = worksite.getSignature();
        this.hasIncidents = hasIncidents;
        
    }

    
}
