package code;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class GenericLinkedStack<E> implements Stack<E>{
     private Elem<E> top; 
     
    
    // public GenericLinkedStack(E model){
     //    top = new Elem
    // }
    
    
        private static class Elem<E>  {

        private E value;
        private Elem<E> next;

        private Elem(E value, Elem<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    public boolean isEmpty() {

        return (top == null);
    }

    public E peek() {

        return top.value;
    }

    public E pop() {
        E saved = top.value;
        top = top.next;
        

        return saved;
    }

    public void push(E element) {

        Elem<E> item = new Elem<E>(null, null);
        item = top;
        top = new Elem<E>(null, null);
        top.value = element;
        top.next = item;

        

    }
    
}
