package fr.isitc.tezea.service.data;

import java.util.UUID;

import fr.isitc.tezea.model.Invoice;
import fr.isitc.tezea.service.DTO.InvoiceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InvoiceData extends InvoiceDTO {
    
    private UUID id;
    private byte[] invoice;
    private String fileExtension;

    public InvoiceData(Invoice invoice) {
        super(invoice);
        this.id = invoice.getId();
        this.invoice = invoice.getInvoice();
        this.fileExtension = invoice.getFileExtension();
    }
}
