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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.personas.Empleado;
import utils.Info;
import utils.Parsear;
import utils.PasswordHash;

/**
 * Realiza en base de datos querys relacionadas con empleados.
 * @author JoséMaría
 */
public class EmpleadosDAO {

    /**
     * Inserta un nuevo empleado con permisos jefe. El usuario creado va a tener
     * credenciales admin admin.
     */
    public void addAdmin() {

        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "INSERT INTO EMPLEADOS (DNI, PASS, JEFE) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "admin");
            pstmt.setString(2, PasswordHash.getInstance().createHash("admin"));
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
    }

    /**
     * Autentifica a un empleado. Realiza una consulta en base de datos comprobando si
     * el password introducido por el empleado coincide con el almacenado.
     * @param e Empleado a autenticar.
     * @return Info con el resultado de la petición.
     */
    public Info validate(Empleado e) {
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "SELECT * FROM EMPLEADOS WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, e.getDni());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Empleado emp = new Empleado("", 0, "", "", rs.getInt("CLIENTES_CAPTADOS"), rs.getInt("PRODUCTOS_VENDIDOS"), rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO1"), rs.getString("APELLIDO2"), rs.getString("DNI"), rs.getString("PASS"), null, rs.getBoolean("JEFE"));
            if (PasswordHash.getInstance().validatePassword(e.getPass(), emp.getPass())){
                info.setSuccess(true);
                info.setMsg(Parsear.getInstance().objectToJson(emp));
            }
            else{
                info.setMsg("Usuario o contraseña incorrectos");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("El empleado no existe");
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
     * Devuelve todos los empleados en base de datos. Realiza la query correspondiente en base
     * de datos y recupera todo los empleados.
     * @return ArrayList de empleados.
     */
    public ArrayList<Empleado> getAllEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "SELECT * FROM EMPLEADOS";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Empleado e = new Empleado(rs.getString("COD_EMPLEADO"), 0, rs.getString("DEPARTAMENTO"), rs.getString("NOMBRE_JEFE"), rs.getInt("CLIENTES_CAPTADOS"), rs.getInt("PRODUCTOS_VENDIDOS"), rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("APELLIDO1"), rs.getString("APELLIDO2"), rs.getString("DNI"), rs.getString("PASS"), null, rs.getBoolean("JEFE"));
                empleados.add(e);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("ERROR");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return empleados;
    }
    
    /**
     * Inserta un nuevo empleado en la base de datos. Realiza una query de inserción en base de datos
     * teniendo en cuenta los parámetros del Empleado e.
     * @param e Empleado a insertar.
     * @return Info con el resultado de la petición.
     */
    public Info addEmpleado(Empleado e){
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
            e.setPass(PasswordHash.getInstance().createHash("provisional"));
            String sql = "SELECT LAST_INSERT_ID()";
            String sql2 = "INSERT INTO EMPLEADOS (DNI, PASS, NOMBRE, APELLIDO1, APELLIDO2, DEPARTAMENTO, JEFE, NOMBRE_JEFE)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String sql3 = "UPDATE EMPLEADOS SET COD_EMPLEADO = ? WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql2);
            pstmt.setString(1, e.getDni());
            pstmt.setString(2, e.getPass());
            pstmt.setString(3, e.getNombre());
            pstmt.setString(4, e.getApellido1());
            pstmt.setString(5, e.getApellido2());
            pstmt.setString(6, e.getDepartamento());
            pstmt.setBoolean(7, e.isJefe());
            pstmt.setString(8, e.getNombreJefe());
            pstmt.executeUpdate();
            
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            e.setId(id);
            e.setCodEmpleado("e-" + id);
            
            pstmt = connection.prepareStatement(sql3);
            pstmt.setString(1, e.getCodEmpleado());
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            
            connection.commit();
            info.setSuccess(true);
            info.setMsg(Parsear.getInstance().objectToJson(e));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ocurrió un error en el acceso a datos.");
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
     * Elimina un empleado de la base de datos. Realiza la query necesaria para eliminar el empleado
     * especificado.
     * @param e Empleado a eliminar
     * @return Info con el resultado de la petición.
     */
    public Info deleteEmpleado(Empleado e){
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "DELETE FROM EMPLEADOS WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, e.getDni());
            int cambios = pstmt.executeUpdate();
            if (cambios >0){
                info.setSuccess(true);
                info.setMsg("Empleado dado de baja.");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ocurrió un error en el acceso a datos.");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return info;
    }
    
    /**
     * Actualiza el valor de los clientes captados del empleado. Reliza la query de modificación necesaria.
     * @param e Empleado a modificar.
     */
    public void anotarClienteCaptado(Empleado e){
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "SELECT ID, CLIENTES_CAPTADOS FROM EMPLEADOS WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pstmt.setString(1, e.getDni());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            rs.updateInt(2, rs.getInt(2) + 1);
            rs.updateRow();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
    }
    
    /**
     * Actualiza el valor de los productos vendidos por el empleado. Reliza la query de modificación necesaria.
     * @param e Empleado a modificar.
     */
    public void anotarProductoVendido(Empleado e){
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "SELECT ID, PRODUCTOS_VENDIDOS FROM EMPLEADOS WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pstmt.setString(1, e.getDni());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            rs.updateInt(2, rs.getInt(2) + 1);
            rs.updateRow();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }
    }
    
    /**
     * Asciende un empleado a Jefe. Realiza la query para cambiar el atributo en base de datos.
     * @param e Empleado a ascender.
     * @return Info con el resultado de la petición.
     */
    public Info ascenderEmpleado(Empleado e){
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            String sql = "UPDATE EMPLEADOS SET JEFE = true WHERE DNI = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, e.getDni());
            int cambios = pstmt.executeUpdate();
            if (cambios >0){
                info.setSuccess(true);
                info.setMsg("Empleado ascendido.");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            info.setMsg("Ocurrió un error en el acceso a datos.");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return info;
    }
}
