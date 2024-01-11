package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.MaterialDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    MaterialDTO convertToDTO(Material material);
    Material convertToMaterial(MaterialDTO materialDTO);
}
