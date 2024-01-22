package com.recycle.recycle.service;

import com.recycle.recycle.domain.ContainerMaterial;
import com.recycle.recycle.domain.Material;
import com.recycle.recycle.dto.ContainerDTO;
import com.recycle.recycle.mapper.ContainerMapper;
import com.recycle.recycle.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContainerService {
    private ContainerRepository containerRepository;
    private ContainerMapper containerMapper;
    private EntityService entityService;

    @Autowired
    public void containerService(ContainerRepository containerRepository,
                                 ContainerMapper containerMapper,
                                 EntityService entityService) {
        this.containerRepository = containerRepository;
        this.containerMapper = containerMapper;
        this.entityService = entityService;
    }

    public ContainerDTO registerContainer(ContainerDTO containerDTO) {
        ContainerMaterial newContainer = containerMapper.convertToContainer(containerDTO);
        Material material = entityService.getMaterial(newContainer.getMaterial());
        newContainer.setMaterial(material);
        entityService.verifyContainerDoesNotExist(material.getMaterialId(), newContainer.getVolume(), newContainer.getValue());
        ContainerMaterial savedContainer = containerRepository.save(newContainer);
        return containerMapper.convertToDTO(savedContainer);
    }

    public List<ContainerMaterial> getAllContainer() {
        return containerRepository.findAll();
    }

    public Optional<ContainerMaterial> getContainerById(String conainerId) {
        return containerRepository.findById(conainerId);
    }

    public ContainerDTO updateContainer(String containerId, ContainerDTO containerDTO) {
        ContainerMaterial containerToUpdate = containerMapper.convertToContainer(containerDTO);
        Material material = entityService.getMaterial(containerToUpdate.getMaterial());
        containerToUpdate.setMaterial(material);
        containerToUpdate.setContainerId(containerId);
        ContainerMaterial updatedContainer = containerRepository.save(containerToUpdate);
        return containerMapper.convertToDTO(updatedContainer);
    }

    public boolean deleteContainer(String containerId) {
        Optional<ContainerMaterial> deletedContainer = getContainerById(containerId);
        if (deletedContainer.isPresent()) {
            containerRepository.delete(deletedContainer.get());
            return true;
        }
        return false;
    }
}
