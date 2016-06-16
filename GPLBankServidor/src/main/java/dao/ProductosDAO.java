/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.personas.Cliente;
import modelo.productos.CuentaAhorro;
import modelo.productos.CuentaNomina;
import modelo.productos.Movimiento;
import modelo.productos.Tarjeta;
import utils.Info;
import utils.Parsear;

/**
 * Realiza en base de datos querys relacionadas con los clientes.
 * @author JoséMaría
 */
public class ProductosDAO {
    
    /**
     * Añade una nueva tarjeta al cliente. Inserta los datos correspondientes en las tablas
     * PRODUCTOS y TARJETAS y devuelve el objeto Info con la Tarjeta creada.
     * @param c Cliente al cual añadir la tarjeta.
     * @return Info con el resultado de la petición.
     */
    public Info addTarjeta(Cliente c) {
        Info info = new Info();
        info.setSuccess(false);
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            //Primero buscar si tiene cuenta nómina
            String sql = "SELECT COUNT(cn.ID) FROM PRODUCTOS p, CUENTAS_NOMINA cn "
                    + "WHERE p.DNI_CLIENTE = ? AND p.ID = cn.ID_PRODUCTO";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                info.setMsg("El cliente no dispone de cuenta nómina");
            } else {
                connection.setAutoCommit(false);

                //Insertar el producto "padre"
                sql = "INSERT INTO PRODUCTOS (TITULAR, IMPORTE, DNI_CLIENTE) VALUES (?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2());
                pstmt.setDouble(2, 0);
                pstmt.setString(3, c.getDni());
                pstmt.executeUpdate();

                //Vincular el producto con el hijo
                sql = "SELECT LAST_INSERT_ID()";
                pstmt = connection.prepareStatement(sql);
                rs = pstmt.executeQuery();
                rs.next();
                int id = rs.getInt(1);
                Tarjeta t = new Tarjeta(c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2(), id);
                sql = "INSERT INTO TARJETAS (ID_PRODUCTO, CADUCIDAD, ACTIVADA, NUMERO_TARJETA) VALUES (?, ?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.setString(2, t.getCaducidad());
                pstmt.setBoolean(3, t.isActivada());
                pstmt.setString(4, t.getNumeroTarjeta());
                pstmt.executeUpdate();
                connection.commit();
                info.setSuccess(true);
                info.setMsg(Parsear.getInstance().objectToJson(t));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
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
     * Añade una nueva cuenta nómina al cliente. Inserta los datos correspondientes en las tablas
     * PRODUCTOS y CUENTAS_NOMINA y devuelve el objeto Info con la CuentaNomina creada.
     * @param c Cliente al cual añadir la cuenta nomina.
     * @return Info con el resultado de la petición.
     */
    public Info addCNomina(Cliente c) {
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

            //Insertar el producto "padre"
            String sql = "INSERT INTO PRODUCTOS (TITULAR, IMPORTE, DNI_CLIENTE) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2());
            pstmt.setDouble(2, 0);
            pstmt.setString(3, c.getDni());
            pstmt.executeUpdate();

            //Vincular el producto con el hijo
            sql = "SELECT LAST_INSERT_ID()";
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            CuentaNomina cNomina = new CuentaNomina(c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2(), id);
            sql = "INSERT INTO CUENTAS_NOMINA (ID_PRODUCTO, IBAN) VALUES (?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, cNomina.getIban());
            pstmt.executeUpdate();
            connection.commit();
            info.setSuccess(true);
            info.setMsg(Parsear.getInstance().objectToJson(cNomina));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
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
     * Añade una nueva cuenta ahorro al cliente. Inserta los datos correspondientes en las tablas
     * PRODUCTOS y CUENTAS_AHORRO y devuelve el objeto Info con la CuentaAhorro creada.
     * @param c Cliente al cual añadir la cuenta ahorro.
     * @param remuneracion double remuneración inicial.
     * @return Info con el resultado de la petición.
     */
    public Info addCAhorro(Cliente c, double remuneracion) {
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

            //Insertar el producto "padre"
            String sql = "INSERT INTO PRODUCTOS (TITULAR, IMPORTE, DNI_CLIENTE) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2());
            pstmt.setDouble(2, 0);
            pstmt.setString(3, c.getDni());
            pstmt.executeUpdate();

            //Vincular el producto con el hijo
            sql = "SELECT LAST_INSERT_ID()";
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            CuentaAhorro cAhorro = new CuentaAhorro(c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2(), id, remuneracion);
            sql = "INSERT INTO CUENTAS_AHORRO (ID_PRODUCTO, IBAN, TIPO_ACREEDOR) VALUES (?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, cAhorro.getIban());
            pstmt.setDouble(3, cAhorro.getTipoAcreedor());
            pstmt.executeUpdate();
            connection.commit();
            info.setSuccess(true);
            info.setMsg(Parsear.getInstance().objectToJson(cAhorro));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
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
     * Devuelve todos las tarjetas del cliente en base de datos. Realiza la query correspondiente en base
     * de datos y recupera todas las tarjetas que el cliente tiene.
     * @param c Cliente del que recuperar las tarjetas.
     * @return Info con el resultado de la petición.
     */
    public Info getAllTarjetasDelCliente(Cliente c) {
        Info i = new Info(false, "");
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "SELECT * FROM PRODUCTOS p, TARJETAS t WHERE DNI_CLIENTE = ? AND p.ID = t.ID_PRODUCTO";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tarjeta t = new Tarjeta(rs.getString(2), rs.getDouble(3), rs.getString(7), rs.getString(8), rs.getBoolean(9), rs.getString(10), rs.getInt(1));
                tarjetas.add(t);
            }
            i.setSuccess(true);
            i.setMsg(Parsear.getInstance().objectToJson(tarjetas));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Devuelve todos las cuentas nómina del cliente en base de datos. Realiza la query correspondiente en base
     * de datos y recupera todas las cuentas nómina que el cliente tiene.
     * @param c Cliente del que recuperar las cuentas nómina.
     * @return Info con el resultado de la petición.
     */
    public Info getAllCNominasDelCliente(Cliente c) {
        Info i = new Info(false, "");
        ArrayList<CuentaNomina> cNominas = new ArrayList<>();
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "SELECT * FROM PRODUCTOS p, CUENTAS_NOMINA c WHERE DNI_CLIENTE = ? AND p.ID = c.ID_PRODUCTO";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ArrayList<String> cotitulares = new ArrayList<>();
                String sql2 = "SELECT COTITULAR FROM COTITULARES WHERE ID_CUENTA_NOMINA = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setInt(1, rs.getInt(5));
                ResultSet rs2 = pstmt2.executeQuery();
                while (rs2.next()) {
                    cotitulares.add(rs2.getString(1));
                }
                CuentaNomina cNomina = new CuentaNomina(rs.getString(2), rs.getDouble(3), rs.getString(7), rs.getInt(1), rs.getDouble(8), cotitulares);
                cNominas.add(cNomina);
            }
            i.setSuccess(true);
            i.setMsg(Parsear.getInstance().objectToJson(cNominas));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Devuelve todos las cuentas ahorro del cliente en base de datos. Realiza la query correspondiente en base
     * de datos y recupera todas las cuentas ahorro que el cliente tiene.
     * @param c Cliente del que recuperar las cuentas ahorro.
     * @return Info con el resultado de la petición.
     */
    public Info getAllCAhorroDelCliente(Cliente c) {
        Info i = new Info(false, "");
        ArrayList<CuentaAhorro> cAhorros = new ArrayList<>();
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "SELECT * FROM PRODUCTOS p, CUENTAS_AHORRO c WHERE DNI_CLIENTE = ? AND p.ID = c.ID_PRODUCTO";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getDni());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CuentaAhorro cAhorro = new CuentaAhorro(rs.getString(2), rs.getDouble(3), rs.getString(7), rs.getInt(1), rs.getDouble(8));
                cAhorros.add(cAhorro);
            }
            i.setSuccess(true);
            i.setMsg(Parsear.getInstance().objectToJson(cAhorros));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Elimina el producto especificado de base de datos.
     * @param pId int del identificador único del producto.
     * @return Info con el resultado de la petición.
     */
    public Info removeProducto(int pId) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "DELETE FROM PRODUCTOS WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, pId);
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                i.setSuccess(true);
                i.setMsg("Producto eliminado.");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Actualiza el importe de una cuenta nómina en base de datos.
     * @param c CuentaNomina a modificar.
     * @return Info con el resultado de la petición.
     */
    public Info modificarImporteCuentaNomina(CuentaNomina c) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "UPDATE PRODUCTOS SET IMPORTE = ? WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, c.getImporte());
            pstmt.setInt(2, c.getId());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                i.setSuccess(true);
                i.setMsg("Operación realizada");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Activa una tarjeta en base de datos.
     * @param t Tarjeta a activar.
     * @return Info con el resultado de la petición.
     */
    public Info activarTarjeta(Tarjeta t) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "UPDATE TARJETAS SET PIN = ?, ACTIVADA = ? WHERE ID_PRODUCTO = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, t.getPIN());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, t.getId());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                i.setSuccess(true);
                i.setMsg("Tarjeta activada");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Desactiva una tarjeta en base de datos.
     * @param t Tarjeta a desactivar.
     * @return Info con el resultado de la petición.
     */
    public Info desactivarTarjeta(Tarjeta t) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "UPDATE TARJETAS SET ACTIVADA = ? WHERE ID_PRODUCTO = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, t.getId());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                i.setSuccess(true);
                i.setMsg("Tarjeta desactivada");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Realiza un una aportación o un retiro de una cuenta ahorro. Si es una aportación,
     * se comprueba el importe de la cuenta nómina para ver si dispone de saldo sufuciente para moverlo
     * de un sitio a otro. En caso afirmativo, se actualiza el importe de la cuenta nómina y de la
     * cuenta ahorro.
     * @param c CuentaAhorro con la que se opera.
     * @return Info con el resultado de la petición.
     */
    public Info operarEnCuentaAhorro(CuentaAhorro c) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            connection.setAutoCommit(false);
            String sql = "SELECT p.* FROM PRODUCTOS p, CUENTAS_NOMINA cn WHERE p.DNI_CLIENTE = (SELECT DNI_CLIENTE FROM PRODUCTOS WHERE ID = ?) AND p.ID = cn.ID_PRODUCTO";
            String sql2 = "SELECT * FROM PRODUCTOS WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            pstmt.setInt(1, c.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double nuevoImporte = rs.getDouble("IMPORTE") - c.getImporte();
                if (nuevoImporte < 0) {
                    i.setMsg("No dispone de suficiente saldo en su cuenta nómina.");
                } else {
                    rs.updateDouble("IMPORTE", nuevoImporte);
                    rs.updateRow();
                    pstmt = connection.prepareStatement(sql2, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                    pstmt.setInt(1, c.getId());
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        rs.updateDouble("IMPORTE", rs.getDouble("IMPORTE") + c.getImporte());
                        rs.updateRow();
                    }

                    i.setSuccess(true);
                    i.setMsg("Operación realizada con éxito.");
                    connection.commit();
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Añade un nuevo cotitular para una cuenta en base de datos. El cotitular ya ha sido añadido en el array
     * de cotitulares en el cliente, así que se añade en base de datos el último cotitular del array.
     * @param c CuentaNomina a la que añadir el cotitular.
     * @return Info con el resultado de la petición.
     */
    public Info anadirCotitularCuenta(CuentaNomina c) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "INSERT INTO COTITULARES (COTITULAR, ID_CUENTA_NOMINA) VALUES (?, (SELECT ID FROM CUENTAS_NOMINA WHERE ID_PRODUCTO = ?))";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getCoTitulares().get(c.getCoTitulares().size() - 1));
            pstmt.setInt(2, c.getId());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                i.setSuccess(true);
                i.setMsg("Cotitular añadido");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Actualiza el saldo de una cuenta nómina al realizar una transferencia.
     * El importe de la cuenta nómina del parámetro de entrada viene con el importe
     * a sustraer una vez realizada la operación.
     * @param c CuentaNomina desde la que se realiza la transferencia.
     * @return Info con el resultado de la petición.
     */
    public Info realizarTransferenciaCuenta(CuentaNomina c) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "SELECT * FROM PRODUCTOS WHERE ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            pstmt.setInt(1, c.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                rs.updateDouble("IMPORTE", rs.getDouble("IMPORTE") - c.getImporte());
                rs.updateRow();
                i.setMsg("Transferencia realizada");
                i.setSuccess(true);
            } else {
                i.setMsg("No se encontraron resultados.");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }

    /**
     * Almacena un nuevo movimiento para la tarjeta seleccionada.
     * @param t Tarjeta a la que vincular el movimiento.
     * @param m Movimiento con la información del concepto y el importe a almacenar.
     * @return Info con el resultado de la petición.
     */
    public Info nuevoMovimiento(Tarjeta t, Movimiento m) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO MOVIMIENTOS (CONCEPTO, IMPORTE, ID_TARJETA) VALUES (?, ?, (SELECT ID FROM TARJETAS WHERE ID_PRODUCTO = ?))";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, m.getConcepto());
            pstmt.setDouble(2, m.getImporte());
            pstmt.setInt(3, t.getId());
            int result = pstmt.executeUpdate();
            if (result > 0) {
                sql = "UPDATE PRODUCTOS SET IMPORTE = IMPORTE+? WHERE ID = ?";
                pstmt = connection.prepareStatement(sql);
                pstmt.setDouble(1, m.getImporte());
                pstmt.setInt(2, t.getId());
                result = pstmt.executeUpdate();
                if (result > 0) {
                    connection.commit();
                    i.setMsg("Pago realizado");
                    i.setSuccess(true);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
            System.out.println(ex.getMessage());
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }
    
    /**
     * Modifica los tipos de las cuentas de ahorro en base de datos.
     * @param importe double Nuevo importe a remunerar.
     * @return Info con el resultado de la petición.
     */
    public Info modificarTiposCA(double importe) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "UPDATE CUENTAS_AHORRO SET TIPO_ACREEDOR = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, importe);
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                i.setSuccess(true);
                i.setMsg("Tipos modificados");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }
    
    /**
     * Recupera de base de datos todos los movimientos asociados a una tarjeta.
     * @param t Tarjeta de la que se solicitan los movimientos.
     * @return Info con el resultado de la petición.
     */
    public Info mostrarMovimientos(Tarjeta t) {
        Info i = new Info(false, "");
        Connection connection = null;
        DBConnection con = new DBConnection();
        try {
            String conn = "jdbc:mysql://dam2chema.ddns.net:1306/GPLBANK";
            String user = "chema";
            String pass = "chemapi";
            connection = con.getConnectionMysql(conn, user, pass);

            String sql = "SELECT m.* FROM PRODUCTOS p, TARJETAS t, MOVIMIENTOS m WHERE p.ID = ? AND t.ID_PRODUCTO = p.ID AND t.ID = m.ID_TARJETA";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, t.getId());
            ArrayList<Movimiento> movimientos = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Movimiento m = new Movimiento(rs.getString(2), rs.getDouble(3), rs.getDate(5));
                movimientos.add(m);
            }
            i.setMsg(Parsear.getInstance().objectToJson(movimientos));
            i.setSuccess(true);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            i.setMsg("Ocurrió un error en el acceso a datos");
        } finally {
            if (con != null) {
                con.cerrarConexion(connection);
            }
        }

        return i;
    }
}
