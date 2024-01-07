package com.priximmo.geojson.parcelle;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.priximmo.abstractcomponent.AbstractProperties;

public class ParcelleProperties extends AbstractProperties {
    @JsonProperty("idu")
    private String idu;

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

}
