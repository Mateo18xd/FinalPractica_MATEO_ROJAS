/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos.exeption;

/**
 *
 * @author Usuario iTC
 */
public class VerticeException extends Exception{

    public VerticeException(String msg) {
        super(msg);
    }

    public VerticeException() {
        super("Vetice fuera de rango");
    }
    
    
}
