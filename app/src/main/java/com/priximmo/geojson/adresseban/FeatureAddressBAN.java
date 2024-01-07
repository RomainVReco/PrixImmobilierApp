package com.priximmo.geojson.adresseban;

import com.priximmo.geojson.geometry.GeometryPoint;

public class FeatureAddressBAN {
    String type;
    GeometryPoint geometry;
    AddressProperties properties;

    public String getType() {
        return type;
    }

    public GeometryPoint getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPoint geometry) {
        this.geometry = geometry;
    }

    public AddressProperties getProperties() {
        return properties;
    }

    public void setProperties(AddressProperties properties) {
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
