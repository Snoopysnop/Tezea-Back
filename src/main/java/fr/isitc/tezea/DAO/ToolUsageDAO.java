package fr.isitc.tezea.DAO;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.isitc.tezea.model.Tool;
import fr.isitc.tezea.model.ToolUsage;
import jakarta.transaction.Transactional;

@Transactional
public interface ToolUsageDAO extends JpaRepository<ToolUsage, UUID> {

    @Query("Select tu from ToolUsage tu where tu.tool = ?1 and (?2 >= tu.workSite.begin and ?2 <= tu.workSite.end)")
    public Set<ToolUsage> findByToolAndDate(Tool tool, LocalDateTime date);

    @Query("Select tu from ToolUsage tu where tu.tool = ?1 and ((tu.workSite.begin >= ?2 and tu.workSite.begin <= ?3) or (tu.workSite.end >= ?2 and tu.workSite.end <= ?3))")
    public Set<ToolUsage> findByToolBetweenDates(Tool tool, LocalDateTime begin, LocalDateTime end);

}
