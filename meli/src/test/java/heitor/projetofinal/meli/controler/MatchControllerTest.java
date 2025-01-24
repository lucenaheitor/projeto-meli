package heitor.projetofinal.meli.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.club.club_dto.DetailClub;
import heitor.projetofinal.meli.domain.club.club_dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.match.dto_match.DetailMatchesDTO;
import heitor.projetofinal.meli.domain.match.dto_match.ListMatches;
import heitor.projetofinal.meli.domain.match.dto_match.UpdateMatchDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.state.State;
import heitor.projetofinal.meli.service.ClubService;
import heitor.projetofinal.meli.service.MatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
class  MatchControllerTest {

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
    void listMatch() throws Exception {
        ListMatches matches = new ListMatches(1L, "coringao", "palmerao test", 2, 1, "stadium test ",LocalDate.of(2025, 02, 12));
        Page<ListMatches> page = new PageImpl<>(Collections.singletonList(matches), PageRequest.of(0, 5), 1);
        var response = mockMvc.perform(get("/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0").param("size", "5"))
                .andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void detailMatch() throws Exception {
        DetailMatchesDTO dto = new DetailMatchesDTO();
        dto.setId(1L);
        dto.setHomeTeamName("coringao test");
        dto.setAwayTeamName("palmeiras test");
        dto.setHomeTeamScore(2);
        dto.setAwayTeamScore(1);
        dto.setMatchDate(LocalDate.of(2025, 02, 12));


        when(matchService.detailMatches(any())).thenReturn(dto);

        var response = mockMvc.perform(get("/clubs/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void update() throws Exception {
        UpdateMatchDTO dto = new UpdateMatchDTO(
                1L,
                "morumbi teste",
                LocalDate.of(2025, 02, 17)
        );
        when(matchService.updateMatch(any(UpdateMatchDTO.class))).thenReturn(dto);
        var JsonContent = objectMapper.writeValueAsString(dto);

        var response = mockMvc.perform(put("/matches")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonContent))
                .andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void delete() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/matches/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(204, response.getStatus());

    }
}