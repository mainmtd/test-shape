package com.shape.test_shape_backpl.bean;

public class ActivatableBean extends GenericBean{
    private boolean active;

    public ActivatableBean() {
    }

    public ActivatableBean(Long id, boolean active) {
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
