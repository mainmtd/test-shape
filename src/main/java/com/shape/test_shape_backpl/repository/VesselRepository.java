package com.shape.test_shape_backpl.repository;

import com.shape.test_shape_backpl.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselRepository extends JpaRepository<Vessel, Long> {
    Vessel findByCode(String code);
}
