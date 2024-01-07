package com.priximmo.geojson.adresseban;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe encapsulée dans AdresseAPI afin d'obtenir les détail d'une adresse
 */
public class AdressProperties {
        @JsonProperty("label")
        private String label;
        @JsonProperty("score")
        private double score;
        @JsonProperty("housenumber")
        private String housenumber;
        @JsonProperty("id")
        private String id;
        @JsonProperty("banId")
        private String banId;
        @JsonProperty("name")
        private String name;
        @JsonProperty("postcode")
        private String postcode;
        @JsonProperty("citycode")
        private String citycode;
        @JsonProperty("oldcitycode")
        private String oldcitycode;
        @JsonProperty("x")
        private double x;
        @JsonProperty("y")
        private double y;
        @JsonProperty("city")
        private String city;
        @JsonProperty("oldcity")
        private String oldcity;
        @JsonProperty("district")
        private String district;
        @JsonProperty("context")
        private String context;
        @JsonProperty("type")
        private String propertyType;
        @JsonProperty("importance")
        private double importance;
        @JsonProperty("street")
        private String street;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String   getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public double getImportance() {
        return importance;
    }

    public void setImportance(double importance) {
        this.importance = importance;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBanId() {
        return banId;
    }

    public void setBanId(String banId) {
        this.banId = banId;
    }

    public String getOldcitycode() {
        return oldcitycode;
    }

    public void setOldcitycode(String oldcitycode) {
        this.oldcitycode = oldcitycode;
    }

    @Override
    public String toString() {
        return "AdressProperties{" +
                "label='" + label + '\'' +
                ", score=" + score +
                ", id='" + id + '\'' +
                ", citycode='" + citycode + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
