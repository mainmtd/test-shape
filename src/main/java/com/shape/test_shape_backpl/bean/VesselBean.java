package com.shape.test_shape_backpl.bean;

import java.util.List;

public class VesselBean extends GenericBean{
    private String code;

    public VesselBean() {
    }

    public VesselBean(Long id, String code) {
        super(id);
        this.code = code;
    }

    public VesselBean(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
