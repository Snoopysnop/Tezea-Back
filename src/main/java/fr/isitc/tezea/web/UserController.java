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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.IncidentDAO;
import fr.isitc.tezea.DAO.ToolUsageDAO;
import fr.isitc.tezea.DAO.UserDAO;
import fr.isitc.tezea.DAO.WorkSiteDAO;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.model.WorkSiteRequest;
import fr.isitc.tezea.model.enums.Role;
import fr.isitc.tezea.service.UserServices;
import fr.isitc.tezea.service.DTO.UserDTO;
import fr.isitc.tezea.service.data.UserData;
import fr.isitc.tezea.service.data.WorkSiteAndRequestData;
import fr.isitc.tezea.service.data.WorkSiteData;
import fr.isitc.tezea.service.data.WorkSiteRequestData;
import fr.isitc.tezea.utils.TimeLine;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserServices userServices;

    @Autowired
    private WorkSiteDAO workSiteDAO;

    @Autowired
    private IncidentDAO incidentDAO;

    @Autowired
    private ToolUsageDAO toolUsageDAO;

    private User findUser(UUID id) {
        Optional<User> user = userDAO.findById(id);

        if (!user.isPresent()) {
            LOGGER.info("User " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return user.get();
    }

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

        User user = findUser(id);
        return new UserData(user);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Finds user by email")
    public UserData findByEmail(@RequestParam String email) {
        LOGGER.info("REST request to find user with email " + email);

        Optional<User> user = userDAO.findByEmail(email);
        if (!user.isPresent()) {
            LOGGER.info("User with email " + email + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new UserData(user.get());
    }

    @RequestMapping(value = "/findSomeUsers", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Returns users with id present in the given list")
    public Set<UserData> findSomeUsers(@RequestBody List<UUID> uuids) {
        LOGGER.info("REST request to find users with id present in " + uuids);

        Set<UserData> users = new HashSet<>();
        for (User user : userDAO.findByIds(uuids)) {
            users.add(new UserData(user));
        }
        return users;
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Create an user")
    public UserData create(@RequestPart("user") UserDTO userDTO, @RequestPart("password") String password, @RequestPart(value = "file", required = false) MultipartFile file) {
        LOGGER.info("REST request to create user " + userDTO);
        return userServices.register(userDTO, password, file);
    }

    @RequestMapping(value = "/{id}/profilePicture", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Update user profile picture")
    public UserData updateProfilePicture(@PathVariable UUID id, @RequestBody String profilePicture) {
        LOGGER.info("REST request to update user " + id + " profile picture");

        User user = findUser(id);
        user.setProfilePicture(profilePicture);
        userDAO.save(user);

        return new UserData(user);
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "User" }, description = "Find users by role")
    public Set<UserData> findbyRole(@RequestParam Role role) {
        LOGGER.info("REST request to find users with role " + role);

        Set<UserData> users = new HashSet<>();

        for (User user : userDAO.findByRole(role)) {
            users.add(new UserData(user));
        }

        return users;
    }

    @RequestMapping(value = "/Concierge", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "Concierge" }, description = "Find a concierge")
    public UserData findConcierge() {
        LOGGER.info("REST request to find a concierge");

        return new UserData(userDAO.findFirstByRole(Role.Concierge));
    }

    @RequestMapping(value = "/SiteChief", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "SiteChief" }, description = "Find a site chief")
    public UserData findSiteChief() {
        LOGGER.info("REST request to find a site chief");

        return new UserData(userDAO.findFirstByRole(Role.SiteChief));
    }

    @RequestMapping(value = "/WorkSiteChief", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteChief" }, description = "Find a work site chief")
    public UserData findWorkSiteChief() {
        LOGGER.info("REST request to find a work site chief");

        return new UserData(userDAO.findFirstByRole(Role.WorkSiteChief));
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
    @Operation(tags = { "WorkSiteChief" }, description = "Find available worksite chiefs")
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

    @RequestMapping(value = "{id}/workSites", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteChief" }, description = "Find worksite chiefs worksites")
    public Set<WorkSiteData> getWorkSiteChiefWorkSites(@PathVariable UUID id) {
        LOGGER.info("REST request to get worksite chief worksites");

        User user = findUser(id);
        if (user.getRole() != Role.WorkSiteChief) {
            LOGGER.info("User is not a worksite chief");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Set<WorkSiteData> workSites = new HashSet<>();
        for (WorkSite workSite : workSiteDAO.findByWorkSiteChief(user)) {
            workSites.add(new WorkSiteData(workSite, toolUsageDAO.findByWorkSite(workSite)));
        }

        return workSites;
    }

    @RequestMapping(value = "{id}/workSiteRequests", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteChief" }, description = "Find worksite chiefs worksite requests")
    public Set<WorkSiteRequestData> getWorkSiteChiefWorkSiteRequests(@PathVariable UUID id) {
        LOGGER.info("REST request to get worksite chief worksite requests");

        User user = findUser(id);
        if (user.getRole() != Role.WorkSiteChief) {
            LOGGER.info("User is not a worksite chief");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Set<WorkSiteRequestData> workSiteRequests = new HashSet<>();
        for (WorkSiteRequest workSiteRequest : workSiteDAO.findWorkSiteRequestByWorkSiteChief(user)) {
            workSiteRequests.add(new WorkSiteRequestData(workSiteRequest));
        }

        return workSiteRequests;
    }

    @RequestMapping(value = "{id}/allWorkSites", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSiteChief" }, description = "Find worksite chiefs worksite requests and workSites")
    public Set<WorkSiteAndRequestData> getWorkSiteChiefWorkSiteRequestsAndWorkSite(@PathVariable UUID id) {
        LOGGER.info("REST request to get worksite chief worksite requests and worksites");

        User user = findUser(id);
        if (user.getRole() != Role.WorkSiteChief) {
            LOGGER.info("User is not a worksite chief");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Set<WorkSiteAndRequestData> datas = new HashSet<>();

        for (WorkSite workSite : workSiteDAO.findByWorkSiteChief(user)) {
            WorkSiteRequestData request = new WorkSiteRequestData(workSite.getRequest());

            // check if workSite has incidents
            boolean hasIncidents = !incidentDAO.findByWorkSite(workSite).isEmpty();

            WorkSiteData workSiteData = new WorkSiteData(workSite, toolUsageDAO.findByWorkSite(workSite));

            datas.add(new WorkSiteAndRequestData(workSiteData, request, hasIncidents));
        }

        return datas;
    }

}
