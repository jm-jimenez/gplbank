/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.personas.Cliente;
import modelo.personas.Empleado;
import modelo.productos.CuentaAhorro;
import modelo.productos.CuentaNomina;
import modelo.productos.Movimiento;
import modelo.productos.Producto;
import modelo.productos.Tarjeta;

/**
 * Serializa objetos a json y reconstruye objetos de distintos tipos desde Strings
 * @author JoséMaría
 */
public class Parsear {

    private static Parsear instance;

    private Parsear() {

    }

    public static Parsear getInstance() {
        if (instance == null) {
            instance = new Parsear();
        }
        return instance;
    }

    public String objectToJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    public Empleado empleadoFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Empleado e = null;
        try {
            e = mapper.readValue(json, Empleado.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public ArrayList<Empleado> empleadosListFromJson(String json) {
        ArrayList<Empleado> empleados = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            empleados = mapper.readValue(json, new TypeReference <ArrayList<Empleado>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }
    
    public Cliente clienteFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Cliente c = null;
        try {
            c = mapper.readValue(json, Cliente.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public Info infoFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Info i = null;
        try {
            i = mapper.readValue(json, Info.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public ArrayList<Tarjeta> tarjetasListFromJson(String json) {
        ArrayList<Tarjeta> tarjetas = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            tarjetas = mapper.readValue(json, new TypeReference <ArrayList<Tarjeta>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tarjetas;
    }
    
    public Tarjeta tarjetaFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Tarjeta t = null;
        try {
            t = mapper.readValue(json, Tarjeta.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }
    
    public ArrayList<CuentaNomina> cNominasListFromJson(String json) {
        ArrayList<CuentaNomina> cNominas = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            cNominas = mapper.readValue(json, new TypeReference <ArrayList<CuentaNomina>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cNominas;
    }
    
    public ArrayList<CuentaAhorro> cAhorroListFromJson(String json) {
        ArrayList<CuentaAhorro> cAhorros = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            cAhorros = mapper.readValue(json, new TypeReference <ArrayList<CuentaAhorro>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cAhorros;
    }
    
    public CuentaNomina cuentaNominaFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CuentaNomina c = null;
        try {
            c = mapper.readValue(json, CuentaNomina.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public CuentaAhorro cuentaAhorroFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CuentaAhorro c = null;
        try {
            c = mapper.readValue(json, CuentaAhorro.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public Movimiento movimientoFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Movimiento m = null;
        try {
            m = mapper.readValue(json, Movimiento.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    
    public ArrayList<Movimiento> movimientoListFromJson(String json){
        ArrayList<Movimiento> movimientos = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            movimientos = mapper.readValue(json, new TypeReference <ArrayList<Movimiento>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movimientos;
    }
    
    public CambiarPass cambiarPassFromJson(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CambiarPass cambiar = null;
        try {
            cambiar = mapper.readValue(json, CambiarPass.class);
        } catch (IOException ex) {
            Logger.getLogger(Parsear.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cambiar;
    }
}
