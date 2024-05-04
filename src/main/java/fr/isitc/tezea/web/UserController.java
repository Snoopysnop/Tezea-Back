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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.UserDAO;
import fr.isitc.tezea.DAO.WorkSiteDAO;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.service.UserServices;
import fr.isitc.tezea.service.DTO.UserDTO;
import fr.isitc.tezea.service.data.UserData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.Multipart;


@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserServices userServices;

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
    public UserData create(@RequestPart("user") UserDTO userDTO, @RequestPart("password") String password, @RequestPart(value = "file", required = false) MultipartFile file) {
        LOGGER.info("REST request to create user " + userDTO);
        return userServices.register(userDTO, password, file);
    }

}
