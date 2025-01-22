package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.DetailMatchesDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
import heitor.projetofinal.meli.domain.match.dto_match.UpdateMatchDTO;
import heitor.projetofinal.meli.domain.match.high_search.DirectConfrontation;
import heitor.projetofinal.meli.domain.match.high_search.MatchDTO;
import heitor.projetofinal.meli.domain.match.high_search.ThrashingDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.service.MatchService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
     private ClubRepository clubRepository;

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
            @RequestParam("club1Id") Long club1Id,
            @RequestParam("club2Id") Long club2Id) {

        Club club1 = clubRepository.findById(club1Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club 1 not found"));
        Club club2 = clubRepository.findById(club2Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club 2 not found"));

        DirectConfrontation confrontationDTO = matchService.getDirectConfrontations(club1, club2);
        return ResponseEntity.ok(confrontationDTO);
    }

    @GetMapping("/thrashing")
    public ResponseEntity<List<ThrashingDTO>> getMatches(
            @RequestParam(value = "goleada", required = false, defaultValue = "false") boolean goleada) {
        List<ThrashingDTO> matches = matchService.getTrashing(goleada);
        return ResponseEntity.ok(matches);
    }
    @GetMapping("/matches")
    public ResponseEntity<List<MatchDTO>> getMatchesByClub(
            @RequestParam(value = "club", required = true) Long clubId,
            @RequestParam(value = "mandante", required = false, defaultValue = "false") boolean mandante,
            @RequestParam(value = "visitante", required = false, defaultValue = "false") boolean visitante) {
        List<MatchDTO> matches = matchService.getMatchesByClub(clubId, mandante, visitante);
        return ResponseEntity.ok(matches);
    }






}
