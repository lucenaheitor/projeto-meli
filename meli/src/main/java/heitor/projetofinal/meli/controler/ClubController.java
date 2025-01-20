package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.club.club_dto.DetailClub;
import heitor.projetofinal.meli.domain.club.club_dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.match.high_search.AdversaryRestrospectiveDTO;
import heitor.projetofinal.meli.domain.match.high_search.ClubRestrospectveDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.club.club_dto.CreateClubDTO;
import heitor.projetofinal.meli.service.ClubService;
import heitor.projetofinal.meli.service.MatchService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;
    @Autowired
    private MatchService matchService;


    @PostMapping
    @Transactional
    public ResponseEntity<CreateClubDTO> register(@RequestBody @Valid CreateClubDTO dto, UriComponentsBuilder uriBuilder){

        CreateClubDTO clubDTO = clubService.register(dto);
        URI address = uriBuilder.path("/clubs/{id}").buildAndExpand(clubDTO.getName()).toUri();

        return  ResponseEntity.created(address).body(clubDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ListClubDTO>> listClub( @PageableDefault(size = 10, sort = "name") Pageable pageable){
        Page page = clubService.list(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailClub>  detail(@PathVariable Long id ){
        DetailClub club =  clubService.detail(id);
        return ResponseEntity.ok(club);
    }

    @PutMapping
    @Transactional
    public  ResponseEntity update(@RequestBody @Valid UpdateClubDTO dto){
        UpdateClubDTO clubDTO = clubService.update(dto);
        return ResponseEntity.ok(clubDTO);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        clubService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clubName}/retrospecto")
    public ResponseEntity<ClubRestrospectveDTO> getRetrospecto(@PathVariable Club clubName) {
        ClubRestrospectveDTO retrospecto = matchService.clubRestrospectve(clubName);
        return ResponseEntity.ok(retrospecto);
    }
    @GetMapping("/retrospecto/adversarios")
    public ResponseEntity<List<AdversaryRestrospectiveDTO>> getRetrospectoContraAdversarios(
            @RequestParam Club clubName) {
        List<AdversaryRestrospectiveDTO> retrospecto = matchService.calcularRetrospectoContraAdversarios(clubName);
        return ResponseEntity.ok(retrospecto);
    }

}
