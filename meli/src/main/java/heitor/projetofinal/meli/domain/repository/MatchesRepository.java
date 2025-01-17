package heitor.projetofinal.meli.domain.repository;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.match.Match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MatchesRepository  extends JpaRepository<Match, Long> {

    List<Match> findByHomeTeamOrAwayTeam(Club homeTeam, Club awayTeam);

}
