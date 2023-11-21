package com.recycle.recycle.repository;

import com.recycle.recycle.domain.ContainerMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<ContainerMaterial, String> {
}
