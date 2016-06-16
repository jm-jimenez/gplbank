/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.personas.Cliente;
import utils.CambiarPass;
import utils.Info;
import utils.Parsear;
import utils.PasswordHash;

/**
 * Realiza en base de datos querys relacionadas con los clientes.
 * @author JoséMaría
 */
public class ClientesDAO {
    
    /**
     * Autentifica a un cliente. Realiza una consulta en base de datos comprobando si
     * el password introducido por el cliente coincide con el almacenado.
     * @param c Cliene a autenticar.
     * @return Info con el resultado de la petición.
     */
    public Info validate(Cliente c) {
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "SELECT * FROM CLIENTES WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String storedPass = rs.getString("PASS");
            if (PasswordHash.getInstance().validatePassword(c.getPass(), storedPass)){
                info.setSuccess(true);
                info.setMsg("Autentificado");
            }
            else{
                info.setMsg("Usuario o contraseña incorrectos");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Verifique los campos");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return info;
    }
    
    /**
     * Añade un cliente en base de datos. Realiza la query correspondiente para insertar en
     * base de datos los datos del objeto Cliente c.
     * @param c Cliente a insertar.
     * @return Info con el resultado de la petición.
     */
    public Info addCliente(Cliente c){
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO CLIENTES VALUES (?, ?, ?, ?, ?)";
            String sql2 = "INSERT INTO TELEFONOS VALUES(?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            pstmt.setString(2, PasswordHash.getInstance().createHash("provisional"));
            pstmt.setString(3, c.getNombre());
            pstmt.setString(4, c.getApellido1());
            pstmt.setString(5, c.getApellido2());
            if(pstmt.executeUpdate()>0){
                info.setSuccess(true);
                info.setMsg("Cliente añadido");
            }
            Iterator<String> it = c.getTelefonos().iterator();
            while(it.hasNext()){
                pstmt = connection.prepareStatement(sql2);
                pstmt.setString(1, it.next());
                pstmt.setString(2, c.getDni());
                if (pstmt.executeUpdate()<1){
                    info.setMsg("Algo va mal");
                }
            }
            connection.commit();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ya existe ese cliente");
            info.setSuccess(false);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
        
        return info;
    }
    
    /**
     * Elimina un cliente de la base de datos. Realiza la query necesaria para eliminar el cliente
     * especificado.
     * @param c Cliente a eliminar
     * @return Info con el resultado de la petición.
     */
    public Info deleteClienteConDni(Cliente c){
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "DELETE FROM CLIENTES WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            if(pstmt.executeUpdate()>0){
                info.setSuccess(true);
                info.setMsg("Cliente dado de baja");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ya existe ese cliente");
            info.setSuccess(false);
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
        
        return info;
    }
    
    /**
     * Reconstruye un Cliente a partir de los datos en base de datos. El parámetro c que le viene
     * como entrada, solo trae especificado el dni, que es lo que se utilizará para realizar la query.
     * @param c Cliente con el dni a buscar.
     * @return Info con el resultado de la petición.
     */
    public Info buscarClienteConDni(Cliente c){
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "SELECT * FROM CLIENTES WHERE DNI = ?";
            String sql2 = "SELECT NUM_TELF FROM TELEFONOS WHERE DNI = ?"; 
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            ResultSet rs = pstmt.executeQuery();
            Cliente c2 = null;
            while (rs.next()){
                c2 = new Cliente(rs.getString("NOMBRE"), rs.getString("APELLIDO1"), rs.getString("APELLIDO2"), rs.getString("DNI"), rs.getString("PASS"), null);
                info.setSuccess(true);
            }
            if (info.isSuccess()){
                TreeSet <String> telefonos = new TreeSet<>();
                pstmt = connection.prepareStatement(sql2);
                pstmt.setString(1, c.getDni());
                rs = pstmt.executeQuery();
                while (rs.next()){
                    telefonos.add(rs.getString(1));
                }
                c2.setTelefonos(telefonos);
                info.setMsg(Parsear.getInstance().objectToJson(c2));
            }
            else{
                info.setMsg("No existe ningún cliente con dni " + c.getDni());
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ocurrió un error en el acceso a datos");
            info.setSuccess(false);
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
        
        return info;
    }
    
    /**
     * Actualiza los datos de un cliente en base de datos. Realiza la query necesaria
     * para realizar el update allí donde coincida el DNI del cliente.
     * @param c Cliente con los datos modificados.
     * @return 
     */
    public Info modificarCliente (Cliente c){
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            connection.setAutoCommit(false);
            String sql = "UPDATE CLIENTES SET NOMBRE = ?, APELLIDO1 = ?, APELLIDO2 = ? WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(4, c.getDni());
            pstmt.setString(1, c.getNombre());
            pstmt.setString(2, c.getApellido1());
            pstmt.setString(3, c.getApellido2());
            if(pstmt.executeUpdate()>0){
                sql = "UPDATE PRODUCTOS SET TITULAR = ? WHERE DNI_CLIENTE = ?";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2());
                pstmt.setString(2, c.getDni());
                if (pstmt.executeUpdate()>0){
                    info.setSuccess(true);
                    info.setMsg("Cliente modificado");
                }
            }
            connection.commit();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ya existe ese cliente");
            info.setSuccess(false);
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
        
        return info;
    }
    
    /**
     * Modifica la contraseña de acceso del cliente. Comprueba que la contraseña actual coincida.
     * En caso afirmativo, modifica la contraseña almacenada por la nueva especifcada, aplicandole
     * el algoritmo de hasheo.
     * @param c Cliente que solicita el cambio de contraseña.
     * @param cambiar CambiarPass con los parámetros contraseña actual y nueva contraseña.
     * @return Info con el resultado de la petición.
     */
    public Info changePassword (Cliente c, CambiarPass cambiar){
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "SELECT * FROM CLIENTES WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            pstmt.setString(1, c.getDni());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if (PasswordHash.getInstance().validatePassword(cambiar.getCurrentP(), rs.getString(2))){
                rs.updateString(2, PasswordHash.getInstance().createHash(cambiar.getNewP()));
                rs.updateRow();
                info.setSuccess(true);
                info.setMsg("Contraseña actualizada");
            }
            else {
                info.setMsg("Contraseña incorrecta.");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ocurrió un error en el acceso a datos");
            info.setSuccess(false);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
        
        return info;
    }
}
