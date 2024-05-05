package fr.isitc.tezea.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import fr.isitc.tezea.model.enums.Role;
import fr.isitc.tezea.service.DTO.UserDTO;
import fr.isitc.tezea.utils.TimeLine;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany
    @Column(name = "work_site_id")
    private Set<WorkSite> workSites = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "work_site_request_id")
    private Set<WorkSite> workSitesRequest = new HashSet<>();

    @OneToMany(mappedBy = "workSiteChief")
    private Set<WorkSite> ownedWorkSites = new HashSet<>();

    @Column(name = "profilePicture")
    private byte[] profilePicture;

    protected User() {

    }

    public User(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.role = userDTO.getRole();
        this.email = userDTO.getEmail();
        this.phoneNumber = userDTO.getPhoneNumber();
        this.profilePicture = userDTO.getProfilePicture();

    }

    public boolean addWorkSite(WorkSite workSite) {

        // check conflicts
        TimeLine timeLine = new TimeLine(workSite.getBegin(), workSite.getEnd());
        for (WorkSite userWorkSites : this.workSites) {
            if (TimeLine.areTimelineInConcurrence(timeLine,
                    new TimeLine(userWorkSites.getBegin(), userWorkSites.getEnd()))) {
                return false;
            }
        }

        this.workSites.add(workSite);
        return true;
    }

}
