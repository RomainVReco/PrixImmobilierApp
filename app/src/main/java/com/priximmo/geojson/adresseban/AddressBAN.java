package com.priximmo.geojson.adresseban;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/*
* Objet utilisé pour la récupération d'une FeatureCollection de l'API Adresse
* */

public class AddressBAN {
    @JsonProperty("type")
    String type;
    @JsonProperty("version")
    String version;
    @JsonProperty("features")
    List<FeatureAddressBAN> featureAddressBAN;
    String attribution;
    String licence;
    String query;
    int limit;

    public List<FeatureAddressBAN> getFeatures() {
        return featureAddressBAN;
    }

    public void setFeatures(List<FeatureAddressBAN> featureAddressBAN) {
        this.featureAddressBAN = featureAddressBAN;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "AdresseBAN{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", features=" + featureAddressBAN +
                ", attribution='" + attribution + '\'' +
                ", licence='" + licence + '\'' +
                ", query='" + query + '\'' +
                ", limit=" + limit +
                '}';
    }

    public String showAdresseBANContent() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        String limitLine = "\n################################################\n\n";
        for (FeatureAddressBAN feature : getFeatures()) {
            sb.append("Label : ").append(feature.getProperties().getLabel()).append(",\n");
            sb.append("Code INSEE : ").append(feature.getProperties().getCitycode()).append(",\n");
            sb.append("Context : ").append(feature.getProperties().getContext()).append(",\n");
            sb.append("Geometry point : ").append(feature.getGeometry().toString()).append("\n");
            if (!(i == getFeatures().size()-1)) {
                sb.append(limitLine);
            }
            i++;
        }
        return sb.toString();
    }
}
