package modelo.productos;

import java.util.Date;

public class Movimiento {

    private String concepto;
    private double importe;
    private Date dia;

    public Movimiento(){

    }

    public Movimiento(String concepto, double importe, Date dia) {
        this.concepto = concepto;
        this.importe = importe;
        this.dia = dia;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }
}
