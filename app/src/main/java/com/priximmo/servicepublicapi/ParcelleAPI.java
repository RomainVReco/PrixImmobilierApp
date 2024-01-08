package com.priximmo.servicepublicapi;

import com.priximmo.servicepublicapi.AbstractRequestAPI;
import com.priximmo.servicepublicapi.ConverterURL;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ParcelleAPI extends AbstractRequestAPI {
    final String URL_API = "https://apicarto.ign.fr/api/cadastre/parcelle?";
    URL URL;
    String parameters;
    public ParcelleAPI(String query, String parameters) throws IOException, URISyntaxException {
        this.parameters = parameters;
        String preparedParameter = parameters;
        String encodedQuery = new ConverterURL(query).getEncodedQuery();
        URL = new URI(URL_API+preparedParameter+encodedQuery).toURL();
        this.conn = this.getRequestResult(this.URL);
    }

}
