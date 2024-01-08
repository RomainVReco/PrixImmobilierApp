package com.priximmo.TestJackson;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.priximmo.geojson.parcelle.Parcelle;
import com.priximmo.servicepublicapi.parcelle.ParcelleAPI;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class ParcelleTest {

    @Test
    void convertBboxToString() throws IOException, URISyntaxException {
        ObjectMapper anotherMapper = new ObjectMapper();
        String queryParcelle = "{\"type\": \"Point\",\"coordinates\": [2.32557,48.830378]}";
        ParcelleAPI parcelleAPI = new ParcelleAPI(queryParcelle, "geom");
        String jsonParcelle = parcelleAPI.readReponseFromAPI(parcelleAPI.getConn());
        Parcelle parcelle = anotherMapper.readValue(jsonParcelle, Parcelle.class);
        String bboxAvMaine = parcelle.convertBboxToString();
        System.out.println(bboxAvMaine);
        assertEquals("2.32476735,48.83012991,2.32562559,48.83054392", bboxAvMaine);

    }
}