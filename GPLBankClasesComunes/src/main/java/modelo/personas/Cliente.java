/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.personas;

import java.util.ArrayList;
import java.util.TreeSet;
import modelo.productos.Producto;

/**
 *
 * @author JoséMaría
 */
public class Cliente extends Persona {

    private ArrayList<Producto> productos;

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String apellido1, String apellido2, String dni, String pass, TreeSet<String> telefonos) {
        super(nombre, apellido1, apellido2, dni, pass, telefonos);
        productos = new ArrayList<Producto>();
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    
    
}
