package com.shape.test_shape_backpl.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class Operation extends GenericEntity{

    private String type;
    private Double cost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="code", referencedColumnName = "code")
    private Equipment equipment;

    public Operation() {
    }

    public Operation(String type, Double cost, Equipment equipment) {
        this.type = type;
        this.cost = cost;
        this.equipment = equipment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
