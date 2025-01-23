package heitor.projetofinal.meli.domain.repository;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.match.Match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface MatchesRepository  extends JpaRepository<Match, Long> {

    List<Match> findByHomeTeamOrAwayTeam(Club homeTeam, Club awayTeam);


    @Query("""
    SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
    FROM Match m
    WHERE m.matchDate = :matchDate
""")
    boolean existsByMatchDate(@Param("matchDate") LocalDate matchDate);


}
