package heitor.projetofinal.meli.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.service.ClubService;
import heitor.projetofinal.meli.service.MatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;


    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void postMatch() throws Exception {
        CreateMatchDTO dto  = new CreateMatchDTO();
        dto.setId(1L);
        dto.setHomeTeam("coringao test");
        dto.setAwayTeam("palmeiras test");
        dto.setStadium(" stadium test");
        dto.setHomeTeamScore(2);
        dto.setAwayTeamScore(1);
        dto.setMatchDate(LocalDate.of(2025, 02, 12));

        when(matchService.createMatch(any(CreateMatchDTO.class))).thenReturn(dto);

        var response = mockMvc.perform(post("/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void listMatch() {
    }

    @Test
    void detailMatch() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}