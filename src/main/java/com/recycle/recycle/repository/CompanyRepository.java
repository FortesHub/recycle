package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
