package heitor.projetofinal.meli.domain.repository;

import heitor.projetofinal.meli.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository  extends JpaRepository<Club, Long> {

    Optional<Club> findByName(String name);


    boolean existsByName( String name);

    Long id(Long id);
}
