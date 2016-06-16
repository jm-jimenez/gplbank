/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.modelo.productos;

import android.os.Parcel;
import android.os.Parcelable;

import clasescomunes.constantes.TipoProducto;

/**
 *
 * @author JoséMaría
 */
public abstract class Producto implements Parcelable {

    private int id;
    private String titular;
    private double importe;
    

    public Producto() {

    }

    public Producto (Parcel in){
        id = in.readInt();
        titular = in.readString();
        importe = in.readDouble();
    }

    public Producto(String titular, int id) {
        this.id = id;
        this.titular = titular;
    }

    public Producto(int id, String titular, double importe) {
        this.id = id;
        this.titular = titular;
        this.importe = importe;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(titular);
        out.writeDouble(importe);
    }

}
