package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.club.ClubRepository;
import heitor.projetofinal.meli.domain.club.CreateClubDTO;
import heitor.projetofinal.meli.infra.service.ClubService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<CreateClubDTO> register(@RequestBody @Valid CreateClubDTO dto , UriComponentsBuilder uriBuilder){

        CreateClubDTO clubDTO = clubService.register(dto);
        URI address = uriBuilder.path("/clubs/{id}").buildAndExpand(clubDTO.getNome().toURI);

        return  ResponseEntity.created(address).body(clubDTO);
    }
}
