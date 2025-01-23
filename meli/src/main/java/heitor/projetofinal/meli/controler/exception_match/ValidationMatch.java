package heitor.projetofinal.meli.controler.exception_match;

import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;

public interface ValidationMatch {

    void validate(CreateMatchDTO data);
}
