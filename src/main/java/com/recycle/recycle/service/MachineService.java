package com.recycle.recycle.service;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.MachineDTO;
import com.recycle.recycle.mapper.MachineMapper;
import com.recycle.recycle.repository.MachineRepository;
import com.recycle.recycle.service.utils.MaterialUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    private MachineRepository machineRepository;
    private MachineMapper machineMapper;
    private MaterialUtils materialUtils;

    @Autowired
    public void machineService(
            MachineRepository machineRepository,
            MachineMapper machineMapper,
            MaterialUtils materialUtils) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
        this.materialUtils = materialUtils;
    }

    public MachineDTO registerMachine(MachineDTO machineDTO) {
        Machine newMachine = machineMapper.convertToMachine(machineDTO);
        newMachine.getStatus();
        List<Material> containers = materialUtils.getContainers(machineDTO.containers());
        newMachine.setContainers(containers);
        Machine registeredMachine = machineRepository.save(newMachine);
        return machineMapper.convertToDTO(registeredMachine);
    }

    public List<Machine> getAllMachine() {
        return machineRepository.findAll();
    }

    public Optional<Machine> getMachineById(String machineId) {
        return machineRepository.findById(machineId);
    }


    public MachineDTO updateMachine(String machineId, MachineDTO machineDTO) {
        Machine machineToUpdate = machineMapper.convertToMachine(machineDTO);
        machineToUpdate.setMachineId(machineId);
        List<Material> containers = materialUtils.getContainers(machineDTO.containers());
        machineToUpdate.setContainers(containers);
        Machine updatedMachine = machineRepository.save(machineToUpdate);
        return machineMapper.convertToDTO(updatedMachine);
    }

    public boolean deleteMachine(String machineId) {
        Optional<Machine> machineToDelete = machineRepository.findById(machineId);
        if (machineToDelete.isPresent()) {
            machineRepository.delete(machineToDelete.get());
            return true;
        }
        return false;
    }

}
