package fr.isitc.tezea.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import fr.isitc.tezea.model.enums.Category;
import fr.isitc.tezea.model.enums.Emergency;
import fr.isitc.tezea.model.enums.RequestStatus;
import fr.isitc.tezea.model.enums.Service;
import fr.isitc.tezea.service.DTO.WorkSiteRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "work_site_request")
public class WorkSiteRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "concierge_id")
    private User concierge;

    @ManyToOne
    @JoinColumn(name = "siteChief_id")
    private User siteChief;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "request")
    @Column(name = "work_sites")
    private Set<WorkSite> workSites = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private Service serviceType;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "emergency")
    private Emergency emergency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "removal")
    private boolean removal;

    @Column(name = "delivery")
    private boolean delivery;

    @Column(name = "removal_recycling")
    private boolean removalRecycling;

    @Column(name = "chrono_quote")
    private boolean chronoQuote;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "estimatedDate")
    private LocalDateTime estimatedDate;

    @Column(name = "weight_estimate")
    private int weightEstimate;

    @Column(name = "volume_estimate")
    private int volumeEstimate;

    @Column(name = "provider")
    private String provider;

    @Column(name = "tezea_affectation")
    private String tezeaAffectation;

    protected WorkSiteRequest() {
    }

    public WorkSiteRequest(User concierge, User siteChief, Customer customer, WorkSiteRequestDTO workSiteRequestDTO) {
        this.concierge = concierge;
        this.siteChief = siteChief;
        this.customer = customer;
        this.city = workSiteRequestDTO.getCity();
        this.serviceType = workSiteRequestDTO.getServiceType();
        this.description = workSiteRequestDTO.getDescription();
        this.emergency = workSiteRequestDTO.getEmergency();
        this.status = RequestStatus.ToComplete;
        this.title = workSiteRequestDTO.getTitle();
        this.category = workSiteRequestDTO.getCategory();
        this.removal = workSiteRequestDTO.isRemoval();
        this.delivery = workSiteRequestDTO.isDelivery();
        this.removalRecycling = workSiteRequestDTO.isRemovalRecycling();
        this.chronoQuote = workSiteRequestDTO.isChronoQuote();
        this.estimatedDate = workSiteRequestDTO.getEstimatedDate();
        this.creationDate = LocalDateTime.now();
        this.weightEstimate = workSiteRequestDTO.getWeightEstimate();
        this.volumeEstimate = workSiteRequestDTO.getWeightEstimate();
        this.provider = workSiteRequestDTO.getProvider();
        this.tezeaAffectation = workSiteRequestDTO.getTezeaAffectation();
    }

}
