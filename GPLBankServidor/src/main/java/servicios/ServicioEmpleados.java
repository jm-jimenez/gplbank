/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.EmpleadosDAO;
import java.util.ArrayList;
import modelo.personas.Empleado;
import utils.Info;

/**
 * Se encarga de instanciar el DAO de empleados y llamar a sus métodos, facilitando los objetos al servlet.
 * @author JoséMaría
 */
public class ServicioEmpleados {
    
    /**
     * Solicita al DAO que cree un usuario tipo admin.
     */
    public void addAdmin(){
        EmpleadosDAO dao = new EmpleadosDAO();
        dao.addAdmin();
    }
    
    /**
     * Solicita al DAO que valide un empleado.
     * @param e Empleado que contiene únicamente el dni y la pass.
     * @return Info con el resultado de la petición.
     */
    public Info validate(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.validate(e);
    }

    /**
     * Solicita al DAO todos los empleados almacenados.
     * @return ArrayList de empleados.
     */
    public ArrayList<Empleado> getAllEmpleados() {
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.getAllEmpleados();
    }
    
    /**
     * Solicita al DAO que inserte un nuevo empleado. 
     * @param e Empleado a añadir.
     * @return Info con el resultado de la petición.
     */
    public Info addEmpleado(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.addEmpleado(e);
    }
    
    /**
     * Solicita al DAO que borre un empleado. 
     * @param e Empleado a borrar.
     * @return Info con el resultado de la petición.
     */
    public Info deleteEmpleado(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.deleteEmpleado(e);
    }
    
    /**
     * Solicita al DAO que anote un nuevo cliente captado a un empleado.
     * @param e Empleado que ha captado el cliente.
     */
    public void anotarClienteCaptado(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        dao.anotarClienteCaptado(e);
    }
    
    /**
     * Solicita al dao que anote un nuevo producto vendido a un empleado.
     * @param e Empleado que ha realizado la venta.
     */
    public void anotarProductoVendido(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        dao.anotarProductoVendido(e);
    }
    
    /**
     * Solicita al dao que ascienda un empleado a Jefe. 
     * @param e Empleado a ascender.
     * @return Info con el resultado de la petición.
     */
    public Info ascenderEmpleado(Empleado e){
        EmpleadosDAO dao = new EmpleadosDAO();
        return dao.ascenderEmpleado(e);
    }
}
