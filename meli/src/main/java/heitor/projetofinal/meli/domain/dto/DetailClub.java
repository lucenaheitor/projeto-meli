package heitor.projetofinal.meli.domain.dto;

import heitor.projetofinal.meli.domain.state.State;

import java.time.LocalDate;

public record DetailClub(Long id, String Nome, State state, LocalDate date, boolean ativo) {

}
