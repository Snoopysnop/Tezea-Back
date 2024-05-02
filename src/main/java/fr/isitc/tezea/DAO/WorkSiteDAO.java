package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.WorkSite;
import jakarta.transaction.Transactional;

@Transactional
public interface WorkSiteDAO extends JpaRepository<WorkSite, UUID> {    

}
