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
import modelo.personas.Empleado;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import utils.CifrarComunicacion;
import utils.Info;
import utils.Parsear;

/**
 * Se encarga de hacer las peticiones http relacionadas con los empleados.
 * Todos las peticiones que sean por método post y necesiten enviar objetos,
 * enviarán el mismo parseado a json-string y posteriormente cifrado, haciendo
 * la comunicación segura.
 * @author JoséMaría
 */
public class EmpleadosDAO {

    /**
     * Realiza una petición http para autentificar los datos de login de un empleado.
     * @param e Empleado con los datos dni y pass, los cuales serán verificados.
     * @return Info con el resultado de la petición.
     */
    public Info validate(Empleado e) {
        String json = Parsear.getInstance().objectToJson(e);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = new Info(false, null);
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/login");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("empleado", mandar));
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
            info.setMsg("No se pudo conectar con el servidor.\n");
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            info.setMsg("No se ha podido establecer una conexión segura.\n"
                    + "Asegúrese de estar conectado a internet y renicie la aplicación.");
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }

    /**
     * Realiza una petición http para conseguir todos los empleados.
     * @return ArrayList de Empleado.
     */
    public ArrayList<Empleado> getAllEmpleados() {
        ArrayList<Empleado> empleados = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(Configuration.getInstance().getServerUrl() + "/controlEmpleados?opcion=get");
            CloseableHttpResponse response1 = httpclient.execute(httpGet, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String descifrado = CifrarComunicacion.getInstance().descifrar(EntityUtils.toString(entity1), SessionContext.getInstance().getSecretKey());
                empleados = Parsear.getInstance().empleadosListFromJson(descifrado);
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return empleados;
    }

    /**
     * Realiza una petición http para añadir un nuevo empleado. El nuevo empleado va
     * como parámetro post.
     * @param e Empleado a añadir.
     * @return Info con el resultado de la petición.
     */
    public Info addEmpleado(Empleado e) {
        Info info = null;
        String json = Parsear.getInstance().objectToJson(e);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlEmpleados");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "add"));
            parametros.add(new BasicNameValuePair("empleado", mandar));
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
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }

    /**
     * Realiza una petición http para dar de baja un empleado.
     * @param e Empleado a dar de baja.
     * @return Info con el resultado de la petición.
     */
    public Info deleteEmpleado(Empleado e) {
        Info info = null;
        String json = Parsear.getInstance().objectToJson(e);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlEmpleados");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "del"));
            parametros.add(new BasicNameValuePair("empleado", mandar));
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
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }

    /**
     * Realiza una petición http para incrementar el número de clientes captados por el empleado.
     * @param e Empleado que ha captado el cliente.
     */
    public void anotarClienteCaptado(Empleado e) {
        String json = Parsear.getInstance().objectToJson(e);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlEmpleados");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "clienteCaptado"));
            parametros.add(new BasicNameValuePair("empleado", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                //String descifrado = Utils.getInstance().descifrarComunicacion(EntityUtils.toString(entity1), MiContext.getInstance().getKey());
                //info = Utils.getInstance().infoFromJson(descifrado);
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Realiza una petición http para incrementar el número de productos vendidos por el empleado.
     * @param e Empleado que ha realizado la nueva venta.
     */
    public void anotarProductoVendido(Empleado e) {
        String json = Parsear.getInstance().objectToJson(e);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlEmpleados");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "productoVendido"));
            parametros.add(new BasicNameValuePair("empleado", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                //String descifrado = Utils.getInstance().descifrarComunicacion(EntityUtils.toString(entity1), MiContext.getInstance().getKey());
                //info = Utils.getInstance().infoFromJson(descifrado);
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Realiza una petición http para ascender un empleado a Jefe.
     * @param e Empleado a ascender
     * @return Info con el resultado de la petición.
     */
    public Info ascenderEmpleado(Empleado e) {
        String json = Parsear.getInstance().objectToJson(e);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlEmpleados");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "ascender"));
            parametros.add(new BasicNameValuePair("empleado", mandar));
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
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return info;
    }
}
