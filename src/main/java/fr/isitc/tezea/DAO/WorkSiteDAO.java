package fr.isitc.tezea.DAO;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.isitc.tezea.model.Incident;
import fr.isitc.tezea.model.Invoice;
import fr.isitc.tezea.model.User;
import fr.isitc.tezea.model.WorkSite;
import fr.isitc.tezea.model.WorkSiteRequest;
import jakarta.transaction.Transactional;

@Transactional
public interface WorkSiteDAO extends JpaRepository<WorkSite, UUID> {    

    @Query("Select w from WorkSite w where (w.begin >= ?1 and w.begin <=?2) or (w.end >= ?1 and w.end <=?2)")
    public Set<WorkSite> findWorkSiteBetweenDate(LocalDateTime begin, LocalDateTime end);

    @Query("Select w.incidents from WorkSite w where w.id = ?1")
    public Set<Incident> findIncidentById(UUID id);

    @Query("Select w.invoices from WorkSite w where w.id = ?1")
    public Set<Invoice> findIncvoicesById(UUID id);

    Set<WorkSite> findByWorkSiteChief(User workSiteChief);

    @Query("Select w.request from WorkSite w where w.workSiteChief = ?1")
    Set<WorkSiteRequest> findWorkSiteRequestByWorkSiteChief(User workSiteChief);

}
