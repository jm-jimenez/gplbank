/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.personas;

import java.util.TreeSet;

/**
 *
 * @author JoséMaría
 */
public abstract class Persona {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dni;
    private String pass;
    private TreeSet<String> telefonos;

    public Persona() {

    }

    public Persona(String nombre, String apellido1, String apellido2, String dni) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        telefonos = new TreeSet<String>();
    }

    public Persona(String nombre, String apellido1, String apellido2, String dni, String pass, TreeSet<String> telefonos) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.pass = pass;
        this.telefonos = telefonos;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1) + "[nombre=" + nombre + ", apellido1=" + apellido1
                + ", apellido2=" + apellido2 + ", dni=" + dni +", pass="+ pass +", telefonos="
                + telefonos.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    
    public TreeSet<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(TreeSet<String> telefonos) {
        this.telefonos = telefonos;
    }

}
