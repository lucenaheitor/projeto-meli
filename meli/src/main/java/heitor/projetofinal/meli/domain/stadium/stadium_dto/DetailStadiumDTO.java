package heitor.projetofinal.meli.domain.stadium.stadium_dto;

import heitor.projetofinal.meli.domain.state.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailStadiumDTO {

    private Long id;
    private String name;
    private State state;
}
