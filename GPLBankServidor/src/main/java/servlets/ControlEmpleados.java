/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.personas.Empleado;
import servicios.ServicioEmpleados;
import utils.Info;

/**
 * Servlet que atiende las peticiones relacionadas con empleados.
 * @author JoséMaría
 */
public class ControlEmpleados extends HttpServlet {

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
        ServicioEmpleados servicioEmpleados = new ServicioEmpleados();
        
        Empleado e;
        Info i;

        switch (opcion) {

            case "crearAdmin":
                servicioEmpleados.addAdmin();
                break;

            case "validar":
                e = (Empleado) request.getAttribute("empleado");
                i = servicioEmpleados.validate(e);
                request.setAttribute("salida", i);
                break;
                
            case "get":
                ArrayList<Empleado> empleados = servicioEmpleados.getAllEmpleados();
                request.setAttribute("salida", empleados);
                break;
                
            case "add":
                e = (Empleado) request.getAttribute("empleado");
                i = servicioEmpleados.addEmpleado(e);
                request.setAttribute("salida", i);
                break;
                
            case "del":
                e = (Empleado) request.getAttribute("empleado");
                i = servicioEmpleados.deleteEmpleado(e);
                request.setAttribute("salida", i);
                break;
                
            case "clienteCaptado":
                e = (Empleado) request.getAttribute("empleado");
                servicioEmpleados.anotarClienteCaptado(e);
                break;
                
            case "productoVendido":
                e = (Empleado) request.getAttribute("empleado");
                servicioEmpleados.anotarProductoVendido(e);
                break;
                
            case "ascender":
                e = (Empleado) request.getAttribute("empleado");
                i = servicioEmpleados.ascenderEmpleado(e);
                request.setAttribute("salida", i);
                break;
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
