package heitor.projetofinal.meli.infra.service;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.club.ClubRepository;
import heitor.projetofinal.meli.domain.club.CreateClubDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ModelMapper modelMapper;


    public CreateClubDTO register(CreateClubDTO dto){
        Club club = modelMapper.map(dto, Club.class);
        club = clubRepository.save(club);
        return  modelMapper.map(club, CreateClubDTO.class);
    }


}
