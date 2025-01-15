package heitor.projetofinal.meli.domain.stadium.stadium_dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStadiumDTO {

    @NotNull
    private Long id;
    private String name;
}
