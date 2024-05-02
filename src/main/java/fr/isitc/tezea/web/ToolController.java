package fr.isitc.tezea.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import fr.isitc.tezea.DAO.ToolDAO;
import fr.isitc.tezea.model.Tool;
import fr.isitc.tezea.service.DTO.ToolDTO;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/tools")
public class ToolController {

    private static final Logger LOGGER = Logger.getLogger(ToolController.class.getName());
    
    @Autowired
    private ToolDAO toolDAO;

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "Tool" }, description = "Returns all tools")
    public List<ToolDTO> findAll() {
        LOGGER.info("REST request to get all tools");

        List<ToolDTO> tools = new ArrayList<>();
        for(Tool tool : toolDAO.findAll()) {
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

        if(!tool.isPresent()){
            LOGGER.info("tool " + name + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
        return new ToolDTO(tool.get());
    }

    @RequestMapping(value = "/create", method=RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    @Operation(tags = { "Tool" }, description = "Create a tool")
    public ToolDTO create(@RequestBody ToolDTO toolDTO) {
        LOGGER.info("REST request to create tool " + toolDTO);

        // check conflicts
        String name = toolDTO.getName();
        Optional<Tool> tool = toolDAO.findById(name);
        if(tool.isPresent()) {
            LOGGER.info("Tool " + name + " already exists");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Tool newTool = new Tool(toolDTO);
        toolDAO.save(newTool);
        return toolDTO;
    }

}
