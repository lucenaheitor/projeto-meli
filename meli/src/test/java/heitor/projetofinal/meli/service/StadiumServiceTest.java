package heitor.projetofinal.meli.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.domain.stadium.Stadium;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.CreateStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.DetailStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.UpdateStadiumDTO;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StadiumServiceTest {


    @InjectMocks
    private StadiumService stadiumService;

    @Mock
    private StadiumRepository stadiumRepository;

    @Mock
    private ModelMapper modelMapper;

    private CreateStadiumDTO createStadiumDTO;
    private Stadium stadium;
    private DetailStadiumDTO detailStadiumDTO;
    private UpdateStadiumDTO updateStadiumDTO;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        createStadiumDTO = new CreateStadiumDTO("Test", State.GO);
        stadium = new Stadium(1L, "Test",State.SP);
        detailStadiumDTO = new DetailStadiumDTO(1L, "Test", State.SP);
        updateStadiumDTO = new UpdateStadiumDTO();
    }

    @Test
    void createStadium() {
        when(modelMapper.map(createStadiumDTO, Stadium.class)).thenReturn(stadium);
        when(stadiumRepository.save(stadium)).thenReturn(stadium);
        when(modelMapper.map(stadium, CreateStadiumDTO.class)).thenReturn(createStadiumDTO);

        CreateStadiumDTO result = stadiumService.createStadium(createStadiumDTO);

        assertEquals(result, createStadiumDTO);
        verify(modelMapper).map(stadium, CreateStadiumDTO.class);
        verify(stadiumRepository).save(stadium);
    }

    @Test
    void listStadiums() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Stadium>  page = new PageImpl<>(List.of(stadium));

        when(stadiumRepository.findAll(pageable)).thenReturn(page);

        stadiumService.listStadium(pageable);

        verify(stadiumRepository).findAll(pageable);
    }

    @Test
    void detailStadiumTest(){

        when(stadiumRepository.findById(anyLong())).thenReturn(Optional.ofNullable(stadium));
        when(modelMapper.map(stadium, DetailStadiumDTO.class)).thenReturn(detailStadiumDTO);

        DetailStadiumDTO result = stadiumService.detailStadium(1L);
        assertEquals(result, detailStadiumDTO);
        verify(modelMapper).map(stadium, DetailStadiumDTO.class);
        verify(stadiumRepository).findById(anyLong());
    }

    @Test
    void updateStadium() {

    }
}
