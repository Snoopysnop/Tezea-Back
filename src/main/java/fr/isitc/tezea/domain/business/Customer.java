package fr.isitc.tezea.domain.business;

import lombok.Data;

@Data
public class Customer {
    
    private CustomerStatus status;
    private String business;
    private Contact contact;

}
