package heitor.projetofinal.meli.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StadiumServiceTest {


    @InjectMocks
    private StadiumService stadiumService;

    @Mock
    private StadiumRepository stadiumRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
}
