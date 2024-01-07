package com.priximmo.servicepublicapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class NextPageAPI extends AbstractRequestAPI {
    URL URL;

    public NextPageAPI (String nextPageURL) {
        String httpsNextPageURL = transformToHTTPS(nextPageURL);
        try {
            URL = new URI(httpsNextPageURL).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println(URL);
            try {
                this.conn = this.getRequestResult(this.URL);
            } catch (IOException e) {
                System.out.println("Pas de réponse du serveur");
            }
    }

    private String transformToHTTPS(String nextPageURL) {
        StringBuilder sb = new StringBuilder(nextPageURL);
        char s = 's';
        sb.insert(4, s);
        return sb.toString();
    }
}
