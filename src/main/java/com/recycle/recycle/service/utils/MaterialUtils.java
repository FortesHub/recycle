package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Material;
import com.recycle.recycle.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialUtils {
    private MaterialRepository materialRepository;

    @Autowired
    public void materialUtils(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> getContainers(List<String> containerTypes) {
        List<Material> containers = new ArrayList<>();
        for (String containerType : containerTypes) {
            Material container = materialRepository.findByTypeIgnoreCase(containerType);
            if (container == null) {
                Material material = new Material();
                material.setType(containerType);
                material = materialRepository.save(material);
                containers.add(material);
            } else{
                containers.add(container);
            }
        }
        return containers;
    }
}
