package com.priximmo.servicepublicapi;

import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GeomutationAPI extends AbstractRequestAPI {
    final String URL_API = "https://apidf-preprod.cerema.fr/dvf_opendata/geomutations/?";
    URL URL;

    public GeomutationAPI (String anneeMutation, String codeInsee, String bbox) throws URISyntaxException, IOException {
        StringBuilder sb = new StringBuilder();
        String encodedQuery = "&in_bbox="+new ConverterURL(bbox).getEncodedQuery();
        String code_insee = ("&code_insee="+codeInsee);
        String annee_mutation = "anneemut="+anneeMutation;
        sb.append(URL_API).append(annee_mutation).append(code_insee).append(encodedQuery);
        URL = new URI(sb.toString()).toURL();
        System.out.println(URL);
        boolean hasSucceed = false;
        int numberOfTries = 0;
        do {
            try {
                this.conn = this.getRequestResult(this.URL);
                hasSucceed = true;
            } catch (SocketException e) {
                System.out.println("Pas de réponse du serveur");
                hasSucceed = false;
                numberOfTries++;
            }
        } while ((!hasSucceed) || (numberOfTries > 3));
    }

    /**
     * A partir de l'année minimum et de la zone, retourne l'ensemble des mutations
     * @param anneeMutMin
     * @param bbox
     * @throws URISyntaxException
     * @throws IOException
     */
    public GeomutationAPI (String anneeMutMin, String bbox) throws URISyntaxException, IOException {
        StringBuilder sb = new StringBuilder();
        String encodedQuery = "&in_bbox="+new ConverterURL(bbox).getEncodedQuery();
        String annee_mutmin = "annee_mutmin="+anneeMutMin;

        sb.append(annee_mutmin).append(encodedQuery);
        URL = new URI(sb.toString()).toURL();
        System.out.println(URL);
        this.conn = this.getRequestResult(this.URL);
        System.out.println("Response code: " + conn.getResponseCode());
    }

    public GeomutationAPI (String anneemut, String contains_geom, String codeInsee, String bbox) throws URISyntaxException, IOException {
        String encodedQuery = "&in_bbox="+new ConverterURL(bbox).getEncodedQuery();
        String annee_mut = "anneemut="+anneemut;
        String geom = "&contains_geom="+contains_geom;

        StringBuilder sb = new StringBuilder();
        sb.append(annee_mut).append(geom).append(encodedQuery);
        /**
         * L'ordre des paramètres change les résultats...
         */
        URL = new URI(sb.toString()).toURL();
        System.out.println(URL);
        this.conn = this.getRequestResult(this.URL);
        System.out.println("Response code: " + conn.getResponseCode());
    }
}
