/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.Configuration;
import dao.EmpleadosDAO;
import dao.SessionContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import modelo.personas.Empleado;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import utils.Info;

/**
 * Se encarga de instanciar el DAO de empleados y llamar a sus métodos, facilitando los objetos a la Vista.
 * @author JoséMaría
 */
public class ControlEmpleados {
    
    /**
     * Solicita al server la SecretKey para cifrar/descifrar el resto de la comunicación. Primero, hace una
     * petición http de cuya respuesta abre un InputStream y almacena el stream de bytes en un fichero binario.
     * Este fichero es la clave pública del servidor y se utilizará para descifrar asimétricamente la segunda
     * petición, la cual contiene un objeto SecretKey que será el utilizado para cifrar/descifrar de manera simétrica.
     */
    public void solicitarClave(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(Configuration.getInstance().getServerUrl() + "/secretKey?opcion=public");
            CloseableHttpResponse response1 = httpclient.execute(httpGet, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                File f = new File("./server1024.publica");
                if (f.exists()){
                    f.delete();
                }
                IOUtils.copy(entity1.getContent(), new FileOutputStream(f));
            } finally {
                response1.close();
            }
            
            httpGet = new HttpGet(Configuration.getInstance().getServerUrl() + "/secretKey?opcion=secret");
            response1 = httpclient.execute(httpGet, SessionContext.getInstance().getContext());
            try {
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                byte[] clave = Base64.decodeBase64(respuesta);
                //descifro
                byte[] bufferPub = new byte[5000];
                File f = new File("server1024.publica");
                System.out.println(f.getAbsolutePath());
                FileInputStream in = new FileInputStream(f);
                int chars = in.read(bufferPub, 0, 5000);
                in.close();

                byte[] bufferPub2 = new byte[chars];
                System.arraycopy(bufferPub, 0, bufferPub2, 0, chars);

                Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                Cipher cifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

                KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA", "BC"); // Hace uso del provider BC
                // 4.2 Recuperar clave publica desde datos codificados en formato X509
                X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub2);
                PublicKey clavePublica2 = keyFactoryRSA.generatePublic(clavePublicaSpec);

                cifrador.init(Cipher.DECRYPT_MODE, clavePublica2); // Descrifra con la clave privada

                byte[] claveAES = cifrador.doFinal(clave);
                SecretKey originalKey = new SecretKeySpec(claveAES, 0, claveAES.length, "AES"); 
                SessionContext.getInstance().setSecretKey(originalKey);
                
            } finally {
                response1.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Solicita al DAO que valide un empleado.
     * @param dni String dni del empleado.
     * @param pass String clave introducida por el empleado.
     * @return Info con el resultado de la petición.
     */
    public Info validate(String dni, String pass){
        Empleado e = new Empleado();
        e.setDni(dni);
        e.setPass(pass);
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.validate(e);
    }
    
    /**
     * Solicita al DAO todos los empleados almacenados.
     * @return ArrayList de empleados.
     */
    public ArrayList<Empleado> getAllEmpleados(){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.getAllEmpleados();
    }
    
    /**
     * Solicita al DAO que inserte un nuevo empleado. 
     * @param e Empleado a añadir.
     * @return Info con el resultado de la petición.
     */
    public Info addEmpleado(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.addEmpleado(e);
    }
    
    /**
     * Solicita al DAO que borre un empleado. 
     * @param e Empleado a borrar.
     * @return Info con el resultado de la petición.
     */
    public Info deleteEmpleado(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.deleteEmpleado(e);
    }
    
    /**
     * Solicita al DAO que anote un nuevo cliente captado a un empleado.
     * @param dniEmpleado String dni del empleado que ha captado el cliente.
     */
    public void anotarClienteCaptado(String dniEmpleado){
        Empleado e = new Empleado();
        e.setDni(dniEmpleado);
        EmpleadosDAO dao = new EmpleadosDAO();
        dao.anotarClienteCaptado(e);
    }
    
    /**
     * Solicita al dao que anote un nuevo producto vendido a un empleado.
     * @param dniEmpleado String dni del empleado que ha realizado la venta.
     */
    public void anotarProductoVendido(String dniEmpleado){
        Empleado e = new Empleado();
        e.setDni(dniEmpleado);
        EmpleadosDAO dao = new EmpleadosDAO();
        dao.anotarProductoVendido(e);
    }
    
    /**
     * Solicita al dao que ascienda un empleado a Jefe. 
     * @param e Empleado a ascender.
     * @return Info con el resultado de la petición.
     */
    public Info ascenderEmpleado(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.ascenderEmpleado(e);
    }
}
