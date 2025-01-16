package heitor.projetofinal.meli.domain.stadium.stadium_dto;

import heitor.projetofinal.meli.domain.state.State;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStadiumDTO {

    @NotNull
    private String name;

    @NotNull
    private State state;


}
