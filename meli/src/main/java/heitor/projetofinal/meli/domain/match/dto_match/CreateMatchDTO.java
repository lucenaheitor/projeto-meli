package heitor.projetofinal.meli.domain.match.dto_match;

import heitor.projetofinal.meli.domain.club.Club;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchDTO {

    @NotNull
    private Long id;

    @NotNull
    private Club homeTeam;

    @NotNull
    private Club awayTeam;

    @NotNull
    private Integer homeTeamScore;

    @NotNull
    private Integer awayTeamScore;

    @NotNull
    private String stadium;

    @NotNull
    private LocalDate matchDate;
}
