package heitor.projetofinal.meli.controler.exception_club;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.club.club_dto.CreateClubDTO;

public interface ValidationClub {
    void validate(CreateClubDTO data);
}
