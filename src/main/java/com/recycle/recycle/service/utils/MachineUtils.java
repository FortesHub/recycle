package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.repository.MachineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MachineUtils {
    private MachineRepository machineRepository;
    private String notFound = "Machine Not Found!";

    @Autowired
    public void machineUtils(MachineRepository machineRepository){
        this.machineRepository = machineRepository;
    }

    public List<Machine> getMachines(List<String> machineIds){
        List<Machine> machines = new ArrayList<>();
        if(machineIds != null){
            for(String machineId : machineIds){
                Machine machine = machineRepository.findById(machineId)
                        .orElseThrow(() -> new EntityNotFoundException(notFound));
                machines.add(machine);
            }
        }
        return machines;
    }

}
