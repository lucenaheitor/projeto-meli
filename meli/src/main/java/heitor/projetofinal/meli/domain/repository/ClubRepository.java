package heitor.projetofinal.meli.domain.repository;

import heitor.projetofinal.meli.domain.club.Club;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository  extends JpaRepository<Club, Long> {
    boolean existsByName( String name);
}
