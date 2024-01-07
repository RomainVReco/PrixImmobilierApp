package com.priximmo.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.priximmo.exceptions.NoParcelleException;
import com.priximmo.geojson.adresseban.AddressBAN;
import com.priximmo.geojson.adresseban.FeatureAddressBAN;
import com.priximmo.geojson.parcelle.Parcelle;
import com.priximmo.servicepublicapi.ParcelleAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class FindMutationParcelle extends FindMutation {
    boolean hasFoundAddress = false;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public FindMutationParcelle () throws IOException, URISyntaxException {
        FeatureAddressBAN adressToLook = new FeatureAddressBAN() ;
        while (!hasFoundAddress){
            AddressBAN listOfAdress = getAdressFromQuery();
            if (listOfAdress.getFeatures().size()>0){
                adressToLook = selectAdressInList(listOfAdress);
                hasFoundAddress = true;
            } else hasFoundAddress = false;
        }
        String geometryPoint = getGeomtryPointFromAdress(adressToLook);
        String cityCode = getCityCodeFromAdress(adressToLook);
        String bbox = getBboxFromParcelle(geometryPoint);
        if (bbox.equals("empty")) try {
            throw new NoParcelleException("Pas de parcelle trouvée");
        } catch (NoParcelleException e) {
            e.printStackTrace();
        } else getGeomutationsFromTerrain(bbox, cityCode);
    }

    private String getCityCodeFromAdress(FeatureAddressBAN adressToLook) {
        return adressToLook.getProperties().getCitycode();
    }

    private String getBboxFromParcelle(String geometryPoint) throws IOException, URISyntaxException {
        ResponseManagerHTTP<Parcelle> responseManagerParcelle = new ResponseManagerHTTP<>();
        callAPI = new ParcelleAPI(geometryPoint, "geom=");
        Optional<Parcelle> optionalParcelle = responseManagerParcelle.getAPIReturn(callAPI, Parcelle.class);
        if (optionalParcelle.isPresent()){
            return optionalParcelle.get().convertBboxToString();
        } else return "empty";
    }



}
