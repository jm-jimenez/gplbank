/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ClientesDAO;
import modelo.personas.Cliente;
import utils.Info;

/**
 * Se encarga de instanciar el DAO de clientes y llamar a sus métodos, facilitando los objetos a la Vista.
 * @author JoséMaría
 */
public class ControlClientes {
    
    /**
     * Solicita al DAO que inserte un nuevo cliente.
     * @param c Cliente a añadir
     * @return Info con el resultado de la petición.
     */
    public Info addCliente(Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.addCliente(c);
    }
    
    /**
     * Solicita al DAO que borre un cliente.
     * @param c Cliente a dar de baja.
     * @return Info con el resultado de la petición.
     */
    public Info deleteClienteConDni(Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.deleteClienteConDni(c);
    }
    
    /**
     * Solicita al dao un cliente con el dni especificado.
     * @param dni String
     * @return Info con el resultado de la petición.
     */
    public Info buscarClienteConDni(String dni){
        Cliente c = new Cliente();
        c.setDni(dni);
        ClientesDAO dao = new ClientesDAO();
        return dao.buscarClienteConDni(c);
    }
    
    /**
     * Solicita al dao modificar un cliente.
     * @param c Cliente con los datos modificados.
     * @return Info con el resultado de la petición.
     */
    public Info modificarCliente(Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.modificarCliente(c);
    }
    
}
