package fr.isitc.tezea.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import fr.isitc.tezea.model.enums.Category;
import fr.isitc.tezea.model.enums.Emergency;
import fr.isitc.tezea.model.enums.RequestStatus;
import fr.isitc.tezea.model.enums.Service;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

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

    @Column(name = "date")
    @JsonFormat(pattern = "yyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;

    @Column(name = "hour_departure")
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime hourDeparture;

    @Column(name = "hour_arrival")
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime hourArrival;

    @Column(name = "hour-return_deposit")
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime hourReturnDeposit;

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

    public WorkSiteRequest(User concierge, User siteChief, Customer customer, String city, Service serviceType,
            String description, Emergency emergency, RequestStatus status, String title, Category category,
            boolean removal, boolean delivery, boolean removalRecycling, boolean chronoQuote, LocalDateTime date,
            LocalDateTime hourDeparture, LocalDateTime hourArrival, LocalDateTime hourReturnDeposit, int weightEstimate,
            int volumeEstimate, String provider, String tezeaAffectation) {
        this.concierge = concierge;
        this.siteChief = siteChief;
        this.customer = customer;
        this.city = city;
        this.serviceType = serviceType;
        this.description = description;
        this.emergency = emergency;
        this.status = status;
        this.title = title;
        this.category = category;
        this.removal = removal;
        this.delivery = delivery;
        this.removalRecycling = removalRecycling;
        this.chronoQuote = chronoQuote;
        this.date = date;
        this.hourDeparture = hourDeparture;
        this.hourArrival = hourArrival;
        this.hourReturnDeposit = hourReturnDeposit;
        this.weightEstimate = weightEstimate;
        this.volumeEstimate = volumeEstimate;
        this.provider = provider;
        this.tezeaAffectation = tezeaAffectation;
    }

}
