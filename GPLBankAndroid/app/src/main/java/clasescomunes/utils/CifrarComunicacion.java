/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.utils;


import javax.crypto.SecretKey;

import cz.msebera.android.httpclient.extras.Base64;


/**
 *
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
        String mandar = new String(Base64.encode(bytes, 0));
        return mandar;
    }
    
    public String descifrar (String stringAdescifrar, SecretKey key) throws Exception {
        byte[] b64 = Base64.decode(stringAdescifrar, 0);
        return PasswordHash.getInstance().descifra(b64, key);
    }
    
}
