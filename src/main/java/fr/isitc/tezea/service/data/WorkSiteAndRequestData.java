package fr.isitc.tezea.service.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.model.WorkSiteRequest;
import lombok.Data;

@Data
public class WorkSiteAndRequestData implements Serializable {

    private Set<WorkSiteRequestData> requests = new HashSet<>();
    private Set<WorkSiteData> workSites = new HashSet<>();

    public void addRequest(WorkSiteRequest request) {
        this.requests.add(new WorkSiteRequestData(request));
    }

    public void addWorkSite(WorkSite workSite) {
        this.workSites.add(new WorkSiteData(workSite));
    }

    
}
