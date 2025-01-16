package heitor.projetofinal.meli.domain.match.dto_match;

import com.fasterxml.jackson.annotation.JsonFormat;
import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.stadium.Stadium;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchDTO {

    private Long id;

    @NotNull
    private String homeTeam;

    @NotNull
    private String awayTeam;

    @NotNull
    private Integer homeTeamScore;

    @NotNull
    private Integer awayTeamScore;

    @NotNull
    private String  stadium;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "dd-MM-yyyy")
    private LocalDate matchDate;
}
