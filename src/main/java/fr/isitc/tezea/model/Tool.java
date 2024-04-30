package fr.isitc.tezea.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Tool {

    @Id
    @ManyToOne
    @JoinColumn(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "name")
    @JoinColumn(name = "schedules")
    private Set<UserSchedule> schedules;

}
