package fr.isitc.tezea.domain.business;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class Contact extends User {
    
    private Civility civility;
    private String phoneNumber;
    private String email;
    private String address;
    private int postalCode;
    private String city;

    protected Contact(){

    }

}