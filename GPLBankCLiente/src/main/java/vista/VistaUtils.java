/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import javax.swing.JTextField;

/**
 * Proporciona utilidades a las clases que se encargan de la vista.
 * @author JoséMaría
 */
public class VistaUtils {

    private static VistaUtils instance;

    private VistaUtils() {

    }

    /**
     * Devuelve la instancia.
     * @return VistaUtils con la instancia.
     */
    public static VistaUtils getInstance() {
        if (instance == null) {
            instance = new VistaUtils();
        }
        return instance;
    }

    /**
     * Añade un key listener al JTextField facilitado. En el key typed,
     * cambiará el color del fondo a blanco. Sirve para reestablecer a blanco los campos
     * incorrectos que han sido pintados a rojo.
     * @param target JTextField al cual añadir el listener.
     */
    public void ponerKeyListenerAlJtf(JTextField target) {
        target.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                target.setBackground(Color.white);
            }
        });
    }

    /**
     * Comprueba si un JTextField que tiene que ser cumplimentado tiene datos o no. Si no tiene
     * ningún dato, se pinta el fondo del text field de color rojo.
     * @param jtf JTextField a comprobar.
     * @return true, si está relleno; false si no.
     */
    public boolean comprobarJtfRellena(JTextField jtf) {
        boolean ok = true;
        if (jtf.getText().equalsIgnoreCase("")) {
            jtf.setBackground(Color.red);
            ok = false;
        }
        return ok;
    }
    
    /**
     * Comprueba si un JTextField que requiere un valor numérico está rellenado con un número.
     * @param jtf - JTextField a comprobar.
     * @return double con el importe de la casilla. -1 si la casilla está mal rellena.
     */
    public double comprobarDoubleJtf(JTextField jtf){
        double importe = 0;
        try{
            importe = Double.parseDouble(jtf.getText());
        } catch (NumberFormatException e){
            importe = -1;
            jtf.setBackground(Color.red);
        }
        return importe;
    }
}
