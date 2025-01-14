package heitor.projetofinal.meli.domain.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.state.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "match")
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

    @Column(nullable = false)
    private String stadium;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "dd/MM/yyyy HH:mm")
    private LocalDate matchDate;
}
