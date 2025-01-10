package heitor.projetofinal.meli.domain.club;

import heitor.projetofinal.meli.domain.state.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Table(name = "club")
@Entity(name =  "Club")
@Data
@EqualsAndHashCode(of = "id")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private State state;

    private LocalDate date;

    private boolean ativo = true;







}
