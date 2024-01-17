package com.recycle.recycle.service;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.dto.EstablishmentDTO;
import com.recycle.recycle.mapper.EstablishmentMapper;
import com.recycle.recycle.repository.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstablishmentService {
    private EstablishmentRepository establishmentRepository;
    private EntityService entityService;
    private EstablishmentMapper establishmentMapper;

    @Autowired
    public void establishmentSevice(EstablishmentRepository establishmentRepository,
                                    EntityService entityService,
                                    EstablishmentMapper establishmentMapper) {
        this.establishmentRepository = establishmentRepository;
        this.entityService = entityService;
        this.establishmentMapper = establishmentMapper;
    }

    public EstablishmentDTO registerEstablishment(EstablishmentDTO establishmentDTO) {
        Establishment newEstablishment = establishmentMapper.convertToEstablishment(establishmentDTO);
        newEstablishment.setAddress(entityService.getAddress(newEstablishment.getAddress()));
        Establishment savedEstablishment = establishmentRepository.save(newEstablishment);
        return establishmentMapper.establishmentToDTO(savedEstablishment);
    }

    public List<Establishment> getAllEstablishment() {
        return establishmentRepository.findAll();
    }

    public Optional<Establishment> getEstablishmentByID(String establishmentId) {
        return establishmentRepository.findById(establishmentId);
    }

    public EstablishmentDTO updateEstablishment(String establishmentId, EstablishmentDTO establishmentDTO) {
        Establishment establishmentToUpdate = establishmentMapper.convertToEstablishment(establishmentDTO);
        establishmentToUpdate.setAddress(entityService.getAddress(establishmentToUpdate.getAddress()));
        establishmentToUpdate.setEstablishmentId(establishmentId);
        Establishment updatedEstablishment = establishmentRepository.save(establishmentToUpdate);
        return establishmentMapper.establishmentToDTO(updatedEstablishment);
    }

    public boolean deleteEstablishment(String establishmentId) {
        Optional<Establishment> establishmentToDelete = establishmentRepository.findById(establishmentId);
        if (establishmentToDelete.isPresent()) {
            establishmentRepository.delete(establishmentToDelete.get());
            return true;
        }
        return false;

    }


}
