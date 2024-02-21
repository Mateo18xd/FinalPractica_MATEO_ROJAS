/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.colas;

import controlador.TDA.listas.Exception.EmptyException;
import controlador.TDA.pilas.FulStackException;

/**
 *
 * @author Usuario iTC
 */
public class QueueUltimate<E> {
    private Queue<E> tail;

    public QueueUltimate(Integer lenght) {
        this.tail = new Queue<>(lenght);
    }
    
    public void queue(E info) throws EmptyException, FulStackException{
        tail.queue(info);
    }
    public  E dequeue() throws EmptyException{
        return tail.dequeue();
    }
    
    public Integer lenght(){
        return tail.getLenght();
    }
    
    public Boolean isFull(){
        return tail.isFull();
    }
    
    public void print(){
        System.out.println("Queue");
        System.out.println(tail.toString());
        System.out.println("");
    }
    
}
