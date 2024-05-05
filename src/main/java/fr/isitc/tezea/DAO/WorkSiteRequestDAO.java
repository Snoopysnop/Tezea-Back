package fr.isitc.tezea.DAO;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.model.WorkSiteRequest;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;


@Transactional
public interface WorkSiteRequestDAO extends JpaRepository<WorkSiteRequest, Integer> {    

    Set<WorkSiteRequest> findByCustomer(Customer customer);

    List<WorkSiteRequest> findAll(Sort sort);

}
