package com.jetbrains;

import java.util.Iterator;
import java.util.NoSuchElementException;
/*
Charles Kolozsvary
 Period 2 Algorithms
 Java Version 4.2.1
 Java SDK: 12.01.0
 Description:
Implement the ArrayStackOfIntegers ADT using arrays. Look at the API in the link below.
Stacks and Queues – Stack Implementation | Mrs. Elia's Algorithms and Data Structures
http://algorithms.mrseliasclasses.org/stacks-implementation/
 */

/* Java program to implement basic stack
operations */
class ArrayStackOfIntegers {
    static final int CAPACITY = 10;
    int n; //number if items in stack
    int [] items = new int[CAPACITY]; // Maximum size of Stack

    boolean isEmpty()
    {
        return (n < 0);
    }
    boolean isFull()
    {
        return(n>=CAPACITY-1);
    }
    ArrayStackOfIntegers()
    {
        n = -1;
    }

    boolean push(int x)
    {
        if (n >= (CAPACITY - 1)) {
            System.out.println("Stack Overflow");
            return false;
        }
        else {
            items[++n] = x;
            System.out.println(x + " pushed into stack");
            return true;
        }
    }

    int pop()
    {
        if (n < 0) {
            System.out.println("Stack Underflow");
            return 0;
        }
        else {
            int poped = items[n--];
            return poped;
        }
    }

    int peek()
    {
        if (n < 0) {
            System.out.println("Stack Underflow");
            return 0;
        }
        else {
            int look = items[n];
            return look;
        }
    }
}

// Driver code
class Main {
    public static void main(String args[])
    {
        ArrayStackOfIntegers s = new ArrayStackOfIntegers();
        System.out.println(s.isEmpty());
        s.push(10);
        s.push(20);
        s.push(30);
        s.push(960);
        s.push(970);
        s.push(980);
        s.push(990);
        s.push(9990);
        s.push(99876);
        s.push(96856568);
        System.out.println(s.isFull());
        s.push(30);
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.peek() + " Peeked");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");
        System.out.println(s.pop() + " Popped from stack");

    }
}
/*
OUTPUT:
true
10 pushed into stack
20 pushed into stack
30 pushed into stack
960 pushed into stack
970 pushed into stack
980 pushed into stack
990 pushed into stack
9990 pushed into stack
99876 pushed into stack
96856568 pushed into stack
true
Stack Overflow
96856568 Popped from stack
99876 Popped from stack
9990 Popped from stack
990 Popped from stack
980 Popped from stack
970 Popped from stack
960 Popped from stack
30 Peeked
30 Popped from stack
20 Popped from stack
10 Popped from stack
Stack Underflow
0 Popped from stack

Process finished with exit code 0

 */
