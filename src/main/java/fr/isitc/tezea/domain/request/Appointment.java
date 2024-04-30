package fr.isitc.tezea.domain.request;

import lombok.Data;

@Data
public class Appointment {
    
    private boolean taken;
    private boolean confirmed;
    private boolean positioned;

}
