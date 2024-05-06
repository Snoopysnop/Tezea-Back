package fr.isitc.tezea.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.ToolUsageDAO;
import fr.isitc.tezea.DAO.UserDAO;
import fr.isitc.tezea.model.ToolUsage;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.DAO.IncidentDAO;
import fr.isitc.tezea.DAO.InvoiceDAO;
import fr.isitc.tezea.DAO.ToolDAO;
import fr.isitc.tezea.DAO.WorkSiteDAO;
import fr.isitc.tezea.DAO.WorkSiteRequestDAO;

import fr.isitc.tezea.model.Incident;
import fr.isitc.tezea.model.Tool;
import fr.isitc.tezea.model.Invoice;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.model.WorkSiteRequest;
import fr.isitc.tezea.model.enums.SatisfactionLevel;
import fr.isitc.tezea.model.enums.WorkSiteStatus;
import fr.isitc.tezea.service.DTO.IncidentDTO;
import fr.isitc.tezea.service.DTO.WorkSiteDTO;
import fr.isitc.tezea.service.data.IncidentData;
import fr.isitc.tezea.service.data.InvoiceData;
import fr.isitc.tezea.service.data.UserData;
import fr.isitc.tezea.service.data.WorkSiteData;
import fr.isitc.tezea.service.DTO.InvoiceDTO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/worksites")
public class WorkSiteController {

    private static final Logger LOGGER = Logger.getLogger(WorkSiteController.class.getName());

    @Autowired
    private WorkSiteDAO workSiteDAO;

    @Autowired
    private WorkSiteRequestDAO workSiteRequestDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ToolDAO toolDAO;

    @Autowired
    private ToolUsageDAO toolUsageDAO;

    @Autowired
    private IncidentDAO incidentDAO;

    @Autowired
    private InvoiceDAO invoiceDAO;

    private WorkSite findWorkSite(UUID id) {
        Optional<WorkSite> workSite = workSiteDAO.findById(id);
        if (!workSite.isPresent()) {
            LOGGER.info("WorkSite " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return workSite.get();
    }

    private Map<String, Integer> getWorkSiteEquipments(WorkSite workSite) {
        Map<String, Integer> equipments = new HashMap<>();
        for (ToolUsage tu : toolUsageDAO.findByWorkSite(workSite)) {
            equipments.put(tu.getTool().getName(), tu.getQuantity());
        }
        return equipments;
    }

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Returns all work sites")
    public List<WorkSiteData> findAll() {
        LOGGER.info("REST request to get all work sites");

        List<WorkSiteData> workSites = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.ASC, "begin");

        for (WorkSite workSite : workSiteDAO.findAll(sort)) {
            WorkSiteData data = new WorkSiteData(workSite);
            data.setEquipments(getWorkSiteEquipments(workSite));
            workSites.add(data);
        }

        return workSites;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Returns the work site {id}")
    public WorkSiteData findById(@PathVariable UUID id) {

        WorkSite worksite = findWorkSite(id);
        WorkSiteData data = new WorkSiteData(worksite);

        data.setEquipments(getWorkSiteEquipments(worksite));
        return data;
    }

    @RequestMapping(value = "/{id}/allUsers", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Returns all the users of a work site")
    public Set<UserData> findAllUsers(@PathVariable UUID id) {
        LOGGER.info("REST request to get worksite chief and staff of worksite " + id);

        WorkSite workSite = findWorkSite(id);

        Set<UserData> allUsers = new HashSet<>();
        User workSiteChief = workSite.getWorkSiteChief();
        Set<User> staff = workSite.getStaff();
        for (User employee : staff) {
            allUsers.add(new UserData(employee));
        }
        allUsers.add(new UserData(workSiteChief));

        return allUsers;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Create a work site")
    public WorkSiteData create(@RequestBody WorkSiteDTO workSiteDTO) {
        LOGGER.info("REST request to create work site " + workSiteDTO);

        Set<User> staff = new HashSet<>();

        for (UUID employeeID : workSiteDTO.getStaff()) {
            Optional<User> user = userDAO.findById(employeeID);
            if (!user.isPresent()) {
                LOGGER.info("user " + employeeID + " not found");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            staff.add(user.get());
        }

        Optional<User> workSiteChief = userDAO.findById(workSiteDTO.getWorkSiteChief());
        if (!workSiteChief.isPresent()) {
            LOGGER.info("user " + workSiteDTO.getWorkSiteChief() + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<WorkSiteRequest> workSiteRequest = workSiteRequestDAO.findById(workSiteDTO.getWorkSiteRequest());
        if (!workSiteRequest.isPresent()) {
            LOGGER.info("workSiteRequest " + workSiteDTO.getWorkSiteRequest() + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        WorkSite newWorkSite = new WorkSite(
                workSiteDTO.getBegin(),
                workSiteDTO.getEnd(),
                workSiteChief.get(),
                staff,
                workSiteDTO.getAddress(),
                workSiteDTO.getTitle(),
                workSiteRequest.get());

        // create ToolUsages
        Set<ToolUsage> equipments = new HashSet<>();
        for (String toolName : workSiteDTO.getEquipments().keySet()) {
            Optional<Tool> tool = toolDAO.findById(toolName);
            if (!tool.isPresent()) {
                LOGGER.info("tool " + toolName + " not found");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            equipments.add(new ToolUsage(tool.get(), newWorkSite, workSiteDTO.getEquipments().get(toolName)));
        }

        workSiteDAO.save(newWorkSite);

        // save ToolUsages
        for (ToolUsage toolUsage : equipments) {
            toolUsageDAO.save(toolUsage);
        }

        return new WorkSiteData(newWorkSite);
    }

    @RequestMapping(value = "/{id}/upload_comment", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Set comment")
    public void uploadComment(@PathVariable UUID id, @RequestBody String comment) {

        WorkSite workSite = findWorkSite(id);

        try {
            workSite.setComment(comment);
            workSiteDAO.save(workSite);
        } catch (Exception e) {
            LOGGER.info("Can't upload comment for work site " + id);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/upload_signature_and_satisfaction", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Set signature and satisfaction")
    public void uploadSignatureAndSatisfaction(@PathVariable UUID id, @RequestParam("image") MultipartFile signature,
            @RequestParam("satisfaction") SatisfactionLevel satisfaction) {

        WorkSite workSite = findWorkSite(id);

        try {
            workSite.setSignature(signature.getBytes());
            workSite.setSatisfaction(satisfaction);

            workSiteDAO.save(workSite);
        } catch (Exception e) {
            LOGGER.info("Can't upload signature for work site " + id);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/update_status", method = RequestMethod.PATCH)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Update status of a work site")
    public void updateStatus(@PathVariable UUID id, @RequestParam("status") WorkSiteStatus status) {

        WorkSite workSite = findWorkSite(id);

        workSite.setStatus(status);
        workSiteDAO.save(workSite);
    }

    @RequestMapping(value = "/{id}/incident", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Incident" }, description = "Apply incident to worksite")
    public IncidentData addIncident(@PathVariable UUID id, @RequestBody IncidentDTO incidentDTO) {
        LOGGER.info("REST request to declare incident " + incidentDTO + " to workSite " + id);

        WorkSite workSite = findWorkSite(id);

        Incident incident = new Incident(workSite, incidentDTO.getLevel(), incidentDTO.getTitle(),
                incidentDTO.getDescription());
        workSite.addIncident(incident);
        workSiteDAO.save(workSite);
        incidentDAO.save(incident);

        return new IncidentData(incident);

    }

    @RequestMapping(value = "/incident/{id}/evidences", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Incident" }, description = "Add evidence to an incident")
    public IncidentData addEvidence(@PathVariable UUID id, @RequestParam MultipartFile evidence) {
        LOGGER.info("REST request to add evidence to incident " + id);

        Optional<Incident> incident = incidentDAO.findById(id);
        if (!incident.isPresent()) {
            LOGGER.info("incident " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Incident foundIncident = incident.get();
        try {
            foundIncident.addEvidence(evidence.getBytes());
        } catch (IOException e) {
            LOGGER.warning("Access error on uploaded file");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        incidentDAO.save(foundIncident);
        return new IncidentData(foundIncident);

    }

    @RequestMapping(value = "/{id}/incidents", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Incident" }, description = "Get worksite's incidents")
    public Set<IncidentData> getWorkSiteIncidents(@PathVariable UUID id) {
        LOGGER.info("REST request get incident for worksite " + id);
        findWorkSite(id);

        Set<IncidentData> incidents = new HashSet<>();
        for (Incident incident : workSiteDAO.findIncidentById(id)) {
            incidents.add(new IncidentData(incident));
        }

        return incidents;
    }

    @RequestMapping(value = "/incidents", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Incident" }, description = "Get all incidents")
    public Set<IncidentData> findAllIncidents() {
        LOGGER.info("REST request get all incidents");
        Set<IncidentData> data = new HashSet<>();

        for (Set<Incident> incidents : workSiteDAO.findAllIncidents()) {
            for (Incident incident : incidents) {
                data.add(new IncidentData(incident));
            }
        }

        return data;
    }

    @RequestMapping(value = "/incident/{id}", method = RequestMethod.DELETE)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Incident" }, description = "Delete incident by id")
    public void deleteIncident(@PathVariable UUID id){
        LOGGER.info("REST request to delete incident " + id);
        incidentDAO.deleteById(id);
    }


    @RequestMapping(value = "/{id}/invoice", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Invoice" }, description = "Apply invoice to worksite")
    public InvoiceData addInvoice(@PathVariable UUID id, @RequestPart("invoice") InvoiceDTO invoiceDTO,
            @RequestPart("file") MultipartFile file) {
        LOGGER.info("REST request to apply invoice " + invoiceDTO + " to workSite " + id);

        WorkSite workSite = findWorkSite(id);

        try {
            Invoice invoice = new Invoice(workSite, file.getBytes(), invoiceDTO.getTitle(), invoiceDTO.getDescription(),
                    invoiceDTO.getAmount(), FilenameUtils.getExtension(file.getOriginalFilename()));
            workSite.addInvoice(invoice);
            workSiteDAO.save(workSite);
            invoiceDAO.save(invoice);
            return new InvoiceData(invoice);

        } catch (IOException e) {
            LOGGER.warning("Access error on uploaded file");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/invoices", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Invoice" }, description = "Get worksite's invoices")
    public Set<InvoiceData> getWorkSiteInvoices(@PathVariable UUID id) {
        LOGGER.info("REST request get invoices for worksite " + id);
        findWorkSite(id);

        Set<InvoiceData> invoices = new HashSet<>();
        for (Invoice invoice : workSiteDAO.findIncvoicesById(id)) {
            invoices.add(new InvoiceData(invoice));
        }

        return invoices;
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.DELETE)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite", "Invoice" }, description = "Delete invoice by id")
    public void deleteInvoice(@PathVariable UUID id){
        LOGGER.info("REST request to delete invoice " + id);
        invoiceDAO.deleteById(id);
    }
}
