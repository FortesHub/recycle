package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, String> {
    void deleteByTypeIgnoreCase(String type);
    Material findByTypeIgnoreCase(String type);
}
