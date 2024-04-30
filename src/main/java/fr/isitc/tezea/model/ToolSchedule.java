package fr.isitc.tezea.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ToolSchedule {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "construction_site_id")
    private int constructionSiteId;

    @Column(name = "begin")
    private String begin;

    @Column(name = "end")
    private String end;

    @Column(name = "quantity")
    private int quantity;

}
