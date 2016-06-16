/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.modelo.personas;

import java.util.TreeSet;

/**
 *
 * @author JoséMaría
 */
public class Empleado extends Persona {

    private String codEmpleado;
    private int extension;
    private String departamento;
    private String nombreJefe;
    private int clientesCaptados;
    private int productosVendidos;
    private int id;
    private boolean jefe;

    public Empleado() {
        super();
    }

    public Empleado(String nombre, String apellido1, String apellido2, String dni,
            int extension, String departamento, boolean jefe, String nombreJefe) {
        super(nombre, apellido1, apellido2, dni);
        this.extension = extension;
        this.departamento = departamento;
        this.jefe = jefe;
        this.nombreJefe = nombreJefe;
        codEmpleado = "e-" + id;
    }

    public Empleado(String nombre, String apellido1, String apellido2, String dni, String pass,
            TreeSet<String> telefonos, int extension, String departamento, String nombreJefe) {
        super(nombre, apellido1, apellido2, dni, pass, telefonos);
        this.extension = extension;
        this.departamento = departamento;
        this.nombreJefe = nombreJefe;
        codEmpleado = "e-" + id;
    }

    public Empleado(String codEmpleado, int extension, String departamento, String nombreJefe, int clientesCaptados,
            int productosVendidos, int id, String nombre, String apellido1, String apellido2, String dni, String pass, TreeSet<String> telefonos, boolean jefe) {
        super(nombre, apellido1, apellido2, dni, pass, telefonos);
        this.extension = extension;
        this.departamento = departamento;
        this.nombreJefe = nombreJefe;
        this.productosVendidos = productosVendidos;
        this.clientesCaptados = clientesCaptados;
        this.codEmpleado = codEmpleado;
        this.id = id;
        this.jefe = jefe;
    }

    @Override
    public String toString() {
        return super.toString() + ", codEmpleado=" + codEmpleado + ", extension=" + extension + ", departamento="
                + departamento + ", nombreJefe=" + nombreJefe +", jefe="+ jefe + "]";
    }

    public String getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNombreJefe() {
        return nombreJefe;
    }

    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }

    public int getClientesCaptados() {
        return clientesCaptados;
    }

    public void setClientesCaptados(int clientesCaptados) {
        this.clientesCaptados = clientesCaptados;
    }

    public int getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(int productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isJefe() {
        return jefe;
    }

    public void setJefe(boolean jefe) {
        this.jefe = jefe;
    }
    
    
    
}
