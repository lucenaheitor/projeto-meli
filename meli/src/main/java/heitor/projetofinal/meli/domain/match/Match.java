package heitor.projetofinal.meli.domain.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.stadium.Stadium;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "matches")
@Entity(name = "Match")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private Club homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private Club awayTeam;

    private  Integer homeTeamScore;
    private  Integer awayTeamScore;

    @ManyToOne
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;
}
