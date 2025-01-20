package heitor.projetofinal.meli.domain.match.high_search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdversaryRestrospectiveDTO {

    private String adversary; // Nome do adversário
    private Integer totalWins;
    private Integer totalDraws;
    private Integer totalLosses;
    private Integer totalGoalsScored;
    private Integer totalGoalsConceded;
}
