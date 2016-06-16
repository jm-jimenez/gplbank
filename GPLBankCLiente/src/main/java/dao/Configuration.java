/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 * Inicializa la configuración de la aplicación. Se trata de un patron sigleton
 * de tal manera que la clase se instancia una vez y se comparte entre las sucesivas llamadas.
 * Resulta relevante para cargar desde un fichero YML la configuración de la aplicación,
 * que incluye la dirección hacia la cual dirigir las peticiones http.
 * @author JoséMaría
 */
public class Configuration {
    
    private String serverUrl;
    private static Configuration instance;
    
    private Configuration(){
        
    }
    
    /**
     * Devuelve la instancia de Configuración. Si ya existe, la devuelve, y si no, crea una
     * nueva instancia desde el fichero config.yml.
     * @return La instancia estática.
     */
    public static Configuration getInstance(){
        if (instance == null){
            Yaml yaml = new Yaml();
            try {
                File f = new File("./config.yml");
                if (f.createNewFile()){
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    bw.write("serverUrl: http://dam2chema.ddns.net:1380/GPLBankServidor-1");
                    bw.close();
                }
                instance = yaml.loadAs(new FileInputStream("./config.yml"), Configuration.class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return instance;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    } 
}
