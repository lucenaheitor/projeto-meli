package heitor.projetofinal.meli.domain.stadium;

import heitor.projetofinal.meli.domain.state.State;
import jakarta.persistence.*;
import lombok.*;


@Table(name ="stadium")
@Entity(name = "Stadium")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;
}
