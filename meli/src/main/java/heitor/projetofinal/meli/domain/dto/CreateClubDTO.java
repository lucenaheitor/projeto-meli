package heitor.projetofinal.meli.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import heitor.projetofinal.meli.domain.state.State;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClubDTO {
    @NotNull
    String nome;

    @NotNull
    State state;

    @NotNull
    @Future
    @JsonFormat(pattern = "dd/mm/yyyy")
    LocalDate date;
}
