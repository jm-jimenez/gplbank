package com.example.chema.gplbankandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import clasescomunes.modelo.personas.Cliente;
import clasescomunes.modelo.personas.Empleado;
import clasescomunes.utils.CifrarComunicacion;
import clasescomunes.utils.Info;
import clasescomunes.utils.Parsear;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.extras.Base64;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;
import dao.Configuration;
import dao.SessionContext;

/**
 * Actividad que muestra la ventana de login. Cuando se inicia la aplicación se negocia en segundo plano
 * la SecretKey que se utilizará para el resto de la comuncación.
 */
public class MainActivity extends AppCompatActivity {

    private EditText etDni;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new EstablecerConexionSegura().execute();
        etDni = (EditText) findViewById(R.id.dni);
        etPass = (EditText) findViewById(R.id.pass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Establece el onclick del botón validar. Si los campos están rellenos, lanza una nueva
     * tarea asíncrona para realizar la autentificación.
     * @param w View del botón pulsado.
     */
    public void validar(View w){
        String dni = etDni.getText().toString();
        String pass = etPass.getText().toString();
        if (dni.equals("") || pass.equals("")){
            Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            new Validar().execute(dni, pass);
        }
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es verificar los credenciales
     * del usuario.
     */
    private class Validar extends AsyncTask<String, Void, Info>{
        private ProgressDialog pd;

        @Override
        protected Info doInBackground(String... params) {
            Cliente c = new Cliente();
            c.setDni(params[0]);
            c.setPass(params[1]);
            String json = Parsear.getInstance().objectToJson(c);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            Info info = new Info(false, null);
            try {
                String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl()+"/login");
                List<NameValuePair> parametros = new ArrayList<>();
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
                info.setMsg("No se pudo conectar con el servidor.\n");
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                info.setMsg("No se pudo establecer una conexión segura.\n" +
                        "Por favor, reinicie la aplicación.");
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return info;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Validando...");
            pd.show();
        }

        @Override
        protected void onPostExecute(Info info) {
            pd.dismiss();
            if(info.isSuccess()) {
                Intent i = new Intent(MainActivity.this, MenuPrincipal.class);
                i.putExtra("dni", etDni.getText().toString());
                startActivity(i);
                finish();
            }
            else{
                Toast.makeText(MainActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es solicitar al servidor
     * la clave pública para descifrar el envío de la SecretKey. Una vez recostruída la SecretKey la guarda
     * en sesión.
     */
    private class EstablecerConexionSegura extends AsyncTask<Void, Void, SecretKey>{

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Estableciendo conexión segura...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected SecretKey doInBackground(Void... params) {
            SecretKey originalKey = null;
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet(Configuration.getInstance().getServerUrl()+"/secretKey?opcion=public");
                CloseableHttpResponse response1 = httpclient.execute(httpGet, SessionContext.getInstance().getContext());
                try {
                    HttpEntity entity1 = response1.getEntity();
                    File f = new File("./server1024.publica");
                    if (f.exists()){
                        f.delete();
                    }
                    IOUtils.copy(entity1.getContent(), MainActivity.this.openFileOutput("server1024.publica", MODE_PRIVATE));
                } finally {
                    response1.close();
                }

                httpGet = new HttpGet(Configuration.getInstance().getServerUrl()+"/secretKey?opcion=secret");
                response1 = httpclient.execute(httpGet, SessionContext.getInstance().getContext());
                try {
                    HttpEntity entity1 = response1.getEntity();
                    String respuesta = EntityUtils.toString(entity1);
                    byte[] clave = Base64.decode(respuesta, 0);
                    //descifro
                    byte[] bufferPub = new byte[5000];

                    FileInputStream in = MainActivity.this.openFileInput("server1024.publica");
                    int chars = in.read(bufferPub, 0, 5000);
                    in.close();

                    byte[] bufferPub2 = new byte[chars];
                    System.arraycopy(bufferPub, 0, bufferPub2, 0, chars);

                    Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
                    Cipher cifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

                    KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA", "BC"); // Hace uso del provider BC
                    // 4.2 Recuperar clave publica desde datos codificados en formato X509
                    X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub2);
                    PublicKey clavePublica2 = keyFactoryRSA.generatePublic(clavePublicaSpec);

                    cifrador.init(Cipher.DECRYPT_MODE, clavePublica2); // Descrifra con la clave privada

                    byte[] claveAES = cifrador.doFinal(clave);
                    originalKey = new SecretKeySpec(claveAES, 0, claveAES.length, "AES");


                } finally {
                    response1.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return originalKey;
        }

        @Override
        protected void onPostExecute(SecretKey secretKey) {
            pd.dismiss();
            SessionContext.getInstance().setSecretKey(secretKey);
        }
    }
}
