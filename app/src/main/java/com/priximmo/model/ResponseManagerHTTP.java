package com.priximmo.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priximmo.servicepublicapi.AbstractRequestAPI;

import com.priximmo.exceptions.UnknownResponseCode;

import java.io.IOException;
import java.util.Optional;

public class ResponseManagerHTTP<T> {

    /**
     * A partir du code retour de l'appel à l'API Adresse, retourne ou non un POJO représentant les adresses contenues
     * dans la réponse
     * @param request
     * @return Un Optional contenant null ou un POJO AdresseBAN
     * @throws IOException
     */
    public Optional<T> getAPIReturn(AbstractRequestAPI request, Class<T> typeClass)  {
        try {
            if (isSuccess(request)) {
                T objectResponse;
                String jsonResponse = request.readReponseFromAPI(request.getConn());
                ObjectMapper objectMapper = new ObjectMapper();
                objectResponse = objectMapper.readValue(jsonResponse, typeClass);
                return Optional.of(objectResponse);
            }
        } catch (UnknownResponseCode | IOException e) {
            e.printStackTrace();
            System.out.println("Could not convert API call in POJO");
        }
        return Optional.empty();
    }

    private boolean isSuccess(AbstractRequestAPI requestAPI) throws IOException, UnknownResponseCode {
        switch (requestAPI.getConn().getResponseCode()) {
            case 200 -> {
                System.out.println("Code retour : 200. Requête OK");
                return true;
            }
            case 400 -> {
                System.out.println("Code retour : 400");
                System.out.println("Motif : " + requestAPI.getConn().getResponseMessage());
                return false;
            }
            case 403 -> {
                System.out.println("Code retour : 403");
                System.out.println("Motif : " + requestAPI.getConn().getResponseMessage());
                return false;
            }
            case 404 -> {
                System.out.println("Code retour : 404");
                System.out.println("Motif : " + requestAPI.getConn().getResponseMessage());
                return false;
            }
            case 429 -> {
                System.out.println("Code retour : 429");
                System.out.println("Motif : " + requestAPI.getConn().getResponseMessage());
                return false;
            }
            case 500 -> {
                System.out.println("Code retour : 500. Avez-vous une connexion internet ?");
                System.out.println("Motif : " + requestAPI.getConn().getResponseMessage());
                return false;
            }

            case 504 -> {
                System.out.println("Code retour : 504. Time out");
                System.out.println("Motif : " + requestAPI.getConn().getResponseMessage());
                return false;
            }
            default -> {
                System.out.println("Cas non prévu, il faut checker les logs");
                throw new UnknownResponseCode("Code erreur inconnu : " + requestAPI.getConn().getResponseMessage());
            }
        }
    }
}
