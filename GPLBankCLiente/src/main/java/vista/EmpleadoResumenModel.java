/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControlEmpleados;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import modelo.personas.Empleado;
import utils.Info;
import utils.Parsear;

/**
 * Modelo para la tabla que recoge información resumida de los empleados.
 * @author JoséMaría
 */
public class EmpleadoResumenModel extends AbstractTableModel {

    private ArrayList<Empleado> empleados;
    private ControlEmpleados controlEmpleados;
    private String[] columnas;

    public EmpleadoResumenModel(ControlEmpleados controlEmpleados) {
        this.controlEmpleados = controlEmpleados;
        empleados = controlEmpleados.getAllEmpleados();
        columnas = new String[]{"COD_EMPLEADO", "NOMBRE", "APELLIDO1", "APELLIDO2", "DEPARTAMENTO", "NOMBRE_JEFE", "PRODUCTOS_VENDIDOS", "CLIENTES_CAPTADOS"};
    }

    @Override
    public int getRowCount() {
        return empleados.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o = null;
        Empleado e = empleados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                o = e.getCodEmpleado();
                break;
            case 1:
                o = e.getNombre();
                break;
            case 2:
                o = e.getApellido1();
                break;
            case 3:
                o = e.getApellido2();
                break;
            case 4:
                o = e.getDepartamento();
                break;
            case 5:
                o = e.getNombreJefe();
                break;
            case 6:
                o = e.getProductosVendidos();
                break;
            case 7:
                o = e.getClientesCaptados();
                break;
        }
        return o;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    /**
     * Añade un nuevo empleado al modelo. Si la petición al control de empleados ha tenido éxito,
     * reconstruye el objeto Empleado que viene en el mensaje en formato json y lo añade al ArrayList
     * de empleados. Una vez añadido notifica a los listeners de la tabla que los datos han cambiado,
     * para que se vuelva a pintar.
     * En cualquier caso (sea exitosa o no la petición) al final se envía a la vista el objeto Info
     * para que muestre información al usuario en caso de ser necesario.
     * @param e Empleado a añadir.
     * @return Info con el resultado de la petición.
     */
    public Info anadirNuevoEmpleado(Empleado e) {
        Info info = controlEmpleados.addEmpleado(e);
        if (info.isSuccess()) {
            empleados.add(Parsear.getInstance().empleadoFromJson(info.getMsg()));
            fireTableDataChanged();
        }
        return info;
    }

     /**
     * Elimina un nuevo empleado del modelo. Si la petición al control de empleados ha tenido éxito,
     * elimina del ArrayList de empleados el empleado en la fila seleccionada. Una vez eliminado
     * notifica a los listeners de la tabla que los datos han cambiado,  para que se vuelva a pintar.
     * En cualquier caso (sea exitosa o no la petición) al final se envía a la vista el objeto Info
     * para que muestre información al usuario en caso de ser necesario.
     * @param filaSeleccionada int Fila seleccionada en la tabla a la hora de pulsar el botón dar de baja.
     * @return Info con el resultado de la petición.
     */
    public Info deleteEmpleado(int filaSeleccionada) {
        Empleado e = empleados.get(filaSeleccionada);
        Info info = controlEmpleados.deleteEmpleado(e);
        if (info.isSuccess()) {
            empleados.remove(e);
            fireTableDataChanged();
        }
        return info;
    }

    /**
     * Asciende a un empleado del modelo. Si la petición al control de empleados ha tenido éxito,
     * cambia la propiedad jefe a true del empleado en la fila seleccionada. Una vez modificado
     * notifica a los listeners de la tabla que los datos han cambiado,  para que se vuelva a pintar.
     * En cualquier caso (sea exitosa o no la petición) al final se envía a la vista el objeto Info
     * para que muestre información al usuario en caso de ser necesario.
     * @param row int Fila seleccionada en la tabla a la hora de pulsar en el menu ascender.
     * @return Info con el resultado de la petición.
     */
    public Info ascenderEmpleadoAtRow(int row) {
        Info i = new Info(false, "");
        Empleado e = empleados.get(row);
        if (e.isJefe()) {
            i.setMsg("El empleado ya es jefe");
        } else {
            i = controlEmpleados.ascenderEmpleado(e);
            if (i.isSuccess()) {
                e.setJefe(true);
                fireTableDataChanged();
            }
        }
        return i;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class <?> myClass = null;
        if (columnIndex == 6 || columnIndex == 7) {
            myClass = Integer.class;
        } else {
            myClass = super.getColumnClass(columnIndex);
        }
        
        return myClass;
    }

}
