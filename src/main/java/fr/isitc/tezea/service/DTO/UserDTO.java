package fr.isitc.tezea.service.DTO;

import java.io.Serializable;

import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.enums.Role;
import lombok.Data;

@Data
public class UserDTO implements Serializable {

    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private String phoneNumber;

    protected UserDTO() {

    }

    public UserDTO(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }
    
}
