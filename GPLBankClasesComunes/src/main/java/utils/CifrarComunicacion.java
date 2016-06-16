/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Base64;

/**
 * Permite cifrar y descifrar la comunicación de manera simétrica.
 * @author JoséMaría
 */
public class CifrarComunicacion {
    
    private static CifrarComunicacion instance;
    
    private CifrarComunicacion(){
        
    }
    
    public static CifrarComunicacion getInstance(){
        if (instance == null){
            instance = new CifrarComunicacion();
        }
        
        return instance;
    }
    
    public String cifrar(String stringACifrar, SecretKey key) throws Exception {
        byte[] bytes = PasswordHash.getInstance().cifra(stringACifrar, key);
        String mandar = new String(Base64.encodeBase64(bytes));
        return mandar;
    }
    
    public String descifrar (String stringAdescifrar, SecretKey key) throws Exception {
        byte[] b64 = Base64.decodeBase64(stringAdescifrar);
        return PasswordHash.getInstance().descifra(b64, key);
    }
    
}
