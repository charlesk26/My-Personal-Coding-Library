package com.jetbrains;
/**
 * Charles Kolozsvary
 * Period 2 Algorithms
 * Java Version 4.2.1
 * Java SDK: 12.01.0
 * This is built off of Robert Sedgewick's Stack.java ADT
 * Description:
 * Dynamic shrinking. With the array implementations of stack and queue, we doubled the size of the array when
 * it was not big enough to store the next element. If we perform a number of doubling operations, and then delete
 * a lot of elements, we might end up with an array that is much bigger than necessary. Implement the following strategy:
 * whenever the array is 1/4 full or less, shrink it to half the size. Explain why we don’t shrink it to half the size when it is 1/2 full or less.
 * Stacks at PU
 * https://algs4.cs.princeton.edu/13stacks/
 * Stacks
 * http://algorithms.mrseliasclasses.org/2-stacks-problems/
 *
 * QUESTION:
 * Explain why we don’t shrink it to half the size when it is 1/2 full or less.
 *
 * ANSWER:
 *  You do not shrink it to half size when doubled because: when you reach the point where
 *  N (number of items) == items.length (the current capacity) you double the capacity. At this moment the amount of items in the
 *  array is now half the capacity, which would mean that you half the size again, which would mean you would double the the size, which
 *  would mean you would have to double the size, ..., etc.
 *  You would end up constantly doubling and halving the size of the array which would be rather wasteful and
 *  bad coding!
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
public class DynamicShrinking<Item> implements Iterable<Item> {
    /*
    Instance fields
     */
    private Item[] items;   // array of items
    private int N;         // number of elements on stack

    /*
     * CONSTRUCTOR
     */
    private DynamicShrinking()
    {
        items = (Item[]) new Object[2]; //initial capacity of 2
        N = 0; //initial size of 0 obv.
    }
    //GETTER METHODS
    /*
    Getter method makes it easier to show effects of implementation
     */
    private int size()
    {
        return N; //return the number of items in the stack
    }
    /*
    returns the size of the arrays, or the current capacity, getter method for showing effects of implementation
     */
    private int getCurrCapacity() //getter method
    {
        return items.length;
    }


    /*
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator()
    {
        return new ReverseArrayIterator();
    }
    private class ReverseArrayIterator implements Iterator<Item>
    {
        /*
        Instance fields
         */
        private int i;

        ReverseArrayIterator()  //package private constructor
        {
            i = N-1;
        }

        public boolean hasNext()
        {
            return i >= 0;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            return items[i--];
        }
    }

    /*
    Changes the size of the array of Items to the new capacity
     */
    private void changeSize(int capacity) //Change the capacity of the array of items
    {
        items = java.util.Arrays.copyOf(items, capacity); //resize the array of items to new capacity
    }

    /*
    push an item onto the stack and double the capacity if the number of items == current capacity
     */
    private void push(Item item)
    {
        System.out.println("Pushed "+item+" onto the stack");
        if (N == items.length-1) //if the capacity is equal to the number of elements double the capacity
        {
            changeSize(2*items.length); //double size/ capacity
        }
        //DYNAMIC shrinking implementation above
        items[N++] = item; // add the item
    }

    /*
    Remove an item from the array of items and half the capacity if N == items.length/4
     */
    private void pop()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Stack underflow"); //gives a no such element exception if this stack is empty(inform user)
        }
        System.out.println("Popped "+items[N-1]+" from the stack");
        Item item = items[N-1];
        items[N-1] = null;
        N--;
        /*
        Dynamic Shrinking below
         */
        if (N > 0 && N == items.length/4) // if the number of items in the array of items is 1/4 the capacity, and N > 0
        {
            changeSize(items.length/2); //then reduce the capacity to 1/2 itself
        }
    }


    /*
    Returns but does not remove (unlike pop) the uppermost item on the stack
     */
    public Item peek() {
        if (isEmpty())
        {
            throw new NoSuchElementException("Stack underflow"); //gives a no such element exception if this stack is empty(inform user)
        }
        return items[N-1]; //return the top value on stack
    }

    /*
    Checks if the stack is empty
     */
    private boolean isEmpty()
    {
        return N == 0; //if it is return true if not return false
    }
    
    /*
    MAIN METHOD
     */
    public static void main(String[] args)
    {
        DynamicShrinking<String> stack = new DynamicShrinking<>();
        stack.push("hello"); //N == 1
        System.out.println("N == "+stack.size()+" Capacity: "+stack.getCurrCapacity());
        stack.push("every"); //N == 2 == items.length //needs to double size
        System.out.println("N == "+stack.size()+" Capacity: "+stack.getCurrCapacity());
        stack.push("single"); //N == 3
        System.out.println("N == "+stack.size()+" Capacity: "+stack.getCurrCapacity());
        stack.push("person"); //N == 4 == items.length //needs to double size
        System.out.println("N == "+stack.size()+" Capacity: "+stack.getCurrCapacity());
        //but wait now we're going to start removing items and the capacity is more than we need
        //good thing as soon as get to 1/4 the capacity we halve the size
        stack.pop(); //N == 3
        System.out.println("N == "+stack.size()+" Capacity: "+stack.getCurrCapacity());
        stack.pop(); //N == 2 == items.length/4, capacity /= 2;
        System.out.println("N == "+stack.size()+" Capacity: "+stack.getCurrCapacity());
    }
}
/*
OUTPUT:
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=62536:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Personal Coding Library/Algorithms and Data Structures quarter 2/out/production/Algorithms and Data Structures quarter 2" com.jetbrains.DynamicShrinking
Pushed hello onto the stack
N == 1 Capacity: 2
Pushed every onto the stack
N == 2 Capacity: 4
Pushed single onto the stack
N == 3 Capacity: 4
Pushed person onto the stack
N == 4 Capacity: 8
Popped person from the stack
N == 3 Capacity: 8
Popped single from the stack
N == 2 Capacity: 4

Process finished with exit code 0

 */