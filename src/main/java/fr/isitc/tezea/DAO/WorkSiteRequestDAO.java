package fr.isitc.tezea.DAO;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.isitc.tezea.model.Customer;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.WorkSiteRequest;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

import fr.isitc.tezea.model.enums.CustomerStatus;
import fr.isitc.tezea.model.enums.RequestStatus;
import fr.isitc.tezea.model.enums.Service;

@Transactional
public interface WorkSiteRequestDAO extends JpaRepository<WorkSiteRequest, Integer> {    

    Set<WorkSiteRequest> findByCustomer(Customer customer);

    Set<WorkSiteRequest> findByStatus(RequestStatus status);

    @Query("Select wsr.city from WorkSiteRequest wsr")
    Set<String> findAllCities();

    Set<WorkSiteRequest> findByCity(String city);

    Set<WorkSiteRequest> findByServiceType(Service serviceType);

    @Query("Select wsr from WorkSiteRequest wsr where wsr.customer.status = ?1")
    Set<WorkSiteRequest> findByCustomerStatus(CustomerStatus customerStatus);

    Set<WorkSiteRequest> findByConcierge(User concierge);

    List<WorkSiteRequest> findAll(Sort sort);

}
