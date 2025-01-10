package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.club.dto.DetailClub;
import heitor.projetofinal.meli.domain.club.dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.club.dto.CreateClubDTO;
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
    public ResponseEntity<Page<ListClubDTO>>listClub(@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page page = clubService.getClub(pageable);
         return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdateClubDTO upadeDTO){
         UpdateClubDTO clubDTO = clubService.update(upadeDTO);

         return ResponseEntity.ok(clubDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        clubService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailClub>  detail(@PathVariable Long id ){
        DetailClub club =  clubService.datail(id);
        return ResponseEntity.ok(club);
    }


}
