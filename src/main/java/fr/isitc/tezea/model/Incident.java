package fr.isitc.tezea.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.isitc.tezea.model.enums.IncidentLevel;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "incident")
public class Incident implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "work_site_id")
    private WorkSite workSite;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private IncidentLevel level;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "evidences", columnDefinition="LONGBLOB")
    @Lob
    @ElementCollection
    private List<byte[]> evidences = new ArrayList<>();

    protected Incident() {

    }

    public Incident(WorkSite workSite, IncidentLevel level, String title, String description) {
        this.workSite = workSite;
        this.level = level;
        this.title = title;
        this.description = description;
    }

    public void addEvidence(byte[] evidence){
        this.evidences.add(evidence);
    }


}
