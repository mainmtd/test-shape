package com.shape.test_shape_backpl.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EquipmentBean extends ActivatableBean{
    private String name;
    private String code;
    private String location;

    private VesselBean vessel;

    public EquipmentBean() {
    }

    public EquipmentBean(Long id, boolean active, String name, String code, String location, VesselBean vessel) {
        super(id, active);
        this.name = name;
        this.code = code;
        this.location = location;
        this.vessel = vessel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @JsonIgnore
    public VesselBean getVessel() {
        return vessel;
    }

    public void setVessel(VesselBean vessel) {
        this.vessel = vessel;
    }
}
