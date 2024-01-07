package com.priximmo.abstractcomponent;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.priximmo.geojson.parcelle.Crs;
import com.priximmo.geojson.parcelle.Links;

import java.util.List;

public abstract class AbstractTerrain<T>{
    private String type;
    @JsonProperty("features")
    private List<T> featuresTerrain;
    private int totalFeatures;
    private int numberMatched;
    @JsonProperty("numberReturned")
    private int numberReturned;
    private String timeStamp;
    @JsonProperty("links")
    private List<Links> links;
    private Crs crs ;
    @JsonProperty("bbox")
    private List<Double> bbox ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getFeaturesTerrain() {
        return featuresTerrain;
    }

    public void setFeaturesTerrain(List<T> featuresTerrain) {
        this.featuresTerrain = featuresTerrain;
    }

    public int getTotalFeatures() {
        return totalFeatures;
    }

    public void setTotalFeatures(int totalFeatures) {
        this.totalFeatures = totalFeatures;
    }

    public int getNumberMatched() {
        return numberMatched;
    }

    public int getNumberReturned() {
        return numberReturned;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setNumberMatched(int numberMatched) {
        this.numberMatched = numberMatched;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Crs getCrs() {
        return crs;
    }

    public void setCrs(Crs crs) {
        this.crs = crs;
    }

    public List<Double> getBbox() {
        return bbox;
    }

    public String convertBboxToString(){
        StringBuilder bbox = new StringBuilder();
        try {
            for (Double point: this.getBbox()) {
                bbox.append(point).append(",");
            }
        } catch (NullPointerException e){
            System.out.println("Pas de résultat pour la conversion de la Bbox en String");
            bbox.append("empty");
        }

        return bbox.substring(0, bbox.length()-1);
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    public abstract String showTerrainContent();
}
