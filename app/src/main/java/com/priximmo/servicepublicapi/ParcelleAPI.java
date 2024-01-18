package com.priximmo.servicepublicapi;

import com.priximmo.servicepublicapi.AbstractRequestAPI;
import com.priximmo.servicepublicapi.ConverterURL;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ParcelleAPI extends AbstractRequestAPI {
    final String URL_API = "https://apicarto.ign.fr/api/cadastre/parcelle?";
    URL URL;
    public ParcelleAPI(Map<String, String> mapOfQueries) throws IOException, URISyntaxException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry: mapOfQueries.entrySet()) {
            sb.append(entry.getKey()).append(new ConverterURL(entry.getValue()).getEncodedQuery()).append("&");
        }
        URL = new URI(URL_API+sb.toString()).toURL();
        this.conn = this.getRequestResult(this.URL);
    }
 }
