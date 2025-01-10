package heitor.projetofinal.meli.domain.club.dto;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.state.State;

public class ListClubDTO {

    private String name;
    private State state;

    public ListClubDTO(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public ListClubDTO(Club club) {
        this(club.getName(), club.getState());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
