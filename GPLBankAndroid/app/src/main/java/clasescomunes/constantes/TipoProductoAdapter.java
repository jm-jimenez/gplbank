/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasescomunes.constantes;

/**
 *
 * @author JoséMaría
 */
public class TipoProductoAdapter {
    private String text;
    private TipoProducto tipo;
    
    public TipoProductoAdapter(){
        
    }
    
    public TipoProductoAdapter(String text, TipoProducto tipo){
        this.text = text;
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        return text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }
    
    
}
