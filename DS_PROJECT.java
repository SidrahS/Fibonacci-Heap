/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fibonacciheapp;

import java.util.*;
import static fibonacciheapp.FibonacciHeap.union;
import fibonacciheapp.FibonacciHeap; 
import fibonacciheapp.FibonacciHeapNode; 

/**
 *
 * @author Ghulam Abbas
 */
public class DS_PROJECT
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Array of some fibonacci nodes
        ArrayList<FibonacciHeapNode<String>> nodes=new ArrayList();
        nodes.add(new FibonacciHeapNode<>("X", 10));
        nodes.add(new FibonacciHeapNode<>("Y", 9.89));
        nodes.add(new FibonacciHeapNode<>("Z", 10.5));
        nodes.add(new FibonacciHeapNode<>("T", 21));
        nodes.add(new FibonacciHeapNode<>("M", 6.2));
        
        FibonacciHeap<String> heap1 = new FibonacciHeap<>();
        System.out.println("Performing operations for Heap1:");
        heap1.insert("A", 6);
        heap1.insert("B", 4);
        heap1.insert("C", 7);
        heap1.insert("D", 1);
        heap1.displayMin();  //Minimum will be displayed on the basis of key i.e. priority.
        heap1.removeMin();
        heap1.displayMin();
        
        heap1.insert(nodes.get(0)); //the one with X as element and 10 as key
        System.out.println(heap1.toString());

        FibonacciHeap<String> heap2 = new FibonacciHeap<>();
        System.out.println("\nPerforming operations for Heap2:");
        heap2.insert("P", 5);
        heap2.insert("Q", 8);
        heap2.insert("R", 3);
        heap2.insert("S", 2);
        
        for(int i=1; i<nodes.size(); i++)
            heap2.insert(nodes.get(i));
        
        heap2.displayMin();
        System.out.println(heap2.toString());
        
        System.out.println("\nMerging Heap1 and Heap2 into heap1:");
        heap1=union(heap1, heap2);
        System.out.println(heap1.toString());
        
        System.out.println("\nDeleting a specific node from heap1:");
        double temp=nodes.get(0).key;
        heap1.delete(nodes.get(0));
        System.out.println(nodes.get(0).data +" with key " +temp +" was removed");
        System.out.println(heap1.toString());
        
        System.out.println("\nDecreasing key of: " +nodes.get(nodes.size()-1).data);
        heap1.  DecreaseKey(nodes.get(nodes.size()-1), 1);
        System.out.println(heap1.toString() +"\n");
        
        heap1.displayMin();
        
        heap1.shortestPath(nodes.get(4)); //to M
        heap1.shortestPath(nodes.get(2)); //to Z
        heap1.shortestPath(nodes.get(3)); //to T
    }
    
}
