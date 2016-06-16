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
import modelo.personas.Empleado;
import servicios.ServicioClientes;
import servicios.ServicioEmpleados;
import utils.CifrarComunicacion;
import utils.Info;
import utils.Parsear;

/**
 * Servlet al que dirigir las peticiones de login.
 * @author JoséMaría
 */
public class Login extends HttpServlet {

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
        
        ServicioEmpleados servicioEmpleados = new ServicioEmpleados();
        ServicioClientes servicioClientes = new ServicioClientes();
        try {
            if (request.getParameter("empleado") != null) {
                String descifrado = CifrarComunicacion.getInstance().descifrar(request.getParameter("empleado"), (SecretKey) request.getSession().getAttribute("clave"));
                Empleado e = Parsear.getInstance().empleadoFromJson(descifrado);
                Info i = servicioEmpleados.validate(e);
                if (i.isSuccess()){
                    request.getSession().setAttribute("login", true);
                }
                response.getWriter().print(CifrarComunicacion.getInstance().cifrar(
                        Parsear.getInstance().objectToJson(i),
                        (SecretKey) request.getSession().getAttribute("clave")));
            }
            
            else if (request.getParameter("cliente") != null){
                String descifrado = CifrarComunicacion.getInstance().descifrar(request.getParameter("cliente"), (SecretKey) request.getSession().getAttribute("clave"));
                Cliente c = Parsear.getInstance().clienteFromJson(descifrado);
                Info i = servicioClientes.validate(c);
                if (i.isSuccess()){
                    request.getSession().setAttribute("login", true);
                }
                response.getWriter().print(CifrarComunicacion.getInstance().cifrar(
                        Parsear.getInstance().objectToJson(i),
                        (SecretKey) request.getSession().getAttribute("clave")));
            }
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
