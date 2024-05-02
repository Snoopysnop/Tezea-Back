package fr.isitc.tezea.service.DTO;

import java.io.Serializable;

import fr.isitc.tezea.model.Tool;
import lombok.Data;

@Data
public class ToolDTO implements Serializable {

    private String name;
    private int quantity;

    protected ToolDTO() {
        
    }

    public ToolDTO(Tool tool){
        this.name = tool.getName();
        this.quantity = tool.getQuantity();
    }
    
}
