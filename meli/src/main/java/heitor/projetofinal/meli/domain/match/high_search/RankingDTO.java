package heitor.projetofinal.meli.domain.match.high_search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {

    private Integer totalGames;
    private Integer totalWin;
    private Integer totalScore;
    private Integer totalPoints;

}
