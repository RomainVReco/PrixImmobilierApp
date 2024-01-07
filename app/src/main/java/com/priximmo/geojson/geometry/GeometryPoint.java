package com.priximmo.geojson.geometry;

import java.util.List;

public class GeometryPoint extends AbstractGeometry<List<Double>>{

    @Override
    public String toString() {
        return "{\"type\": \"Point\",\"coordinates\":"+coordinates+"}";
    }
}

