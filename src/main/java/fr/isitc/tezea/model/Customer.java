package fr.isitc.tezea.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.enums.Civility;
import fr.isitc.tezea.model.enums.CustomerStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "civility")
    private Civility civility;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private int postalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CustomerStatus status;

    @Column(name = "company")
    private String company;

    @OneToMany(mappedBy = "customer")
    private Set<WorkSiteRequest> requests;

}
