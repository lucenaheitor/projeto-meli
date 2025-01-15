package heitor.projetofinal.meli.controler;

import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.service.MatchService;
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
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;


//    public ResponseEntity postMatch(@RequestBody CreateMatchDTO  dto, UriComponentsBuilder uriBuilder) {
//        CreateMatchDTO createMatchDTO=  matchService.createMatch(dto);
//        URI adress =  uriBuilder.path("/matches/{id}").buildAndExpand(createMatchDTO.getId()).toUri();
//
//        return ResponseEntity.created(adress).body(createMatchDTO);
//    }

}
