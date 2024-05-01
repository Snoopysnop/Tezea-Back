package fr.isitc.tezea.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

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

    @Column(name = "concierge")
    private User concierge;

    @Column(name = "site_chief")
    private User siteChief;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "request")
    @Column(name = "work_sites")
    private Set<WorkSite> workSites;

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

    @Column(name = "category")
    private Category category;

}
