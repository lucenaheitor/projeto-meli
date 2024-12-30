package heitor.projetofinal.meli.domain.club;

import heitor.projetofinal.meli.domain.state.State;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "club")
@Entity(name =  "Club")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private State state;



}
