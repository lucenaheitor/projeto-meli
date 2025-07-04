package heitor.projetofinal.meli.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.club.club_dto.CreateClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.DetailClub;
import heitor.projetofinal.meli.domain.club.club_dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.state.State;
import heitor.projetofinal.meli.service.ClubService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
class ClubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClubService clubService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClubRepository clubRepository;

    private CreateClubDTO createClubDTO;

    @Mock
    private  ClubController clubController;

    @BeforeEach
    void setUp() {
        createClubDTO = new CreateClubDTO("Santos", State.SP, LocalDate.of(1954, 03, 12));
    }

    @Test
    void register() throws Exception {
        when(clubService.register(createClubDTO)).thenReturn(createClubDTO);

        ResponseEntity<CreateClubDTO> response = clubController.register(createClubDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(clubService).register(createClubDTO);
        verify(clubController).register(createClubDTO);

    }

    @Test
    void listClub() throws Exception {
        ListClubDTO club1 = new ListClubDTO(1L, "test2", State.PR, LocalDate.of(1984, 07, 23), true);
        Page<ListClubDTO> page = new PageImpl<>(Collections.singletonList(club1), PageRequest.of(0, 5), 1);
        var response = mockMvc.perform(get("/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0").param("size", "5"))
                .andReturn().getResponse();
        assertEquals(200, response.getStatus());


    }

    @Test
    void detail() throws Exception {
        DetailClub detail = new DetailClub();
        detail.setId(1L);
        detail.setName("test");
        detail.setState(State.SP);
        detail.setDate(LocalDate.of(1984, 07, 23));
        detail.setAtivo(true);

        when(clubService.detail(any())).thenReturn(detail);

        var response = mockMvc.perform(get("/clubs/1"))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void testUpdateClub() throws Exception {
        UpdateClubDTO testDTO = new UpdateClubDTO(
                1L,
                "club test",
                State.SP,
                true
        );
        when(clubService.update(any(UpdateClubDTO.class))).thenReturn(testDTO);
        String jsonContent = objectMapper.writeValueAsString(testDTO);
        var response = mockMvc.perform(put("/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn().getResponse();
        assertEquals(200, response.getStatus());
    }


    @Test
    void delete() {
        Club club = new Club();
        club.setId(1L);
        club.setName("test");
        club.setState(State.SP);
        club.setDate(LocalDate.of(1984, 07, 23));
        club.setAtivo(true);

        if(club.isAtivo()){
            clubService.delete(1L);
            club.setAtivo(false);
        }

        verify(clubService).delete(1L);

    }
}