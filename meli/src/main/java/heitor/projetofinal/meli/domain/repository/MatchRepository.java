package heitor.projetofinal.meli.domain.repository;

import heitor.projetofinal.meli.domain.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchRepository  extends JpaRepository<Match, Long> {

}
