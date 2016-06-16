/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * Objeto que permite almacenar información del cambio de contraseña. Incluye la contraseña actual
 * y la nueva contraseña. La primera para verificarla con la que existe en base de datos, y la segunda,
 * para si es correcta la primera, actualizarla.
 * @author JoséMaría
 */
public class CambiarPass {
    private String currentP;
    private String newP;

    public CambiarPass(){

    }

    public CambiarPass(String currentP, String newP) {
        this.currentP = currentP;
        this.newP = newP;
    }

    public String getCurrentP() {
        return currentP;
    }

    public void setCurrentP(String currentP) {
        this.currentP = currentP;
    }

    public String getNewP() {
        return newP;
    }

    public void setNewP(String newP) {
        this.newP = newP;
    }
}
