package com.priximmo.geojson.geometry;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractGeometry<T> {
    @JsonProperty("type")
    String type;
    @JsonProperty("coordinates")
    T coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(T coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "type='" + type + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
