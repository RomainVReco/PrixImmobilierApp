package com.priximmo.geojson.geomutation;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.gson.annotations.SerializedName;
import com.priximmo.geojson.geometry.GeometryPolygon;

public class FeatureMutation {
    /**
     * L'id correspond à l'idmutation
     */
   @SerializedName("id")
   private int id;
   @SerializedName("type")
   private String type;
   @SerializedName("geometry")
   private GeometryPolygon geometry;
   @SerializedName("properties")
   GeomutationPoperties geomutationPoperties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeometryPolygon getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPolygon geometry) {
        this.geometry = geometry;
    }

    public GeomutationPoperties getGeomutationPoperties() {
        return geomutationPoperties;
    }

    public void setGeomutationPoperties(GeomutationPoperties geomutationPoperties) {
        this.geomutationPoperties = geomutationPoperties;
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder sb = new StringBuilder();
            sb.append("Id de mutation : ").append(this.getId()).append("\n");
            sb.append("Date de mutation : ").append(this.getGeomutationPoperties().getDatemut()).append("\n");
            sb.append("Type de mutation : ").append((this.getGeomutationPoperties().getLibnatmut())).append("\n");
            sb.append("VEFA : ").append(this.getGeomutationPoperties().isVefa()).append("\n");
            sb.append("Montant de transaction : ").append(this.getGeomutationPoperties().getValeurfonc()).append("\n");
            sb.append("Parcelle(s)  : ").append(this.getGeomutationPoperties().getlIdpar().toString()).append("\n");
            sb.append("Nombre de lot  : ").append(this.getGeomutationPoperties().getNblocmut()).append("\n");
            sb.append("Surface du bati : ").append(this.getGeomutationPoperties().getSbati()).append("m²").append("\n");
            sb.append("Type de bien : ").append(this.getGeomutationPoperties().getLibtypbien()).append("\n");
            String limitLine = "\n################################################\n\n";
            sb.append(limitLine);
            return sb.toString();
    }
}
