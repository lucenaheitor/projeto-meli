package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.stadium.stadium_dto.CreateStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.DetailStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.ListStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.UpdateStadiumDTO;
import heitor.projetofinal.meli.service.StadiumService;
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
    @GetMapping
    public  ResponseEntity<Page<ListStadiumDTO>> list(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        Page page  = stadiumService.listStadium(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<DetailStadiumDTO> datail(@PathVariable Long id) {
         DetailStadiumDTO detailDTO = stadiumService.detailStadium(id);
         return ResponseEntity.ok(detailDTO);
    }

    @PutMapping
    @Transactional
    public  ResponseEntity upadte(@RequestBody @Valid UpdateStadiumDTO dto) {
        UpdateStadiumDTO updateDTO =  stadiumService.updateStadium(dto);
        return ResponseEntity.ok(updateDTO);
    }
}
