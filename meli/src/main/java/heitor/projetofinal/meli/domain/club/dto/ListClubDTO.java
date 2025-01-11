package heitor.projetofinal.meli.domain.club.dto;

import heitor.projetofinal.meli.domain.state.State;

import java.time.LocalDate;

public class ListClubDTO {

    private  Long id;
    private  String name;
    private  State state;
    private LocalDate date;
    private Boolean ativo;


    public ListClubDTO() {

    }

    public ListClubDTO(Long id, String name, State state, LocalDate date, Boolean ativo) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.date = date;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}