package com.priximmo.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.priximmo.geojson.adresseban.AddressBAN;
import com.priximmo.geojson.adresseban.FeatureAddressBAN;
import com.priximmo.geojson.feuille.Feuille;
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

public class FindMutationFeuille extends FindMutation {

    private AbstractRequestAPI callAPI;
    private ResponseManagerHTTP<AddressBAN> responseManagerAdresse;
    private ResponseManagerHTTP<Feuille> responseManagerFeuille;
    private ResponseManagerHTTP<Geomutation> responseManagerGeomutation;
    private final String EMPTY_RETURN = "No object in POJO";
    private Set<FeatureMutation> setOfGeomutations = new HashSet<>();
    Geomutation geomutation;
    GestionUser gestionUser = new GestionUser();

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public FindMutationFeuille(String query) {
        try {
            getAdressFromQuery();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String bboxOfFeuille = new String();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public AddressBAN getAdressFromQuery() throws IOException, URISyntaxException {
        String query = gestionUser.promptString("Donner une adresse : ");
        callAPI = new AdresseAPI(query);
        responseManagerAdresse = new ResponseManagerHTTP<>();
        Optional<AddressBAN> optionalAdresseBAN = responseManagerAdresse.getAPIReturn(callAPI, AddressBAN.class);
        if (optionalAdresseBAN.isEmpty()){
            System.out.println("Erreur lors de la requête de cette adresse");
        } else getListOfAdress(optionalAdresseBAN);
        return null;
    }

    private void getListOfAdress(Optional<AddressBAN> optionalAdresseBAN) throws IOException, URISyntaxException {
        String bboxOfFeuille;
        AddressBAN addressBan;
        if (optionalAdresseBAN.isPresent()){
            addressBan = optionalAdresseBAN.get();
        } else {
            System.out.println("Pas de résultat pour cette adresse : "+optionalAdresseBAN.get().getQuery());
            return;
        }
        if (addressBan.getFeatures().size()==1) {
            String cityCode = addressBan.getFeatures().get(0).getProperties().getCitycode();
            String geometryPoint = addressBan.getFeatures().get(0).getGeometry().toString();
            bboxOfFeuille = getBboxOfFeuille(geometryPoint);
            getGeomutationsFromTerrain(bboxOfFeuille, cityCode);

        } else if (addressBan.getFeatures().size()>1) {
            FeatureAddressBAN selectedAdresse = selectAdressInList(addressBan);
            String geometryPoint = selectedAdresse.getGeometry().toString();
            String cityCode = selectedAdresse.getProperties().getCitycode();
            bboxOfFeuille = getBboxOfFeuille(geometryPoint);
            getGeomutationsFromTerrain(bboxOfFeuille, cityCode);
        }
    }

    public FeatureAddressBAN selectAdressInList(AddressBAN addressBan) {
        HashMap<Integer, FeatureAddressBAN> listeOfAdress = new HashMap<>();
        int i = 1;
        System.out.println("\nSélectionnez l'adresse exacte : ");
        for (FeatureAddressBAN adresse : addressBan.getFeatures()) {
            listeOfAdress.put(i, adresse);
            System.out.printf("[%d] "+adresse.showAdressLabel()+"\n",i);
            i++;
        }
        String userChoice = gestionUser.promptSingleDigit("Numéro de ligne", addressBan.getFeatures().size());
        return listeOfAdress.get(Integer.parseInt(userChoice));
    }

    private String getBboxOfFeuille(String geometryPoint) throws IOException, URISyntaxException {
        responseManagerFeuille = new ResponseManagerHTTP<>();
        callAPI = new FeuilleAPI(geometryPoint);
        Optional<Feuille> optionalFeuille = responseManagerFeuille.getAPIReturn(callAPI, Feuille.class);
        return optionalFeuille.get().convertBboxToString();
    }

}
