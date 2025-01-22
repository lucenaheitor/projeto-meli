package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.match.high_search.RankingDTO;
import heitor.projetofinal.meli.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class Ranking {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<RankingDTO>> getRanking(@RequestParam("criterio") String criterio) {
        List<RankingDTO> ranking = matchService.getRanking(criterio);
        return ResponseEntity.ok(ranking);
    }

}
