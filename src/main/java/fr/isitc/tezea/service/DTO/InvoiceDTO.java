package fr.isitc.tezea.service.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class InvoiceDTO implements Serializable {
    
    private String title;
    private String description;
    private int amount;

    protected InvoiceDTO() {

    }
}
