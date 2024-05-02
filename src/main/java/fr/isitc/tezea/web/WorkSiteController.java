package fr.isitc.tezea.web;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.IncidentDAO;
import fr.isitc.tezea.DAO.InvoiceDAO;
import fr.isitc.tezea.DAO.WorkSiteDAO;
import fr.isitc.tezea.model.Incident;
import fr.isitc.tezea.model.Invoice;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.service.DTO.IncidentDTO;
import fr.isitc.tezea.service.DTO.InvoiceDTO;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/worksites")
public class WorkSiteController {

    private static final Logger LOGGER = Logger.getLogger(WorkSiteController.class.getName());

    @Autowired
    private WorkSiteDAO workSiteDAO;

    @Autowired
    private IncidentDAO incidentDAO;

    @Autowired
    private InvoiceDAO invoiceDAO;

    private WorkSite findWorkSite(UUID id){
        Optional<WorkSite> workSite = workSiteDAO.findById(id);
        if(!workSite.isPresent()) {
            LOGGER.info("WorkSite " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return workSite.get();
    }

    @RequestMapping(value = "/{id}/incident", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Apply incident to worksite")
    public void addIncident(@PathVariable UUID id, @RequestBody IncidentDTO incidentDTO){
        LOGGER.info("REST request to declare incident " + incidentDTO + " to workSite " + id);

        WorkSite workSite = findWorkSite(id);

        Incident incident = new Incident(workSite, incidentDTO.getLevel(), incidentDTO.getTitle(), incidentDTO.getDescription());
        workSite.addIncident(incident);
        workSiteDAO.save(workSite);
        incidentDAO.save(incident);
    }

    @RequestMapping(value = "/{id}/invoice", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description ="Apply invoice to worksite")
    public void addInvoice(@PathVariable UUID id, @RequestPart("invoice") InvoiceDTO invoiceDTO, @RequestPart("file") MultipartFile file){
        LOGGER.info("REST request to apply invoice " + invoiceDTO + " to workSite " + id);

        WorkSite workSite = findWorkSite(id);

        try {
            Invoice invoice = new Invoice(workSite, file.getBytes(), invoiceDTO.getTitle(), invoiceDTO.getDescription(), invoiceDTO.getAmount());
            workSite.addInvoice(invoice);
            workSiteDAO.save(workSite);
            invoiceDAO.save(invoice);

        } catch (IOException e) {
            LOGGER.warning("Access error on uploaded file");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
