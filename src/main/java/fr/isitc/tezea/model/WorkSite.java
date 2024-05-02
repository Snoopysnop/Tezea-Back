package fr.isitc.tezea.model;

import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.enums.WorkSiteStatus;
import fr.isitc.tezea.model.enums.SatisfactionLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.*;

@Data
@Entity
@Table(name = "work_site")
public class WorkSite implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "work_site_chief")
    private User workSiteChief;

    @ManyToMany
    @Column(name = "staff")
    private Set<User> staff;

    @OneToMany(mappedBy = "workSite")
    @Column(name = "equipment")
    private Set<ToolUsage> equipment;

    @Column(name = "begin")
    private LocalDateTime begin;

    @Column(name = "end")
    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkSiteStatus status;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private WorkSiteRequest request;

    @Enumerated(EnumType.STRING)
    @Column(name = "satisfaction")
    private SatisfactionLevel satisfaction;

    @Column(name = "signature")
    private byte[] signature;

}
