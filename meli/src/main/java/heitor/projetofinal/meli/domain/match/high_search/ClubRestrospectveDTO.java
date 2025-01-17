package heitor.projetofinal.meli.domain.match.high_search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubRestrospectveDTO {

    private int totalWins;
    private int totalDraws;
    private int totalLosses;
    private int totalGoalsScored;
    private int totalGoalsConceded;
}
