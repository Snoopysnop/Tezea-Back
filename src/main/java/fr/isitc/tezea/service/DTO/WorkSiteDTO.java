package fr.isitc.tezea.service.DTO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.WorkSite;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WorkSiteDTO implements Serializable {

    private Integer workSiteRequest;
    private UUID workSiteChief;
    private LocalDateTime begin;
    private LocalDateTime end;
    private Set<UUID> staff = new HashSet<>();
    private Map<String, Integer> equipments = new HashMap<>();

    protected WorkSiteDTO() {
    }

    public WorkSiteDTO(WorkSite workSite) {
        this.workSiteRequest = workSite.getRequest() == null ? null : workSite.getRequest().getId();
        this.workSiteChief = workSite.getWorkSiteChief() == null ? null : workSite.getWorkSiteChief().getId();
        this.begin = workSite.getBegin();
        this.end = workSite.getEnd();
        if(workSite.getStaff() != null){
            workSite.getStaff().forEach(employee -> staff.add(employee.getId()));
        }
        if(workSite.getEquipments() != null) {
            workSite.getEquipments().forEach(tool -> equipments.put(tool.getTool().getName(), tool.getQuantity()));
        }
        
    }

}
