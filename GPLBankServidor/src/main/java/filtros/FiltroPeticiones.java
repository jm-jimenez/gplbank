/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import modelo.personas.Cliente;
import modelo.personas.Empleado;
import modelo.productos.CuentaAhorro;
import modelo.productos.CuentaNomina;
import modelo.productos.Tarjeta;
import utils.CambiarPass;
import utils.CifrarComunicacion;
import utils.Parsear;

/**
 * Se encarga de reconstruir objetos y meterlos en sesión. Recoge los parámetros de los distintos request
 * y genera el objeto correspondiente, reconstruyéndolo y metiéndolo como atributo. De esta manera, los
 * servlets solo trabajan con los objetos que esperan recibir.
 * @author JoséMaría
 */
public class FiltroPeticiones implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public FiltroPeticiones() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FiltroPeticiones:DoBeforeProcessing");
        }

        HttpServletRequest miRequest = (HttpServletRequest) request;
        try {
            if (request.getParameter("opcion") != null && (request.getParameter("opcion").equals("validar")
                    || request.getParameter("opcion").equals("add")
                    || request.getParameter("opcion").equals("clienteCaptado")
                    || request.getParameter("opcion").equals("productoVendido")
                    || request.getParameter("opcion").equals("del")
                    || request.getParameter("opcion").equals("get")
                    || request.getParameter("opcion").equals("modify")
                    || request.getParameter("opcion").equals("activar")
                    || request.getParameter("opcion").equals("operar")
                    || request.getParameter("opcion").equals("cotitular")
                    || request.getParameter("opcion").equals("transferir")
                    || request.getParameter("opcion").equals("nuevoMovimiento")
                    || request.getParameter("opcion").equals("mostrarMovimientos")
                    || request.getParameter("opcion").equals("changeP")
                    || request.getParameter("opcion").equals("ascender"))) {

                if (request.getParameter("empleado") != null) {
                    String empleadoJson = request.getParameter("empleado");
                    Empleado e;
                    e = Parsear.getInstance().empleadoFromJson(CifrarComunicacion.getInstance().descifrar(empleadoJson, (SecretKey) miRequest.getSession().getAttribute("clave")));
                    request.setAttribute("empleado", e);
                } else if (request.getParameter("cliente") != null) {
                    String clienteJson = request.getParameter("cliente");
                    Cliente c = Parsear.getInstance().clienteFromJson(CifrarComunicacion.getInstance().descifrar(clienteJson, (SecretKey) miRequest.getSession().getAttribute("clave")));
                    request.setAttribute("cliente", c);
                    if (request.getParameter("detalle") != null){
                        String cambiarJson =request.getParameter("detalle");
                        CambiarPass cambiar = Parsear.getInstance().cambiarPassFromJson(CifrarComunicacion.getInstance().descifrar(cambiarJson, (SecretKey) miRequest.getSession().getAttribute("clave")));
                        request.setAttribute("detalle", cambiar);
                    } 
                } else if (request.getParameter("detalle") != null) {
                    if (request.getParameter("producto").equals("cNomina")) {
                        String cNominaJson = request.getParameter("detalle");
                        CuentaNomina c = Parsear.getInstance().cuentaNominaFromJson(CifrarComunicacion.getInstance().descifrar(cNominaJson, (SecretKey) miRequest.getSession().getAttribute("clave")));
                        request.setAttribute("detalle", c);
                    }
                    else if (request.getParameter("producto").equals("tarjeta")){
                        String tarjetaJson = request.getParameter("detalle");
                        Tarjeta t = Parsear.getInstance().tarjetaFromJson(CifrarComunicacion.getInstance().descifrar(tarjetaJson, (SecretKey) miRequest.getSession().getAttribute("clave")));
                        request.setAttribute("detalle", t);
                    }
                    else if (request.getParameter("producto").equals("cAhorro")){
                        String cAhorroJson = request.getParameter("detalle");
                        CuentaAhorro c = Parsear.getInstance().cuentaAhorroFromJson(CifrarComunicacion.getInstance().descifrar(cAhorroJson, (SecretKey) miRequest.getSession().getAttribute("clave")));
                        request.setAttribute("detalle", c);
                    }
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(FiltroPeticiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FiltroPeticiones:DoAfterProcessing");
        }

        Object o = request.getAttribute("salida");
        HttpServletRequest miRequest = (HttpServletRequest) request;
        if (o != null) {
            try {
                String mandar = CifrarComunicacion.getInstance().cifrar(Parsear.getInstance().objectToJson(o), (SecretKey) miRequest.getSession().getAttribute("clave"));
                response.getWriter().print(mandar);
            } catch (Exception ex) {
                Logger.getLogger(FiltroPeticiones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("FiltroPeticiones:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("FiltroPeticiones:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FiltroPeticiones()");
        }
        StringBuffer sb = new StringBuffer("FiltroPeticiones(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
