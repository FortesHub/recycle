package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.ContainerMaterial;
import com.recycle.recycle.dto.ContainerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContainerMapper {
    ContainerDTO convertToDTO(ContainerMaterial containerMaterial);
    ContainerMaterial convertToContainer(ContainerDTO containerDTO);
}

