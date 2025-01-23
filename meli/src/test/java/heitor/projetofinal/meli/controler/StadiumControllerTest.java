package heitor.projetofinal.meli.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.club.club_dto.ListClubDTO;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.CreateStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.DetailStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.ListStadiumDTO;
import heitor.projetofinal.meli.domain.state.State;
import heitor.projetofinal.meli.service.StadiumService;
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

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
class StadiumControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StadiumRepository stadiumRepository;

    @MockitoBean
    StadiumService stadiumService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register() throws Exception {
        CreateStadiumDTO  dto = new CreateStadiumDTO();
        dto.setName("test");
        dto.setState(State.ES);

        when(stadiumService.createStadium(any(CreateStadiumDTO.class))).thenReturn(dto);

        var response = mockMvc.perform(post("/stadiums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());


    }

    @Test
    void list() throws Exception {
        ListStadiumDTO stadium = new ListStadiumDTO(1L, "stadium test");
        Page<ListStadiumDTO> page = new PageImpl<>(Collections.singletonList(stadium), PageRequest.of(0, 5), 1);
        var response = mockMvc.perform(get("/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0").param("size", "5"))
                .andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void datail() throws Exception {
        DetailStadiumDTO dto = new DetailStadiumDTO();
        dto.setId(1L);
        dto.setName("test");
        dto.setState(State.SP);

        when(stadiumService.detailStadium(any())).thenReturn(dto);

         var response = mockMvc.perform(get("/stadiums/1"))
                 .andReturn().getResponse();
         Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void upadte() {
    }
}