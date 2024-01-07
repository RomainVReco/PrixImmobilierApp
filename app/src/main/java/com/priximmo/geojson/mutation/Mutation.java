package com.priximmo.geojson.mutation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Mutation {

// Pour des raison de visibilité, les déclarations de variables, getter/setter se situent sous les méthodes
    public String showMutationContent(){
        StringBuilder sb = new StringBuilder();
        String limitLine = "\n################################################\n\n";
        sb.append("Id de mutation : ").append(this.idmutation).append("\n");
        sb.append("Date de mutation : ").append(this.datemut).append("\n");
        sb.append("Type de mutation : ").append(this.libnatmut).append("\n");
        sb.append("VEFA : ").append(this.vefa).append("\n");
        sb.append("Montant de transaction : ").append(this.valeurfonc).append("\n");
        sb.append("Nombre de lot(s) : ").append(this.nblot).append("\n");
//        sb.append("Nombre de locaux : ").append(this.nblocmut).append("\n");
        sb.append("Nombre de dépendance : ").append(this.nblocdep).append("\n");
        sb.append("Surface du bati : ").append(this.sbati).append("\n");
        sb.append("Type de bien : ").append(this.libtypbien).append("\n");
        sb.append(limitLine);
        return sb.toString();
    }

    private int idmutation;
    private String idmutinvar;
    private String idopendata;
    private byte idnatmut;
    private String codservch;
    private String refdoc;
    private String datemut;
    private int anneemut;
    private byte moismut;
    private String coddep;
    private String libnatmut;
    private int nbartcgi;
    @JsonProperty("l_artcgi")
    private List<String> lArtCgi;
    private boolean vefa;
    private float valeurfonc;
    private int nbdispo;
    private int nblot;
    private int nbcomm;
    @JsonProperty("l_codinsee")
    private List<String> lCodInsee;
    private byte nbsection;
    @JsonProperty("l_section")
    private List<String> lSection;
    private byte nbpar;
    @JsonProperty("l_idpar")
    private List<String> lIdPar;
    private int nbparmut;
    @JsonProperty("l_idparmut")
    private List<String> lIdParMut;
    private int nbsuf;
    private float sterr;
    @JsonProperty("l_dcnt")
    private List<String> lDcnt;
    private byte nbvolmut;
    /**
     * nombre de locaux ayant muté
     */
    private int nblocmut;
    /**
     * liste des identifiants de locaux ayant muté (idloc)
     */
    @JsonProperty("l_idlocmut")
    private List<String> lIdLocMut;
    /**
     * 	nombre de maisons ayant muté
     */
    private byte nblocmai;
    /**
     * nombre d'appartements ayant muté
     */
    private int nblocapt;
    /**
     * 	nombre de dépendances ayant muté
     */
    private int nblocdep;
    /**
     * nombre de locaux d'activités ayant muté
     */
    private int nblocact;
    /**
     * nombre d'appartements avec au plus une pièce principale ayant muté
     */
    private byte nbapt1pp;
    /**
     * nombre d'appartements avec 2 pièces principales ayant muté
     */
    private byte nbapt2pp;
    /**
     * nombre d'appartements avec 3 pièces principales ayant muté
     */
    private byte nbapt3pp;
    /**
     * nombre d'appartements avec 4 pièces principales ayant muté
     */
    private byte nbapt4pp;
    /**
     * nombre d'appartements avec au moins 5 pièces principales ayant muté
     */
    private byte nbapt5pp;
    /**
     * nombre de maisons avec au plus une pièce principale ayant muté
     */
    private byte nbmai1pp;
    /**
     * nombre de maisons avec 2 pièces principales ayant muté
     */
    private byte nbmai2pp;
    /**
     * nombre de maisons avec 3 pièces principales ayant muté
     */
    private byte nbmai3pp;
    /**
     * nombre de maisons avec 4 pièces principales ayant muté
     */
    private byte nbmai4pp;
    /**
     * nombre de maisons avec au moins 5 pièces principales ayant muté
     */
    private byte nbmai5pp;
    /**
     * surface de l'ensemble du bâti ayant muté
     */
    private float sbati;
    /**
     * surface de l'ensemble des maisons ayant muté
     */
    private float sbatmai;
    /**
     * surface de l'ensemble des appartements ayant muté
     */
    private float sbatapt;
    /**
     * surface de l'ensemble du bâti d'activité ayant muté
     */
    private float sbatact;
    /**
     * surface de l'ensemble des appartements avec au plus une pièce principale ayant muté
     */
    private float sapt1pp;
    /**
     * surface de l'ensemble des appartements avec 2 pièces principales ayant muté
     */
    private float sapt2pp;
    /**
     * surface de l'ensemble des appartements avec 3 pièces principales ayant muté
     */
    private float sapt3pp;
    /**
     * surface de l'ensemble des appartements avec 4 pièces principales ayant muté
     */
    private float sapt4pp;
    /**
     * surface de l'ensemble des appartements avec au moins 5 pièces principales ayant muté
     */
    private float sapt5pp;
    /**
     * surface de l'ensemble des maisons avec au plus une pièce principale ayant muté
     */
    private float smai1pp;
    /**
     * surface de l'ensemble des maisons avec 2 pièces principales ayant muté
     */
    private float smai2pp;
    /**
     * surface de l'ensemble des maisons avec 3 pièces principales ayant muté
     */
    private float smai3pp;
    /**
     * surface de l'ensemble des maisons avec 4 pièces principales ayant muté
     */
    private float smai4pp;
    /**
     * surface de l'ensemble des maisons avec au moins 5 pièces principales ayant muté
     */
    private float smai5pp;
    /**
     * code de la typologie des biens du GnDVF
     */
    private String codtypbien;
    /**
     * libellé de la typologie des biens du GnDVF
     */
    private String libtypbien;

    public int getIdmutation() {
        return idmutation;
    }

    public void setIdmutation(int idmutation) {
        this.idmutation = idmutation;
    }

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

    public byte getIdnatmut() {
        return idnatmut;
    }

    public void setIdnatmut(byte idnatmut) {
        this.idnatmut = idnatmut;
    }

    public String getCodservch() {
        return codservch;
    }

    public void setCodservch(String codservch) {
        this.codservch = codservch;
    }

    public String getRefdoc() {
        return refdoc;
    }

    public void setRefdoc(String refdoc) {
        this.refdoc = refdoc;
    }

    public String getDatemut() {
        return datemut;
    }

    public void setDatemut(String datemut) {
        this.datemut = datemut;
    }

    public int getAnneemut() {
        return anneemut;
    }

    public void setAnneemut(int anneemut) {
        this.anneemut = anneemut;
    }

    public byte getMoismut() {
        return moismut;
    }

    public void setMoismut(byte moismut) {
        this.moismut = moismut;
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

    public int getNbartcgi() {
        return nbartcgi;
    }

    public void setNbartcgi(int nbartcgi) {
        this.nbartcgi = nbartcgi;
    }

    public List<String> getlArtCgi() {
        return lArtCgi;
    }

    public void setlArtCgi(List<String> lArtCgi) {
        this.lArtCgi = lArtCgi;
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

    public int getNbdispo() {
        return nbdispo;
    }

    public void setNbdispo(int nbdispo) {
        this.nbdispo = nbdispo;
    }

    public int getNblot() {
        return nblot;
    }

    public void setNblot(int nblot) {
        this.nblot = nblot;
    }

    public int getNbcomm() {
        return nbcomm;
    }

    public void setNbcomm(int nbcomm) {
        this.nbcomm = nbcomm;
    }

    public List<String> getlCodInsee() {
        return lCodInsee;
    }

    public void setlCodInsee(List<String> lCodInsee) {
        this.lCodInsee = lCodInsee;
    }

    public byte getNbsection() {
        return nbsection;
    }

    public void setNbsection(byte nbsection) {
        this.nbsection = nbsection;
    }

    public List<String> getlSection() {
        return lSection;
    }

    public void setlSection(List<String> lSection) {
        this.lSection = lSection;
    }

    public byte getNbpar() {
        return nbpar;
    }

    public void setNbpar(byte nbpar) {
        this.nbpar = nbpar;
    }

    public List<String> getlIdPar() {
        return lIdPar;
    }

    public void setlIdPar(List<String> lIdPar) {
        this.lIdPar = lIdPar;
    }

    public int getNbparmut() {
        return nbparmut;
    }

    public void setNbparmut(int nbparmut) {
        this.nbparmut = nbparmut;
    }

    public List<String> getlIdParMut() {
        return lIdParMut;
    }

    public void setlIdParMut(List<String> lIdParMut) {
        this.lIdParMut = lIdParMut;
    }

    public int getNbsuf() {
        return nbsuf;
    }

    public void setNbsuf(int nbsuf) {
        this.nbsuf = nbsuf;
    }

    public float getSterr() {
        return sterr;
    }

    public void setSterr(float sterr) {
        this.sterr = sterr;
    }

    public List<String> getlDcnt() {
        return lDcnt;
    }

    public void setlDcnt(List<String> lDcnt) {
        this.lDcnt = lDcnt;
    }

    public byte getNbvolmut() {
        return nbvolmut;
    }

    public void setNbvolmut(byte nbvolmut) {
        this.nbvolmut = nbvolmut;
    }

    public int getNblocmut() {
        return nblocmut;
    }

    public void setNblocmut(int nblocmut) {
        this.nblocmut = nblocmut;
    }

    public List<String> getlIdLocMut() {
        return lIdLocMut;
    }

    public void setlIdLocMut(List<String> lIdLocMut) {
        this.lIdLocMut = lIdLocMut;
    }

    public byte getNblocmai() {
        return nblocmai;
    }

    public void setNblocmai(byte nblocmai) {
        this.nblocmai = nblocmai;
    }

    public int getNblocapt() {
        return nblocapt;
    }

    public void setNblocapt(int nblocapt) {
        this.nblocapt = nblocapt;
    }

    public int getNblocdep() {
        return nblocdep;
    }

    public void setNblocdep(int nblocdep) {
        this.nblocdep = nblocdep;
    }

    public int getNblocact() {
        return nblocact;
    }

    public void setNblocact(int nblocact) {
        this.nblocact = nblocact;
    }

    public byte getNbapt1pp() {
        return nbapt1pp;
    }

    public void setNbapt1pp(byte nbapt1pp) {
        this.nbapt1pp = nbapt1pp;
    }

    public byte getNbapt2pp() {
        return nbapt2pp;
    }

    public void setNbapt2pp(byte nbapt2pp) {
        this.nbapt2pp = nbapt2pp;
    }

    public byte getNbapt3pp() {
        return nbapt3pp;
    }

    public void setNbapt3pp(byte nbapt3pp) {
        this.nbapt3pp = nbapt3pp;
    }

    public byte getNbapt4pp() {
        return nbapt4pp;
    }

    public void setNbapt4pp(byte nbapt4pp) {
        this.nbapt4pp = nbapt4pp;
    }

    public byte getNbapt5pp() {
        return nbapt5pp;
    }

    public void setNbapt5pp(byte nbapt5pp) {
        this.nbapt5pp = nbapt5pp;
    }

    public byte getNbmai1pp() {
        return nbmai1pp;
    }

    public void setNbmai1pp(byte nbmai1pp) {
        this.nbmai1pp = nbmai1pp;
    }

    public byte getNbmai2pp() {
        return nbmai2pp;
    }

    public void setNbmai2pp(byte nbmai2pp) {
        this.nbmai2pp = nbmai2pp;
    }

    public byte getNbmai3pp() {
        return nbmai3pp;
    }

    public void setNbmai3pp(byte nbmai3pp) {
        this.nbmai3pp = nbmai3pp;
    }

    public byte getNbmai4pp() {
        return nbmai4pp;
    }

    public void setNbmai4pp(byte nbmai4pp) {
        this.nbmai4pp = nbmai4pp;
    }

    public byte getNbmai5pp() {
        return nbmai5pp;
    }

    public void setNbmai5pp(byte nbmai5pp) {
        this.nbmai5pp = nbmai5pp;
    }

    public float getSbati() {
        return sbati;
    }

    public void setSbati(float sbati) {
        this.sbati = sbati;
    }

    public float getSbatmai() {
        return sbatmai;
    }

    public void setSbatmai(float sbatmai) {
        this.sbatmai = sbatmai;
    }

    public float getSbatapt() {
        return sbatapt;
    }

    public void setSbatapt(float sbatapt) {
        this.sbatapt = sbatapt;
    }

    public float getSbatact() {
        return sbatact;
    }

    public void setSbatact(float sbatact) {
        this.sbatact = sbatact;
    }

    public float getSapt1pp() {
        return sapt1pp;
    }

    public void setSapt1pp(float sapt1pp) {
        this.sapt1pp = sapt1pp;
    }

    public float getSapt2pp() {
        return sapt2pp;
    }

    public void setSapt2pp(float sapt2pp) {
        this.sapt2pp = sapt2pp;
    }

    public float getSapt3pp() {
        return sapt3pp;
    }

    public void setSapt3pp(float sapt3pp) {
        this.sapt3pp = sapt3pp;
    }

    public float getSapt4pp() {
        return sapt4pp;
    }

    public void setSapt4pp(float sapt4pp) {
        this.sapt4pp = sapt4pp;
    }

    public float getSapt5pp() {
        return sapt5pp;
    }

    public void setSapt5pp(float sapt5pp) {
        this.sapt5pp = sapt5pp;
    }

    public float getSmai1pp() {
        return smai1pp;
    }

    public void setSmai1pp(float smai1pp) {
        this.smai1pp = smai1pp;
    }

    public float getSmai2pp() {
        return smai2pp;
    }

    public void setSmai2pp(float smai2pp) {
        this.smai2pp = smai2pp;
    }

    public float getSmai3pp() {
        return smai3pp;
    }

    public void setSmai3pp(float smai3pp) {
        this.smai3pp = smai3pp;
    }

    public float getSmai4pp() {
        return smai4pp;
    }

    public void setSmai4pp(float smai4pp) {
        this.smai4pp = smai4pp;
    }

    public float getSmai5pp() {
        return smai5pp;
    }

    public void setSmai5pp(float smai5pp) {
        this.smai5pp = smai5pp;
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
