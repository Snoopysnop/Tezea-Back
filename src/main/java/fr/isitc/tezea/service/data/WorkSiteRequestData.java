package fr.isitc.tezea.service.data;

import java.util.UUID;

import fr.isitc.tezea.model.WorkSiteRequest;
import fr.isitc.tezea.model.enums.RequestStatus;
import fr.isitc.tezea.service.DTO.WorkSiteRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkSiteRequestData extends WorkSiteRequestDTO {

    private UUID id;
    private RequestStatus status;

    public WorkSiteRequestData(WorkSiteRequest workSiteRequest) {
        super(workSiteRequest);
        this.id = workSiteRequest.getId();
        this.status = workSiteRequest.getStatus();
    }
}
