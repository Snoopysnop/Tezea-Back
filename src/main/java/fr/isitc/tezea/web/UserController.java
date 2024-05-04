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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.UserDAO;
import fr.isitc.tezea.DAO.WorkSiteDAO;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.model.enums.Role;
import fr.isitc.tezea.service.DTO.UserDTO;
import fr.isitc.tezea.service.data.UserData;
import fr.isitc.tezea.utils.TimeLine;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private WorkSiteDAO workSiteDAO;

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Returns all users")
    public List<UserData> findAll() {
        LOGGER.info("REST request to get all users");

        List<UserData> users = new ArrayList<>();
        for (User user : userDAO.findAll()) {
            users.add(new UserData(user));
        }

        return users;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Returns the user with the id")
    public UserData findOne(@PathVariable UUID id) {
        LOGGER.info("REST request to find user with id " + id);

        Optional<User> user = userDAO.findById(id);

        if (!user.isPresent()) {
            LOGGER.info("User " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new UserData(user.get());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Create an user")
    public UserData create(@RequestBody UserDTO userDTO) {
        LOGGER.info("REST request to create user " + userDTO);

        User user = new User(userDTO);
        userDAO.save(user);
        return new UserData(user);
    }

    @RequestMapping(value = "/{role}", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Find users by role")
    public Set<UserData> findbyRole(@RequestBody Role role) {
        LOGGER.info("REST request to find users with role" + role);

        Set<UserData> users = new HashSet<>();

        for (User user : userDAO.findByRole(role)) {
            users.add(new UserData(user));
        }

        return users;
    }

    @RequestMapping(value = "/staff/availabilities", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Find available staff")
    public Set<UserData> getAvailableStaff(@RequestBody TimeLine timeLine) {
        LOGGER.info("REST request to find employees available between " + timeLine.getBegin() + " and "
                + timeLine.getEnd());

        Set<User> users = userDAO.findByRole(Role.Employee);

        Set<WorkSite> workSites = workSiteDAO.findWorkSiteBetweenDate(timeLine.getBegin(), timeLine.getEnd());
        for (WorkSite workSite : workSites) {
            users.removeAll(workSite.getStaff());
        }

        Set<UserData> availableStaff = new HashSet<>();
        for (User user : users) {
            availableStaff.add(new UserData(user));
        }

        return availableStaff;
    }

    @RequestMapping(value = "/workSiteChiefs/availabilities", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Find available worksite chiefs")
    public Set<UserData> getAvailableWorkSiteChiefs(@RequestBody TimeLine timeLine) {
        LOGGER.info("REST request to find employees available between " + timeLine.getBegin() + " and "
                + timeLine.getEnd());

        Set<User> users = userDAO.findByRole(Role.WorkSiteChief);

        Set<WorkSite> workSites = workSiteDAO.findWorkSiteBetweenDate(timeLine.getBegin(), timeLine.getEnd());
        for (WorkSite workSite : workSites) {
            users.remove(workSite.getWorkSiteChief());
        }

        Set<UserData> availableWorkSiteChiefs = new HashSet<>();
        for (User user : users) {
            availableWorkSiteChiefs.add(new UserData(user));
        }

        return availableWorkSiteChiefs;
    }

}
