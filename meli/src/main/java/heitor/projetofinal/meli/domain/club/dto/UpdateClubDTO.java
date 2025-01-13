package heitor.projetofinal.meli.domain.club.dto;


import heitor.projetofinal.meli.domain.state.State;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClubDTO{
        @NotNull
   private  Long id;
   private String Name;
   private State state;
}
