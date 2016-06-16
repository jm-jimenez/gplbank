/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.modelo.productos;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author JoséMaría
 */
public class Tarjeta extends Producto{

    private String numeroTarjeta;
    private String caducidad;
    private boolean activada;
    private String PIN;

    public Tarjeta() {

    }

    public Tarjeta(String titular, int id) {
        super(titular, id);
        String longitud = "" + id;
        StringBuffer aux = new StringBuffer();
        int auxInt = 16 - longitud.length();
        for (int i = 0; i < auxInt; i++) {
            int otroAux = (int) (48 + Math.random() * (58 - 48));
            char siguiente = (char) otroAux;
            aux.append(siguiente);
        }
        aux.append(id);
        numeroTarjeta = aux.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 5);

        caducidad = sdf.format(c.getTime());
        activada = false;
    }

    public Tarjeta(Tarjeta tarjeta) {
        super(tarjeta.getId(), tarjeta.getTitular(), tarjeta.getImporte());
        numeroTarjeta = tarjeta.getNumeroTarjeta();
        caducidad = tarjeta.getCaducidad();
        activada = false;
    }

    public Tarjeta(String titular, double importe, String numeroTarjeta, String caducidad, boolean activada,
            String PIN, int id) {
        super(id, titular, importe);
        this.numeroTarjeta = numeroTarjeta;
        this.caducidad = caducidad;
        this.activada = activada;
        this.PIN = PIN;
    }

    protected Tarjeta(Parcel in) {
        super(in);
        numeroTarjeta = in.readString();
        caducidad = in.readString();
        PIN = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(numeroTarjeta);
        dest.writeString(caducidad);
        dest.writeString(PIN);
    }

    public static final Creator<Tarjeta> CREATOR = new Creator<Tarjeta>() {
        @Override
        public Tarjeta createFromParcel(Parcel in) {
            return new Tarjeta(in);
        }

        @Override
        public Tarjeta[] newArray(int size) {
            return new Tarjeta[size];
        }
    };

    @Override
    public String toString() {
        return super.toString() + " [Numero Tarjeta: " + numeroTarjeta + ", Caducidad: " + caducidad + ", Proximo cargo: " + getImporte() + ", Activada: " + activada + "]";
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public boolean isActivada() {
        return activada;
    }

    public void setActivada(boolean activada) {
        this.activada = activada;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
