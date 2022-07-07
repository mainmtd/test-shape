package com.shape.test_shape_backpl.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
public class Vessel extends GenericEntity{

    @Column(unique = true)
    @NaturalId
    @NotEmpty(message = "Vessel 'code' is a required field")
    private String code;

    @OneToMany(mappedBy = "vessel")
    private List<Equipment> equipments = new ArrayList<>();

    public Vessel(){}

    public Vessel(Long id, String code) {
        super(id);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
