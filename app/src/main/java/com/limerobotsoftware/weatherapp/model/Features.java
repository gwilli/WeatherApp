package com.limerobotsoftware.weatherapp.model;

import com.google.gson.annotations.Expose;

public class Features {

    @Expose
    private Integer conditions;

    public Integer getConditions() {
        return conditions;
    }

    public void setConditions(Integer conditions) {
        this.conditions = conditions;
    }

}