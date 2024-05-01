package fr.isitc.tezea.model;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tool")
public class Tool implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "tool")
    private Set<ToolSchedule> schedules;

}
