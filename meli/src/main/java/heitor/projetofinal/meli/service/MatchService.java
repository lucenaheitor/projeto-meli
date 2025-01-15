package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.repository.MatchRepository;
import heitor.projetofinal.meli.infra.excepetion.ValidationExcepetion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private  MatchRepository matchesRepository;

//    public CreateMatchDTO createMatch(CreateMatchDTO createMatchDTO) {
//
//        if (matchesRepository.existsById(createMatchDTO.getId())) {
//             throw new ValidationExcepetion(" club is not exists");
//        }
//
//    }
}
