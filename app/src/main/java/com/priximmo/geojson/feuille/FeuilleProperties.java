package com.priximmo.geojson.feuille;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.priximmo.abstractcomponent.AbstractProperties;

public class FeuilleProperties extends AbstractProperties {
    @JsonProperty("echelle")
    private String echelle;
    @JsonProperty("edition")
    private String edition;

    public String getEchelle() {
        return echelle;
    }

    public void setEchelle(String echelle) {
        this.echelle = echelle;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
