package fr.isitc.tezea.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.isitc.tezea.DAO.ToolDAO;
import fr.isitc.tezea.DAO.ToolUsageDAO;
import fr.isitc.tezea.model.Tool;
import fr.isitc.tezea.model.ToolUsage;
import fr.isitc.tezea.service.DTO.ToolDTO;
import fr.isitc.tezea.utils.TimeLine;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/tools")
public class ToolController {

    private static final Logger LOGGER = Logger.getLogger(ToolController.class.getName());

    @Autowired
    private ToolDAO toolDAO;

    @Autowired
    private ToolUsageDAO toolUsageDAO;

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "Tool" }, description = "Returns all tools")
    public List<ToolDTO> findAll() {
        LOGGER.info("REST request to get all tools");

        List<ToolDTO> tools = new ArrayList<>();
        for (Tool tool : toolDAO.findAll()) {
            tools.add(new ToolDTO(tool));
        }

        return tools;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "Tool" }, description = "Returns the tool with the name")
    public ToolDTO findOne(@PathVariable String name) {
        LOGGER.info("REST request to find tool with name " + name);

        Optional<Tool> tool = toolDAO.findById(name);

        if (!tool.isPresent()) {
            LOGGER.info("tool " + name + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ToolDTO(tool.get());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "Tool" }, description = "Create a tool")
    public ToolDTO create(@RequestBody ToolDTO toolDTO) {
        LOGGER.info("REST request to create tool " + toolDTO);

        // check conflicts
        String name = toolDTO.getName();
        Optional<Tool> tool = toolDAO.findById(name);
        if (tool.isPresent()) {
            LOGGER.info("Tool " + name + " already exists");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Tool newTool = new Tool(toolDTO);
        toolDAO.save(newTool);
        return toolDTO;
    }

    @RequestMapping(value = "/{name}/availabilities", method = RequestMethod.GET)
    @CrossOrigin
    @Operation(tags = {
            "Tool" }, description = "Returns the number of availabilities for tool with the name at specified timeline")
    public int numberOfAvailabilitiesAtTimeline(@PathVariable String name, @RequestBody TimeLine timeLine) {
        LOGGER.info("REST request to get the availability for tool " + name + " between " + timeLine.getBegin()
                + " and " + timeLine.getEnd());

        Optional<Tool> tool = toolDAO.findById(name);

        if (!tool.isPresent()) {
            LOGGER.info("tool " + name + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Tool foundTool = tool.get();
        int availability = foundTool.getQuantity();
        int maxUses = 0;

        // find ToolUsage using tool during the timeline
        Set<ToolUsage> toolUsages = toolUsageDAO.findByToolBetweenDates(foundTool, timeLine.getBegin(),
                timeLine.getEnd());

        // find all concurrence point during the timeline
        Set<LocalDateTime> concurrencePoints = new HashSet<>();
        for (ToolUsage toolUsage : toolUsages) {
            concurrencePoints.add(toolUsage.getWorkSite().getBegin());
            concurrencePoints.add(toolUsage.getWorkSite().getEnd());
        }

        for (LocalDateTime point : concurrencePoints) {
            if (timeLine.isInConcurrenceWith(point)) {
                int simultaneousUses = 0;

                Set<ToolUsage> toolUsages_ = toolUsageDAO.findByToolAndDate(foundTool, point);
                for (ToolUsage toolUsage : toolUsages_) {
                    simultaneousUses += toolUsage.getQuantity();
                }
                maxUses = Math.max(maxUses, simultaneousUses);
            }

        }

        return availability - maxUses;
    }

    @RequestMapping(value = "/availabilities", method = RequestMethod.POST)
    @CrossOrigin
    @Operation(tags = { "Tool" }, description = "Returns the availabilities for all tools at specified timeline")
    public Map<String, Integer> getAvailabilities(@RequestBody TimeLine timeLine) {
        LOGGER.info("REST request to get tools availabilities between " + timeLine.getBegin() + " and "
                + timeLine.getEnd());

        Map<String, Integer> availabilities = new HashMap<>();
        for (Tool tool : toolDAO.findAll()) {
            availabilities.put(tool.getName(), numberOfAvailabilitiesAtTimeline(tool.getName(), timeLine));
        }

        return availabilities;
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "Tool" }, description = "Delete tool by name")
    public void deleteInvoice(@PathVariable String name) {
        LOGGER.info("REST request to delete tool " + name);

        Tool tool = toolDAO.findById(name).orElse(null);
        if (tool == null) {
            LOGGER.info("tool " + name + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        toolDAO.deleteById(name);
    }

}
