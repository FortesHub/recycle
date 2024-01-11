package com.recycle.recycle.mapper;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.MaterialDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaterialMapperTest {
    private MaterialMapper materialMapper = Mappers.getMapper(MaterialMapper.class);
    @Test
    void convertToDTO() {
        Material material = new Material();
        material.setType("plastic");
        MaterialDTO materialDTO = materialMapper.convertToDTO(material);
        assertEquals(material.getType(), materialDTO.type());
    }

    @Test
    void convertToMaterial() {
        MaterialDTO materialDTO = new MaterialDTO("plastic");
        Material material = materialMapper.convertToMaterial(materialDTO);
        assertEquals(material.getType(), materialDTO.type());
    }
}
