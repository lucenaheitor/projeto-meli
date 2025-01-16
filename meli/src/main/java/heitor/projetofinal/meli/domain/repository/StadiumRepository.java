package heitor.projetofinal.meli.domain.repository;

import heitor.projetofinal.meli.domain.stadium.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    Optional<Stadium> findByName(String name);

}
