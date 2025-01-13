package heitor.projetofinal.meli.controler.exception_club;

import heitor.projetofinal.meli.domain.club.club_dto.CreateClubDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.infra.excepetion.ValidationExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationClubName  implements ValidationClub{

    @Autowired
    private ClubRepository clubRepository;

    @Override
    public void validate(CreateClubDTO data) {
        if(clubRepository.existsByName(data.getName())){
            throw  new ValidationExcepetion(" This Name Already Exist");
        }

    }
}
