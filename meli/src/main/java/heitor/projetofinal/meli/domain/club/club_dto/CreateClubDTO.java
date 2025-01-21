package heitor.projetofinal.meli.domain.club.club_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import heitor.projetofinal.meli.domain.state.State;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClubDTO {
    @NotNull
    String name;

    @NotNull
    State state;

    @NotNull
    @PastOrPresent
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate date;

}
