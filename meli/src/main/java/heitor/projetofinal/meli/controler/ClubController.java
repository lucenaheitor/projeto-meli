package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.club.club_dto.DetailClub;
import heitor.projetofinal.meli.domain.club.club_dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.club.club_dto.CreateClubDTO;
import heitor.projetofinal.meli.service.ClubService;
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

@RestController
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;


    @PostMapping
    @Transactional
    public ResponseEntity<CreateClubDTO> register(@RequestBody @Valid CreateClubDTO dto, UriComponentsBuilder uriBuilder){

        CreateClubDTO clubDTO = clubService.register(dto);
        URI address = uriBuilder.path("/clubs/{id}").buildAndExpand(clubDTO.getName()).toUri();

        return  ResponseEntity.created(address).body(clubDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ListClubDTO>> listClub( @PageableDefault(size = 5, sort = "name") Pageable pageable){
        Page page = clubService.list(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailClub>  detail(@PathVariable Long id ){
        DetailClub club =  clubService.datail(id);
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

}
