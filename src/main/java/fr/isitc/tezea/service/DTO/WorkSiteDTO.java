package fr.isitc.tezea.service.DTO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.ToolUsage;
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
    private String address;
    private String title;

    protected WorkSiteDTO() {
    }

    public WorkSiteDTO(WorkSite workSite, Set<ToolUsage> equipments) {
        this.workSiteRequest = workSite.getRequest() == null ? null : workSite.getRequest().getId();
        this.workSiteChief = workSite.getWorkSiteChief() == null ? null : workSite.getWorkSiteChief().getId();
        this.begin = workSite.getBegin();
        this.end = workSite.getEnd();
        if (workSite.getStaff() != null) {
            workSite.getStaff().forEach(employee -> staff.add(employee.getId()));
        }
        this.equipments = ToolUsage.toMap(equipments);
        this.address = workSite.getAddress();
        this.title = workSite.getTitle();

    }

}
