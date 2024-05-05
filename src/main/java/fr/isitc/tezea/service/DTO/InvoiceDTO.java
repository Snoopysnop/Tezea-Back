package fr.isitc.tezea.service.DTO;

import java.io.Serializable;

import fr.isitc.tezea.model.Invoice;
import lombok.Data;

@Data
public class InvoiceDTO implements Serializable {
    
    private String title;
    private String description;
    private int amount;

    protected InvoiceDTO() {

    }

    public InvoiceDTO(Invoice invoice){
        this.title = invoice.getTitle();
        this.description = invoice.getDescription();
        this.amount = invoice.getAmount();
    }
}
