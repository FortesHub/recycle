package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Machine;
import com.recycle.recycle.dto.MachineDTO;
import com.recycle.recycle.mapper.utils.MappersUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MachineMapper extends MappersUtils  {
    @Mapping(target = "containers", expression = "java(mapContainers(machine.getContainers()))")
    MachineDTO convertToDTO(Machine machine);
    @Mapping(target = "containers", ignore = true)
    Machine convertToMachine(MachineDTO machineDTO);
}

