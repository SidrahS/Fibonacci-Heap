/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fibonacciheapp;

/**
 *
 * @author Ghulam Abbas
 */

class FibonacciHeapNode<T>
{
    
    T data; //value to be manipulated.
    double key; //Priority 
    boolean mark; //true if this node has had a child removed since this node was added to its parent
    int degree; //number of children of this node (does not count grandchildren)
    FibonacciHeapNode<T> child, parent;
    FibonacciHeapNode<T> left, right; //references to left and right siblings
    
    /**
     * @param data data for this node
     * @param key initial key for node
     */
    public FibonacciHeapNode(T data, double key)
    {
        right = this;
        left = this;
        this.data = data;
        this.key = key;
    }
    
    public final T GetData()
    {
        return data;
    }
    
    public final double GetKey()
    {
        return key;
    }
}
