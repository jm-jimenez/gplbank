/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.modelo.productos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author JoséMaría
 */
public class CuentaAhorro extends Cuenta {

    private double tipoAcreedor;

    public CuentaAhorro() {
        super();
    }

    public CuentaAhorro(String titular, int id) {
        super(titular, id);
    }

    public CuentaAhorro(String titular, int id, double tipoAcreedor) {
        super(titular, id);
        this.tipoAcreedor = tipoAcreedor;
    }

    public CuentaAhorro(String titular, double importe, String iban, int id, double tipoAcreedor) {
        super(titular, importe, iban, id);
        this.tipoAcreedor = tipoAcreedor;
    }

    protected CuentaAhorro(Parcel in) {
        super(in);
        tipoAcreedor = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(tipoAcreedor);
    }

    public static final Creator<CuentaAhorro> CREATOR = new Creator<CuentaAhorro>() {
        @Override
        public CuentaAhorro createFromParcel(Parcel in) {
            return new CuentaAhorro(in);
        }

        @Override
        public CuentaAhorro[] newArray(int size) {
            return new CuentaAhorro[size];
        }
    };

    @Override
    public String toString() {
        return super.toString() + ", Capital: " + getImporte() + ", Remuneración: " + tipoAcreedor + "%]";
    }

    public double getTipoAcreedor() {
        return tipoAcreedor;
    }

    public void setTipoAcreedor(double tipoAcreedor) {
        this.tipoAcreedor = tipoAcreedor;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
