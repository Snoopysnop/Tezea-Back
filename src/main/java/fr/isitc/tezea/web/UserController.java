package fr.isitc.tezea.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import fr.isitc.tezea.service.DTO.UserDTO;
import fr.isitc.tezea.service.data.UserData;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value = "/users")
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
        for(User user : userDAO.findAll()) {
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

        if(!user.isPresent()){
            LOGGER.info("User " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
        return new UserData(user.get());
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Create an user")
    public UserData create(@RequestBody UserDTO userDTO) {
        LOGGER.info("REST request to create user " + userDTO);

        User user = new User(userDTO);
        userDAO.save(user);
        return new UserData(user);
    }

    @RequestMapping(value = "/{id}/worksite/{workSiteId}", method = RequestMethod.PUT)
    @CrossOrigin
    @Operation(tags = { "User" }, description = "Affect a worksite to user")
    public void affectWorkSite(@PathVariable UUID id, @PathVariable UUID workSiteId) {
        LOGGER.info("REST request to affect workSote " + workSiteId + " to user " + id);

        // find User
        Optional<User> user = userDAO.findById(id);
        if(!user.isPresent()) {
            LOGGER.info("User " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // find WorkSite
        Optional<WorkSite> workSite = workSiteDAO.findById(workSiteId);
        if(!workSite.isPresent()) {
            LOGGER.info("WorkSite " + workSiteId + " not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // apply workSite
        User foundUser = user.get();
        WorkSite foundWorkSite = workSite.get();

        if(!foundUser.addWorkSite(foundWorkSite)){
            LOGGER.info("User " + id + " is not available between " + foundWorkSite.getBegin() + " and " + foundWorkSite.getEnd());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        userDAO.save(foundUser);

    }

}
