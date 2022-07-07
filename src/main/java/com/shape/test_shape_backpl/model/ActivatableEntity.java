package com.shape.test_shape_backpl.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ActivatableEntity extends GenericEntity{

    @Column(name = "active", nullable = false)
    public boolean active;

    public ActivatableEntity() {
    }

    public ActivatableEntity(Long id, boolean active) {
        super(id);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
