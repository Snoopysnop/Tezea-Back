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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "work_site")
public class WorkSite {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "work_site_chief")
    private User workSiteChief;

    @OneToMany(mappedBy = "work_site_id")
    @JoinColumn(name = "staff")
    private Set<UserSchedule> staff;

    @OneToMany(mappedBy = "work_site_id")
    @JoinColumn(name = "equipment")
    private Set<ToolSchedule> equipment;

    @Column(name = "begin")
    private String begin; // quel librairie pour date?

    @Column(name = "end")
    private String end;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkSiteStatus status;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private int requestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "satisfaction")
    private SatisfactionLevel satisfaction;

}
