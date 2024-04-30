package fr.isitc.tezea.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.domain.business.User;
import jakarta.transaction.Transactional;


@Transactional
public interface UserDAO extends JpaRepository<User, Long> {
   
}
