package fr.isitc.tezea.web;

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

import fr.isitc.tezea.DAO.WorkSiteDAO;
import fr.isitc.tezea.model.Incident;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.service.DTO.IncidentDTO;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/worksites")
public class WorkSiteController {

    private static final Logger LOGGER = Logger.getLogger(WorkSiteController.class.getName());

    @Autowired
    private WorkSiteDAO workSiteDAO;

    @RequestMapping(value = "/{id}/incident", method = RequestMethod.PUT)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "WorkSite" }, description = "Returns the user with the id")
    public void addIncident(@PathVariable UUID id, @RequestBody IncidentDTO incidentDTO){
        LOGGER.info("REST request to declare incident " + incidentDTO + " to workSite " + id);

        // find workSite
        Optional<WorkSite> workSite = workSiteDAO.findById(id);
        if(!workSite.isPresent()) {
            LOGGER.info("WorkSite " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        WorkSite foundWorkSite = workSite.get();

        Incident incident = new Incident(foundWorkSite, incidentDTO.getLevel(), incidentDTO.getTitle(), incidentDTO.getDescription());
        foundWorkSite.addIncident(incident);
        workSiteDAO.save(foundWorkSite);
    }
    
}
