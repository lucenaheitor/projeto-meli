package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.dto.ListClubDTO;
import heitor.projetofinal.meli.domain.dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.dto.CreateClubDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ListClubDTO> getClub(Pageable pageable){
         return  clubRepository.findAll(pageable)
                 .map(club -> modelMapper.map(club, ListClubDTO.class));
    }

    public UpdateClubDTO update( UpdateClubDTO dto){
        Club club = modelMapper.map(dto, Club.class);
        clubRepository.getReferenceById(club.getId());
        clubRepository.save(club);
        return modelMapper.map(club, UpdateClubDTO.class);
    }


}
