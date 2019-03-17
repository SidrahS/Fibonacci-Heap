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

import java.util.*;

public class FibonacciHeap<T>
{
    private static final double oneOverLogPhi = 1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0); //for the U.P in consolidate
    private FibonacciHeapNode<T> minNode; //reference to the minimum node (root) of the heap
    private int num_Nodes; //num of nodes in the heap
    
    public FibonacciHeap()
    {
        num_Nodes=0; //initially the no. of nodes are zero.
    }
    
    public boolean isEmpty()
    {
        return minNode==null;
    }
    
    public void clear() //removes all elements from this heap
    {
        minNode=null;
        num_Nodes=0;
    }
    
    public int size()
    {
        return num_Nodes;
    }
    
    private FibonacciHeapNode<T> min() //gets the min node from the heap
    {
        return minNode;
    }
    
    //---------main functions--------------

    /*
     * @param node node to decrease the key of
     * @param k new key value for node x
     */
    public void DecreaseKey(FibonacciHeapNode<T> node, double k) //decreases key of a node. Heap may be changed; no consolidation
    {
        if(k > node.key)
            throw new IllegalArgumentException("decreaseKey() got larger key value");

        node.key = k; //new key

        FibonacciHeapNode<T> p = node.parent; 

        if((p != null) && (node.key < p.key)) //node's new key is less than parent's key
        {
            cut(node, p);
            CascadingCut(p); //removes the connection between that selected node and its parent
        }

        if (node.key < minNode.key)
            minNode = node;  //Updating minimum node if after decrement, the new key is the smallest.
    }
    
    /**
     * @return node with the smallest key
     */
    public FibonacciHeapNode<T> removeMin() //removes the root and causes consolidation if necessary
    {
        FibonacciHeapNode<T> node = minNode; //Node to be deleted.

        if (node != null)
        {
            int numKids = node.degree; 
            FibonacciHeapNode<T> x = node.child; //Child's of minimum node and the one to be deleted.
            FibonacciHeapNode<T> tempRight;

            while (numKids > 0) //for each child of z do...
            {
                tempRight = x.right;

                // remove x from child list
                x.left.right = x.right;
                x.right.left = x.left;

                // add x to root list of heap
                x.left = minNode;
                x.right = minNode.right;
                minNode.right = x;
                x.right.left = x;

                // set parent[x] to null
                x.parent = null;
                x = tempRight;
                numKids--;
            }

            // remove node from root list of heap
            node.left.right = node.right;
            node.right.left = node.left;

            if (node == node.right)
                minNode = null;
            
            else
            {
                minNode = node.right;
                consolidate();
            }
            
            num_Nodes--; //decrement size of heap cuz node was removed
        }

        return node;
    }

    /**
     * @param to_delete - the node that is to be deleted
     */
    public void delete(FibonacciHeapNode<T> to_delete) //deletes a specific node from the heap
    {
        DecreaseKey(to_delete, Double.NEGATIVE_INFINITY); //make to_delete's key as teeny tiny as possible

        removeMin(); //remove the smallest
    }
    
    public void insert(T value, double key) //when the user passes immediate values as parameterzz
    {
        FibonacciHeapNode<T> node=new FibonacciHeapNode<>(value, key); //creates a node.
        node.key = key;

        if (minNode != null) //concatenate node into min list
        {
            node.left = minNode;
            node.right = minNode.right;
            minNode.right = node;
            node.right.left = node;

            if (key < minNode.key)  //Updates the min node.
                minNode = node;
        }
        
        else
            minNode = node;

        num_Nodes++;
    }
    
    public void insert(FibonacciHeapNode<T> node) //when user passes a NODE as a parameter
    {
        if (minNode != null) //concatenate node into min list
        {
            node.left = minNode;
            node.right = minNode.right;
            minNode.right = node;
            node.right.left = node;

            if (node.key < minNode.key)
                minNode = node;
        }
        
        else
            minNode = node;

        num_Nodes++;
    }

    /**
     * @param <T>
     * @param h1 first heap
     * @param h2 second heap
     *
     * @return new heap containing h1 and h2
     */
    public static <T> FibonacciHeap<T> union(FibonacciHeap<T> h1, FibonacciHeap<T> h2) //merges 2 heaps into 1
                                                                                       //no consolidation
    {
        FibonacciHeap<T> h = new FibonacciHeap<>(); //the new fibonacci heap

        if ((h1 != null) && (h2 != null))
        {
            h.minNode = h1.minNode; //to put the shortest node of heap 1 into the new heap.

            if (h.minNode != null) 
            {
                if (h2.minNode != null) //concatenates the root lists of h1 and h2 & determines the new minimum node
                {
                    h.minNode.right.left = h2.minNode.left; 
                    h2.minNode.left.right = h.minNode.right;
                    h.minNode.right = h2.minNode;
                    h2.minNode.left = h.minNode;

                    if (h2.minNode.key < h1.minNode.key)
                        h.minNode = h2.minNode;
                    
                }
            }
            
            else
                h.minNode = h2.minNode;

            h.num_Nodes = h1.num_Nodes + h2.num_Nodes; //Combining num of nodes of heap 1 & heap 2.
        }

        return h;
    }
    
    public void shortestPath(FibonacciHeapNode<T> node) //computest the shortest path from minNode to destNode
    {
        if(node==null)
            System.out.println("\nThe entered node is null");
        
        else
        {
            Stack<FibonacciHeapNode<T>> stack = new Stack<>();
            stack.push(node);

            System.out.print("\nShortest route to " +node.data +" is computed as follows: ");
            
            while(node!=minNode)
            {
                if(node.parent!=null)
                    node=node.parent;

                else if(node.left!=null)
                    node=node.left;
                
                else if(node.right!=null)
                    node=node.right;

                stack.push(node);
            }
            
//            System.out.println("\nShortest route is computed as follows: ");
            
            while(!stack.empty())
            {
                node=stack.pop();
                System.out.print(node.data +" ");
            }
        }
    }
    
    public void displayMin() //displays the minNode
    {
        FibonacciHeapNode<T> temp=min();
        System.out.println("Min element in the heap is:\nElement: "+temp.data+ "\nKey: "+temp.key);
    }
    
    @Override
    public String toString() //string representation of the fibonacci heap
    {
        if (minNode == null)
            return "FibonacciHeap=[]";
        
        Stack<FibonacciHeapNode<T>> stack = new Stack<>(); //create a new stack & put root in it
        stack.push(minNode);

        StringBuilder buffer = new StringBuilder(512);
        buffer.append("FibonacciHeap=[");
        FibonacciHeapNode<T> current;
        
        while (!stack.empty()) //do a simple breadth-first traversal on the tree
        {
            current = stack.pop();
            buffer.append("Element: ");
            buffer.append(current.data);
            buffer.append(" -- ");
            buffer.append("Key: ");
            buffer.append(current.key);
            buffer.append(", ");

            if (current.child != null)
                stack.push(current.child);

            FibonacciHeapNode<T> start = current;
            current = current.right;

            while (current != start)
            {
                buffer.append("Element: ");
                buffer.append(current.data);
                buffer.append(" -- ");
                buffer.append("Key: ");
                buffer.append(current.key);
                buffer.append(", ");

                if (current.child != null)
                    stack.push(current.child);

                current = current.right;
            }
        }

        buffer.append(']');

        return buffer.toString();
    }
    
    //-------Helper functions-------------

    /**
     * 
     * @param node node to perform cascading cut on
     */
    private void CascadingCut(FibonacciHeapNode<T> node) //This cuts y from its parent and then does
    {                                                   //the same for its parent, and so on up the tree.
        FibonacciHeapNode<T> p = node.parent;
        
        if (p != null) //if there's a parent...
        {
            if (!node.mark) //if node is unmarked, set it marked
                node.mark = true;
            
            else
            {
                cut(node, p); //it's marked, cut it from parent
                
                CascadingCut(p); //cut its parent as well
            }
        }
    }

    private void consolidate() //used in removeMin function //further details are in that txt file
    {
        int Size = ((int) Math.floor(Math.log(num_Nodes) * oneOverLogPhi)) + 1; //idea taken from Cormen

        List<FibonacciHeapNode<T>> array = new ArrayList<>(Size);
        
        for (int i = 0; i < Size; i++) //initialise degree array
            array.add(null);

        // Find the number of root nodes.
        int num_Roots = 0;
        FibonacciHeapNode<T> t = minNode;

        if (t != null)
        {
            num_Roots++;
            t = t.right;

            while (t != minNode)
            {
                num_Roots++;
                t = t.right;
            }
        }
        
        while (num_Roots > 0) //for each node in root list...
        {
            // Access this node's degree..
            int d = t.degree;
            FibonacciHeapNode<T> next = t.right;
            
            for ( ; ; ) //..and see if there's another of the same degree.
            {
                FibonacciHeapNode<T> y = array.get(d);
              
                if (y == null)
                    break; //Nop

                // There is, make one of the nodes a child of the other.
                // Do this based on the key value.
                if (t.key > y.key)
                {
                    FibonacciHeapNode<T> temp = y;
                    y = t;
                    t = temp;
                }
                
                link(y, t); //FibonacciHeapNode<T> y disappears from root list. Whooosh. Disappeared!
                
                array.set(d, null); //we've handled this degree, go to next one
                d++;
            }

            // Save this node for later when we might encounter another
            // of the same degree.
            array.set(d, t);

            // Move forward through list.
            t = next;
            num_Roots--;
        }

        // Set min to null (effectively losing the root list) and
        // reconstruct the root list from the array entries in array[].
        minNode = null;

        for (int i = 0; i < Size; i++)
        {
            FibonacciHeapNode<T> y = array.get(i);
            if (y == null)
                continue;
            
            if (minNode != null) //We've got a live one, add it to root list.
            {
                // First remove node from root list.
                y.left.right = y.right;
                y.right.left = y.left;

                // Now add to root list, again.
                y.left = minNode;
                y.right = minNode.right;
                minNode.right = y;
                y.right.left = y;

                // Check if this is a new min.
                if (y.key < minNode.key)
                    minNode = y;
            }
            
            else
                minNode = y;
        }
    }

    /**
     * @param x child of y to be removed from y's child list
     * @param y parent of x about to lose a child
     */
    private void cut(FibonacciHeapNode<T> x, FibonacciHeapNode<T> y) //The reverse of the link operation:
                                                                       //removes x from the child list of y.
    {
        // remove x from childlist of y and decrement degree[y]
        x.left.right = x.right;
        x.right.left = x.left;
        y.degree--;

        // reset y.child if necessary
        if (y.child == x)
            y.child = x.right;

        if (y.degree == 0)
            y.child = null;

        // add x to root list of heap
        x.left = minNode;
        x.right = minNode.right;
        minNode.right = x;
        x.right.left = x;

        // set parent[x] to nil
        x.parent = null;

        // set mark[x] to false
        x.mark = false;
    }

    /**
     * Make node y a child of node x.
     * @param y node to become child
     * @param x node to become parent
     */
    private void link(FibonacciHeapNode<T> y, FibonacciHeapNode<T> x) //used in consolidate
    {
        // remove y from root list of heap
        y.left.right = y.right;
        y.right.left = y.left;
        
        y.parent = x; //make y a child of x

        if (x.child == null)
        {
            x.child = y;
            y.right = y;
            y.left = y;
        }
        
        else
        {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }
        
        x.degree++; //increase degree[x]
        
        y.mark = false; //set mark[y] false
    }
    
}
