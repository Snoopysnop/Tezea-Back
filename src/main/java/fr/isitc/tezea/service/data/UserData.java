package fr.isitc.tezea.service.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.service.DTO.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserData extends UserDTO {

    private UUID id;
    private Set<UUID> workSites = new HashSet<>();
    
    public UserData(User user) {
        super(user);
        this.id = user.getId();
        for(WorkSite workSite : user.getWorkSites()){
            this.workSites.add(workSite.getId());
        }
       
    }
}
