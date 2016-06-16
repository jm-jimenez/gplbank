/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Realiza la conexión a base MySQL.
 * @author oscar
 */
public class DBConnection {
    /**
     * Devuelve la conexión a la bbdd con los parámetros especificados.
     * @param conn String url a la base de datos.
     * @param user String usuario para la conexión.
     * @param pass String password para la conexión.
     * @return
     * @throws ClassNotFoundException 
     */   
    public Connection getConnectionMysql(String conn,String user, String pass) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(conn,user,pass);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }
    
    /**
     * Termina una conexión a bbdd.
     * @param connection Conexión a terminar.
     */
    public void cerrarConexion( Connection connection )
    {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
