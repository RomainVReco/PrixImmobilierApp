package com.priximmo.geojson.geomutation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Geomutation {
    @JsonProperty("type")
    private String type;
    @JsonProperty("count")
    private int count;
    @JsonProperty("next")
    private String next;
    @JsonProperty("previous")
    private String previous;
    @JsonProperty("features")
    private List<FeatureMutation> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<FeatureMutation> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureMutation> features) {
        this.features = features;
    }

    public String showGeomutationContent() {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        String limitLine = "\n################################################\n\n";
        System.out.println(getFeatures().size());
        for (FeatureMutation feature : getFeatures()) {
            sb.append("Nombre de mutation(s) : ").append(this.count).append(",\n");
            sb.append("Id de mutation : ").append(feature.getId()).append("\n");
            sb.append("Date de mutation : ").append(feature.getGeomutationPoperties().getDatemut()).append("\n");
            sb.append("Type de mutation : ").append((feature.getGeomutationPoperties().getLibnatmut())).append("\n");
            sb.append("VEFA : ").append(feature.getGeomutationPoperties().isVefa()).append("\n");
            sb.append("Montant de transaction : ").append(feature.getGeomutationPoperties().getValeurfonc()).append("\n");
            sb.append("Parcelle(s)  : ").append(feature.getGeomutationPoperties().getlIdpar().toString()).append("\n");
            sb.append("Nombre de lot  : ").append(feature.getGeomutationPoperties().getNblocmut()).append("\n");
            sb.append("Surface du bati : ").append(feature.getGeomutationPoperties().getSbati()).append("m²").append("\n");
            sb.append("Type de bien : ").append(feature.getGeomutationPoperties().getLibtypbien()).append("\n");
            if (!(i == getFeatures().size()-1)) {
                sb.append(limitLine);
            }
            i++;
        }
        return sb.toString();
    }
}
