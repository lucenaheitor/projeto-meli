package heitor.projetofinal.meli.domain.match.high_search;

import heitor.projetofinal.meli.domain.match.Match;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectConfrontation {
    private List<Match> matches;
    private RetrospectDTO club1Retrospect;
    private RetrospectDTO club2Retrospect;
}
