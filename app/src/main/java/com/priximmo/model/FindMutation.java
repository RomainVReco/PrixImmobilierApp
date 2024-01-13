package com.priximmo.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.priximmo.exceptions.NoParcelleException;
import com.priximmo.geojson.adresseban.AddressBAN;
import com.priximmo.geojson.adresseban.FeatureAddressBAN;
import com.priximmo.geojson.feuille.Feuille;
import com.priximmo.geojson.geomutation.FeatureMutation;
import com.priximmo.geojson.geomutation.Geomutation;
import com.priximmo.geojson.parcelle.FeatureParcelle;
import com.priximmo.geojson.parcelle.OrderByDistanceReference;
import com.priximmo.geojson.parcelle.Parcelle;
import com.priximmo.geojson.parcelle.SimplifiedParcelle;
import com.priximmo.servicepublicapi.*;
import com.priximmo.userinput.GestionUser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class FindMutation {
    protected AbstractRequestAPI callAPI;
    protected ResponseManagerHTTP<AddressBAN> responseManagerAdresse;
    protected ResponseManagerHTTP<Geomutation> responseManagerGeomutation;
    protected final String EMPTY_RETURN = "No object in POJO";
    protected Set<FeatureMutation> setOfGeomutations = new HashSet<>();
    Geomutation geomutation;
    GestionUser gestionUser = new GestionUser();


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public AddressBAN getAdressFromQuery() throws IOException, URISyntaxException {
        String query = gestionUser.promptString("Donner une adresse");
        callAPI = new AdresseAPI(query);
        responseManagerAdresse = new ResponseManagerHTTP<>();
        Optional<AddressBAN> optionalAdresseBAN = responseManagerAdresse.getAPIReturn(callAPI, AddressBAN.class);
        if (optionalAdresseBAN.isEmpty()){
            System.out.println("Erreur lors de la requête de cette adresse");
        }
        return optionalAdresseBAN.orElse(new AddressBAN());
    }

    public FeatureAddressBAN selectAdressInList(AddressBAN addressBan) throws IOException, URISyntaxException {
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

    public String getGeomtryPointFromAdress(FeatureAddressBAN adressToLook) {
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

    public String getNearestSection(String cityCode, String geometryPoint) throws IOException, URISyntaxException, NoParcelleException {
        callAPI = new FeuilleAPI(cityCode, geometryPoint, 2);
        ResponseManagerHTTP<Feuille> feuilleResponseManagerHTTP = new ResponseManagerHTTP<>();
        Optional<Feuille> optionalFeuille = feuilleResponseManagerHTTP.getAPIReturn(callAPI, Feuille.class);
        if (optionalFeuille.isPresent() && optionalFeuille.get().getNumberReturned()!=0) {
            return optionalFeuille.get().getFeaturesTerrain().get(0).getTerrainProperties().getSection();
        } else throw new NoParcelleException("Pas de section trouvée");
    }

    public String checkForNearestParcelle(Parcelle parcelleToReview, String geometryPoint) {
        List<Double> longLat = new ArrayList<>();
        longLat = extractLatitudeLongitude(geometryPoint);
        List<SimplifiedParcelle> simplifiedParcelleList = new ArrayList<>();
        for (FeatureParcelle featureTerrain : parcelleToReview.getFeaturesTerrain()) {
            SimplifiedParcelle subparcelle = new SimplifiedParcelle();
            subparcelle.setBbox(featureTerrain.getTerrainProperties().getBbox());
            subparcelle.setGeometryPolygon(featureTerrain.getGeometry());
            subparcelle.setId(featureTerrain.getTerrainProperties().getIdu());
            subparcelle.setConvertedBbox(featureTerrain.getTerrainProperties().convertBboxToString());
            // racine((xb-xa)²+(yb-ya)²)
            double distanceMinEucliX = Math.pow(subparcelle.getBbox().get(0)-longLat.get(0),2);
            double distanceMinEucliY = Math.pow(subparcelle.getBbox().get(1)-longLat.get(1),2);
            double distanceMinLongitude = Math.sqrt(distanceMinEucliX+distanceMinEucliY);

            double distanceMaxEucliX = Math.pow(subparcelle.getBbox().get(2)-longLat.get(0),2);
            double distanceMaxEucliY = Math.pow(subparcelle.getBbox().get(3)-longLat.get(1),2);
            double distanceMaxLongitude = Math.sqrt(distanceMaxEucliX+distanceMaxEucliY);
            subparcelle.setDistanceFromReference(Math.abs(distanceMinLongitude)+Math.abs(distanceMaxLongitude));
            simplifiedParcelleList.add(subparcelle);
        }
        Collections.sort(simplifiedParcelleList, new OrderByDistanceReference());
        System.out.println(simplifiedParcelleList.get(0).toString());
        return simplifiedParcelleList.get(0).getConvertedBbox();
    }

    private List<Double> extractLatitudeLongitude(String geometryPoint) {
        int firstBrace = geometryPoint.indexOf("[");
        String removeFirstBrace = geometryPoint.substring(firstBrace+1);
        int secondBrace = removeFirstBrace.indexOf("]");
        String removeSecondBrace = removeFirstBrace.substring(0, secondBrace);
        String[] coordinatesToParse = removeSecondBrace.split(",");
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(Double.valueOf(coordinatesToParse[0]));
        doubleList.add(Double.valueOf(coordinatesToParse[1]));
        return doubleList;
    }

    public AbstractRequestAPI getCallAPI() {
        return callAPI;
    }

    public void setCallAPI(AbstractRequestAPI callAPI) {
        this.callAPI = callAPI;
    }

    public ResponseManagerHTTP<AddressBAN> getResponseManagerAdresse() {
        return responseManagerAdresse;
    }

    public void setResponseManagerAdresse(ResponseManagerHTTP<AddressBAN> responseManagerAdresse) {
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
