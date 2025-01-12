package heitor.projetofinal.meli.domain.club.dto;

import heitor.projetofinal.meli.domain.state.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailClub {

    private  Long id;
    private String name;
    private State state;
    private LocalDate date;
    private  boolean ativo;



}
