package com.jetbrains;

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

    /*
    Checks if the stack is empty
     */
    private boolean isEmpty()
    {
        return N == 0; //if it is return true if not return false
    }

    /*

     */
    public int size()
    {
        return N; //return the number of items in the stack
    }

    /*
    returns the size of the arrays, or the current capacity
     */
    public int getCurrCapacity() //getter method
    {
        return items.length;
    }

    /*

     */
    private void changeSize(int capacity) //Change the capacity of the array of items
    {
        assert capacity >= N;
        Item[] temporary = (Item[]) new Object[capacity]; //create new array of items at size of capacity
        if (N >= 0) //make sure there is anything to copy over in the first place
        {
            System.arraycopy(items, 0, temporary, 0, N); //copy the contents of the previous array to the new one of new capacity
        }
    }

    /*

     */
    private void push(Item item)
    {
        if (N == items.length)
        {
            changeSize(2*items.length); // double size of array if necessary
        }
        //DYNAMIC shrinking implementation above
        items[N++] = item;                            // add item
    }

    /*

     */
    public Item pop()
    {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow"); //throws java.util.NoSuchElementException if this stack is empty
        Item item = items[N-1];
        items[N-1] = null;                              // to avoid loitering
        N--;
        /*
        DYNAMIC SHRINKING IMPLEMENTATION below
         */
        if (N > 0 && N == items.length/4) // shrink size of array if necessary
        {
            changeSize(items.length/2);
        }
        /*
        DYNAMIC SHRINKING IMPLEMENTATION above
         */
        return item; //return the item most recently added
    }


    /*
    Returns but does not remove(like pop) the uppermost item on the stack
     */
    public Item peek() {
        if (isEmpty())
        {
            throw new NoSuchElementException("Stack underflow"); //throws java.util.NoSuchElementException if this stack is empty
        }
        return items[N-1]; //return the item most recently added to this stack
    }

    /*
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator() {
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
    MAIN METHOD
     */
    public static void main(String[] args)
    {
        DynamicShrinking<String> stack = new DynamicShrinking<>();
        stack.push("hello");
        stack.push("every");

    }
}
