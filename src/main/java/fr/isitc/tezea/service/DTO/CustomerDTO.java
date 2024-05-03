package fr.isitc.tezea.service.DTO;

import java.io.Serializable;

import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.model.enums.Civility;
import fr.isitc.tezea.model.enums.CustomerStatus;
import lombok.Data;

@Data
public class CustomerDTO implements Serializable {

    private String firstName;
    private String lastName;
    private Civility civility;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private int postalCode;
    private CustomerStatus status;
    private String company;

    protected CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.address = customer.getAddress();
        this.city = customer.getCity();
        this.civility = customer.getCivility();
        this.company = customer.getCompany();
        this.email = customer.getEmail();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phoneNumber = customer.getPhoneNumber();
        this.postalCode = customer.getPostalCode();
        this.status = customer.getStatus();
    }
}
