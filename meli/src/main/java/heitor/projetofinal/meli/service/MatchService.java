package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.repository.MatchesRepository;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.domain.stadium.Stadium;
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
    private StadiumRepository stadiumRepository;

    @Autowired
    private MatchesRepository matchesRepository;

    public CreateMatchDTO createMatch(CreateMatchDTO dto) {
        var homeClub = clubRepository.findById(dto.getHomeTeam().getId())
                .orElseThrow(() -> new ValidationExcepetion("Home club not found"));
        var awayClub = clubRepository.findById(dto.getAwayTeam().getId())
                .orElseThrow(() -> new ValidationExcepetion("Away club not found"));

        if(homeClub.getId().equals(awayClub.getId())) {
            throw new ValidationExcepetion("A match  cannot  be create  between the same club. ");
        }

        var stadium = stadiumRepository.findById(dto.getStadium().getId());

        Match match =  new Match();
        if(dto.getStadium().getState().equals(dto.getHomeTeam().getState())) {
            match.setHomeTeam(homeClub);
            match.setAwayTeam(awayClub);
        } else if (dto.getStadium().getState().equals(dto.getAwayTeam().getState())) {
            match.setHomeTeam(awayClub);
            match.setAwayTeam(homeClub);
        }else {
            throw new ValidationExcepetion("Stadium state does no match either club");
        }

        match.setMatchDate(dto.getMatchDate());
        match.setStadium(dto.getStadium());
        match.setHomeTeamScore(dto.getHomeTeamScore());
        match.setAwayTeamScore(dto.getAwayTeamScore());

        matchesRepository.save(match);

        return modelMapper.map(match, CreateMatchDTO.class);
    }
}
