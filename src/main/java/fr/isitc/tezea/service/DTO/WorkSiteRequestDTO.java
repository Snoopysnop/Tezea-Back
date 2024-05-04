package fr.isitc.tezea.service.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import fr.isitc.tezea.model.WorkSiteRequest;
import fr.isitc.tezea.model.enums.Category;
import fr.isitc.tezea.model.enums.Emergency;
import fr.isitc.tezea.model.enums.Service;
import lombok.Data;

@Data
public class WorkSiteRequestDTO implements Serializable {

    private UUID concierge;
    private UUID siteChief;
    private UUID customer;
    private String city;
    private Service serviceType;
    private String description;
    private Emergency emergency;
    private String title;
    private Category category;
    private boolean removal;
    private boolean delivery;
    private boolean removalRecycling;
    private boolean chronoQuote;
    private LocalDateTime date;
    private LocalDateTime hourDeparture;
    private LocalDateTime hourArrival;
    private LocalDateTime hourReturnDeposit;
    private int weightEstimate;
    private int volumeEstimate;
    private String provider;
    private String tezeaAffectation;

    protected WorkSiteRequestDTO() {
    }

    public WorkSiteRequestDTO(WorkSiteRequestDTO workSiteRequestDTO) {
        this.concierge = workSiteRequestDTO.getConcierge();
        this.siteChief = workSiteRequestDTO.getSiteChief();
        this.customer = workSiteRequestDTO.getCustomer();
        this.city = workSiteRequestDTO.getCity();
        this.serviceType = workSiteRequestDTO.getServiceType();
        this.description = workSiteRequestDTO.getDescription();
        this.emergency = workSiteRequestDTO.getEmergency();
        this.title = workSiteRequestDTO.getTitle();
        this.category = workSiteRequestDTO.getCategory();
        this.removal = workSiteRequestDTO.isRemoval();
        this.delivery = workSiteRequestDTO.isDelivery();
        this.removalRecycling = workSiteRequestDTO.isRemovalRecycling();
        this.chronoQuote = workSiteRequestDTO.isChronoQuote();
        this.date = workSiteRequestDTO.getDate();
        this.hourDeparture = workSiteRequestDTO.getHourDeparture();
        this.hourArrival = workSiteRequestDTO.getHourArrival();
        this.hourReturnDeposit = workSiteRequestDTO.getHourReturnDeposit();
        this.weightEstimate = workSiteRequestDTO.getWeightEstimate();
        this.volumeEstimate = workSiteRequestDTO.getVolumeEstimate();
        this.provider = workSiteRequestDTO.getProvider();
        this.tezeaAffectation = workSiteRequestDTO.getTezeaAffectation();
    }

    public WorkSiteRequestDTO(WorkSiteRequest workSiteRequest) {
        this.category = workSiteRequest.getCategory();
        this.city = workSiteRequest.getCity();
        this.concierge = workSiteRequest.getConcierge().getId();
        this.customer = workSiteRequest.getCustomer().getId();
        this.date = workSiteRequest.getDate();
        this.description = workSiteRequest.getDescription();
        this.chronoQuote = workSiteRequest.isChronoQuote();
        this.delivery = workSiteRequest.isDelivery();
        this.emergency = workSiteRequest.getEmergency();
        this.hourArrival = workSiteRequest.getHourArrival();
        this.hourDeparture = workSiteRequest.getHourDeparture();
        this.hourReturnDeposit = workSiteRequest.getHourReturnDeposit();
        this.provider = workSiteRequest.getProvider();
        this.removal = workSiteRequest.isRemoval();
        this.removalRecycling = workSiteRequest.isRemovalRecycling();
        this.serviceType = workSiteRequest.getServiceType();
        this.siteChief = workSiteRequest.getSiteChief().getId();
        this.tezeaAffectation = workSiteRequest.getTezeaAffectation();
        this.title = workSiteRequest.getTitle();
        this.volumeEstimate = workSiteRequest.getVolumeEstimate();
        this.weightEstimate = workSiteRequest.getWeightEstimate();
    }
}
