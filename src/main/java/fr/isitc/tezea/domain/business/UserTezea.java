package fr.isitc.tezea.domain.business;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public abstract class UserTezea {

    private long id;

    private String firstname;
    private String lastname;

    protected UserTezea() {
        
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
        
}
