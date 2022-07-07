package com.shape.test_shape_backpl.service;

import com.shape.test_shape_backpl.bean.VesselBean;
import com.shape.test_shape_backpl.dto.VesselAvgCostDTO;
import com.shape.test_shape_backpl.model.Equipment;
import com.shape.test_shape_backpl.model.Operation;
import com.shape.test_shape_backpl.model.Vessel;
import com.shape.test_shape_backpl.repository.EquipmentRepository;
import com.shape.test_shape_backpl.repository.OperationRepository;
import com.shape.test_shape_backpl.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VesselService {
    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private EquipmentService equipmentService;

    public VesselBean insertVessel(VesselBean vesselBean){
        Vessel vessel = this.beanToEntity(vesselBean);
        return this.entitytoBean(this.vesselRepository.save(vessel));
    }

    public List<VesselBean> findAll(){
        return this.entitiesToBeans(vesselRepository.findAll());
    }

    public List<VesselBean> entitiesToBeans(List<Vessel> entities){
        return entities.stream().map(this::entitytoBean).collect(Collectors.toList());
    }

    public List<VesselAvgCostDTO> avgCostByVessel(){
        List<Vessel> vessels = this.vesselRepository.findAll();
        List<VesselAvgCostDTO> dtos = new ArrayList<>();

        if(!vessels.isEmpty()){
            dtos = vessels.stream().map(this::vesselAverageCost).collect(Collectors.toList());
        }
        return dtos;
    }

    private VesselAvgCostDTO vesselAverageCost(Vessel vessel){
        int operationsQnt = 0;
        Double totalCost = 0.0;
        double avgCost = 0.0;
        VesselAvgCostDTO dto = new VesselAvgCostDTO();

        for(Equipment equipment : vessel.getEquipments()){
            operationsQnt += equipment.getOperations().size();
            List<Operation> equipmentOperations = equipment.getOperations();
            totalCost += this.equipmentService.getTotalOperationsCost(equipmentOperations);
        }
        avgCost = totalCost / operationsQnt;
        dto.setVessel(vessel.getCode());
        dto.setAverageCost(avgCost);

        return dto;
    }

    public Vessel beanToEntity(VesselBean bean){
        Vessel entity = new Vessel();
        entity.setCode(bean.getCode());

        return entity;
    }

    public VesselBean entitytoBean(Vessel entity){
        VesselBean bean = new VesselBean();
        bean.setCode(entity.getCode());
        bean.setId(entity.getId());

        return bean;
    }
}
