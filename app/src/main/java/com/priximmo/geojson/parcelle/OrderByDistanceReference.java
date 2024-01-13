package com.priximmo.geojson.parcelle;

import java.util.Comparator;

public class OrderByDistanceReference implements Comparator<SimplifiedParcelle> {
    @Override
    public int compare(SimplifiedParcelle o1, SimplifiedParcelle o2) {
        return Double.compare(o1.getDistanceFromReference(), o2.getDistanceFromReference());
    }
}
