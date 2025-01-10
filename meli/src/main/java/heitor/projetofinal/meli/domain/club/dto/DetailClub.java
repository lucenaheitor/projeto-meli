package heitor.projetofinal.meli.domain.club.dto;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.state.State;

import java.time.LocalDate;

public record DetailClub(Long id, String name, State state, LocalDate date, boolean ativo) {

    public DetailClub(Club club){
        this(club.getId(), club.getName(), club.getState(), club.getDate(), club.isAtivo());
    }

}
