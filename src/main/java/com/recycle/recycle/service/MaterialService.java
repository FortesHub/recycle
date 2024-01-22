package com.recycle.recycle.service;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.MaterialDTO;
import com.recycle.recycle.mapper.MaterialMapper;
import com.recycle.recycle.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {
    private MaterialRepository materialRepository;
    private MaterialMapper materialMapper;

    @Autowired
    public void materialService(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    public MaterialDTO registerMaterial(MaterialDTO materialDTO) {
        Material newMaterial = materialMapper.convertToMaterial(materialDTO);
        Material savedMaterial = this.materialRepository.save(newMaterial);
        return materialMapper.convertToDTO(savedMaterial);
    }

    public List<Material> getAllMaterial() {
        return this.materialRepository.findAll();
    }

    public Material getMaterialByType(String type) {
        return this.materialRepository.findByTypeIgnoreCase(type);
    }

    public MaterialDTO updateMaterial(String id, MaterialDTO materialDTO) {
        Material updatingMaterial = materialMapper.convertToMaterial(materialDTO);
        updatingMaterial.setMaterialId(id);
        Material updatedMaterial = this.materialRepository.save(updatingMaterial);
        return materialMapper.convertToDTO(updatedMaterial);
    }

    @Transactional
    public boolean deleteMaterial(String type) {
        Material deletedMaterial = getMaterialByType(type);
        if (deletedMaterial != null) {
            materialRepository.deleteByTypeIgnoreCase(deletedMaterial.getType());
            return true;
        }
        return false;
    }
}

