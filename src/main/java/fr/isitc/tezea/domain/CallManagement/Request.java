package fr.isitc.tezea.domain.CallManagement;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import fr.isitc.tezea.domain.business.Customer;
import fr.isitc.tezea.domain.business.User;
import fr.isitc.tezea.domain.request.Appointment;
import fr.isitc.tezea.domain.request.Emergency;
import fr.isitc.tezea.domain.request.RequestStatus;
import fr.isitc.tezea.domain.request.SatisfactionLevel;
import fr.isitc.tezea.domain.request.Service;
import lombok.Data;

@Data
@Document("requests")
public class Request {
    
    @Id
    private String id;
    private Date date;
    private User concierge;
    private Service service;
    private Customer customer;
    private Emergency emergency;
    private RequestStatus requestStatus;
    private User assigned;
    private Appointment appointment;
    private User closer;
    private String details;
    private String provider;
    private SatisfactionLevel satisfactionLevel;

}
