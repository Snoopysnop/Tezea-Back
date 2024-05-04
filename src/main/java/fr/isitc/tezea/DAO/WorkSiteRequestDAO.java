package fr.isitc.tezea.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.model.WorkSiteRequest;
import jakarta.transaction.Transactional;

import java.util.Set;


@Transactional
public interface WorkSiteRequestDAO extends JpaRepository<WorkSiteRequest, Integer> {    

    Set<WorkSiteRequest> findByCustomer(Customer customer);

}
