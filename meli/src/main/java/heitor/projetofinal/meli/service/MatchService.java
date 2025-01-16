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
        var homeClub = clubRepository.findById(dto.getHomeTeam_id())
                .orElseThrow(() -> new ValidationExcepetion("Home club not found"));
        var awayClub = clubRepository.findById(dto.getAwayTeam_id())
                .orElseThrow(() -> new ValidationExcepetion("Away club not found"));

        if (homeClub.getId().equals(awayClub.getId())) {
            throw new ValidationExcepetion("A match cannot be created between the same club.");
        }

        var stadium = stadiumRepository.findById(dto.getStadium_id())
                .orElseThrow(() -> new ValidationExcepetion("Stadium not found"));

        Match match = new Match();

        // Determina qual clube será o mandante com base no estado do estádio
        if (stadium.getState().equals(homeClub.getState())) {
            match.setHomeTeam(homeClub);
            match.setAwayTeam(awayClub);
        } else if (stadium.getState().equals(awayClub.getState())) {
            match.setHomeTeam(awayClub);
            match.setAwayTeam(homeClub);
        } else {
            throw new ValidationExcepetion("Stadium state does not match either club.");
        }

        match.setMatchDate(dto.getMatchDate());
        match.setStadium(stadium);
        match.setHomeTeamScore(dto.getHomeTeamScore());
        match.setAwayTeamScore(dto.getAwayTeamScore());

        matchesRepository.save(match);

        return modelMapper.map(match, CreateMatchDTO.class);
    }

}
