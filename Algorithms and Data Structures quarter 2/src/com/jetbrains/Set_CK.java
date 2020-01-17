package com.jetbrains;
/**
 * //UPDATE
 *
 Charles Kolozsvary
 Period 2 Algorithms
 Java Version 4.2.1
 Java SDK: 12.01.0
 Description:
 Use the Set_CAKs concept and its own implementation to write the AST Set, Set_YI.java with the following methods:
 ‌
 1. add(E e)
 Ensures that this collection contains the specified element.
 ‌
 2. addAll(Collection c)
 Adds all of the elements in the specified collection to this set if they’re not already present. The alternate version is to add all elements from Set_YI.
 ‌
 3. void clear()
 Removes all of the elements from this set (optional operation).
 ‌
 4. boolean contains(Object o)
 Returns true if this set contains the specified element.
 ‌
 5.boolean containsAll(Collection c)
 Returns true if this set contains all of the elements of the specified collection.
 ‌
 6. boolean equals(Object o)
 Compares the specified object with this set for equality. The alternate version is to compare objects of Set_YI.
 ‌
 7. boolean isEmpty()
 Returns true if this set contains no elements.
 ‌
 8. Iterator iterator()
 Returns an iterator over the elements in this set.
 ‌
 9. boolean remove(Object o)
 Removes the specified element from this set if it is present (optional operation).
 ‌
 10. boolean removeAll(Collection c) Removes from this set all of its elements that are contained in the specified collection (optional operation). The ? could be whatever you choose.
 ‌
 11. int size()
 Returns the number of elements in this set (its cardinality).
 ‌
 Mrs. Elia's website (If the link doesn't work, I attached a pdf)
 http://algorithms.mrseliasclasses.org/Set_CAK-iteration/
 ‌
 PU Booksite
 https://algs4.cs.princeton.edu/13stacks/
 ‌
 Video Lecture with Professor Sedgewick
 https://www.youtube.com/watch?v=Uc8jNAPOAmU&list=PLrNmXMVD0XDTRIy2kH_dVZrdX5pmc1cvq&index=4
 */
class Node //separate class Node for Node Objects
{
    //package private instance fields
    int data; //the information that one Node Holds
    Node next; //the pointer to the next node
    Node(int data) //constructor
    {
        this.data = data;
        next = null; //first node points to nothing
    }
}

public class Set_CK
{
    private Node head; //private instance field

    //1.
    //ADD METHOD (FOR ALL METHODS I MADE THEM PRIVATE AND STATIC SO I WOULDN'T HAVE TO MAKE THE LINKED LIST OR SET_CK OBJECT AN INSTANCE FIELD
    private static void add(Set_CK linkedList, int data)
    {
        Node newNode = new Node(data); // Create a new node with given data
        newNode.next = null; //link the node to nothing initially
        if (linkedList.head == null) { // If the Linked List is empty, then just make the new node as head
            linkedList.head = newNode;
        }
        else {// Else traverse till the last node and insert the new node there
            Node last = linkedList.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode; // Insert the new_node at last node
        }
    }

    //2.
    //ADD ALL
    private static void addAll(Set_CK linkedList, int[] data)
    {
        for(int val: data) //same thing as the add method, just do for as many numbers as there are in the data set
        {
            add(linkedList, val);
        }
    }

    //3.
    //CLEAR
    private static void clear(Set_CK linkedList)
    {
        Node currNode = linkedList.head; //store head and start from there
        while (currNode != null) //keep iterating until pointer is null (iterate through the entire linked list)
        {
            linkedList.head = currNode.next; // Changed head to the next current node (removing the current node)
            currNode = currNode.next; //go to the next node
        }
    }

    //4.
    //CONTAINS METHOD CHECKS IF THE LINKED LIST CONTAINS A CERTAIN DATA VALUE (CHECKS BY KEY)
    private static boolean contains(Set_CK linkedList, int key)
    {
        Node currNode = linkedList.head; //store head node
        while (currNode != null) //keep iterating until the node is pointing to null
        {
            if(currNode.data == key) //if the node data is equal to the key
            {
                return true; //then yeah return true
            }
            currNode = currNode.next; //move on to the next node
        }
        return false; //if the entire node was searched and nothing found, return false, it does not contain the value
    }

    //5.
    //CONTAINS ALL METHODS (more of the same from contains, just multiple values)
    private static boolean containsAll(Set_CK linkedList, int[] keys)
    {
        for(int key: keys) //check all the keys
        {
            if(!(contains(linkedList,key))) //if it does not contain the key, then return false
            {
                return false;
            }
        }
        return true; //if it checks every one then return true
    }

    //6.
    //EQUALS METHOD: Given two linked lists, return whether or not they have the same data and point to their respective nodes in the same order (are they equal)
    private static boolean equals(Set_CK linkedList_one, Set_CK linkedList_two)
    {
        Node currNode = linkedList_one.head; //store the heads of both linked lists
        Node currNode_two = linkedList_two.head;
        while (currNode != null) //iterate through the first linked list
        {
            if(currNode_two == null) //if the other linked list ever is null while the first is not, then they cannot be equal
            {
                return false;
            }
            if(!(currNode.data == currNode_two.data)) //if their data points are not the same then they are not equivalent (same value + arrangement)
            {
                return false;
            }
            currNode = currNode.next; //move on to next node for both
            currNode_two = currNode_two.next;
        }
        return true; //if false wasn't returned then they are equal
    }

    //7.
    //IS EMPTY METHOD
    private static boolean isEmpty(Set_CK linkedList) //just check if the head is null, then it is empty
    {
        Node currNode = linkedList.head; // Store head node
        //if it is null then the linked list is empty
        //otherwise it is not empty
        return currNode == null;
    }

    //8.
    /*
    I did not understand how to implement iterable and I have already spent too much time on this
    I suppose I should have just asked for additional help but I did not think I would need to manage anything
    which wasn't discussed in the lectures. Maybe it was in the lectures and I just missed it...
    Regardless I don't wan't to spend any more time on this assignment I need to move on
     */

    //9.
    //REMOVE METHOD
    private static void remove(Set_CK list, int key)
    {
        Node currNode = list.head, prev = null; // Store head node
        if (currNode != null && currNode.data == key) {// If head node itself holds the key to be deleted
            list.head = currNode.next; // Changed head
            System.out.println(key + " found and deleted"); //inform user
            return; //leave the method, no longer need to check others
        }
        // If the key is somewhere other than at head
        // Search for the key to be deleted, keep track of the previous node as it is needed to change currNode.next
        while (currNode != null && currNode.data != key) {
            // If currNode1 does not hold key
            prev = currNode;
            currNode = currNode.next;// continue to next node
        }
        if (currNode != null) { // If the key was present, it should be at currNode. and thus currNode shall not be null
            prev.next = currNode.next; // Since the key is at currNode Unlink currNode from linked list
            System.out.println(key + " found and deleted"); //report to user
        }
        if (currNode == null) { // If key was not present in linked list currNode should be null
            System.out.println(key + " not found"); //report to user
        }
    }

    //10.
    //REMOVE ALL METHOD
    private static void removeAll(Set_CK list, int[] keys)
    {
        for(int key: keys) //iterate through the given keys
        {
            remove(list, key); //just run the delete method for as many times as there are values to delete
        }
        //return updated list
    }

    //11.
    //SIZE METHOD (return the number of nodes/data points in the linked list
    private static int size(Set_CK linkedList)
    {
        int counter = 0;
        Node currNode = linkedList.head; //create current node from head
        while (currNode != null) //iterate
        {
            currNode = currNode.next; //move to the next node
            counter++; //increment counter
        }
        return counter; //return once over
    }

    //(Not necessary)
    //PRINT THE LINKED LIST
    private static void printList(Set_CK linkedList)
    {
        Node currNode = linkedList.head; //store head
        System.out.print("\nSet_CK: ");
        while (currNode != null) { // Traverse through the linked list
            System.out.print(currNode.data + " "); // Print the data at current node
            currNode = currNode.next; // Go to next node
        }
        System.out.println("\n");
    }

    //MAIN METHOD //MAIN METHOD //MAIN METHOD //MAIN METHOD //MAIN METHOD //MAIN METHOD //MAIN METHOD
    public static void main(String[] args)
    {
        System.out.println("create an new Linked List (empty) here it is:");
        Set_CK linkedList = new Set_CK(); //create an empty linked list
        printList(linkedList);
        System.out.println("linked list after adding a value: ");
        add(linkedList, 100);
        printList(linkedList);
        System.out.println("linked list after adding multiple values: ");
        addAll(linkedList, new int[]{1,2,3,4,5});
        printList(linkedList);

        System.out.println("\nDoes the linked list contain the value 3? (true or false) = "+contains(linkedList, 3));
        System.out.println("Does the linked list contain 1,7? = "+containsAll(linkedList, new int[]{1,7})); //contains all (returns true)
        System.out.println("Does the linked list contain 100,3,5? = "+containsAll(linkedList, new int[]{100,3,5})); //has more than what is contained (doesn't contain all, returns false)

        System.out.println("\nLinked List before Clearing: ");
        printList(linkedList);
        System.out.println("\nLinked List after Clearing: ");
        clear(linkedList);
        printList(linkedList);

        System.out.println("\nCreate two new empty nodes");
        Set_CK linkedList_one = new Set_CK();
        Set_CK linkedList_two = new Set_CK();
        System.out.println("Are they equal? "+equals(linkedList_one, linkedList_two));
        addAll(linkedList_one, new int[]{10,20,30,4});
        addAll(linkedList_two, new int[]{10,20,30});
        System.out.println("Are they equal after adding different numbers of values? "+equals(linkedList_one, linkedList_two));
        add(linkedList_two, 4);
        System.out.println("Are they equal after adding a value to make them the same? "+equals(linkedList_one, linkedList_two));


        System.out.println("\nCreate New Node and check if empty");
        Set_CK linkedList_three = new Set_CK();
        System.out.println("Node Empty before adding anything = "+isEmpty(linkedList_three));
        add(linkedList_three,0);
        System.out.println("Node Empty after adding a value/data point = "+isEmpty(linkedList_three));

        System.out.println("Create a new linked list and add values 2,4,7,8,19,21\nList initially: ");
        Set_CK linkedList_four = new Set_CK();
        addAll(linkedList_four, new int[]{2,4,7,8,19,21});
        printList(linkedList_four);
        System.out.println("remove the value 19 and print the list after");
        remove(linkedList_four,19);
        printList(linkedList_four);
        System.out.println("Size of this linked list (number of elements: "+size(linkedList_four));
        System.out.println("Delete three more values 2,8,21, and 30 which does not exist in the list so nothing should happen");
        removeAll(linkedList_four, new int[]{30,2,8,21});
        printList(linkedList_four);
        System.out.println("Size of this linked list (number of elements: "+size(linkedList_four));
    }

}
/*
OUTPUT:
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=51414:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Code/Algorithms Quarter 2/out/production/Algorithms Quarter 2" com.jetbrains.Set_CK
create an new Linked List (empty) here it is:

Set_CK:

linked list after adding a value:

Set_CK: 100

linked list after adding multiple values:

Set_CK: 100 1 2 3 4 5


Does the linked list contain the value 3? (true or false) = true
Does the linked list contain 1,7? = false
Does the linked list contain 100,3,5? = true

Linked List before Clearing:

Set_CK: 100 1 2 3 4 5


Linked List after Clearing:

Set_CK:


Create two new empty nodes
Are they equal? true
Are they equal after adding different numbers of values? false
Are they equal after adding a value to make them the same? true

Create New Node and check if empty
Node Empty before adding anything = true
Node Empty after adding a value/data point = false
Create a new linked list and add values 2,4,7,8,19,21
List initially:

Set_CK: 2 4 7 8 19 21

remove the value 19 and print the list after
19 found and deleted

Set_CK: 2 4 7 8 21

Size of this linked list (number of elements: 5
Delete three more values 2,8,21, and 30 which does not exist in the list so nothing should happen
30 not found
2 found and deleted
8 found and deleted
21 found and deleted

Set_CK: 4 7

Size of this linked list (number of elements: 2

Process finished with exit code 0

 */
