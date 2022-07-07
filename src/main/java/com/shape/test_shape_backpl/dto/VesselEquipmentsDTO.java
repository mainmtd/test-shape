package com.shape.test_shape_backpl.dto;

import com.shape.test_shape_backpl.bean.EquipmentBean;

import java.util.List;

public class VesselEquipmentsDTO {
    private String vesselCode;
    private List<EquipmentBean> equipments;

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public List<EquipmentBean> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentBean> equipments) {
        this.equipments = equipments;
    }
}
