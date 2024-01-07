package com.priximmo.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.priximmo.geojson.adresseban.AdresseBAN;
import com.priximmo.geojson.adresseban.FeatureAdresseBAN;
import com.priximmo.geojson.geomutation.FeatureMutation;
import com.priximmo.geojson.geomutation.Geomutation;
import com.priximmo.servicepublicapi.*;
import com.priximmo.userinput.GestionUser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class FindMutation {
    protected AbstractRequestAPI callAPI;
    protected ResponseManagerHTTP<AdresseBAN> responseManagerAdresse;
    protected ResponseManagerHTTP<Geomutation> responseManagerGeomutation;
    protected final String EMPTY_RETURN = "No object in POJO";
    protected Set<FeatureMutation> setOfGeomutations = new HashSet<>();
    Geomutation geomutation;
    GestionUser gestionUser = new GestionUser();


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public AdresseBAN getAdressFromQuery() throws IOException, URISyntaxException {
        String query = gestionUser.promptString("Donner une adresse");
        callAPI = new AdresseAPI(query);
        responseManagerAdresse = new ResponseManagerHTTP<>();
        Optional<AdresseBAN> optionalAdresseBAN = responseManagerAdresse.getAPIReturn(callAPI, AdresseBAN.class);
        if (optionalAdresseBAN.isEmpty()){
            System.out.println("Erreur lors de la requête de cette adresse");
        }
        return optionalAdresseBAN.orElse(new AdresseBAN());
    }

    public FeatureAdresseBAN selectAdressInList(AdresseBAN adresseBan) throws IOException, URISyntaxException {
        HashMap<Integer, FeatureAdresseBAN> listeOfAdress = new HashMap<>();
        int i = 1;
        System.out.println("\nSélectionnez l'adresse exacte : ");
        for (FeatureAdresseBAN adresse : adresseBan.getFeatures()) {
            listeOfAdress.put(i, adresse);
            System.out.printf("[%d] "+adresse.showAdressLabel()+"\n",i);
            i++;
        }
        String userChoice = gestionUser.promptSingleDigit("Numéro de ligne", adresseBan.getFeatures().size());
        return listeOfAdress.get(Integer.parseInt(userChoice));
    }

    public String getGeomtryPointFromAdress(FeatureAdresseBAN adressToLook) {
        return adressToLook.getGeometry().toString();
    }

    public void getGeomutationsFromTerrain(String bboxOfFeuille, String cityCode) throws URISyntaxException, IOException {
        System.out.println("Pour quelle année souhaitez-vous faire une recherche ? à partir de 2010");
        String inputYear = gestionUser.promptYear();
        callAPI = new GeomutationAPI(inputYear, cityCode, bboxOfFeuille);
        responseManagerGeomutation = new ResponseManagerHTTP<>();
        Optional<Geomutation> optionalGeomutation = responseManagerGeomutation.getAPIReturn(callAPI, Geomutation.class);
        if (optionalGeomutation.isPresent()) {
            geomutation = optionalGeomutation.orElse(new Geomutation());
            System.out.println(geomutation.showGeomutationContent());
            setOfGeomutations.addAll(geomutation.getFeatures());
            while (geomutation.getNext() != null) {
                callAPI = new NextPageAPI(geomutation.getNext());
                geomutation = responseManagerGeomutation.getAPIReturn(callAPI, Geomutation.class).get();
                System.out.println(geomutation.showGeomutationContent());
                setOfGeomutations.addAll(geomutation.getFeatures());
            }
        } else {
            System.out.println("Pas de mutation pout cette adresse");
        }
    }

    public AbstractRequestAPI getCallAPI() {
        return callAPI;
    }

    public void setCallAPI(AbstractRequestAPI callAPI) {
        this.callAPI = callAPI;
    }

    public ResponseManagerHTTP<AdresseBAN> getResponseManagerAdresse() {
        return responseManagerAdresse;
    }

    public void setResponseManagerAdresse(ResponseManagerHTTP<AdresseBAN> responseManagerAdresse) {
        this.responseManagerAdresse = responseManagerAdresse;
    }

    public ResponseManagerHTTP<Geomutation> getResponseManagerGeomutation() {
        return responseManagerGeomutation;
    }

    public void setResponseManagerGeomutation(ResponseManagerHTTP<Geomutation> responseManagerGeomutation) {
        this.responseManagerGeomutation = responseManagerGeomutation;
    }

    public Set<FeatureMutation> getSetOfGeomutations() {
        return setOfGeomutations;
    }

    public void setSetOfGeomutations(Set<FeatureMutation> setOfGeomutations) {
        this.setOfGeomutations = setOfGeomutations;
    }

    public Geomutation getGeomutation() {
        return geomutation;
    }

    public void setGeomutation(Geomutation geomutation) {
        this.geomutation = geomutation;
    }

    public GestionUser getGestionUser() {
        return gestionUser;
    }
}
