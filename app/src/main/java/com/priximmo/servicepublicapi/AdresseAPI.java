package com.priximmo.servicepublicapi;

import android.util.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AdresseAPI extends AbstractRequestAPI {

    final String URL_API = "https://api-adresse.data.gouv.fr/search/?q=";
    URL URL;

    public AdresseAPI(String query) {
        try {
            String encodedQuery = new ConverterURL(query).getEncodedQuery();
            URL = new URI(URL_API+encodedQuery).toURL();
            this.conn = this.getRequestResult(this.URL);
            Log.d("AdresseAPI", String.valueOf(this.conn.getResponseCode()));
            Log.d("AdresseAPI", String.valueOf(this.conn.getResponseMessage()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error with AdresseAPI call");
        }
    }

}
