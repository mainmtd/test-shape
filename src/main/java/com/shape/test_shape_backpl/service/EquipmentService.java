package com.shape.test_shape_backpl.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.shape.test_shape_backpl.bean.EquipmentBean;
import com.shape.test_shape_backpl.bean.OperationBean;
import com.shape.test_shape_backpl.bean.VesselBean;
import com.shape.test_shape_backpl.dto.EquipmentCostDTO;
import com.shape.test_shape_backpl.dto.VesselEquipmentsDTO;
import com.shape.test_shape_backpl.exception.InvalidJSONParameterException;
import com.shape.test_shape_backpl.model.Equipment;
import com.shape.test_shape_backpl.model.Operation;
import com.shape.test_shape_backpl.model.Vessel;
import com.shape.test_shape_backpl.repository.EquipmentRepository;
import com.shape.test_shape_backpl.repository.OperationRepository;
import com.shape.test_shape_backpl.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EquipmentService {
    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private OperationRepository operationRepository;

    public EquipmentBean insertEquipment(ObjectNode json){
        EquipmentBean equipmentBean = this.jsonToBean(json);
        Equipment equipment = this.beanToEntity(equipmentBean);
        equipment.setActive(true);

        equipmentBean = this.entityToBean(this.equipmentRepository.save(equipment));

        return equipmentBean;
    }

    public List<EquipmentBean> findAll(){
        return this.entitiesToBeans(equipmentRepository.findAll());
    }

    public void updateEquipmentStatus(List<String> codes){
        codes.forEach(code -> {
            Equipment equipment = this.equipmentRepository.findByCode(code);
            if(equipment != null){
                equipment.setActive(false);
                this.equipmentRepository.save(equipment);
            }
        });
    }

    public VesselEquipmentsDTO getActiveByVesselCode(String vesselCode){
        VesselEquipmentsDTO dto = new VesselEquipmentsDTO();
        dto.setVesselCode(vesselCode);
        List<EquipmentBean> equipments = this.entitiesToBeans(equipmentRepository.findActiveByVesselCode(vesselCode));
        dto.setEquipments(equipments);

        return dto;
    }

    public OperationBean newOperation(OperationBean operationBean){
        Equipment equipment = this.equipmentRepository.findByCode(operationBean.getCode());
        if(equipment != null){
            Operation operation = new Operation();
            operation.setEquipment(equipment);
            operation.setCost(operationBean.getCost());
            operation.setType(operationBean.getType());

            operationBean =this.operationToBean(operationRepository.save(operation));
        }
        return operationBean;
    }

    public EquipmentCostDTO getTotalCostByCode(String equipmentCode){
        Equipment equipment = this.equipmentRepository.findByCode(equipmentCode);
        EquipmentCostDTO dto = new EquipmentCostDTO();

        if(equipment != null){
            List<Operation> operations = this.operationRepository.findByEquipmentCode(equipment.getCode());
            Double totalCost = operations.stream()
                    .map(Operation::getCost)
                    .reduce(0.0, Double::sum);
            dto.setEquipment(equipment.getCode());
            dto.setTotalCost(totalCost);
        }
        return dto;
    }

    public EquipmentCostDTO getTotalCostByName(String equipmentName){
        Equipment equipment = this.equipmentRepository.findByName(equipmentName);
        EquipmentCostDTO dto = new EquipmentCostDTO();

        if(equipment != null){
            List<Operation> operations = this.operationRepository.findByEquipmentName(equipment.getCode());

            dto.setEquipment(equipment.getCode());
            dto.setTotalCost(this.getTotalOperationsCost(operations));
        }
        return dto;
    }

    public Double getTotalOperationsCost(List<Operation> operations){
        return operations.stream()
                .map(Operation::getCost)
                .reduce(0.0, Double::sum);
    }

    public OperationBean operationToBean(Operation operation){
        OperationBean bean = new OperationBean();
        bean.setCode(operation.getEquipment().getCode());
        bean.setType(operation.getType());
        bean.setCost(operation.getCost());

        return bean;
    }

    public List<EquipmentBean> entitiesToBeans(List<Equipment> entities){
        return entities.stream().map(this::entityToBean).collect(Collectors.toList());
    }

    public EquipmentBean entityToBean(Equipment entity){
        EquipmentBean bean = new EquipmentBean();
        bean.setId(entity.getId());
        bean.setCode(entity.getCode());
        bean.setLocation(entity.getName());
        bean.setActive(entity.isActive());
        bean.setName(entity.getName());

        if(entity.getVessel() != null){
            VesselBean vessel = new VesselBean();
            vessel.setCode(entity.getVessel().getCode());
            bean.setVessel(vessel);
        }
        return bean;
    }

    public Equipment beanToEntity(EquipmentBean bean){
        if(bean.getVessel() == null){
            throw new NullPointerException(bean.getVessel().getClass().getSimpleName());
        }

        Equipment entity = new Equipment();

        entity.setId(bean.getId());
        entity.setCode(bean.getCode());
        entity.setLocation(bean.getLocation());
        entity.setName(bean.getName());
        Vessel vessel = this.vesselRepository.findByCode(bean.getVessel().getCode());
        if(vessel == null){
            throw new EntityNotFoundException(Vessel.class.getSimpleName());
        }
        entity.setVessel(vessel);
        entity.setActive(bean.isActive());

        return entity;
    }

    private EquipmentBean jsonToBean(ObjectNode json){
        if(json.get("vessel_code") == null){
            String message = "Vessel code not found in request body";
            throw new InvalidJSONParameterException(message);
        }
        if(json.get("equipment") == null){
            String message = "Equipment not found in request body";
            throw new InvalidJSONParameterException(message);
        }
        Gson gson = new Gson();

        VesselBean vesselBean = new VesselBean(json.get("vessel_code").textValue());
        EquipmentBean equipmentBean = gson.fromJson(String.valueOf(json.get("equipment")), EquipmentBean.class);
        equipmentBean.setVessel(vesselBean);
        return equipmentBean;
    }
}
