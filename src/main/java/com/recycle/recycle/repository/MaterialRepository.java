package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, String> {
    List<Material> deleteByTypeIgnoreCase(String type);
}
