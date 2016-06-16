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
public abstract class Cuenta extends Producto {

    private String iban;

    public Cuenta() {

    }

    public Cuenta(String titular, int id) {
        super(titular, id);
        StringBuffer aux = new StringBuffer("ES");
        String longitud = "" + id;
        int auxInt = 10 - longitud.length();
        for (int i = 0; i < auxInt; i++) {
            int otroAux = (int) (48 + Math.random() * (58 - 48));
            char siguiente = (char) otroAux;
            aux.append(siguiente);
        }
        aux.append(id);
        iban = aux.toString();
    }

    /*public Cuenta(String titular, double importe, String iban) {
        super(titular, importe);
        this.iban = iban;
    }*/
    public Cuenta(String titular, double importe, String iban, int id) {
        super(id, titular, importe);
        this.iban = iban;
    }

    @Override
    public String toString() {
        return super.toString() + " [IBAN " + iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
