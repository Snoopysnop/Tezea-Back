package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.model.User;
import jakarta.transaction.Transactional;

@Transactional
public interface UserDAO extends JpaRepository<User, UUID> {

}
