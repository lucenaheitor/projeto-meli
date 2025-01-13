package heitor.projetofinal.meli.domain.club.club_dto;

import heitor.projetofinal.meli.domain.state.State;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClubDTO {

    @NotNull
    private Long id;
    private String name;
    private State state;
    private  boolean ativo;
}
