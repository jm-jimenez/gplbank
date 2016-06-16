/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.vista;

import clasescomunes.modelo.productos.CuentaAhorro;

/**
 *
 * @author JoséMaría
 */
public class GPLBankTextUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        CuentaAhorro p = new CuentaAhorro("chema", 300, "ES13138998357", 223, 3);
        
        System.out.println(p);
    }
    
}
