package fr.isitc.tezea.service.data;

import java.util.UUID;

import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.service.DTO.CustomerDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerData extends CustomerDTO {

    private UUID id;

    public CustomerData(Customer customer){
        super(customer);
        this.id = customer.getId();
    }

}
