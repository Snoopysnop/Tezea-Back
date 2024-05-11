package fr.isitc.tezea.DAO;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.isitc.tezea.model.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.isitc.tezea.model.enums.Role;

@Transactional
public interface UserDAO extends JpaRepository<User, UUID> {

    public Set<User> findByRole(Role role);

    User findFirstByRole(Role role);

    Optional<User> findByIdAndRole(UUID id, Role role);

    @Query("SELECT u FROM User u WHERE u.id IN (?1)")
    public List<User> findByIds(List<UUID> uuids);

}
