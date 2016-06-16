/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.modelo.productos;

import android.os.Parcel;

import java.util.ArrayList;

/**
 *
 * @author JoséMaría
 */
public class CuentaNomina extends Cuenta {

    private double proximoCargo;
    private ArrayList<String> coTitulares;

    public CuentaNomina() {
        super();
    }

    public CuentaNomina(String titular, int id) {
        super(titular, id);
        proximoCargo = 0;
        coTitulares = new ArrayList<>();
    }

    public CuentaNomina(String titular, double importe, String iban, int id, double proximoCargo, ArrayList<String> coTitulares) {
        super(titular, importe, iban, id);
        this.proximoCargo = proximoCargo;
        this.coTitulares = coTitulares;
    }

    @Override
    public String toString() {
        return super.toString() + ", Saldo: " + getImporte() + ", Próximo cargo: " + proximoCargo + "]";
    }

    public double getProximoCargo() {
        return proximoCargo;
    }

    public void setProximoCargo(double proximoCargo) {
        this.proximoCargo = proximoCargo;
    }

    public ArrayList<String> getCoTitulares() {
        return coTitulares;
    }

    public void setCoTitulares(ArrayList<String> coTitulares) {
        this.coTitulares = coTitulares;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected CuentaNomina(Parcel in) {
        super(in);
        proximoCargo = in.readDouble();
        coTitulares = new ArrayList<>();
        in.readStringList(coTitulares);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeDouble(proximoCargo);
        out.writeStringList(coTitulares);
    }

    public static final Creator<CuentaNomina> CREATOR = new Creator<CuentaNomina>() {
        @Override
        public CuentaNomina createFromParcel(Parcel in) {
            return new CuentaNomina(in);
        }

        @Override
        public CuentaNomina[] newArray(int size) {
            return new CuentaNomina[size];
        }
    };
}
