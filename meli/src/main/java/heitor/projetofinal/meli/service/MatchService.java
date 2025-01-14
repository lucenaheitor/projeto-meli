package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.repository.MatchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MatchRepository matchRepository;

    public CreateMatchDTO createMatch(CreateMatchDTO createMatchDTO) {
        Match match =  modelMapper.map(createMatchDTO, Match.class);
        matchRepository.save(match);
        return modelMapper.map(match, CreateMatchDTO.class);
    }
}
