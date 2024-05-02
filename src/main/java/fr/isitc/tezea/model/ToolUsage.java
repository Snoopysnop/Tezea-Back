package fr.isitc.tezea.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tool_usage")
public class ToolUsage implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tool")
    private Tool tool;

    @ManyToOne
    @JoinColumn(name = "work_site_id")
    private WorkSite workSite;

    @Column(name = "quantity")
    private int quantity;

}
