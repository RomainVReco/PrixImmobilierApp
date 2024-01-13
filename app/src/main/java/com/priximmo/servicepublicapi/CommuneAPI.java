package com.priximmo.servicepublicapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CommuneAPI extends AbstractRequestAPI {

    final String URL_API = "https://geo.api.gouv.fr/communes?codePostal=";
    public CommuneAPI(String query) throws URISyntaxException, IOException {
        String finalQuery = URL_API+query;
        URL URL = new URI(finalQuery).toURL();
        this.conn = this.getRequestResult(URL);
    }
}
