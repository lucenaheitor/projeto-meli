package heitor.projetofinal.meli.service;


import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.domain.stadium.Stadium;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.CreateStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.DetailStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.ListStadiumDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StadiumService {
    @Autowired
    private ModelMapper modelMapper;

    private StadiumRepository stadiumRepository;

    public CreateStadiumDTO createStadium(CreateStadiumDTO createStadiumDTO) {
        Stadium stadium = modelMapper.map(createStadiumDTO, Stadium.class);
        stadium = stadiumRepository.save(stadium);
        return modelMapper.map(stadium, CreateStadiumDTO.class);
    }

    public Page<ListStadiumDTO> listStadium(Pageable pageable) {
        return   stadiumRepository.findAll(pageable)
                .map(stadium -> modelMapper.map(stadium, ListStadiumDTO.class));
    }

    public DetailStadiumDTO detailStadium(Long id) {
         Stadium stadium =  stadiumRepository.findById(id).orElse(null);
         return modelMapper.map(stadium, DetailStadiumDTO.class);
    }


}
