package com.project.smartcommunity.repository;

import com.project.smartcommunity.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
}
