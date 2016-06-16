/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.productos;

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

}
