package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.repository.MatchesRepository;
import heitor.projetofinal.meli.infra.excepetion.ValidationExcepetion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClubRepository clubRepository;



    @Autowired
    private MatchesRepository matchesRepository;

    public CreateMatchDTO createMatch(CreateMatchDTO dto) {


        if (matchesRepository.existsById(dto.getId())) {
             throw new ValidationExcepetion(" club is not exists");
        }

        if(clubRepository.existsById(dto.getId)

    }
}
