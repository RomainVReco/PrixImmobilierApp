package com.priximmo.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.priximmo.exceptions.NoParcelleException;
import com.priximmo.geojson.adresseban.AddressBAN;
import com.priximmo.geojson.adresseban.FeatureAddressBAN;
import com.priximmo.geojson.parcelle.Parcelle;
import com.priximmo.servicepublicapi.CommuneAPI;
import com.priximmo.servicepublicapi.ParcelleAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FindMutationParcelle extends FindMutation {
    boolean hasFoundAddress = false;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public FindMutationParcelle () throws IOException, URISyntaxException, NoParcelleException {
        FeatureAddressBAN adressToLook = new FeatureAddressBAN() ;
        while (!hasFoundAddress){
            AddressBAN listOfAdress = getAdressFromQuery();
            if (listOfAdress.getFeatures().size()>0){
                adressToLook = selectAdressInList(listOfAdress);
                hasFoundAddress = true;
            } else hasFoundAddress = false;
        }
        String geometryPoint = getGeomtryPointFromAdress(adressToLook);
        String postCode = getCityCodeFromAdress(adressToLook);
        String codeInsee = getCodeInseeFromPostCode(postCode);
        String bbox = getBboxFromParcelle(geometryPoint);
        if (bbox.equals("empty")) {
            System.out.println("Oups, je suis vide");
            String section = getNearestSection(codeInsee, geometryPoint);
            bbox = getParecelleBboxFromSection(codeInsee, section, geometryPoint);
        }
        getGeomutationsFromTerrain(bbox, codeInsee);
    }

    private String getCodeInseeFromPostCode(String postCode) throws URISyntaxException, IOException {
        callAPI = new CommuneAPI(postCode);
        String jsonResponse = callAPI.readReponseFromAPI(callAPI.getConn());
        return jsonResponse.substring(38,43);
    }

    private String getCityCodeFromAdress(FeatureAddressBAN adressToLook) {
        return adressToLook.getProperties().getCitycode();
    }

    private String getBboxFromParcelle(String geometryPoint) throws IOException, URISyntaxException {
        ResponseManagerHTTP<Parcelle> responseManagerParcelle = new ResponseManagerHTTP<>();
        callAPI = new ParcelleAPI(Map.of("geom=", geometryPoint));
        Optional<Parcelle> optionalParcelle = responseManagerParcelle.getAPIReturn(callAPI, Parcelle.class);
        if (optionalParcelle.isPresent()){
            return optionalParcelle.get().convertBboxToString();
        } else return "empty";
    }

    public String getParecelleBboxFromSection(String cityCode, String section, String geometryPoint) throws IOException, URISyntaxException, NoParcelleException {
        HashMap mapOfQueries = new HashMap<>();
        mapOfQueries.put("code_insee=", cityCode);
        mapOfQueries.put("section=", section);
        mapOfQueries.put("geom=", geometryPoint);
        callAPI = new ParcelleAPI(mapOfQueries);
        ResponseManagerHTTP<Parcelle> parcelleResponseManagerHTTP = new ResponseManagerHTTP<>();
        Optional<Parcelle> optionalParcelle = parcelleResponseManagerHTTP.getAPIReturn(callAPI, Parcelle.class);
        if (optionalParcelle.isPresent()) {
            Parcelle parcelleToReview = optionalParcelle.get();
            return checkForNearestParcelle(parcelleToReview, geometryPoint);
        } else throw new NoParcelleException("Pas de parcelle trouvée");
    }



}
