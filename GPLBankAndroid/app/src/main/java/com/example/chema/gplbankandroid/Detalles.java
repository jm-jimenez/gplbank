package com.example.chema.gplbankandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import adaptadores.CuentaAhorroAdapter;
import adaptadores.CuentaNominaAdapter;
import adaptadores.MovimientoAdapter;
import adaptadores.TarjetaAdapter;
import clasescomunes.constantes.Flags;
import clasescomunes.modelo.productos.CuentaAhorro;
import clasescomunes.modelo.productos.CuentaNomina;
import clasescomunes.modelo.productos.Movimiento;
import clasescomunes.modelo.productos.Tarjeta;
import clasescomunes.utils.CifrarComunicacion;
import clasescomunes.utils.Info;
import clasescomunes.utils.Parsear;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
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
 * Actividad que mostrará en un listview el listado de productos seleccionados en el menú principal
 */
public class Detalles extends AppCompatActivity {

    private ArrayList<CuentaNomina> cuentasNomina;
    private ArrayList<CuentaAhorro> cuentasAhorro;
    private ArrayList<Tarjeta> tarjetas;
    private ListView detalleProductos;
    private TextView productoInfo;

    private double importeModificarCuentaNomina;
    private Intent i;

    /**
     * Crea la actividad. Al crearla, se recoge el intent y se mira determinada información para saber qué tipo
     * de ArrayList configurar. Dependiendo del Flag "TIPO_DETALLE" se instanciará un ArrayList de producto u otro.
     * En función del tipo que se haya instanciado, se poblará el listview con su correspondiente adaptador.
     * Independientemente del tipo de adaptador que sea, a los items del list view se les establece el onItemClickListener
     * para poder interactuar con los elementos mostrando opciones adicionales.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        i= new Intent();
        i.putExtra(Flags.DATA_CHANGED, false);
        detalleProductos = (ListView) findViewById(R.id.lv_detalle);
        productoInfo = (TextView) findViewById(R.id.detail_info);
        String tipoProducto = getIntent().getStringExtra(Flags.TIPO_DETALLE);
        switch (tipoProducto) {
            case Flags.TIPO_CN:
                cuentasNomina = getIntent().getParcelableArrayListExtra(Flags.PRODUCTOS_CN);
                CuentaNominaAdapter cnAdapter = new CuentaNominaAdapter(Detalles.this, R.layout.cuenta_nomina_detalle_layout, cuentasNomina);
                View header = LayoutInflater.from(Detalles.this).inflate(R.layout.cuenta_nomina_headers, null);
                detalleProductos.addHeaderView(header, null, false);
                detalleProductos.setAdapter(cnAdapter);
                productoInfo.setText("Información sobre sus cuentas nómina:");
                ponerListenerItemCuentaNomina();
                break;
            case Flags.TIPO_CA:
                cuentasAhorro = getIntent().getParcelableArrayListExtra(Flags.PRODUCTOS_CA);
                CuentaAhorroAdapter caAdapter = new CuentaAhorroAdapter(Detalles.this, R.layout.cuenta_ahorro_detalle_layout, cuentasAhorro);
                View headerCA = LayoutInflater.from(Detalles.this).inflate(R.layout.cuenta_ahorro_headers, null);
                detalleProductos.addHeaderView(headerCA, null, false);
                detalleProductos.setAdapter(caAdapter);
                productoInfo.setText("Información sobre sus cuentas ahorro:");
                ponerListenerItemCuentaAhorro();
                break;
            case Flags.TIPO_T:
                tarjetas = getIntent().getParcelableArrayListExtra(Flags.PRODUCTOS_T);
                TarjetaAdapter tAdapter = new TarjetaAdapter(Detalles.this, R.layout.tarjeta_detalle_layout, tarjetas);
                View headerT = LayoutInflater.from(Detalles.this).inflate(R.layout.tarjeta_headers, null);
                detalleProductos.addHeaderView(headerT, null, false);
                detalleProductos.setAdapter(tAdapter);
                productoInfo.setText("Información sobre sus tarjetas:");
                ponerListenerItemTarjetas();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalles, menu);
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

    @Override
    public void onBackPressed() {
        if (i.getBooleanExtra(Flags.DATA_CHANGED, false)) {
            switch (i.getStringExtra(Flags.TIPO_DETALLE)) {
                case Flags.TIPO_CA:
                    i.putExtra(Flags.PRODUCTOS_CA, cuentasAhorro);
                    break;
            }
            i.putExtra(Flags.IMPORTE_MODIFICAR, importeModificarCuentaNomina);
        }

        setResult(RESULT_OK, i);
        finish();
    }

    /**
     * Añade el onItemClickListener al listview cuando está poblado con items de tipo cuenta ahorro.
     */
    private void ponerListenerItemCuentaAhorro() {
        detalleProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CuentaAhorro cuentaSeleccionada = (CuentaAhorro) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                builder.setMessage("¿Qué desea hacer?");
                btnIngresarEnCuentaAhorro(cuentaSeleccionada, builder);
                btnRetirarDeCuentaAhorro(cuentaSeleccionada, builder);
                builder.show();
            }
        });
    }

    /**
     * Establece el botón "Ingresar" del AlertDialog.
     * @param c CuentaAhorro en la que se ha hecho click.
     * @param builder AlertDialog al que ponerle el possitive button.
     */
    private void btnIngresarEnCuentaAhorro(final CuentaAhorro c, AlertDialog.Builder builder) {
        builder.setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final double importeOriginal = c.getImporte();
                View v = LayoutInflater.from(Detalles.this).inflate(R.layout.dialog_with_edit_text, null);
                ((TextView) v.findViewById(R.id.title)).setText("¿Qué cantidad desea ingresar?");
                final EditText cantidad = (EditText) v.findViewById(R.id.cantidad);
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                builder.setView(v);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String importeS = cantidad.getText().toString();
                        if (importeS.equals("")) {
                            Toast.makeText(Detalles.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                        } else {
                            c.setImporte(Double.parseDouble(importeS));
                            new PeticionOperacionCuentaAhorro().execute(c, importeOriginal);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Establece el botón "Retirar" del AlertDialog.
     * @param c CuentaAhorro en la que se ha hecho click.
     * @param builder AlertDialog al que ponerle el negative button.
     */
    private void btnRetirarDeCuentaAhorro(final CuentaAhorro c, AlertDialog.Builder builder) {
        builder.setNegativeButton("Retirar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final double importeOriginal = c.getImporte();
                final View v = LayoutInflater.from(Detalles.this).inflate(R.layout.dialog_with_edit_text, null);
                ((TextView) v.findViewById(R.id.title)).setText("¿Qué cantidad desea retirar?");
                final EditText cantidad = (EditText) v.findViewById(R.id.cantidad);
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                builder.setView(v);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String importeS = cantidad.getText().toString();
                        if (importeS.equals("")) {
                            Toast.makeText(Detalles.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                        } else {
                            c.setImporte(-1 * Double.parseDouble(cantidad.getText().toString()));
                            new PeticionOperacionCuentaAhorro().execute(c, importeOriginal);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Añade el onItemClickListener al listview cuando está poblado con items de tipo cuenta nomina.
     */
    private void ponerListenerItemCuentaNomina(){
        detalleProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CuentaNomina cuentaSeleccionada = (CuentaNomina) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                builder.setMessage("¿Qué desea hacer?");
                btnVerCotitulares(builder);
                btnAnadirCotitular(cuentaSeleccionada, builder);
                btnRealizarTransferencia(cuentaSeleccionada, builder);
                builder.show();
            }
        });
    }

    /**
     * Establece el botón "Ver cotitulares" del AlertDialog.
     * @param builder AlertDialog al que ponerle el possitive button.
     */
    private void btnVerCotitulares(AlertDialog.Builder builder){
        builder.setPositiveButton("Ver cotitulares", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                View v = LayoutInflater.from(Detalles.this).inflate(R.layout.unico_listview_layout, null);
                ArrayList<String> mostrar = Detalles.this.cuentasNomina.get(0).getCoTitulares();
                if (mostrar.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Detalles.this, android.R.layout.simple_list_item_1, mostrar);
                    ((ListView) v.findViewById(R.id.lv_a_rellenar)).setAdapter(adapter);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                    builder.setView(v);
                    builder.show();
                } else {
                    Toast.makeText(Detalles.this, "La cuenta seleccionada no tiene ningún cotitular", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * Establece el botón "Añadir cotitular" del AlertDialog.
     * @param cn CuentaNomina en la que se ha hecho click.
     * @param builder AlertDialog al que ponerle el neutral button.
     */
    private void btnAnadirCotitular(final CuentaNomina cn, AlertDialog.Builder builder){
        builder.setNeutralButton("Añadir cotitular", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final View v = LayoutInflater.from(Detalles.this).inflate(R.layout.dialog_with_edit_text, null);
                ((TextView) v.findViewById(R.id.title)).setText("Introduzca titular: ");
                final EditText nuevoCotitular = (EditText) v.findViewById(R.id.cantidad);
                AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                builder.setView(v);
                builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!nuevoCotitular.getText().toString().equals("")) {
                            cn.getCoTitulares().add(nuevoCotitular.getText().toString());
                            new PeticionAnadirCotitular().execute(cn);
                        } else {
                            Toast.makeText(Detalles.this, "Campo vacío", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();

            }
        });
    }

    /**
     * Establece el botón "Realizar transferencia" del AlertDialog.
     * @param cn CuentaNomina en la que se ha hecho click.
     * @param builder AlertDialog al que ponerle el negative button.
     */
    private void btnRealizarTransferencia(final CuentaNomina cn, AlertDialog.Builder builder){
        builder.setNegativeButton("Realizar transferencia", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final double importeOriginal = cn.getImporte();
                View v = LayoutInflater.from(Detalles.this).inflate(R.layout.transferencia_layout, null);
                EditText cuentaDest = (EditText) v.findViewById(R.id.et_cuenta_destino);
                final EditText importe = (EditText) v.findViewById(R.id.et_importe);
                AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                builder.setView(v);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String importeS = importe.getText().toString();
                        if (importeS.equals("")) {
                            Toast.makeText(Detalles.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                        } else {
                            cn.setImporte(Double.parseDouble(importe.getText().toString()));
                            new PeticionRealizarTransferencia().execute(cn, importeOriginal);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Añade el onItemClickListener al listview cuando está poblado con items de tipo tarjeta.
     */
    private void ponerListenerItemTarjetas(){
        detalleProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarjeta tarjetaSeleccionada = (Tarjeta) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Detalles.this);
                builder.setMessage("¿Qué desea hacer?");
                btnMostrarUltimosMovimientos(tarjetaSeleccionada, builder);
                builder.show();
            }
        });
    }

    /**
     * Establece el botón "Mostrar últimos movimientos" del AlertDialog.
     * @param tarjetaSeleccionada Tarjeta en la que se ha hecho click.
     * @param builder AlertDialog al que ponerle el positive button.
     */
    private void btnMostrarUltimosMovimientos(final Tarjeta tarjetaSeleccionada, AlertDialog.Builder builder){
        builder.setPositiveButton("Mostrar últimos movimientos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new PeticionUltimosMovimientos().execute(tarjetaSeleccionada);
            }
        });
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es una aportación o un retiro de una
     * cuenta ahorro.
     */
    private class PeticionOperacionCuentaAhorro extends AsyncTask<Object, Void, Info> {

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(Detalles.this);
            pd.setMessage("Un momento por favor...");
            pd.show();
        }

        @Override
        protected Info doInBackground(Object... params) {
            Info info = new Info(false, "");
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String json = Parsear.getInstance().objectToJson(params[0]);
            try {
                String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
                List<NameValuePair> parametros = new ArrayList<>();
                parametros.add(new BasicNameValuePair("opcion", "operar"));
                parametros.add(new BasicNameValuePair("producto", "cAhorro"));
                parametros.add(new BasicNameValuePair("detalle", mandar));
                httpPost.setEntity(new UrlEncodedFormEntity(parametros));
                CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
                if (info.isSuccess()){
                    importeModificarCuentaNomina -= ((CuentaAhorro)params[0]).getImporte();
                    ((CuentaAhorro)params[0]).setImporte(((CuentaAhorro)params[0]).getImporte() + (double)params[1]);
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return info;
        }

        @Override
        protected void onPostExecute(Info info) {
            pd.dismiss();
            Toast.makeText(Detalles.this, info.getMsg(), Toast.LENGTH_SHORT).show();
            if (info.isSuccess()){
                CuentaAhorroAdapter caAdapter = new CuentaAhorroAdapter(Detalles.this, R.layout.cuenta_ahorro_detalle_layout, cuentasAhorro);
                detalleProductos.setAdapter(caAdapter);
                i.putExtra(Flags.DATA_CHANGED, true);
                i.putExtra(Flags.TIPO_DETALLE, Flags.TIPO_CA);

            }
        }
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es incorporar un nuevo
     * cotitular.
     */
    private class PeticionAnadirCotitular extends AsyncTask<CuentaNomina, Void, Info>{
        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(Detalles.this);
            pd.setMessage("Un momento por favor...");
        }

        @Override
        protected Info doInBackground(CuentaNomina... params) {
            Info info = new Info(false, "");
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String json = Parsear.getInstance().objectToJson(params[0]);
            try {
                String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
                List<NameValuePair> parametros = new ArrayList<>();
                parametros.add(new BasicNameValuePair("opcion", "cotitular"));
                parametros.add(new BasicNameValuePair("producto", "cNomina"));
                parametros.add(new BasicNameValuePair("detalle", mandar));
                httpPost.setEntity(new UrlEncodedFormEntity(parametros));
                CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return info;
        }

        @Override
        protected void onPostExecute(Info info) {
            pd.dismiss();
            if (!info.isSuccess()){
                cuentasNomina.remove(cuentasNomina.size() - 1);
                if (info.getMsg().equals("")){
                    info.setMsg("Ocurrió un error durante la petición");
                }
            }
            Toast.makeText(Detalles.this, info.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es nueva transferencia.
     */
    private class PeticionRealizarTransferencia extends AsyncTask<Object, Void, Info>{

        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(Detalles.this);
            pd.setMessage("Un momento por favor...");
            pd.show();
        }

        @Override
        protected Info doInBackground(Object... params) {
            Info info = new Info(false, "");
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String json = Parsear.getInstance().objectToJson(params[0]);
            try {
                String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
                List<NameValuePair> parametros = new ArrayList<>();
                parametros.add(new BasicNameValuePair("opcion", "transferir"));
                parametros.add(new BasicNameValuePair("producto", "cNomina"));
                parametros.add(new BasicNameValuePair("detalle", mandar));
                httpPost.setEntity(new UrlEncodedFormEntity(parametros));
                CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
                if (info.isSuccess()){
                    importeModificarCuentaNomina -= ((CuentaNomina)params[0]).getImporte();
                    ((CuentaNomina)params[0]).setImporte((double)params[1] - ((CuentaNomina)params[0]).getImporte());
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return info;
        }

        @Override
        protected void onPostExecute(Info info) {
            pd.dismiss();
            if (info.isSuccess()) {
                CuentaNominaAdapter cnAdapter = new CuentaNominaAdapter(Detalles.this, R.layout.cuenta_nomina_detalle_layout, cuentasNomina);
                detalleProductos.setAdapter(cnAdapter);
                i.putExtra(Flags.DATA_CHANGED, true);
                i.putExtra(Flags.TIPO_DETALLE, Flags.TIPO_CN);
            }
            Toast.makeText(Detalles.this, info.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Realiza una petición http en segundo plano. La tarea que realiza es solicitar todos los movimientos
     * de la tarjeta seleccionada.
     */
    private class PeticionUltimosMovimientos extends AsyncTask<Tarjeta, Void, Info>{
        private ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(Detalles.this);
            pd.setMessage("Un momento por favor...");
            pd.show();
        }

        @Override
        protected Info doInBackground(Tarjeta... params) {
            Info info = new Info(false, "");
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String json = Parsear.getInstance().objectToJson(params[0]);
            try {
                String mandar = CifrarComunicacion.getInstance().cifrar(json, SessionContext.getInstance().getSecretKey());
                HttpPost httpPost = new HttpPost(Configuration.getInstance().getServerUrl() + "/controlProductos");
                List<NameValuePair> parametros = new ArrayList<>();
                parametros.add(new BasicNameValuePair("opcion", "mostrarMovimientos"));
                parametros.add(new BasicNameValuePair("producto", "tarjeta"));
                parametros.add(new BasicNameValuePair("detalle", mandar));
                httpPost.setEntity(new UrlEncodedFormEntity(parametros));
                CloseableHttpResponse response1 = httpclient.execute(httpPost, SessionContext.getInstance().getContext());
                HttpEntity entity1 = response1.getEntity();
                String respuesta = EntityUtils.toString(entity1);
                String descifrado = CifrarComunicacion.getInstance().descifrar(respuesta, SessionContext.getInstance().getSecretKey());
                info = Parsear.getInstance().infoFromJson(descifrado);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return info;
        }

        @Override
        protected void onPostExecute(Info info) {
            pd.dismiss();
            if (info.isSuccess()){
                ArrayList<Movimiento> movimientos = Parsear.getInstance().movimientoListFromJson(info.getMsg());
                if (movimientos.size()>0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Detalles.this);
                    View v = LayoutInflater.from(Detalles.this).inflate(R.layout.unico_listview_layout, null);
                    dialog.setView(v);
                    ListView poblar = (ListView) v.findViewById(R.id.lv_a_rellenar);
                    MovimientoAdapter adapter = new MovimientoAdapter(Detalles.this, R.layout.movimiento_layout, movimientos);
                    View headers = LayoutInflater.from(Detalles.this).inflate(R.layout.movimiento_headers, null);
                    poblar.addHeaderView(headers);
                    poblar.setAdapter(adapter);
                    dialog.show();
                }
                else{
                    Toast.makeText(Detalles.this, "No se ha realizado ningún movimiento con la tarjeta seleccionada", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Detalles.this, info.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}