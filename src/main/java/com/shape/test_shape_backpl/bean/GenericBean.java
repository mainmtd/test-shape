package com.shape.test_shape_backpl.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GenericBean {

    private Long id;

    public GenericBean(){}

    public GenericBean(Long id){
        this.id = id;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
