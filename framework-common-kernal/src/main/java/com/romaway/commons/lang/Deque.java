/**
 * 
 */
package com.romaway.commons.lang;

import java.util.Iterator;
import java.util.Queue;


public interface Deque<E> extends Queue<E> {
    
    void addFirst(E e);

    
    void addLast(E e);

    
    boolean offerFirst(E e);

    
    boolean offerLast(E e);

   
    E removeFirst();

    
    E removeLast();

   
    E pollFirst();

   
    E pollLast();

    
    E getFirst();

  
    E getLast();

   
    E peekFirst();

    
    E peekLast();

   
    boolean removeFirstOccurrence(Object o);

    
    boolean removeLastOccurrence(Object o);

    // *** Queue methods ***

    
    boolean add(E e);

    
    boolean offer(E e);

   
    E remove();

   
    E poll();

    
    E element();

    
    E peek();


    // *** Stack methods ***

   
    void push(E e);

    E pop();


    // *** Collection methods ***

    
    boolean remove(Object o);

    
    boolean contains(Object o);

    /**
     * Returns the number of elements in this deque.
     *
     * @return the number of elements in this deque
     */
    public int size();

    /**
     * Returns an iterator over the elements in this deque in proper sequence.
     * The elements will be returned in order from first (head) to last (tail).
     *
     * @return an iterator over the elements in this deque in proper sequence
     */
    Iterator<E> iterator();

    /**
     * Returns an iterator over the elements in this deque in reverse
     * sequential order.  The elements will be returned in order from
     * last (tail) to first (head).
     *
     * @return an iterator over the elements in this deque in reverse
     * sequence
     */
    Iterator<E> descendingIterator();

}
