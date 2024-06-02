package fr.isitc.tezea.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.CustomerDAO;
import fr.isitc.tezea.DAO.WorkSiteRequestDAO;
import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.model.WorkSiteRequest;
import fr.isitc.tezea.service.DTO.CustomerDTO;
import fr.isitc.tezea.service.data.CustomerData;
import fr.isitc.tezea.service.data.WorkSiteRequestData;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {

    private static final Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private WorkSiteRequestDAO workSiteRequestDAO;

    private Customer findCustomer(UUID id) {
        Optional<Customer> customer = customerDAO.findById(id);

        if (!customer.isPresent()) {
            LOGGER.info("Customer " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return customer.get();
    }



    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Operation(tags = { "Customer" }, description = "Returns all customers")
    public List<CustomerData> findAll() {
        LOGGER.info("REST request to get all customers");

        List<CustomerData> customers = new ArrayList<>();
        for (Customer customer : customerDAO.findAll()) {
            customers.add(new CustomerData(customer));
        }

        return customers;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Operation(tags = { "Customer" }, description = "Returns the customer with the id")
    public CustomerData findOne(@PathVariable UUID id) {
        LOGGER.info("REST request to find customer with id " + id);

        Customer customer = findCustomer(id);
        return new CustomerData(customer);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @Operation(tags = { "Customer" }, description = "Create an customer")
    public CustomerData create(@RequestBody CustomerDTO customerDTO) {
        LOGGER.info("REST request to create customer " + customerDTO);

        Customer newCustomer = new Customer(customerDTO);
        customerDAO.save(newCustomer);
        return new CustomerData(newCustomer);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PATCH)
    @ResponseBody
    @Operation(tags = { "Customer" }, description = "Create an customer")
    public CustomerData update(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO) {
        LOGGER.info("REST request to update customer " + id + " with " + customerDTO);
        Customer customer = findCustomer(id);
        
        customer.patch(customerDTO);
        customerDAO.save(customer);

        return new CustomerData(customer);
    }

    @RequestMapping(value = "/{id}/workSiteRequest", method = RequestMethod.GET)
    @ResponseBody
    @Operation(tags = { "Customer" }, description = "get customer's workSiteRequest")
    public Set<WorkSiteRequestData> getCustomerRequests(@PathVariable UUID id) {
        LOGGER.info("REST request to get workSiteRequest of customer " + id);

        Optional<Customer> customer = customerDAO.findById(id);
        if (!customer.isPresent()) {
            LOGGER.info("Customer " + id + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Set<WorkSiteRequestData> requests = new HashSet<>();
        for (WorkSiteRequest request : workSiteRequestDAO.findByCustomer(customer.get())) {
            requests.add(new WorkSiteRequestData(request));
        }

        return requests;
    }
}
