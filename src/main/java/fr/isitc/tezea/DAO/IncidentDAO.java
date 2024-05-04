package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Incident;
import jakarta.transaction.Transactional;

@Transactional
public interface IncidentDAO extends JpaRepository<Incident, UUID> {

}
