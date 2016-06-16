/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.personas.Cliente;
import servicios.ServicioClientes;
import utils.CambiarPass;
import utils.Info;

/**
 * Servlet que atiende las peticiones relacionadas con los clientes.
 * @author JoséMaría
 */
public class ControlClientes extends HttpServlet {

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
        
        String opcion = request.getParameter("opcion");
        ServicioClientes servicioClientes = new ServicioClientes();
        
        Cliente c;
        Info info = null;
        
        switch (opcion){
            case "validar":
                c = (Cliente) request.getAttribute("cliente");
                info = servicioClientes.validate(c);
                break;
            
            case "add":
                c = (Cliente) request.getAttribute("cliente");
                info = servicioClientes.addCliente(c);
                break;
                
            case "del":
                c = (Cliente) request.getAttribute("cliente");
                info = servicioClientes.deleteClienteConDni(c);
                break;
                
            case "get":
                c = (Cliente) request.getAttribute("cliente");
                info = servicioClientes.buscarClienteConDni(c);
                break;
            
            case "modify":
                c = (Cliente) request.getAttribute("cliente");
                info = servicioClientes.modificarCliente(c);
                break;
                
            case "changeP":
                c = (Cliente) request.getAttribute("cliente");
                CambiarPass cambiar = (CambiarPass) request.getAttribute("detalle");
                info = servicioClientes.changePassword(c, cambiar);
        }
        
        request.setAttribute("salida", info);
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
