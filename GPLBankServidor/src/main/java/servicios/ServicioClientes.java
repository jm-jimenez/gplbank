/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.ClientesDAO;
import modelo.personas.Cliente;
import utils.CambiarPass;
import utils.Info;

/**
 * Se encarga de instanciar el DAO de clientes y llamar a sus métodos, facilitando los objetos al servlet.
 * @author JoséMaría
 */
public class ServicioClientes {
    
    /**
     * Solicita al DAO que autentifique un cliente.
     * @param c Cliente a validar.
     * @return Info con el resultado de la petición.
     */
    public Info validate(Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.validate(c);
    }
    
    /**
     * Solicita al DAO que inserte un nuevo cliente.
     * @param c Cliente a añadir
     * @return Info con el resultado de la petición.
     */
    public Info addCliente (Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.addCliente(c);
    }
    
    /**
     * Solicita al DAO que borre un cliente.
     * @param c Cliente a dar de baja.
     * @return Info con el resultado de la petición.
     */
    public Info deleteClienteConDni (Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.deleteClienteConDni(c);
    }
    
    /**
     * Solicita al dao un cliente con el dni especificado.
     * @param c Cliente que solo trae el dni a buscar.
     * @return Info con el resultado de la petición.
     */
    public Info buscarClienteConDni (Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.buscarClienteConDni(c);
    }
    
    /**
     * Solicita al dao modificar un cliente.
     * @param c Cliente con los datos modificados.
     * @return Info con el resultado de la petición.
     */
    public Info modificarCliente (Cliente c){
        ClientesDAO dao = new ClientesDAO();
        return dao.modificarCliente(c);
    }
    
    /**
     * Solicita al dao modicar la contraseña de acceso de un cliente.
     * @param c Cliente a modifcar la contraseña.
     * @param cambiar CambiarPass que contiene la contraseña actual y la nueva contraseña.
     * @return Info con el resultado de la petición.
     */
    public Info changePassword(Cliente c, CambiarPass cambiar){
        ClientesDAO dao = new ClientesDAO();
        return dao.changePassword(c, cambiar);
    }
}
