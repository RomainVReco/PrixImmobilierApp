package com.priximmo.servicepublicapi;

import com.priximmo.config.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public abstract class AbstractRequestAPI {
    Config config;

    HttpsURLConnection conn;

    public AbstractRequestAPI() {

    }

    protected HttpsURLConnection getRequestResult(URL url) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        return conn;
    }

    public String readReponseFromAPI(HttpsURLConnection conn){
        StringBuilder str = new StringBuilder();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                str.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public boolean createJSONFile(String nomFichier, String contenuJson){
        String PATH = Config.getInstance().givePath();
        File file = new File(PATH+nomFichier+".json");
        FileWriter fw;

        try {
            fw = new FileWriter(file);
            fw.append(contenuJson);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public HttpsURLConnection getConn() {
        return conn;
    }

    public void setConn(HttpsURLConnection conn) {
        this.conn = conn;
    }

}
