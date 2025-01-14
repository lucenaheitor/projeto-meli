package heitor.projetofinal.meli.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.club.club_dto.CreateClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.DetailClub;
import heitor.projetofinal.meli.domain.club.club_dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.club_dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.state.State;
import heitor.projetofinal.meli.service.ClubService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void register() throws Exception {
        CreateClubDTO testDTO = new CreateClubDTO();
         testDTO.setName("test");
         testDTO.setState(State.SP);
         testDTO.setDate(LocalDate.of(1979, 1, 24));

         when(clubService.register(any(CreateClubDTO.class))).thenReturn(testDTO);

         var response = mockMvc.perform(post("/clubs")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(testDTO)))
                 .andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());


    }

    @Test
    void listClub() throws Exception {
       ListClubDTO club1 = new ListClubDTO(1L, "test2", State.PR, LocalDate.of(1984, 07, 23), true);
       Page<ListClubDTO> page = new PageImpl<>(Collections.singletonList(club1), PageRequest.of(0, 5), 1);
        var response = mockMvc.perform(get("/clubs")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0").param("size", "5"))
                .andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());


    }

    @Test
    void detail() throws Exception {
        DetailClub detail = new DetailClub();
        detail.setId(1L);
        detail.setName("test");
        detail.setState(State.SP);
        detail.setDate(LocalDate.of(1984, 07, 23));
        detail.setAtivo(true);

        when(clubService.datail(any())).thenReturn(detail);

        var response = mockMvc.perform(get("/clubs/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void uodate() throws Exception {
        UpdateClubDTO testDTO = new UpdateClubDTO();
        testDTO.setId(1L);
        testDTO.setName("test");
        testDTO.setState(State.SP);
        testDTO.setAtivo(true);

        when(clubService.datail(any())).thenReturn();

        var response = mockMvc.perform(put("/clubs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDTO))
    }

    @Test
    void delete() {
    }
}