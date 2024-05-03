package fr.isitc.tezea.service.data;

import java.util.UUID;

import fr.isitc.tezea.model.User;
import fr.isitc.tezea.service.DTO.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserData extends UserDTO {

    private UUID id;
    
    public UserData(User user) {
        super(user);
        this.id = user.getId();
       
    }
}
