package com.priximmo.TestJackson;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.priximmo.geojson.parcelle.Parcelle;
import com.priximmo.servicepublicapi.ParcelleAPI;
import com.priximmo.retrofitapi.parcelle.ParcelleRetrofitAPI;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

class ParcelleTest {

    @Test
    void convertBboxToString() throws IOException, URISyntaxException {
        ObjectMapper anotherMapper = new ObjectMapper();
        String queryParcelle = "{\"type\": \"Point\",\"coordinates\": [2.32557,48.830378]}";
        ParcelleAPI parcelleAPI = new ParcelleAPI(Map.of("geom=", queryParcelle));
        String jsonParcelle = parcelleAPI.readReponseFromAPI(parcelleAPI.getConn());
        Parcelle parcelle = anotherMapper.readValue(jsonParcelle, Parcelle.class);
        String bboxAvMaine = parcelle.convertBboxToString();
        System.out.println(bboxAvMaine);
        assertEquals("2.32476735,48.83012991,2.32562559,48.83054392", bboxAvMaine);

    }
}