/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.crypto.SecretKey;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * Clase estática que almacena el contexto de las peticiones http y la SecretKey utilizada para cifrar.
 * Implementa el patrón singleton de tal manera que en la primera llamada que se utilice el contexto,
 * se crea con el método estático de HttpClientContext. En las sucesivas llamadas, se devuelve la instancia,
 * que almacenará la cookiestore y otra información de sesión relevante.
 * @author JoséMaría
 */
public class SessionContext {
    
    private static SessionContext instance;
    
    private HttpClientContext context;
    private SecretKey secretKey;
    
    private SessionContext(){
        context = HttpClientContext.create();
    }
    
    /**
     * Devuelve la instancia o, si no existe, crea una nueva.
     * @return 
     */
    public static SessionContext getInstance(){
        if (instance == null){
            instance = new SessionContext();
        }
        return instance;
    }

    public HttpClientContext getContext() {
        return context;
    }

    public void setContext(HttpClientContext context) {
        this.context = context;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
    
    
}
