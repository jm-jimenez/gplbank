package dao;

/**
 * Permite configurar la dirección que se utilizará como servidor de aplicaciones.
 * Created by JoséMaría on 16/05/2016.
 */
public class Configuration {

    private static Configuration instance;
    private String serverUrl;

    private Configuration(){
        serverUrl = "http://dam2chema.ddns.net:1380/GPLBankServidor-1";
    }

    public static Configuration getInstance(){
        if (instance == null){
            instance = new Configuration();
        }
        return instance;
    }

    public String getServerUrl(){
        return serverUrl;
    }
}
