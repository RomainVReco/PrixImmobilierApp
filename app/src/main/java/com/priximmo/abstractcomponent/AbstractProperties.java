package com.priximmo.abstractcomponent;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe abstraire pour les propriétés de Feuille et de Parcelle
 */
public abstract class AbstractProperties {
    private String numero;
    private int feuille;
    private String section;
    @JsonProperty("code_dep")
    private String codeDep;
    @JsonProperty("nom_com")
    private String nomCommune;
    @JsonProperty("code_com")
    private String codeCommune;
    @JsonProperty("com_abs")
    private String comAbs;
    @JsonProperty("code_arr")
    private String codeArr;
    @JsonProperty("code_insee")
    private String codeInsee;
    private String contenance;
    @JsonProperty("bbox")
    private List<Double> bbox;

    @Override
    public String toString() {
        return "Properties{" +
                "numero='" + numero + '\'' +
                ", feuille=" + feuille +
                ", section='" + section + '\'' +
                ", codeDep='" + codeDep + '\'' +
                ", nomCommune='" + nomCommune + '\'' +
                ", codeCommune='" + codeCommune + '\'' +
                ", comAbs='" + comAbs + '\'' +
                ", codeArr='" + codeArr + '\'' +
                ", codeInsee='" + codeInsee + '\'' +
                ", contenance='" + contenance + '\'' +
                ", bbox=" + bbox +
                '}';
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getFeuille() {
        return feuille;
    }

    public void setFeuille(int feuille) {
        this.feuille = feuille;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCodeDep() {
        return codeDep;
    }

    public void setCodeDep(String codeDep) {
        this.codeDep = codeDep;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getComAbs() {
        return comAbs;
    }

    public void setComAbs(String comAbs) {
        this.comAbs = comAbs;
    }

    public String getCodeArr() {
        return codeArr;
    }

    public void setCodeArr(String codeArr) {
        this.codeArr = codeArr;
    }

    public String getCodeInsee() {
        return codeInsee;
    }

    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }

    public String getContenance() {
        return contenance;
    }

    public void setContenance(String contenance) {
        this.contenance = contenance;
    }

    public List<Double> getBbox() {
        return bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
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
}
