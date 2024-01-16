package com.priximmo.geojson.geomutation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeomutationPoperties {

    private String idmutinvar;
    private String idopendata;
    private String datemut;
    private short anneemut;
    private String coddep;
    private String libnatmut;
    private boolean vefa;
    private float valeurfonc;
    private int nbcomm;
    @SerializedName("l_codinsee")
    private List<String> lCodinsee;
    private int nbpar ;
    @SerializedName("l_idpar")
    private List<String> lIdpar;
    private int nbparmut;
    @SerializedName("l_idparmut")
    private List<String> lIdparmut;
    private float sterr;
    private int nbvolmut;
    private int nblocmut;
    @SerializedName ("l_idlocmut")
    private List<String> lIdlocmut;
    private float sbati;
    private String codtypbien;
    private String libtypbien;

    public String getIdmutinvar() {
        return idmutinvar;
    }

    public void setIdmutinvar(String idmutinvar) {
        this.idmutinvar = idmutinvar;
    }

    public String getIdopendata() {
        return idopendata;
    }

    public void setIdopendata(String idopendata) {
        this.idopendata = idopendata;
    }

    public String getDatemut() {
        return datemut;
    }

    public void setDatemut(String datemut) {
        this.datemut = datemut;
    }

    public short getAnneemut() {
        return anneemut;
    }

    public void setAnneemut(short anneemut) {
        this.anneemut = anneemut;
    }

    public String getCoddep() {
        return coddep;
    }

    public void setCoddep(String coddep) {
        this.coddep = coddep;
    }

    public String getLibnatmut() {
        return libnatmut;
    }

    public void setLibnatmut(String libnatmut) {
        this.libnatmut = libnatmut;
    }

    public boolean isVefa() {
        return vefa;
    }

    public void setVefa(boolean vefa) {
        this.vefa = vefa;
    }

    public float getValeurfonc() {
        return valeurfonc;
    }

    public void setValeurfonc(float valeurfonc) {
        this.valeurfonc = valeurfonc;
    }

    public int getNbcomm() {
        return nbcomm;
    }

    public void setNbcomm(int nbcomm) {
        this.nbcomm = nbcomm;
    }

    public List<String> getlCodinsee() {
        return lCodinsee;
    }

    public void setlCodinsee(List<String> lCodinsee) {
        this.lCodinsee = lCodinsee;
    }

    public int getNbpar() {
        return nbpar;
    }

    public void setNbpar(int nbpar) {
        this.nbpar = nbpar;
    }

    public List<String> getlIdpar() {
        return lIdpar;
    }

    public void setlIdpar(List<String> lIdpar) {
        this.lIdpar = lIdpar;
    }

    public int getNbparmut() {
        return nbparmut;
    }

    public void setNbparmut(int nbparmut) {
        this.nbparmut = nbparmut;
    }

    public List<String> getlIdparmut() {
        return lIdparmut;
    }

    public void setlIdparmut(List<String> lIdparmut) {
        this.lIdparmut = lIdparmut;
    }

    public float getSterr() {
        return sterr;
    }

    public void setSterr(float sterr) {
        this.sterr = sterr;
    }

    public int getNbvolmut() {
        return nbvolmut;
    }

    public void setNbvolmut(int nbvolmut) {
        this.nbvolmut = nbvolmut;
    }

    public int getNblocmut() {
        return nblocmut;
    }

    public void setNblocmut(int nblocmut) {
        this.nblocmut = nblocmut;
    }

    public List<String> getlIdlocmut() {
        return lIdlocmut;
    }

    public void setlIdlocmut(List<String> lIdlocmut) {
        this.lIdlocmut = lIdlocmut;
    }

    public float getSbati() {
        return sbati;
    }

    public void setSbati(float sbati) {
        this.sbati = sbati;
    }

    public String getCodtypbien() {
        return codtypbien;
    }

    public void setCodtypbien(String codtypbien) {
        this.codtypbien = codtypbien;
    }

    public String getLibtypbien() {
        return libtypbien;
    }

    public void setLibtypbien(String libtypbien) {
        this.libtypbien = libtypbien;
    }
}
