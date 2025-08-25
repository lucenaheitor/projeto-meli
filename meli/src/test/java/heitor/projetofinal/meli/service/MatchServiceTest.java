package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.controler.exception_match.ValidationMatch;
import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.club.club_dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.DetailMatchesDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
import heitor.projetofinal.meli.domain.match.dto_match.UpdateMatchDTO;
import heitor.projetofinal.meli.domain.match.high_search.AdversaryRestrospectiveDTO;
import heitor.projetofinal.meli.domain.match.high_search.ClubRestrospectveDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.repository.MatchesRepository;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.domain.stadium.Stadium;
import heitor.projetofinal.meli.domain.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MatchServiceTest {


    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private MatchService matchService;

    @Mock
    private StadiumRepository stadiumRepository;

    @Mock
    private MatchesRepository matchesRepository;

    @Mock
    private ModelMapper modelMapper;


    private CreateMatchDTO createMatchDTO;
    private ListMatches listMatches;
    private Stadium stadium;
    private Match match;
    private DetailMatchesDTO detailMatchesDTO;
    private UpdateMatchDTO updateMatchDTO;

    @Mock
    private List<ValidationMatch> validationMatches;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        Club homeTeam = new Club(1L, "homeTeam", State.SP, LocalDate.of(1954, 03, 12), true );
        Club awayTeam = new Club(2L, "awayTeam", State.RJ, LocalDate.of(1954, 01, 22), true );
        createMatchDTO = new CreateMatchDTO(1L, "homeTeam", "awayTeam", 3, 1, "stadiumTest", LocalDate.of(1954, 03, 12));
        stadium = new Stadium(1L, "Test", State.SP);
        match = new Match( 1L, homeTeam, awayTeam, 3, 2, stadium, LocalDate.of(2025, 7, 23));
        listMatches = new ListMatches(1L, "homeTeam1", "awayTeam1",  1, 2, stadium.getName(), LocalDate.of(2025, 7, 23));
        detailMatchesDTO = new DetailMatchesDTO(1L, homeTeam.getName(), awayTeam.getName(), 3, 2, "stadiumTest", LocalDate.of(2025, 7, 23));
        updateMatchDTO = new UpdateMatchDTO(1L, "STADIUMTESTUPDATE", LocalDate.of(2025, 7, 23));


    }


    @Test
    void createMatchTest(){
        Club homeTeam = new Club(1L, "homeTeam", State.SP, LocalDate.of(1954, 03, 12), true );
        Club awayTeam = new Club(2L, "awayTeam", State.RJ, LocalDate.of(1954, 01, 22), true );
        CreateMatchDTO matchDTO = new CreateMatchDTO(1L, homeTeam.getName(), awayTeam.getName(), 3, 2, stadium.getName(), LocalDate.of(2025, 7, 23));

        when(clubRepository.findByName(homeTeam.getName())).thenReturn(Optional.of(homeTeam));
        when(clubRepository.findByName(awayTeam.getName())).thenReturn(Optional.of(awayTeam));
        when(stadiumRepository.findByName(anyString())).thenReturn(Optional.of(stadium));
        when(matchesRepository.save(any(Match.class))).thenReturn(new Match());
        when(modelMapper.map(any(Match.class), eq(CreateMatchDTO.class))).thenReturn(createMatchDTO);

        doNothing().when(validationMatches).forEach(any());

        CreateMatchDTO result  = matchService.createMatch(matchDTO);

        assertEquals("homeTeam", result.getHomeTeam());
        assertEquals("awayTeam", result.getAwayTeam());
        verify(clubRepository, times(2)).findByName(anyString());
        verify(stadiumRepository, times(1)).findByName(anyString());
    }

    @Test
    void matchService_ListMatches(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Match> matchPage = new PageImpl<>(List.of(match));

        when(matchesRepository.findAll(pageable)).thenReturn(matchPage);
        when(modelMapper.map(any(Match.class), eq(ListMatches.class))).thenReturn(listMatches);

        Page<ListMatches> result = matchService.listMatches(pageable);

        assertEquals(1, result.getTotalElements());
        verify(matchesRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(any(Match.class), eq(ListMatches.class));

    }

    @Test
    void matchService_destailMatch(){

        when(matchesRepository.findById(anyLong())).thenReturn(Optional.of(match));
        when(modelMapper.map(any(Match.class), eq(DetailMatchesDTO.class))).thenReturn(detailMatchesDTO);

        DetailMatchesDTO result =  matchService.detailMatches(detailMatchesDTO.getId());

        assertEquals(detailMatchesDTO.getId(), result.getId());
        verify(matchesRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(Match.class), eq(DetailMatchesDTO.class));

    }

    @Test
    void clubService_updateMatchTest(){
        when(matchesRepository.getReferenceById(anyLong())).thenReturn(match);
        when(modelMapper.map(any(Match.class), eq(UpdateMatchDTO.class))).thenReturn(updateMatchDTO);
        when(matchesRepository.save(any(Match.class))).thenReturn(match);
        when(modelMapper.map(any(UpdateMatchDTO.class), eq(Match.class))).thenReturn(match);


        UpdateMatchDTO result = matchService.updateMatch(updateMatchDTO);

        assertEquals(updateMatchDTO.getId(), result.getId());
        verify(matchesRepository, times(1)).getReferenceById(anyLong());
        verify(modelMapper, times(1)).map(any(Match.class), eq(UpdateMatchDTO.class));
        verify(matchesRepository, times(1)).save(any(Match.class));
    }

    @Test
    void matchService_deleteMatchTest(){
       Long id = 1L;

       matchService.deleteMatch(id);

       verify(matchesRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void matchService_ClubRestrospectveDTO(){
        Club club = new Club();
        club.setName("Clube de Teste");

        Match partidaVitoria = new Match();
        partidaVitoria.setHomeTeam(club);
        partidaVitoria.setAwayTeam(club);
        partidaVitoria.setHomeTeamScore(3);
        partidaVitoria.setAwayTeamScore(1);

        Match partidaEmpate = new Match();
        partidaEmpate.setHomeTeam(new Club());
        partidaEmpate.setAwayTeam(club);
        partidaEmpate.setHomeTeamScore(2);
        partidaEmpate.setAwayTeamScore(2);

        Match partidaDerrota = new Match();
        partidaDerrota.setHomeTeam(club);
        partidaDerrota.setAwayTeam(club);
        partidaDerrota.setHomeTeamScore(1);
        partidaDerrota.setAwayTeamScore(0);

        List<Match> partidas = Arrays.asList(partidaVitoria, partidaEmpate, partidaDerrota);

        when(matchesRepository.findByHomeTeamOrAwayTeam(club, club)).thenReturn(partidas);

        ClubRestrospectveDTO dto = matchService.clubRestrospectve(club);

        assertEquals("Clube de Teste", dto.getClubName());
        assertEquals(2, dto.getTotalWins());
        assertEquals(1, dto.getTotalDraws());
        assertEquals(0, dto.getTotalLosses());
        assertEquals(6, dto.getTotalGoalsScored());
        assertEquals(3, dto.getTotalGoalsConceded());
    }

        @Test
        void calcularRetrospectoContraAdversarios_ok() {
            // given
            Club club = new Club(); club.setName("test club");
            Club rival = new Club(); rival.setName("Rival FC");

            // 3 jogos: W(2x1 em casa), D(0x0 fora), L(1x3 em casa)
            Match m1 = new Match();
            m1.setHomeTeam(club); m1.setAwayTeam(rival);
            m1.setHomeTeamScore(2); m1.setAwayTeamScore(1);

            Match m2 = new Match();
            m2.setHomeTeam(rival); m2.setAwayTeam(club);
            m2.setHomeTeamScore(0); m2.setAwayTeamScore(0);

            Match m3 = new Match();
            m3.setHomeTeam(club); m3.setAwayTeam(rival);
            m3.setHomeTeamScore(1); m3.setAwayTeamScore(3);

            when(clubRepository.findByName("test club")).thenReturn(Optional.of(club));
            // Use any(...) para não travar na instância
            when(matchesRepository.findByHomeTeamOrAwayTeam(any(Club.class), any(Club.class)))
                    .thenReturn(List.of(m1, m2, m3));

            // when
            List<AdversaryRestrospectiveDTO> resp =
                    matchService.calcularRetrospectoContraAdversarios("test club");

            // then (não dependa de índice — encontre pelo nome do adversário)
            AdversaryRestrospectiveDTO rivalDto = resp.stream()
                    .filter(d -> d.getAdversary().equals("Rival FC"))
                    .findFirst()
                    .orElseThrow();

            assertEquals(1, rivalDto.getTotalWins());
            assertEquals(0, rivalDto.getTotalDraws());
            assertEquals(1, rivalDto.getTotalLosses());
            assertEquals(3, rivalDto.getTotalGoalsScored());   // 2 + 0 + 1
            assertEquals(4, rivalDto.getTotalGoalsConceded()); // 1 + 0 + 3

            verify(matchesRepository).findByHomeTeamOrAwayTeam(any(Club.class), any(Club.class));
        }

        @Test
        void calcularRetrospectoContraAdversarios_semPartidas_retornaVazio() {
            Club club = new Club(); club.setName("test club");
            when(clubRepository.findByName("test club")).thenReturn(Optional.of(club));
            when(matchesRepository.findByHomeTeamOrAwayTeam(any(), any())).thenReturn(List.of());

            List<AdversaryRestrospectiveDTO> resp =
                    matchService.calcularRetrospectoContraAdversarios("test club");

            assertTrue(resp.isEmpty());
        }

        @Test
        void calcularRetrospectoContraAdversarios_clubeInexistente_lancaExcecao() {
            when(clubRepository.findByName("nope")).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class,
                    () -> matchService.calcularRetrospectoContraAdversarios("nope"));
        }



}
