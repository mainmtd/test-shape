package com.shape.test_shape_backpl.repository;

import com.shape.test_shape_backpl.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, String> {

    @Query("SELECT o FROM Operation o WHERE o.equipment.code = ?1")
    List<Operation> findByEquipmentCode(String equipmentCode);

    @Query("SELECT o FROM Operation o WHERE o.equipment.name = ?1")
    List<Operation> findByEquipmentName(String equipmentName);
}
