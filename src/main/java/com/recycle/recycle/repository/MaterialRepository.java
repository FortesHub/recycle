package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, String> {
}
