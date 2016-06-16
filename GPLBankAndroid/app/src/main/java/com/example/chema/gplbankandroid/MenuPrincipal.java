package com.example.chema.gplbankandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import adaptadores.NumeroTarjetaAdapter;
import clasescomunes.constantes.Flags;
import clasescomunes.modelo.personas.Cliente;
import clasescomunes.modelo.productos.CuentaAhorro;
import clasescomunes.modelo.productos.CuentaNomina;
import clasescomunes.modelo.productos.Movimiento;
import clasescomunes.modelo.productos.Producto;
import clasescomunes.modelo.productos.Tarjeta;
import clasescomunes.utils.CambiarPass;
import clasescomunes.utils.CifrarComunicacion;
import clasescomunes.utils.Info;
import clasescomunes.utils.Parsear;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;
import dao.Configuration;
import dao.SessionContext;

/**
 * Pantalla principal de la aplicación. Muestra la información con la posición global del cliente y le
 * permite realizar distintas acciones.
 */
public class MenuPrincipal extends AppCompatActivity {

    private String dni;
    private ArrayList<Producto> productos;
    private ArrayList<Tarjeta> tarjetas;
    private ArrayList<CuentaNomina> cuentasNomina;
    private ArrayList<CuentaAhorro> cuentasAhorro;

    private TextView saldoTarjetas;
    private TextView saldoCuentasNomina;
    private TextView saldoCuentasAhorro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        dni = getIntent().getStringExtra("dni");
        tarjetas = new ArrayList<>(0);
        cuentasNomina = new ArrayList<>(0);
        cuentasAhorro = new ArrayList<>(0);
        solicitarPosicionGlobal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
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
     * Recupera toda la información del cliente para mostrarla por pantalla.
     */
    private void solicitarPosicionGlobal() {
        new Gestion().execute(dni);
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es solicitar todos los productos
     * del cliente, para reconstruir el ArrayList de productos y poder dibujar su posición global.
     */
    private class Gestion extends AsyncTask<String, Void, ArrayList<Producto>> {

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MenuPrincipal.this);
            pd.setMessage("Cargando información...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected ArrayList<Producto> doInBackground(String... params) {
            ArrayList<Producto> productos = new ArrayList<>();
            Cliente c = new Cliente();
            c.setDni(params[0]);
            Info info = new Info();
            String json = Parsear.getInstance().objectToJson(c);
            ArrayList<Tarjeta> tarjetas = null;
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl()+"/controlProductos");
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
                        productos.addAll(tarjetas);

                        ArrayList<CuentaNomina> cNominas = null;

                        httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
                        parametros = new ArrayList<>();
                        parametros.add(new BasicNameValuePair("opcion", "get"));
                        parametros.add(new BasicNameValuePair("producto", "cNomina"));
                        parametros.add(new BasicNameValuePair("cliente", mandar));
                        httpPost.setEntity(new UrlEncodedFormEntity(parametros));
                        response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
                        try {
                            entity1 = response1.getEntity();
                            respuesta = EntityUtils.toString(entity1);
                            descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                            info = Parsear.getInstance().infoFromJson(descifrado);
                            if (info.isSuccess()) {
                                cNominas = Parsear.getInstance().cNominasListFromJson(info.getMsg());
                                productos.addAll(cNominas);

                                ArrayList<CuentaAhorro> cAhorros = null;
                                httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
                                parametros = new ArrayList<>();
                                parametros.add(new BasicNameValuePair("opcion", "get"));
                                parametros.add(new BasicNameValuePair("producto", "cAhorro"));
                                parametros.add(new BasicNameValuePair("cliente", mandar));
                                httpPost.setEntity(new UrlEncodedFormEntity(parametros));
                                response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
                                try {
                                    entity1 = response1.getEntity();
                                    respuesta = EntityUtils.toString(entity1);
                                    descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                                    info = Parsear.getInstance().infoFromJson(descifrado);
                                    if (info.isSuccess()) {
                                        cAhorros = Parsear.getInstance().cAhorroListFromJson(info.getMsg());
                                        productos.addAll(cAhorros);
                                    }
                                } finally {
                                    response1.close();
                                }
                            }
                        } finally {
                            response1.close();
                        }
                    }
                } finally {
                    response1.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return productos;
        }

        @Override
        protected void onPostExecute(ArrayList<Producto> productos) {
            MenuPrincipal.this.productos = productos;
            calcularSaldoCuentasNomina();
            calcularSaldoCuentasAhorro();
            calcularSaldoTarjetas();
            pd.dismiss();
        }
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es realizar un nuevo pago,
     * aumentando el dispuesto de la tarjeta ese mes, y aumentando el próximo cargo de la cuenta nómina.
     */
    private class RealizarPago extends AsyncTask<Object, Void, Object[]>{
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MenuPrincipal.this);
            pd.setMessage("Un momento por favor...");
            pd.show();
        }

        @Override
        protected Object[] doInBackground(Object... params) {
            String tarjetaJson = Parsear.getInstance().objectToJson(params[0]);
            String movimientoJson = Parsear.getInstance().objectToJson(params[1]);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            Info info = null;
            try {
                String mandarTarjeta = CifrarComunicacion.getInstance().cifrar(tarjetaJson, SessionContext.getInstance().getSecretKey());
                String mandarMovimiento = CifrarComunicacion.getInstance().cifrar(movimientoJson, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
                List<NameValuePair> parametros = new ArrayList<>();
                parametros.add(new BasicNameValuePair("opcion", "nuevoMovimiento"));
                parametros.add(new BasicNameValuePair("producto", "tarjeta"));
                parametros.add(new BasicNameValuePair("detalle", mandarTarjeta));
                parametros.add(new BasicNameValuePair("movimiento", mandarMovimiento));
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
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return new Object[]{info, params[0], params[1], params[2]};
        }

        @Override
        protected void onPostExecute(Object[] result) {
            pd.dismiss();
            Info i = (Info) result[0];
            if (i.isSuccess()){
                Tarjeta t = (Tarjeta)result[1];
                double importe = ((Movimiento)result[2]).getImporte();
                t.setImporte(t.getImporte() + importe);
                cuentasNomina.get(0).setProximoCargo(cuentasNomina.get(0).getProximoCargo() + importe);
                saldoTarjetas.setText(String.valueOf(Double.parseDouble(saldoTarjetas.getText().subSequence(0, saldoTarjetas.getText().length()-2).toString()) + importe) + " €");
            }

            Toast.makeText(MenuPrincipal.this, i.getMsg(), Toast.LENGTH_SHORT).show();
            ((AlertDialog) result[3]).dismiss();
        }
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es modificar la contraseña de acceso
     * de la aplicación.
     */
    private class ChangePassword extends AsyncTask<Object, Void, Info>{

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MenuPrincipal.this);
            pd.setMessage("Un momento por favor...");
            pd.show();
        }

        @Override
        protected Info doInBackground(Object... params) {
            String jsonCambiar = Parsear.getInstance().objectToJson(params[0]);
            String jsonCliente = Parsear.getInstance().objectToJson(params[1]);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            Info info = null;
            try {
                String mandarCambiar = CifrarComunicacion.getInstance().cifrar(jsonCambiar, SessionContext.getInstance().getSecretKey());
                String mandarCliente = CifrarComunicacion.getInstance().cifrar(jsonCliente, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlClientes");
                List<NameValuePair> parametros = new ArrayList<>();
                parametros.add(new BasicNameValuePair("opcion", "changeP"));
                parametros.add(new BasicNameValuePair("cliente", mandarCliente));
                parametros.add(new BasicNameValuePair("detalle", mandarCambiar));
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
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    httpclient.close();
                } catch (IOException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return info;
        }

        @Override
        protected void onPostExecute(Info info) {
            pd.dismiss();
            Toast.makeText(MenuPrincipal.this, info.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Calcula el saldo global de todas las cuentas nóminas para pintarlo en la pantalla principal. A la vez,
     * cada instancia que se encuentra lo va guardando en el array list de cuentas nominas.
     */
    private void calcularSaldoCuentasNomina(){
        double saldo = 0;
        for (Producto p : productos){
            if (p instanceof CuentaNomina){
                saldo += ((CuentaNomina)p).getImporte();
                cuentasNomina.add((CuentaNomina) p);
            }
        }
        saldoCuentasNomina = (TextView)findViewById(R.id.saldo_cuentas_nomina);
        saldoCuentasNomina.setText(String.valueOf(saldo) + " €");
    }

    /**
     * Calcula el saldo global de todas las cuentas ahorro para pintarlo en la pantalla principal. A la vez,
     * cada instancia que se encuentra lo va guardando en el array list de cuentas ahorro.
     */
    private void calcularSaldoCuentasAhorro(){
        double saldo = 0;
        for (Producto p : productos){
            if (p instanceof CuentaAhorro){
                saldo += ((CuentaAhorro)p).getImporte();
                cuentasAhorro.add((CuentaAhorro)p);
            }
        }
        saldoCuentasAhorro = (TextView)findViewById(R.id.saldo_cuentas_ahorro);
        saldoCuentasAhorro.setText(String.valueOf(saldo) + " €");
    }

    /**
     * Calcula el saldo global de todas las tarjetas para pintarlo en la pantalla principal. A la vez,
     * cada instancia que se encuentra lo va guardando en el array list de tarjetas.
     */
    private void calcularSaldoTarjetas(){
        double saldo = 0;
        for (Producto p : productos){
            if (p instanceof Tarjeta){
                saldo += ((Tarjeta)p).getImporte();
                tarjetas.add((Tarjeta)p);
            }
        }
        saldoTarjetas = (TextView)findViewById(R.id.saldo_tarjetas);
        saldoTarjetas.setText(String.valueOf(saldo) + " €");
    }

    /**
     * Muestra un AlertDialog con varias opciones al hacer click en el botón.
     * @param v View que dispara el onclick.
     */
    public void mostrarDialogoProductos(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        String [] items = {"Cuentas nómina", "Cuentas ahorro", "Tarjetas"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MenuPrincipal.this, Detalles.class);
                switch (which) {
                    case 0:
                        i.putExtra(Flags.TIPO_DETALLE, Flags.TIPO_CN);
                        i.putExtra(Flags.PRODUCTOS_CN, cuentasNomina);
                        break;
                    case 1:
                        i.putExtra(Flags.TIPO_DETALLE, Flags.TIPO_CA);
                        i.putExtra(Flags.PRODUCTOS_CA, cuentasAhorro);
                        break;

                    case 2:
                        i.putExtra(Flags.TIPO_DETALLE, Flags.TIPO_T);
                        i.putExtra(Flags.PRODUCTOS_T, tarjetas);
                        break;
                }

                startActivityForResult(i, Flags.DETALLES_ACTIVITY);

            }
        });

        builder.setTitle("Seleccione productos a mostrar");
        builder.show();
    }

    /**
     * Muestra un AlertDialog para realizar un nuevo pago.
     * @param v View que dispara el onclick.
     */
    public void realizarPago(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        View dialogView = LayoutInflater.from(MenuPrincipal.this).inflate(R.layout.nuevo_pago_layout, null);
        final EditText concepto = (EditText) dialogView.findViewById(R.id.et_concepto);
        final EditText importe = (EditText) dialogView.findViewById(R.id.et_importe);
        builder.setView(dialogView);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                String conceptoS = concepto.getText().toString();
                String importeS = importe.getText().toString();
                if (conceptoS.equals("") || importeS.equals("")){
                    Toast.makeText(MenuPrincipal.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                }
                else {

                    final Movimiento m = new Movimiento(conceptoS, Double.parseDouble(importeS), null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
                    View tarjetasView = LayoutInflater.from(MenuPrincipal.this).inflate(R.layout.unico_listview_layout, null);
                    ListView lv = (ListView) tarjetasView.findViewById(R.id.lv_a_rellenar);
                    NumeroTarjetaAdapter adapter = new NumeroTarjetaAdapter(MenuPrincipal.this, R.layout.numero_tarjetas_layout, tarjetas);
                    lv.setAdapter(adapter);
                    builder.setView(tarjetasView);
                    builder.setTitle("Elija una tarjeta: ");
                    final AlertDialog dlg = builder.show();
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final Tarjeta t = (Tarjeta) parent.getItemAtPosition(position);
                            if (t.isActivada()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
                                View vInflada = LayoutInflater.from(MenuPrincipal.this).inflate(R.layout.dialog_with_edit_text, null);
                                ((TextView) vInflada.findViewById(R.id.title)).setText("Introduzca PIN");
                                final EditText pin = (EditText) vInflada.findViewById(R.id.cantidad);
                                builder.setView(vInflada);
                                builder.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (pin.getText().toString().equals(t.getPIN())) {
                                            new RealizarPago().execute(t, m, dlg);
                                        } else {
                                            Toast.makeText(MenuPrincipal.this, "Pin Incorrecto. La tarjeta ha sido bloqueada", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                builder.show();
                            } else {
                                Toast.makeText(MenuPrincipal.this, "La tarjeta seleccionada no está activada.\n" +
                                        "Diríjase a su sucursal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        builder.show();
    }

    /**
     * Muestra un AlertDialog con los campos necesarios para modificar la contraseña.
     * @param v View que dispara el onclick
     */
    public void changePassword(View v){
        final Cliente cliente = new Cliente();
        cliente.setDni(dni);
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        View vInflada = LayoutInflater.from(MenuPrincipal.this).inflate(R.layout.change_password_layout, null);
        builder.setView(vInflada);
        final EditText current = (EditText) vInflada.findViewById(R.id.et_current_password);
        final EditText newPwd = (EditText) vInflada.findViewById(R.id.new_password);
        final EditText confirm = (EditText) vInflada.findViewById(R.id.confirm_password);
        builder.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentS = current.getText().toString();
                String newPwdS = newPwd.getText().toString();
                String confirmS = confirm.getText().toString();
                if (currentS.equals("") || newPwdS.equals("") || confirmS.equals("")){
                    Toast.makeText(MenuPrincipal.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (newPwdS.equals(confirmS)) {
                        CambiarPass cambiar = new CambiarPass(currentS, newPwdS);
                        new ChangePassword().execute(cambiar, cliente);
                    } else {
                        Toast.makeText(MenuPrincipal.this, "La nueva contraseña no coincide.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atención");
        builder.setMessage("¿Seguro que desea salir?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuPrincipal.this.finish();
            }
        });
        builder.setNegativeButton("NO", null);
        builder.show();
    }

    /**
     * Cuando la actividad Detalles finaliza, se recoge el activityResult para
     * ver si hay que modificar el resto de productos. Por ejemplo, si ha realizado una transferencia,
     * el saldo de la cuenta nómina se tendrá que actualizar para mostrarlo en el menú principal.
     * @param requestCode int Identificador de la actividad que ha terminado.
     * @param resultCode int Identificador de cómo ha ido el resultado.
     * @param data Intent con los datos.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Flags.DETALLES_ACTIVITY && resultCode == RESULT_OK){
            double importeModificar;
            if (data.getBooleanExtra(Flags.DATA_CHANGED, false)){
                switch (data.getStringExtra(Flags.TIPO_DETALLE)){
                    case Flags.TIPO_CA:
                        cuentasAhorro = data.getParcelableArrayListExtra(Flags.PRODUCTOS_CA);
                        importeModificar = data.getDoubleExtra(Flags.IMPORTE_MODIFICAR, 0);
                        saldoCuentasAhorro.setText(String.valueOf(Double.parseDouble(saldoCuentasAhorro.getText().subSequence(0, saldoCuentasAhorro.getText().length()-2).toString()) - importeModificar) + " €");
                        saldoCuentasNomina.setText(String.valueOf(Double.parseDouble(saldoCuentasNomina.getText().subSequence(0, saldoCuentasNomina.getText().length()-2).toString()) + importeModificar) + " €");
                        break;

                    case Flags.TIPO_CN:
                        importeModificar = data.getDoubleExtra(Flags.IMPORTE_MODIFICAR, 0);
                        saldoCuentasNomina.setText(String.valueOf(Double.parseDouble(saldoCuentasNomina.getText().subSequence(0, saldoCuentasNomina.getText().length()-2).toString()) + importeModificar) + " €");
                        break;
                }
            }
        }
    }
}
