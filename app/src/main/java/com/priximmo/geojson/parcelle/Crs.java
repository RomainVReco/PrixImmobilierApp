package com.priximmo.geojson.parcelle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Crs {
    private String type;
    @JsonProperty("properties")
    private CrsProperties crsProperties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CrsProperties getCrsProperties() {
        return crsProperties;
    }

    public void setCrsProperties(CrsProperties crsProperties) {
        this.crsProperties = crsProperties;
    }
}
