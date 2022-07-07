package com.shape.test_shape_backpl.model;

import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Equipment extends ActivatableEntity{

    private String name;
    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Equipment 'code' is a required field")
    @NaturalId
    private String code;
    private String location;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vesselCode",referencedColumnName = "code")
    private Vessel vessel;
    @OneToMany(mappedBy = "equipment")
    private List<Operation> operations = new ArrayList<>();

    public Equipment(Long id, boolean active, String name, String code, String location, Vessel vessel) {
        super(id, active);
        this.name = name;
        this.code = code;
        this.location = location;
        this.vessel = vessel;
    }

    public Equipment(){

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

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
