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
import modelo.productos.CuentaAhorro;
import modelo.productos.CuentaNomina;
import modelo.productos.Tarjeta;
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
 * Se encarga de hacer las peticiones http relacionadas con los productos.
 * Todos las peticiones que sean por método post y necesiten enviar objetos,
 * enviarán el mismo parseado a json-string y posteriormente cifrado, haciendo
 * la comunicación segura.
 * @author JoséMaría
 */
public class ProductosDAO {

    /**
     * Realiza una petición http para dar de alta una nueva tarjeta al Cliente c.
     * @param c Cliente al cual añadir la tarjeta.
     * @return Info con el resultado de la petición.
     */
    public Info addTarjeta(Cliente c) {
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "add"));
            parametros.add(new BasicNameValuePair("producto", "tarjeta"));
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return info;
    }
    
    /**
     * Realiza una petición http para dar de alta una nueva cuenta nómina al Cliente c.
     * @param c Cliente al cual añadir la cuenta.
     * @return Info con el resultado de la petición.
     */
    public Info addCNomina(Cliente c) {
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "add"));
            parametros.add(new BasicNameValuePair("producto", "cNomina"));
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return info;
    }
    
    /**
     * Realiza una petición http para dar de alta una nueva cuenta ahorro al Cliente c.
     * @param c Cliente al cual añadir la cuenta.
     * @param remuneracion double indicando la remuneración inicial.
     * @return Info con el resultado de la petición.
     */
    public Info addCAhorro(Cliente c, double remuneracion) {
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "add"));
            parametros.add(new BasicNameValuePair("producto", "cAhorro"));
            parametros.add(new BasicNameValuePair("remuneracion", String.valueOf(remuneracion)));
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return info;
    }

    /**
     * Realiza una petición http para recoger todas las tarjetas del Cliente c.
     * @param c Cliente del que se quieren recoger los productos.
     * @return ArrayList de producto Tarjeta
     */
    public ArrayList<Tarjeta> getAllTarjetasDelCliente(Cliente c) {
        String json = Parsear.getInstance().objectToJson(c);
        ArrayList<Tarjeta> tarjetas = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "get"));
            parametros.add(new BasicNameValuePair("producto", "tarjeta"));
            parametros.add(new BasicNameValuePair("cliente", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
                if (info.isSuccess()) {
                    tarjetas = Parsear.getInstance().tarjetasListFromJson(info.getMsg());
                }
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tarjetas;
    }
    
    /**
     * Realiza una petición http para recoger todas las tarjetas del Cliente c.
     * @param c Cliente del que se quieren recoger los productos.
     * @return ArrayList de producto Tarjeta
     */
    public ArrayList<CuentaNomina> getAllCNominasDelCliente(Cliente c) {
        String json = Parsear.getInstance().objectToJson(c);
        ArrayList<CuentaNomina> cNominas = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "get"));
            parametros.add(new BasicNameValuePair("producto", "cNomina"));
            parametros.add(new BasicNameValuePair("cliente", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
                if (info.isSuccess()) {
                    cNominas = Parsear.getInstance().cNominasListFromJson(info.getMsg());
                }
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cNominas;
    }
    
    /**
     * Realiza una petición http para recoger todas las cuentas ahorro del Cliente c.
     * @param c Cliente del que se quieren recoger los productos.
     * @return ArrayList de producto CuentaAhorro
     */
    public ArrayList<CuentaAhorro> getAllCAhorroDelCliente(Cliente c) {
        String json = Parsear.getInstance().objectToJson(c);
        ArrayList<CuentaAhorro> cAhorros = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "get"));
            parametros.add(new BasicNameValuePair("producto", "cAhorro"));
            parametros.add(new BasicNameValuePair("cliente", mandar));
            httpPost.setEntity(new UrlEncodedFormEntity(parametros));
            CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
                if (info.isSuccess()) {
                    cAhorros = Parsear.getInstance().cAhorroListFromJson(info.getMsg());
                }
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cAhorros;
    }
    
    /**
     * Realiza una petición http para dar de baja un producto.
     * @param pId int Identificador único del producto.
     * @return Info con el resultado de la petición.
     */
    public Info removeProducto(int pId) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;      
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(String.valueOf(pId), SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "del"));
            parametros.add(new BasicNameValuePair("producto", mandar));;
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }
    
    /**
     * Realiza una petición http para modificar el importe de una cuenta nómina.
     * @param c CuentaNómina con el importe a modificar.
     * @return Info con el resultado de la petición.
     */
    public Info modificarImporteCuentaNomina(CuentaNomina c) {
        String json = Parsear.getInstance().objectToJson(c);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "modify"));
            parametros.add(new BasicNameValuePair("producto", "cNomina"));
            parametros.add(new BasicNameValuePair("detalle", mandar));
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }
    
    /**
     * Realiza una petición http para activar una tarjeta.
     * @param t Tarjeta a activar
     * @return Info con el resultado de la petición.
     */
    public Info activarTarjeta (Tarjeta t){
        String json = Parsear.getInstance().objectToJson(t);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "activar"));
            parametros.add(new BasicNameValuePair("activar", "true"));
            parametros.add(new BasicNameValuePair("producto", "tarjeta"));
            parametros.add(new BasicNameValuePair("detalle", mandar));
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }
    
    /**
     * Realiza una petición http para desactivar una tarjeta.
     * @param t Tarjeta a desactivar
     * @return Info con el resultado de la petición.
     */
    public Info desactivarTarjeta (Tarjeta t){
        String json = Parsear.getInstance().objectToJson(t);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "activar"));
            parametros.add(new BasicNameValuePair("activar", "false"));
            parametros.add(new BasicNameValuePair("producto", "tarjeta"));
            parametros.add(new BasicNameValuePair("detalle", mandar));
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }
    
    /**
     * Realiza una petición http para modificar los tipos de las cuentas ahorro.
     * @param importe double Nuevo importe a remunerar.
     * @return Info con el resultado de la petición.
     */
    public Info modificarTiposCA(double importe) {
        String json = String.valueOf(importe);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Info info = null;
        try {
            String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
            HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
            List<NameValuePair> parametros = new ArrayList<>();
            parametros.add(new BasicNameValuePair("opcion", "modify"));
            parametros.add(new BasicNameValuePair("producto", "cAhorro"));
            parametros.add(new BasicNameValuePair("importe", mandar));
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
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return info;
    }
}
