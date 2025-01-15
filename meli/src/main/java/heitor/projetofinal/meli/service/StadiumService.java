package heitor.projetofinal.meli.service;


import heitor.projetofinal.meli.domain.repository.StadiumRepository;
import heitor.projetofinal.meli.domain.stadium.Stadium;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.CreateStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.DetailStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.ListStadiumDTO;
import heitor.projetofinal.meli.domain.stadium.stadium_dto.UpdateStadiumDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StadiumService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StadiumRepository stadiumRepository;

    public CreateStadiumDTO createStadium(CreateStadiumDTO dto) {
        Stadium stadium = modelMapper.map(dto,  Stadium.class);
        stadium = stadiumRepository.save(stadium);
        return modelMapper.map(stadium, CreateStadiumDTO.class);
    }

    public Page<ListStadiumDTO> listStadium(Pageable pageable) {
        return   stadiumRepository.findAll(pageable)
                .map(stadium -> modelMapper.map(stadium, ListStadiumDTO.class));
    }

    public DetailStadiumDTO detailStadium(Long id) {
         Stadium stadium =  stadiumRepository.findById(id)
                 .orElseThrow(() -> new EntityNotFoundException(Stadium.class.getName()));
         return modelMapper.map(stadium, DetailStadiumDTO.class);
    }


    public UpdateStadiumDTO updateStadium(UpdateStadiumDTO dto ) {
        Stadium stadium =   stadiumRepository.getReferenceById(dto.getId());
        modelMapper.map(dto , stadium);
        stadium = stadiumRepository.save(stadium);
        return modelMapper.map(stadium, UpdateStadiumDTO.class);
    }


}
