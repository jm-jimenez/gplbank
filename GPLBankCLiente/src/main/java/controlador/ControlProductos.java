/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ProductosDAO;
import java.util.ArrayList;
import modelo.personas.Cliente;
import modelo.productos.CuentaAhorro;
import modelo.productos.CuentaNomina;
import modelo.productos.Tarjeta;
import utils.Info;

/**
 *  Se encarga de instanciar el DAO de productos y llamar a sus métodos, facilitando los objetos a la Vista.
 * @author JoséMaría
 */
public class ControlProductos {
    
    /**
     * Solicita al dao que añada una nueva tarjeta al Cliente c.
     * @param c Cliente al cual añadir el producto.
     * @return Info con el resultado de la petición
     */
    public Info addTarjeta(Cliente c){
        ProductosDAO dao = new ProductosDAO();
        return dao.addTarjeta(c);
    }
    
    /**
     * Solicita al dao que añada una nueva cuenta nómina al Cliente c.
     * @param c Cliente al cual añadir el producto.
     * @return Info con el resultado de la petición.
     */
    public Info addCNomina(Cliente c){
        ProductosDAO dao = new ProductosDAO();
        return dao.addCNomina(c);
    }
    
    /**
     * Solicita al dao que añada una nueva cuenta nómina al Cliente c.
     * @param c Cliente al cual añadir el producto.
     * @param remuneracion double con la remuneración con la que se abre la cuenta ahorro.
     * @return Info con el resultado de la petición.
     */
    public Info addCAhorro(Cliente c, double remuneracion){
        ProductosDAO dao = new ProductosDAO();
        return dao.addCAhorro(c, remuneracion);
    }
    
    /**
     * Solicita al dao todas las tarjetas del Cliente c.
     * @param c Cliente del que se solicitan los productos.
     * @return ArrayList de productos Tarjeta
     */
    public ArrayList<Tarjeta> getAllTarjetasDelCliente(Cliente c){
        ProductosDAO dao = new ProductosDAO();
        return dao.getAllTarjetasDelCliente(c);
    }
    
    /**
     * Solicita al dao todas las cuentas nóminas del Cliente c.
     * @param c Cliente del que se solicitan los productos.
     * @return ArrayList de productos CuentaNomina
     */
    public ArrayList<CuentaNomina> getAllCNominasDelCliente(Cliente c){
        ProductosDAO dao = new ProductosDAO();
        return dao.getAllCNominasDelCliente(c);
    }
    
    /**
     * Solicita al dao todas las cuentas ahorro del Cliente c.
     * @param c Cliente del que se solicitan los productos.
     * @return ArrayList de productos CuentaAhorro
     */
    public ArrayList<CuentaAhorro> getAllCAhorroDelCliente(Cliente c){
        ProductosDAO dao = new ProductosDAO();
        return dao.getAllCAhorroDelCliente(c);
    }
    
    /**
     * Solicita al dao que elimine un producto.
     * @param pId int identificador único del producto a eliminar.
     * @return Info con el resultado de la petición.
     */
    public Info removeProducto(int pId){
        ProductosDAO dao = new ProductosDAO();
        return dao.removeProducto(pId);
    }
    
    /**
     * Solicita al dao que actualice el importe de la Cuenta Nómina c
     * @param c CuentaNomina a modificar.
     * @return Info con el resultado de la petición.
     */
    public Info modificarImporteCuentaNomina (CuentaNomina c){
        ProductosDAO dao = new ProductosDAO ();
        return dao.modificarImporteCuentaNomina (c);
    }
    
    /**
     * Solicita al dao que active la Tarjeta t
     * @param t Tarjeta a activar
     * @return Info con el resultado de la petición.
     */
    public Info activarTarjeta(Tarjeta t){
        ProductosDAO dao = new ProductosDAO();
        return dao.activarTarjeta(t);
    }
    
    /**
     * Solicita al dao que active la Tarjeta t
     * @param t Tarjeta a desactivar
     * @return Info con el resultado de la petición.
     */
    public Info desactivarTarjeta(Tarjeta t){
        ProductosDAO dao = new ProductosDAO();
        return dao.desactivarTarjeta(t);
    }
    
    /**
     * Solicita al dao que actualice los tipos de las cuentas de ahorro.
     * @param importe double con el nuevo tipo de interés a aplicar.
     * @return Info con el resultado de la petición.
     */
    public Info modificarTiposCA (double importe){
        ProductosDAO dao = new ProductosDAO();
        return dao.modificarTiposCA (importe);
    }
}
