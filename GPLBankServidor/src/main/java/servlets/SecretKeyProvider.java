/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Servlet encargado de generar la SecretKey utilizada para el resto de la comuncación.
 * @author JoséMaría
 */
public class SecretKeyProvider extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");
        

        switch (opcion) {
            case "public":
                InputStream is = getServletContext().getResourceAsStream("/WEB-INF/server1024.publica");
                IOUtils.copy(is, response.getOutputStream());
                break;

            case "secret": {
                try {
                    SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
                    request.getSession().setAttribute("clave", secretKey);

                    Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
                    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                    Cipher cifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

                    KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA", "BC"); // Hace uso del provider BC

                    byte[] bufferPriv = new byte[5000];
                    InputStream in = getServletContext().getResourceAsStream("/WEB-INF/server1024.privada");
                    int chars = in.read(bufferPriv, 0, 5000);
                    in.close();

                    byte[] bufferPriv2 = new byte[chars];
                    System.arraycopy(bufferPriv, 0, bufferPriv2, 0, chars);

                    // 2.2 Recuperar clave privada desde datos codificados en formato PKCS8
                    PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferPriv2);
                    PrivateKey clavePrivada2 = keyFactoryRSA.generatePrivate(clavePrivadaSpec);

                    // PASO 3a: Poner cifrador en modo CIFRADO
                    cifrador.init(Cipher.ENCRYPT_MODE, clavePrivada2);  // Cifra con la clave publica

                    byte[] bufferCifrado = cifrador.doFinal(secretKey.getEncoded());

                    String mandar = new String(Base64.encodeBase64(bufferCifrado));
                    response.getWriter().print(mandar);

                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(SecretKeyProvider.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchProviderException ex) {
                    Logger.getLogger(SecretKeyProvider.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(SecretKeyProvider.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                Logger.getLogger(SecretKeyProvider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(SecretKeyProvider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(SecretKeyProvider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(SecretKeyProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
