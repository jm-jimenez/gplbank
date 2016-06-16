/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.productos;

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
}
