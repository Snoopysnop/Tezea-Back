package fr.isitc.tezea.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isitc.tezea.domain.business.UserTezea;
import jakarta.transaction.Transactional;


@Transactional
public interface UserTezeaDAO extends JpaRepository<UserTezea, Long> {
   
}
