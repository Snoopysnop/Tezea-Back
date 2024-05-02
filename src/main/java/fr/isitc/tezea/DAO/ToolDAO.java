package fr.isitc.tezea.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Tool;
import jakarta.transaction.Transactional;

@Transactional
public interface ToolDAO extends JpaRepository<Tool, String> {

}
