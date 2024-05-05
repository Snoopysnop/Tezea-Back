package fr.isitc.tezea.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.WorkSiteRequestDAO;
import fr.isitc.tezea.DAO.CustomerDAO;
import fr.isitc.tezea.DAO.UserDAO;
import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.WorkSiteRequest;
import fr.isitc.tezea.model.enums.CustomerStatus;
import fr.isitc.tezea.model.enums.RequestStatus;
import fr.isitc.tezea.model.enums.Role;
import fr.isitc.tezea.model.enums.Service;
import fr.isitc.tezea.service.DTO.WorkSiteRequestDTO;
import fr.isitc.tezea.service.data.WorkSiteRequestData;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/workSiteRequests")
public class WorkSiteRequestController {

    private static final Logger LOGGER = Logger.getLogger(WorkSiteRequestController.class.getName());

    @Autowired
    private WorkSiteRequestDAO workSiteRequestDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Returns all work site requests")
    public Set<WorkSiteRequestData> findAll(@RequestBody(required = false) String sortString) {
        LOGGER.info("REST request to get all work site requests");
        
        List<WorkSiteRequest> actualRequests;

        if("creationDate".equals("sortString") || "estimatedDate".equals("sortString")){
            Sort sort = Sort.by(Sort.Direction.ASC, sortString);
            actualRequests = workSiteRequestDAO.findAll(sort);
        }
        else {
            actualRequests = workSiteRequestDAO.findAll();
        }
        
        Set<WorkSiteRequestData> requests = new HashSet<>();
        for (WorkSiteRequest request : actualRequests) {
            requests.add(new WorkSiteRequestData(request));
        }
        return requests;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Returns the work site request {id}")
    public WorkSiteRequestData findById(@PathVariable Integer id) {

        Optional<WorkSiteRequest> workSiteRequest;
        workSiteRequest = workSiteRequestDAO.findById(id);
        if(!workSiteRequest.isPresent()) {
            LOGGER.info("Work site request " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new WorkSiteRequestData(workSiteRequest.get());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Create a work site request")
    public WorkSiteRequestData create(@RequestBody WorkSiteRequestDTO workSiteRequestDTO) {
        LOGGER.info("REST request to create work site request " + workSiteRequestDTO);

        Optional<User> concierge = userDAO.findById(workSiteRequestDTO.getConcierge());
        if (!concierge.isPresent()) {
            LOGGER.info("concierge " + workSiteRequestDTO.getConcierge() + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
        User siteChief = null;
        if(workSiteRequestDTO.getSiteChief() != null){
            siteChief = userDAO.findById(workSiteRequestDTO.getSiteChief()).get();
        }
       
        Customer customer = null;
        if(workSiteRequestDTO.getCustomer() != null) {
            customer = customerDAO.findById(workSiteRequestDTO.getCustomer()).get();
        }
        WorkSiteRequest newWorkSiteRequest = new WorkSiteRequest(concierge.get(), siteChief, customer, workSiteRequestDTO);

        workSiteRequestDAO.save(newWorkSiteRequest);
        return new WorkSiteRequestData(newWorkSiteRequest);
    }

    @RequestMapping(value = "/{id}/update_status", method = RequestMethod.PATCH)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Update status of a work site request")
    public void updateStatus(@PathVariable Integer id, @RequestParam("status") RequestStatus status) {

        Optional<WorkSiteRequest> workSiteRequest = workSiteRequestDAO.findById(id);
        if (!workSiteRequest.isPresent()) {
            LOGGER.info("Work site request " + id + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            workSiteRequest.get().setStatus(status);

            workSiteRequestDAO.save(workSiteRequest.get());
        } catch (Exception e) {
            LOGGER.info("Can't update status for work site request " + id);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/statistics/status", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Get number of worksite request by status")
    public Map<RequestStatus, Integer> getStatusStatistics(){
        LOGGER.info(("REST request to get status statistics"));

        Map<RequestStatus, Integer> statistics = new HashMap<>();
        for(RequestStatus status : RequestStatus.values()) {
            statistics.put(status, workSiteRequestDAO.findByStatus(status).size());
        }

        return statistics;
    }

    @RequestMapping(value = "/statistics/cities", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Get number of worksite request by cites")
    public Map<String, Integer> getCitiesStatistics(){
        LOGGER.info("REST request to get cities statistics");

        Map<String, Integer> statistics = new HashMap<>();
        for(String city : workSiteRequestDAO.findAllCities()) {
            statistics.put(city, workSiteRequestDAO.findByCity(city).size());
        }

        return statistics;
    }


    @RequestMapping(value = "/statistics/services", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Get number of worksite request by service types")
    public Map<Service, Integer> getServicesStatistics(){
        LOGGER.info("REST request to get service types statistics");

        Map<Service, Integer> statistics = new HashMap<>();
        for(Service serviceType : Service.values()) {
            statistics.put(serviceType, workSiteRequestDAO.findByServiceType(serviceType).size());
        }

        return statistics;
    }

    @RequestMapping(value = "/statistics/customers", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Get number of worksite request by customer status")
    public Map<CustomerStatus, Integer> getCustomerStatistics(){
        LOGGER.info("REST request to get customer status statistics");

        Map<CustomerStatus, Integer> statistics = new HashMap<>();
        for(CustomerStatus customerStatus : CustomerStatus.values()) {
            statistics.put(customerStatus, workSiteRequestDAO.findByCustomerStatus(customerStatus).size());
        }

        return statistics;
    }

    @RequestMapping(value = "/statistics/concierges", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteRequest" }, description = "Get number of worksite request by cites")
    public Map<UUID, Integer> getConciergesStatistics(){
        LOGGER.info("REST request to get concierges statistics");

        Map<UUID, Integer> statistics = new HashMap<>();
        for(User concierge : userDAO.findByRole(Role.Concierge)) {
            statistics.put(concierge.getId(), workSiteRequestDAO.findByConcierge(concierge).size());
        }

        return statistics;
    }


}
