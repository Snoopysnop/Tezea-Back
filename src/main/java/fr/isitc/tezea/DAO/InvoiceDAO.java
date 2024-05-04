package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.Invoice;
import jakarta.transaction.Transactional;

@Transactional
public interface InvoiceDAO extends JpaRepository<Invoice, UUID> {

}
