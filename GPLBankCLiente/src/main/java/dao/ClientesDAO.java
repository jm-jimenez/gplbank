/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.personas.Cliente;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import utils.CifrarComunicacion;
import utils.Info;
import utils.Parsear;

/**
 * Se encarga de hacer las peticiones http relacionadas con los clientes.
 * Todos las peticiones que sean por método post y necesiten enviar objetos,
 * enviarán el mismo parseado a json-string y posteriormente cifrado, haciendo
 * la comunicación segura.
 * @author JoséMaría
 */
public class ClientesDAO {
    
    /**
     * Realiza una petición http para dar de alta un nuevo cliente.
     * @param c Cliente a dar de alta.
     * @return Info con el resultado de la petición.
     */
    public Info addCliente(Cliente c){
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl()+"/controlClientes");
            List <NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "add"));
            parametros.add(new BasicNameValuePair("cliente", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return info;
    }
    
    /**
     * Realiza una petición http para dar de baja un cliente.
     * @param c Cliente a dar de baja.
     * @return Info con el resultado de la petición.
     */
    public Info deleteClienteConDni(Cliente c){
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlClientes");
            List <NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "del"));
            parametros.add(new BasicNameValuePair("cliente", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return info;
    }
    
    /**
     * Hace una peticion http para recuperar un Cliente de base de datos.
     * @param c Cliente que solo traerá inicializado el atributo dni, por el cual será buscado en la base de datos.
     * @return Info con el resultado de la petición.
     */
    public Info buscarClienteConDni(Cliente c){
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlClientes");
            List <NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "get"));
            parametros.add(new BasicNameValuePair("cliente", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return info;
    }
    
    /**
     * Realiza una petición http para modificar los datos del cliente. El objeto cliente
     * que se envía ya tiene los datos modificados.
     * @param c Cliente con los nuevos datos, para modificar los almacenados.
     * @return Info con el resultado de la petición.
     */
    public Info modificarCliente(Cliente c){
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlClientes");
            List <NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "modify"));
            parametros.add(new BasicNameValuePair("cliente", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return info;
    }
}
