package heitor.projetofinal.meli.service;

import heitor.projetofinal.meli.domain.club.Club;
import heitor.projetofinal.meli.domain.club.dto.DetailClub;
import heitor.projetofinal.meli.domain.club.dto.ListClubDTO;
import heitor.projetofinal.meli.domain.club.dto.UpdateClubDTO;
import heitor.projetofinal.meli.domain.repository.ClubRepository;
import heitor.projetofinal.meli.domain.club.dto.CreateClubDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


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

   public Page<ListClubDTO> list(Pageable pageable){
        return  clubRepository.findAll(pageable)
                .map(club -> modelMapper.map(club, ListClubDTO.class));
   }

    public DetailClub datail(Long id){
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        return modelMapper.map( club, DetailClub.class);
    }


    public UpdateClubDTO update(UpdateClubDTO dto){
        Club club =  modelMapper.map(dto, Club.class);
        clubRepository.getReferenceById(club.getId());
        club = clubRepository.save(club);
        return  modelMapper.map(club, UpdateClubDTO.class);
    }

    public void delete(@PathVariable Long id){
      Optional<Club>  club =   clubRepository.findById(id);
      if(club.isPresent()){
          Club clubTenp =  club.get();
          clubTenp.setAtivo(false);
          clubRepository.save(clubTenp);
      }else{
          throw new RuntimeException("Club not found");
      }



    }

}
