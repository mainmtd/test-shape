package com.shape.test_shape_backpl.repository;

import com.shape.test_shape_backpl.bean.EquipmentBean;
import com.shape.test_shape_backpl.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Equipment findByCode(String code);

    Equipment findByName(String name);

    @Query("SELECT e FROM Equipment e WHERE e.active = true AND e.vessel.code = ?1")
    List<Equipment> findActiveByVesselCode(String vesselCode);
}
