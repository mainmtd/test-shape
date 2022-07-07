package com.shape.test_shape_backpl.dto;

public class VesselAvgCostDTO {
    private String vessel;
    private Double averageCost;

    public VesselAvgCostDTO(String vessel, Double averageCost) {
        this.vessel = vessel;
        this.averageCost = averageCost;
    }

    public VesselAvgCostDTO() {
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }
}
