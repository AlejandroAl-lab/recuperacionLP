package com.recuperacion.repository;

import com.recuperacion.model.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, Long> {
}
