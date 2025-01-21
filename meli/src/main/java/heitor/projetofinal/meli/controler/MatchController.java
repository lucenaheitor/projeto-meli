package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.DetailMatchesDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
import heitor.projetofinal.meli.domain.match.dto_match.UpdateMatchDTO;
import heitor.projetofinal.meli.domain.match.high_search.DirectConfrontation;
import heitor.projetofinal.meli.service.MatchService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    public ResponseEntity<DetailMatchesDTO> detailMatch(@PathVariable Long id) {
        DetailMatchesDTO detailDTO = matchService.detailMatches(id);
        return ResponseEntity.ok(detailDTO);
    }

    @PutMapping
    @Transactional
    public  ResponseEntity<UpdateMatchDTO> update(@RequestBody @Valid UpdateMatchDTO dto){
         UpdateMatchDTO updateMatchDTO = matchService.updateMatch(dto);
         return ResponseEntity.ok(updateMatchDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/direct-confrontation")
    public ResponseEntity<DirectConfrontation> getDirectConfrontations(
            @RequestParam("club1") Club club1,
            @RequestParam("club2") Club club2) {

        DirectConfrontation confrontationDTO = matchService.getDirectConfrontations(club1, club2);
        return ResponseEntity.ok(confrontationDTO);
    }




}
