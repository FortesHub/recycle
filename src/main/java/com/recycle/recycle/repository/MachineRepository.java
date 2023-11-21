package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, String> {
}
