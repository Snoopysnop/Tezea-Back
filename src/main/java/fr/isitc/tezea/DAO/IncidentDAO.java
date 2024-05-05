package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Incident;
import fr.isitc.tezea.model.WorkSite;
import jakarta.transaction.Transactional;
import java.util.Set;

@Transactional
public interface IncidentDAO extends JpaRepository<Incident, UUID> {

    Set<Incident> findByWorkSite(WorkSite workSite);

}
