package com.priximmo.geojson.adresseban;

import com.priximmo.geojson.geometry.GeometryPoint;

public class FeatureAdresseBAN {
    String type;
    GeometryPoint geometry;
    AdressProperties properties;

    public String getType() {
        return type;
    }

    public GeometryPoint getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPoint geometry) {
        this.geometry = geometry;
    }

    public AdressProperties getProperties() {
        return properties;
    }

    public void setProperties(AdressProperties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "type='" + type + '\'' +
                ", geometry=" + geometry +
                ", properties=" + properties +
                '}';
    }

    public String showAdressLabel() {
        return this.getProperties().getLabel();
    }
}
