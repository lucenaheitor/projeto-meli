package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.controler.exception_match.ValidationMatch;
import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.match.Match;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.DetailMatchesDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
