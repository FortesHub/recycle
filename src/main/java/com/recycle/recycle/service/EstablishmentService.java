package com.recycle.recycle.service;

import com.recycle.recycle.domain.*;
import com.recycle.recycle.dto.EstablishmentDTO;
import com.recycle.recycle.mapper.EstablishmentMapper;
import com.recycle.recycle.repository.EstablishmentRepository;
import com.recycle.recycle.service.utils.AddressUtils;
import com.recycle.recycle.service.utils.MachineUtils;
import com.recycle.recycle.service.utils.PersonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstablishmentService {
    private EstablishmentRepository establishmentRepository;
    private EstablishmentMapper establishmentMapper;
    private AddressUtils addressUtils;
    private PersonUtils personUtils;
    private MachineUtils machineUtils;

    @Autowired
    public void establishmentSevice(EstablishmentRepository establishmentRepository,
                                    EstablishmentMapper establishmentMapper,
                                    AddressUtils addressUtils,
                                    PersonUtils personUtils,
                                    MachineUtils machineUtils
    ) {
        this.establishmentRepository = establishmentRepository;
        this.establishmentMapper = establishmentMapper;
        this.addressUtils = addressUtils;
        this.personUtils = personUtils;
        this.machineUtils = machineUtils;
    }

    public EstablishmentDTO registerEstablishment(EstablishmentDTO establishmentDTO) {
        Establishment newEstablishment = establishmentMapper.convertToEstablishment(establishmentDTO);
        Address address = addressUtils.getAddress(newEstablishment.getAddress());
        newEstablishment.setAddress(address);
        List<Person> employees = personUtils.getEmployees(establishmentDTO.employees());
        newEstablishment.setEmployees(employees);
        List<Machine> machines = machineUtils.getMachines(establishmentDTO.machines());
        newEstablishment.setMachines(machines);
        Establishment savedEstablishment = establishmentRepository.save(newEstablishment);
        return establishmentMapper.establishmentToDTO(savedEstablishment);
    }

    public List<Establishment> getAllEstablishment() {
        return establishmentRepository.findAll();
    }

    public List<Establishment> getEstablishmentByFilters(String name, String postalCode, String type){
        if (name != null && postalCode != null && type != null) {
            return establishmentRepository.findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(name, postalCode, type);
        }
        if (name != null && postalCode != null) {
            return establishmentRepository.findByNameIgnoreCaseContainingAndAddress_AddressComposite_PostalCodeIgnoreCaseContaining(name, postalCode);
        }
        if (name != null && type != null) {
            return establishmentRepository.findByNameIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(name, type);
        }
        if (postalCode != null && type != null) {
            return establishmentRepository.findByAddress_AddressComposite_PostalCodeIgnoreCaseContainingAndMachines_Containers_TypeIgnoreCase(postalCode, type);
        }
        if (name != null) {
            return establishmentRepository.findByNameIgnoreCaseContaining(name);
        }
        if (postalCode != null) {
            return establishmentRepository.findByAddress_AddressComposite_PostalCodeIgnoreCaseContaining(postalCode);
        }
        if (type != null) {
            return establishmentRepository.findByMachines_Containers_TypeIgnoreCase(type);
        }
        return Collections.emptyList();
    }

    public Optional<Establishment> getEstablishmentByID(String establishmentId) {
        return establishmentRepository.findById(establishmentId);
    }

    public EstablishmentDTO updateEstablishment(String establishmentId, EstablishmentDTO establishmentDTO) {
        Establishment establishmentToUpdate = establishmentMapper.convertToEstablishment(establishmentDTO);
        Address address = addressUtils.getAddress(establishmentToUpdate.getAddress());
        establishmentToUpdate.setAddress(address);
        List<Person> employees = personUtils.getEmployees(establishmentDTO.employees());
        establishmentToUpdate.setEmployees(employees);
        List<Machine> machines = machineUtils.getMachines(establishmentDTO.machines());
        establishmentToUpdate.setMachines(machines);
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
