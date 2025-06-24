package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.controler.exception_club.ValidationClub;
import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.club.club_dto.CreateClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.DetailClub;
import heitor.projetofinal.meli.domain.club.club_dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClubServiceTest {


    @InjectMocks
    private ClubService clubService;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private ModelMapper modelMapper;


    @Mock
    private List<ValidationClub> validationClubs;

    private CreateClubDTO createClubDTO;
    private Club club;
    private ListClubDTO listClubDTO;
    private DetailClub detailClub;
    private UpdateClubDTO updateClubDTO;



    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
         club = new Club(1L, "Santos", State.SP, LocalDate.of(1954, 03, 12), true );
         createClubDTO = new CreateClubDTO("Santos", State.SP, LocalDate.of(1954, 03, 12));
         listClubDTO = new ListClubDTO();
         detailClub =  new DetailClub(1L, "test", State.CE, LocalDate.of(1955, 03, 12), true);
         updateClubDTO = new UpdateClubDTO(1L, "test", State.AL, true);
    }

    @Test
    void ClubService_createTest(){
        when(modelMapper.map(createClubDTO, Club.class)).thenReturn(club);
        when(clubRepository.save(club)).thenReturn(club);

        when(validationClubs.isEmpty()).thenReturn(false);

        CreateClubDTO result =  clubService.register(createClubDTO);

        assertNull(result);
        verify(modelMapper).map(createClubDTO, Club.class);
        verify(clubRepository).save(club);
    }

    @Test
    void ClubService_detailTest(){
        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(modelMapper.map(any(), eq(DetailClub.class))).thenReturn(detailClub);

        clubService.detail(club.getId());

        verify(modelMapper).map(club, DetailClub.class);
        verify(clubRepository).findById(anyLong());
    }

    @Test
    void ClubService_listTest(){

        Pageable pageable = PageRequest.of(0, 10);
        Page<Club> clubPage = new PageImpl<>(List.of(club));

        when(clubRepository.findAll(pageable)).thenReturn(clubPage);
        when(validationClubs.isEmpty()).thenReturn(false);
        when(modelMapper.map(any(Club.class), eq(ListClubDTO.class))).thenReturn(listClubDTO);

       Page<ListClubDTO> result = clubService.list(pageable);
       
        assertEquals(1, result.getTotalElements());
       verify(clubRepository, times(1)).findAll(pageable);
       verify(modelMapper).map(any(Club.class), eq(ListClubDTO.class));

    }

    @Test
    void ClubService_updateClubTest(){
        when(clubRepository.getReferenceById(anyLong())).thenReturn(club);
        when(clubRepository.save(club)).thenReturn(club);
        when(modelMapper.map(any(Club.class), eq(UpdateClubDTO.class))).thenReturn(updateClubDTO);

        UpdateClubDTO response =  clubService.update(updateClubDTO);

        assertEquals(updateClubDTO, response);
        verify(clubRepository, times(1)).getReferenceById(anyLong());
        verify(modelMapper).map(any(Club.class), eq(UpdateClubDTO.class));
        verify(clubRepository, times(1)).save(club);
    }

    @Test
    void ClubService_deleteTest(){
        Club club = new Club();
        club.setAtivo(true);

        when(clubRepository.findById(anyLong())).thenReturn(Optional.of(club));
        when(clubRepository.save(any(Club.class))).thenReturn(club);

        clubService.delete(anyLong());

        assertFalse(club.isAtivo());
        verify(clubRepository).findById(anyLong());
        verify(clubRepository).save(club);
    }
    @Test
    void ClubService_deleteElseFlowTest(){
        when(clubRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clubService.delete(anyLong()));

        verify(clubRepository, never()).save(any());

    }

}
