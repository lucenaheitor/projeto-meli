package heitor.projetofinal.meli.domain.club.dto;


import heitor.projetofinal.meli.domain.state.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UpdateClubDTO {

    String  nome;
    State state;
}
