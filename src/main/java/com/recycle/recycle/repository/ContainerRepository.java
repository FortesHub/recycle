package com.recycle.recycle.repository;

import com.recycle.recycle.domain.ContainerMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<ContainerMaterial, String> {
}
