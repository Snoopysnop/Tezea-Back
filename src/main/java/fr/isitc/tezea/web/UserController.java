package fr.isitc.tezea.web;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.isitc.tezea.DAO.UserDAO;
import fr.isitc.tezea.domain.business.User;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping()
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(
        tags = {"User"},
        description = "Returns all users"
    )
    public List<User> findAll() {
        LOGGER.info("REST request to get all users");
        return userDAO.findAll();
    }

}
