package heitor.projetofinal.meli.domain.match.dto_match;

import heitor.projetofinal.meli.domain.club.Club;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchDTO {


    private Long id;
    private Club homeTeam;
    private Club awayTeam;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private String stadium;
    private LocalDate matchDate;
}
