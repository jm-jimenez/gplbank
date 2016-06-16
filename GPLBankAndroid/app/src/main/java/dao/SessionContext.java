/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.crypto.SecretKey;

import cz.msebera.android.httpclient.client.protocol.HttpClientContext;

/**
 *
 * @author JoséMaría
 */
public class SessionContext {
    
    private static SessionContext instance;
    
    private HttpClientContext context;
    private SecretKey secretKey;
    
    private SessionContext(){
        context = HttpClientContext.create();
    }
    
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
