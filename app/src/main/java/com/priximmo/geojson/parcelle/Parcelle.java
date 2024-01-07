package com.priximmo.geojson.parcelle;

import com.priximmo.abstractcomponent.AbstractTerrain;

public class Parcelle extends AbstractTerrain<FeatureParcelle> {

    @Override
    public String showTerrainContent() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        String limitLine = "\n################################################\n\n";
        System.out.println(getFeaturesTerrain().size());
        for (FeatureParcelle feature : getFeaturesTerrain()) {
            sb.append("Parcelle id : ").append(feature.getId()).append(",\n");
            sb.append("Parcelle idu : ").append(feature.getTerrainProperties().getIdu()).append(",\n");
            sb.append("Contenance : ").append(feature.getTerrainProperties().getContenance()).append("m²").append(",\n");
            sb.append("Border box : ").append(this.convertBboxToString());
            if (!(i == getFeaturesTerrain().size()-1)) {
                sb.append(limitLine);
            }
            i++;
        }
        return sb.toString();
    }
}
