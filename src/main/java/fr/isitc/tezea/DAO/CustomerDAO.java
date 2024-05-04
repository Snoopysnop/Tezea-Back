package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Customer;
import jakarta.transaction.Transactional;

@Transactional
public interface CustomerDAO extends JpaRepository<Customer, UUID> {

}