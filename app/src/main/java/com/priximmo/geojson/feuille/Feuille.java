package com.priximmo.geojson.feuille;

import com.priximmo.abstractcomponent.AbstractTerrain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Feuille extends AbstractTerrain<FeatureFeuille> {

    @Override
    public String showTerrainContent() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        String limitLine = "\n################################################\n\n";
        System.out.println(getFeaturesTerrain().size());
        for (FeatureFeuille feature : getFeaturesTerrain()) {
            sb.append("Feuille id : ").append(feature.getId()).append(",\n");
            sb.append("Section : ").append(feature.getTerrainProperties().getSection()).append(",\n");
            sb.append("Code INSEE : ").append(feature.getTerrainProperties().getCodeInsee()).append(",\n");
            sb.append("Border box : ").append(this.convertBboxToString());
            if (!(i == getFeaturesTerrain().size()-1)) {
                sb.append(limitLine);
            }
            i++;
        }
        return sb.toString();
    }

    public String getStringFromSectionOfCity() {
        StringBuilder sb = new StringBuilder();
        sb.append("Code INSEE : ").append(this.getFeaturesTerrain().get(0).getTerrainProperties().getCodeInsee()).append("\n");
        sb.append("Nombre de résultats : ").append(this.getNumberReturned()).append("\n");
        int i = 0;
        String limitLine = "##############\n";
        for (FeatureFeuille feature : getFeaturesTerrain()) {
            sb.append("Section : ").append(feature.getTerrainProperties().getSection()).append("\n");
            if (!(i == getFeaturesTerrain().size()-1)) {
                sb.append(limitLine);
            }
            i++;
        }
        return sb.toString();
    }

    public Set<List<Double>> getListOfSectionsFromCity () {
        Set<List<Double>> setOfSections = new HashSet<>();
        System.out.println(this.getStringFromSectionOfCity());
        for (FeatureFeuille feature : getFeaturesTerrain()) {
            setOfSections.add(feature.getTerrainProperties().getBbox());
        }
        return setOfSections;
    }
}
