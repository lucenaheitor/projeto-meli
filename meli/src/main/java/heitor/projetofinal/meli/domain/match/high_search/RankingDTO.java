package heitor.projetofinal.meli.domain.match.high_search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {

    private String clubName;
    private int totalMatches;
    private int totalWins;
    private int totalGoals;
    private int totalPoints;;

}
