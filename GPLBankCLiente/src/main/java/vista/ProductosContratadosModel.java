/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControlProductos;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import modelo.personas.Cliente;
import modelo.productos.CuentaAhorro;
import modelo.productos.CuentaNomina;
import modelo.productos.Producto;
import modelo.productos.Tarjeta;
import utils.Info;
import utils.Parsear;

/**
 * Modelo para la tabla que recoge información resumida de los productos contratados por un cliente.
 * @author JoséMaría
 */
public class ProductosContratadosModel extends AbstractTableModel {

    private Cliente clienteActual;
    private ControlProductos controlProductos;
    private ArrayList <Producto> productos;
    private String [] columnas;
    
    /**
     * Crea un nuevo modelo. Necesita el control de productos y el clienteActual, ambos facilitados
     * por la vista principal.
     * @param controlProductos ControlProductos para llamar a los métodos dao.
     * @param clienteActual Cliente sobre el cual añadir, modificar, eliminar productos.
     */
    public ProductosContratadosModel (ControlProductos controlProductos, Cliente clienteActual){
        this.controlProductos = controlProductos;
        this.clienteActual = clienteActual;
        productos = new ArrayList<>();
        ArrayList<Tarjeta> tarjetas = controlProductos.getAllTarjetasDelCliente(clienteActual);
        ArrayList<CuentaNomina> cNominas = controlProductos.getAllCNominasDelCliente(clienteActual);
        ArrayList<CuentaAhorro> cAhorro = controlProductos.getAllCAhorroDelCliente(clienteActual);
        productos.addAll(tarjetas);
        productos.addAll(cNominas);
        productos.addAll (cAhorro);
        columnas = new String[]{"TITULAR", "IMPORTE", "TIPO"};
    }
    
    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto p = productos.get(rowIndex);
        Object o = null;
        switch (columnIndex){
            case 0:
                o = p.getTitular();
                break;
            case 1:
                o = p.getImporte();
                break;
            case 2:
                if (p instanceof Tarjeta){
                    o = "TARJETA";
                }
                else if (p instanceof CuentaNomina){
                    o = "CUENTA NOMINA";
                }
                else if (p instanceof CuentaAhorro){
                    o = "CUENTA AHORRO";
                }
                break;
        }
        return o;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
    
    /**
     * Añade una nueva tarjeta al modelo. Si la petición al control de productos ha tenido éxito,
     * reconstruye el objeto Tarjeta que viene en el mensaje en formato json y lo añade al ArrayList
     * de productos. Una vez añadido notifica a los listeners de la tabla que los datos han cambiado,
     * para que se vuelva a pintar.
     * En cualquier caso (sea exitosa o no la petición) al final se envía a la vista el objeto Info
     * para que muestre información al usuario en caso de ser necesario.
     * @return Info con el resultado de la petición.
     */
    public Info addTarjeta(){
        Info i = controlProductos.addTarjeta(clienteActual);
        if (i.isSuccess()){
            productos.add(Parsear.getInstance().tarjetaFromJson(i.getMsg()));
            i.setMsg("Tarjeta añadida con éxito");
            fireTableDataChanged();
        }
        return i;
    }
    
    /**
     * Añade una nueva cuenta nómina al modelo. Si la petición al control de productos ha tenido éxito,
     * reconstruye el objeto CuentaNomina que viene en el mensaje en formato json y lo añade al ArrayList
     * de productos. Una vez añadido notifica a los listeners de la tabla que los datos han cambiado,
     * para que se vuelva a pintar.
     * En cualquier caso (sea exitosa o no la petición) al final se envía a la vista el objeto Info
     * para que muestre información al usuario en caso de ser necesario.
     * @return Info con el resultado de la petición.
     */
    public Info addCNomina(){
        Info i = controlProductos.addCNomina(clienteActual);
        if (i.isSuccess()){
            productos.add(Parsear.getInstance().cuentaNominaFromJson(i.getMsg()));
            i.setMsg("Cuenta nómina añadida con éxito");
            fireTableDataChanged();
        }
        return i;
    }
    
    /**
     * Añade una nueva cuenta ahorro al modelo. Si la petición al control de productos ha tenido éxito,
     * reconstruye el objeto CuentaAhorro que viene en el mensaje en formato json y lo añade al ArrayList
     * de productos. Una vez añadido notifica a los listeners de la tabla que los datos han cambiado,
     * para que se vuelva a pintar.
     * En cualquier caso (sea exitosa o no la petición) al final se envía a la vista el objeto Info
     * para que muestre información al usuario en caso de ser necesario.
     * @param remuneracion double con la remuneración inicial de la cuenta.
     * @return Info con el resultado de la petición.
     */
    public Info addCAhorro(double remuneracion){
        Info i = controlProductos.addCAhorro(clienteActual, remuneracion);
        if (i.isSuccess()){
            productos.add(Parsear.getInstance().cuentaAhorroFromJson(i.getMsg()));
            i.setMsg("Cuenta ahorro añadida con éxito");
            fireTableDataChanged();
        }
        return i;
    }
    
    /**
     * Elimina un producto del model. Si la petición al control de productos ha tenido éxito,
     * elimina el producto seleccionado del ArrayList de productos.
     * Una vez añadido notifica a los listeners de la tabla que los datos han cambiado,
     * para que se vuelva a pintar.
     * En cualquier caso (sea exitosa o no la petición) al final se envía a la vista el objeto Info
     * para que muestre información al usuario en caso de ser necesario.
     * @param filaSeleccionada int con la fila en la que está el producto que se va a eliminar.
     * @return Info con el resultado de la petición.
     */
    public Info removeProducto(int filaSeleccionada){
        Producto p = productos.get(filaSeleccionada);
        Info i = controlProductos.removeProducto(p.getId());
        if (i.isSuccess()){
            productos.remove(p);
            fireTableDataChanged();
        }
        return i;
    }
    
    /**
     * Refresca todos los productos de la tabla. El objetivo de este método es actualizar la tabla
     * cuando se han modificado los datos del usuario con la ventana modal correspondiente. En la tabla
     * con los datos del cliente, se recoge el cambio inmediatamente, pero es necesario cambiar el titular
     * de todos los productos que se encontraban pintados en la tabla de productos.
     */
    public void refresh(){
        productos.clear();
        ArrayList<Tarjeta> tarjetas = controlProductos.getAllTarjetasDelCliente(clienteActual);
        ArrayList<CuentaNomina> cNominas = controlProductos.getAllCNominasDelCliente(clienteActual);
        ArrayList<CuentaAhorro> cAhorro = controlProductos.getAllCAhorroDelCliente(clienteActual);
        productos.addAll(tarjetas);
        productos.addAll(cNominas);
        productos.addAll (cAhorro);
        fireTableDataChanged();
    }
    
    public Producto getProductAtRow(int row){
        return productos.get(row);
    }
    
    
}
