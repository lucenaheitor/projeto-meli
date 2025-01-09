package heitor.projetofinal.meli.domain.club.dto;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.state.State;

public class ListClubDTO {

    private String nome;
    private State state;

    public ListClubDTO(String nome, State state) {
        this.nome = nome;
        this.state = state;
    }

    public ListClubDTO(Club club) {
        this(club.getName(), club.getState());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
