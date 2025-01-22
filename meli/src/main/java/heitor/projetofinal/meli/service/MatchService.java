package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.DetailMatchesDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
import heitor.projetofinal.meli.domain.match.dto_match.UpdateMatchDTO;
import heitor.projetofinal.meli.domain.match.high_search.*;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.repository.MatchesRepository;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.infra.excepetion.ValidationExcepetion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public ClubRestrospectveDTO clubRestrospectve(Club club) {
        List<Match> matches = matchesRepository.findByHomeTeamOrAwayTeam(club, club);

        int totalWins = 0;
        int totalDraws = 0;
        int totalLosses = 0;
        int totalGoalsScored = 0;
        int totalGoalsConceded = 0;

        for (Match match : matches) {
            boolean isHomeTeam = match.getHomeTeam().equals(club);

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

        return new ClubRestrospectveDTO(club.getName(), totalWins, totalDraws, totalLosses, totalGoalsScored, totalGoalsConceded);
    }


    public List<AdversaryRestrospectiveDTO> calcularRetrospectoContraAdversarios(String clubName) {
        Club club = clubRepository.findByName(clubName)
                .orElseThrow(() -> new IllegalArgumentException("Club not found: " + clubName));

        List<Match> matches = matchesRepository.findByHomeTeamOrAwayTeam(club, club);

        Map<String, AdversaryRestrospectiveDTO> retrospectoPorAdversario = new HashMap<>();

        for (Match match : matches) {
            boolean isHomeTeam = match.getHomeTeam().equals(club);

            Club adversary = isHomeTeam ? match.getAwayTeam() : match.getHomeTeam();
            int goalsScored = isHomeTeam ? match.getHomeTeamScore() : match.getAwayTeamScore();
            int goalsConceded = isHomeTeam ? match.getAwayTeamScore() : match.getHomeTeamScore();

            AdversaryRestrospectiveDTO dto = retrospectoPorAdversario.computeIfAbsent(adversary.getName(),
                    key -> new AdversaryRestrospectiveDTO(adversary.getName(), 0, 0, 0, 0, 0));

            dto.setTotalGoalsScored(dto.getTotalGoalsScored() + goalsScored);
            dto.setTotalGoalsConceded(dto.getTotalGoalsConceded() + goalsConceded);

            if (goalsScored > goalsConceded) {
                dto.setTotalWins(dto.getTotalWins() + 1);
            } else if (goalsScored == goalsConceded) {
                dto.setTotalDraws(dto.getTotalDraws() + 1);
            } else {
                dto.setTotalLosses(dto.getTotalLosses() + 1);
            }
        }

        return new ArrayList<>(retrospectoPorAdversario.values());
    }

    public DirectConfrontation getDirectConfrontations(Club club1, Club club2) {
        List<Match> matches = matchesRepository.findByHomeTeamOrAwayTeam(club1, club2);

        RetrospectDTO retrospect1 = new RetrospectDTO(club1.getName(), 0, 0, 0, 0, 0);
        RetrospectDTO retrospect2 = new RetrospectDTO(club2.getName(), 0, 0, 0, 0, 0);

        for (Match match : matches) {
            boolean isClub1Home = match.getHomeTeam().equals(club1);

            int club1Goals = isClub1Home ? match.getHomeTeamScore() : match.getAwayTeamScore();
            int club2Goals = isClub1Home ? match.getAwayTeamScore() : match.getHomeTeamScore();

            retrospect1.setTotalGoalsScored(retrospect1.getTotalGoalsScored() + club1Goals);
            retrospect1.setTotalGoalsConceded(retrospect1.getTotalGoalsConceded() + club2Goals);

            retrospect2.setTotalGoalsScored(retrospect2.getTotalGoalsScored() + club2Goals);
            retrospect2.setTotalGoalsConceded(retrospect2.getTotalGoalsConceded() + club1Goals);

            // Determina o vencedor e atualiza os resultados
            if (club1Goals > club2Goals) {
                retrospect1.setTotalWins(retrospect1.getTotalWins() + 1);
                retrospect2.setTotalLosses(retrospect2.getTotalLosses() + 1);
            } else if (club1Goals == club2Goals) {
                retrospect1.setTotalDraws(retrospect1.getTotalDraws() + 1);
                retrospect2.setTotalDraws(retrospect2.getTotalDraws() + 1);
            } else {
                retrospect1.setTotalLosses(retrospect1.getTotalLosses() + 1);
                retrospect2.setTotalWins(retrospect2.getTotalWins() + 1);
            }
        }

        return new DirectConfrontation(matches, retrospect1, retrospect2);
    }

    public List<RankingDTO> getRanking(String criteria) {
        List<Club> clubs = clubRepository.findAll();

        List<RankingDTO> ranking = new ArrayList<>();

        for (Club club : clubs) {
            List<Match> matches = matchesRepository.findByHomeTeamOrAwayTeam(club, club);

            if (matches.isEmpty()) {
                continue;
            }

            int totalMatches = matches.size();
            int totalWins = 0;
            int totalGoals = 0;
            int totalPoints = 0;

            for (Match match : matches) {
                boolean isHome = match.getHomeTeam().equals(club);


                int goalsScored = isHome ? match.getHomeTeamScore() : match.getAwayTeamScore();
                int goalsConceded = isHome ? match.getAwayTeamScore() : match.getHomeTeamScore();

                totalGoals += goalsScored;

                if (goalsScored > goalsConceded) {
                    totalWins++;
                    totalPoints += 3;
                } else if (goalsScored == goalsConceded) {
                    totalPoints += 1;
                }
            }

            if ((criteria.equalsIgnoreCase("jogos") && totalMatches > 0) ||
                    (criteria.equalsIgnoreCase("vitorias") && totalWins > 0) ||
                    (criteria.equalsIgnoreCase("gols") && totalGoals > 0) ||
                    (criteria.equalsIgnoreCase("pontos") && totalPoints > 0)) {

                ranking.add(new RankingDTO(
                        club.getName(),
                        totalMatches,
                        totalWins,
                        totalGoals,
                        totalPoints
                ));
            }
        }

        ranking.sort((a, b) -> {
            switch (criteria.toLowerCase()) {
                case "jogos":
                    return Integer.compare(b.getTotalMatches(), a.getTotalMatches());
                case "vitorias":
                    return Integer.compare(b.getTotalWins(), a.getTotalWins());
                case "gols":
                    return Integer.compare(b.getTotalGoals(), a.getTotalGoals());
                case "pontos":
                    return Integer.compare(b.getTotalPoints(), a.getTotalPoints());
                default:
                    throw new IllegalArgumentException("Critério inválido: " + criteria);
            }
        });

        return ranking;
    }

}