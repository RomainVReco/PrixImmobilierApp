package com.priximmo.config;

public final class Config {
    private static Config config;
    String os = System.getProperty("os.name");

    private Config (){

    }

    public String getOs() {
        return os;
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public String givePath(){
        if (this.os.contains("Mac")) return "Ressources/";
        else return "Ressouces\\";

    }
}
