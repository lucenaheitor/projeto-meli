package heitor.projetofinal.meli.controler.exception_match;

import heitor.projetofinal.meli.domain.match.dto_match.CreateMatchDTO;
import heitor.projetofinal.meli.domain.repository.MatchesRepository;
import heitor.projetofinal.meli.infra.excepetion.ValidationExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidateMatchSameHour  implements  ValidationMatch{

    @Autowired
    private MatchesRepository matchesRepository;

    @Override
    public void validate(CreateMatchDTO data) {
        var  partidaNoMesmoHorario = matchesRepository.existsByMatchDate(data.getMatchDate());
        if (partidaNoMesmoHorario) {
            throw new ValidationExcepetion("Match already exists in this time " + data.getMatchDate());
        }
    }
}
