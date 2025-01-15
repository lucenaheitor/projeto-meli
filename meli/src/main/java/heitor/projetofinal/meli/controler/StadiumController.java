package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.stadium.stadium_dto.CreateStadiumDTO;
import heitor.projetofinal.meli.service.StadiumService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("stadiums")
public class StadiumController {

    @Autowired
    private StadiumService stadiumService;

    @PostMapping
    @Transactional
    public ResponseEntity<CreateStadiumDTO> registe (@RequestBody  CreateStadiumDTO dto, UriComponentsBuilder uriBuilder) {
        CreateStadiumDTO createStadiumDTO =  stadiumService.createStadium(dto);
        URI adress =  uriBuilder.path("/stadiums/{id}").buildAndExpand(createStadiumDTO.getName()).toUri();
        return ResponseEntity.created(adress).body(createStadiumDTO);
    }
}
