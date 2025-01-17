package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.DetailMatchesDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
import heitor.projetofinal.meli.domain.match.dto_match.UpdateMatchDTO;
import heitor.projetofinal.meli.domain.match.high_search.ClubRestrospectveDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.repository.MatchesRepository;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.infra.excepetion.ValidationExcepetion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.lang.annotation.Native;
import java.util.List;

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
        var homeClub = clubRepository.findByName(dto.getHomeTeam())
                .orElseThrow(() -> new ValidationExcepetion("Home club not found: " + dto.getHomeTeam()));
        var awayClub = clubRepository.findByName(dto.getAwayTeam())
                .orElseThrow(() -> new ValidationExcepetion("Away club not found: " + dto.getAwayTeam()));

        if (homeClub.getId().equals(awayClub.getId())) {
            throw new ValidationExcepetion("A match cannot be created between the same club.");
        }

        var stadium = stadiumRepository.findByName(dto.getStadium())
                .orElseThrow(() -> new ValidationExcepetion("Stadium not found: " + dto.getStadium()));

        Match match = new Match();

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

  public  Page<ListMatches> listMatches( @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        return  matchesRepository.findAll(pageable)
                .map(match -> modelMapper.map(match, ListMatches.class));
  }

  public DetailMatchesDTO detailMatches(Long id) {
      Match Match = matchesRepository.findById(id)
              .orElseThrow(() -> new ValidationExcepetion("Match not found"));
      return modelMapper.map(Match, DetailMatchesDTO.class);
  }

  public UpdateMatchDTO updateMatch(UpdateMatchDTO dto) {
        Match match = matchesRepository.getReferenceById(dto.getId());
        modelMapper.map(dto, match);
        matchesRepository.save(match);
        return modelMapper.map(match, UpdateMatchDTO.class);
  }

  public void deleteMatch(Long id) {
        matchesRepository.deleteById(id);
  }


  public ClubRestrospectveDTO clubRestrospectve(Club  clubName) {

      List<Match> matches  = matchesRepository.findByHomeTeamOrAwayTeam(clubName, clubName);

      Integer totalWins = 0;
      Integer totalDraws = 0;
      Integer totalLosses = 0;
      Integer totalGoalsScored = 0;
      Integer totalGoalsConceded = 0;

      for (Match match : matches) {
           boolean isHomeTeam = match.getHomeTeam().equals(clubName);

          int goalsScored = isHomeTeam ? match.getHomeTeamScore() : match.getAwayTeamScore();
          int goalsConceded = isHomeTeam ? match.getAwayTeamScore() : match.getHomeTeamScore();

          totalGoalsScored += goalsScored;
          totalGoalsConceded += goalsConceded;

          if (goalsScored > goalsConceded) {
              totalWins++;
          } else if (goalsScored == goalsConceded) {
              totalDraws++;
          } else {
              totalLosses++;
          }
      }
      return new ClubRestrospectveDTO(totalWins, totalDraws, totalLosses, totalGoalsScored, totalGoalsConceded);

  }

}



