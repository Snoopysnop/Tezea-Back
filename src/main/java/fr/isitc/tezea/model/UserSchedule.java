package fr.isitc.tezea.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "user_schedule")
public class UserSchedule implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "work_site_id")
    private WorkSite workSite;

    @Column(name = "begin")
    private LocalDateTime begin;

    @Column(name = "end")
    private LocalDateTime end;

}
