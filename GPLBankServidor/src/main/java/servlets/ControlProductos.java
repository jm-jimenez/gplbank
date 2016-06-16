/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.personas.Cliente;
import modelo.productos.CuentaAhorro;
import modelo.productos.CuentaNomina;
import modelo.productos.Movimiento;
import modelo.productos.Tarjeta;
import servicios.ServicioProductos;
import utils.CifrarComunicacion;
import utils.Info;
import utils.Parsear;

/**
 * Servlet que atiende las peticiones relacionadas con productos.
 * @author JoséMaría
 */
public class ControlProductos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String tipoProducto = request.getParameter("producto");
            String opcion = request.getParameter("opcion");
            Cliente c = (Cliente) request.getAttribute("cliente");
            Info i = null;
            ServicioProductos servicioProductos = new ServicioProductos();
            
            switch (tipoProducto) {
                case "tarjeta":
                    Tarjeta t;
                    switch (opcion) {
                        case "add":
                            i = servicioProductos.addTarjeta(c);
                            break;
                        case "get":
                            i = servicioProductos.getAllTarjetasDelCliente(c);
                            break;
                        case "activar":
                            t = (Tarjeta) request.getAttribute("detalle");
                            if (request.getParameter("activar").equals("true")) {
                                i = servicioProductos.activarTarjeta(t);
                            } else if (request.getParameter("activar").equals("false")) {
                                i = servicioProductos.desactivarTarjeta(t);
                            }
                            break;
                        case "nuevoMovimiento":
                            t = (Tarjeta) request.getAttribute("detalle");
                            Movimiento m = Parsear.getInstance().movimientoFromJson(CifrarComunicacion.getInstance().descifrar(request.getParameter("movimiento"), (SecretKey) request.getSession().getAttribute("clave")));
                            i = servicioProductos.nuevoMovimiento(t, m);
                            break;
                        case "mostrarMovimientos":
                            t = (Tarjeta) request.getAttribute("detalle");
                            i = servicioProductos.mostrarMovimientos(t);
                            break;
                    }
                    break;
                    
                case "cNomina":
                    switch (opcion) {
                        case "add":
                            i = servicioProductos.addCNomina(c);
                            break;
                        case "get":
                            i = servicioProductos.getAllCNominasDelCliente(c);
                            break;
                        case "modify":
                            CuentaNomina cn = (CuentaNomina) request.getAttribute("detalle");
                            i = servicioProductos.modificarImporteCuentaNomina(cn);
                            break;
                        case "cotitular":
                            CuentaNomina cNomina = (CuentaNomina) request.getAttribute("detalle");
                            i = servicioProductos.anadirCotitularCuenta(cNomina);
                            break;
                        case "transferir":
                            CuentaNomina cN = (CuentaNomina) request.getAttribute("detalle");
                            i = servicioProductos.realizarTransferenciaCuenta(cN);
                    }
                    break;
                    
                case "cAhorro":
                    switch (opcion) {
                        case "add":
                            double remuneracion = Double.parseDouble(request.getParameter("remuneracion"));
                            i = servicioProductos.addCAhorro(c, remuneracion);
                            break;
                        case "get":
                            i = servicioProductos.getAllCAhorroDelCliente(c);
                            break;
                        case "operar":
                            CuentaAhorro cAhorro = (CuentaAhorro) request.getAttribute("detalle");
                            i = servicioProductos.operarEnCuentaAhorro(cAhorro);
                            break;
                        case "modify":
                            double importe = Double.parseDouble(CifrarComunicacion.getInstance().descifrar(request.getParameter("importe"), (SecretKey) request.getSession().getAttribute("clave")));
                            i = servicioProductos.modificarTiposCA(importe);
                            break;
                    }
                    break;
                default:
                    int pId;
                    try {
                        pId = Integer.parseInt(CifrarComunicacion.getInstance().descifrar(tipoProducto, (SecretKey) request.getSession().getAttribute("clave")));
                        i = servicioProductos.removeProducto(pId);
                    } catch (Exception ex) {
                        Logger.getLogger(ControlProductos.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            request.setAttribute("salida", i);
        } catch (Exception ex) {
            Logger.getLogger(ControlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
