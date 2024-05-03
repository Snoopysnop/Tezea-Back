package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.model.WorkSiteRequest;
import jakarta.transaction.Transactional;

import java.util.Set;


@Transactional
public interface WorkSiteRequestDAO extends JpaRepository<WorkSiteRequest, UUID> {    

    Set<WorkSiteRequest> findByCustomer(Customer customer);

}
