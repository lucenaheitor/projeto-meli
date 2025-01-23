package heitor.projetofinal.meli.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.CreateStadiumDTO;
import heitor.projetofinal.meli.domain.state.State;
import heitor.projetofinal.meli.service.StadiumService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
    void list() {
    }

    @Test
    void datail() {
    }

    @Test
    void upadte() {
    }
}