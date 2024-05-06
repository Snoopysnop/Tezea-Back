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
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "work_site_id")
    private WorkSite workSite;

    @Column(name = "invoice")
    private byte[] invoice;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private int amount;

    @Column(name = "file_extension")
    private String fileExtension;

    protected Invoice() {
        
    }

    public Invoice(WorkSite workSite, byte[] invoice, String title, String description, int amount, String fileExtension) {
        this.workSite = workSite;
        this.invoice = invoice;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.fileExtension = fileExtension;
    }
    
}
