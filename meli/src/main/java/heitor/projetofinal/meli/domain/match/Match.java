package heitor.projetofinal.meli.domain.match;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.state.State;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    @Enumerated(EnumType.STRING)
    private State state;
}
