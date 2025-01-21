package heitor.projetofinal.meli.domain.match.high_search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrospectDTO {

    private String clubName;
    private int totalWins;
    private int totalDraws;
    private int totalLosses;
    private int totalGoalsScored;
    private int totalGoalsConceded;
}
