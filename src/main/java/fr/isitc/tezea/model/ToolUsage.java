package fr.isitc.tezea.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tool_usage")
public class ToolUsage implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tool")
    private Tool tool;

    @ManyToOne
    @JoinColumn(name = "work_site_id")
    private WorkSite workSite;

    @Column(name = "quantity")
    private int quantity;

    protected ToolUsage() {

    }

    public static Map<String, Integer> toMap(Set<ToolUsage> toolUsages){
        Map<String, Integer> equipments = new HashMap<>();
        for (ToolUsage tu : toolUsages) {
            equipments.put(tu.getTool().getName(), tu.getQuantity());
        }
        return equipments;
    }

    public ToolUsage(Tool tool, WorkSite workSite, int quantity) {
        this.tool = tool;
        this.workSite = workSite;
        this.quantity = quantity;
    }

}
