package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
import heitor.projetofinal.meli.service.MatchService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping
    @Transactional
    public ResponseEntity<CreateMatchDTO> postMatch(@RequestBody CreateMatchDTO  dto, UriComponentsBuilder uriBuilder) {
        CreateMatchDTO createMatchDTO=  matchService.createMatch(dto);
        URI adress =  uriBuilder.path("/matches/{id}").buildAndExpand(createMatchDTO.getId()).toUri();

        return ResponseEntity.created(adress).body(createMatchDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ListMatches>>  listMatch(Pageable pageable) {
         Page page = matchService.listMatches(pageable);
         return ResponseEntity.ok(page);
    }
}
