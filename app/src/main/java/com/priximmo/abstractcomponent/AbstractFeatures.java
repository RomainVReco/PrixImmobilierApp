package com.priximmo.abstractcomponent;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.priximmo.geojson.geometry.GeometryPolygon;

public abstract class AbstractFeatures<T> {
    private String type;
    private String id;
    private GeometryPolygon geometry;
    @JsonProperty("geometry_name")
    private String geometryName;
    @JsonProperty("properties")
    private T terrainProperties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeometryPolygon getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPolygon geometry) {
        this.geometry = geometry;
    }

    public String getGeometryName() {
        return geometryName;
    }

    public void setGeometryName(String geometryName) {
        this.geometryName = geometryName;
    }

    public T getTerrainProperties() {
        return terrainProperties;
    }

    public void setParcelleProperties(T parcelleProperties) {
        this.terrainProperties = parcelleProperties;
    }
}
