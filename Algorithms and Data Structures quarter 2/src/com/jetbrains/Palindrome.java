package com.jetbrains;
/*
Charles Kolozsvary
 Period 2 Algorithms
 Java Version 4.2.1
 Java SDK: 12.01.0
 Queue and Stack ADT's are from Robert Sedgewick (from Princeton)
 Description:
 Create a system using a stack and a queue to test whether a given string is a palindrome (i.e., the characters read the same forward or backward).
http://algorithms.mrseliasclasses.org/queue-adt/
 */
public class Palindrome
{
    public static void main(String[] args)
    {
        Queue<String> queue = new Queue<String>(); //create Queue and Stack Objects both storing strings
        Stack<String> stack = new Stack<String>();
        String[] input = args[0].split(""); //input string subdivided into individual components in array
        int n = input.length; //the number of characters in the string
        boolean odd = false; //innocent until proven guilty, boolean for keeping track if the the number of characters is even or odd
        int middle = n/2; //middle position
        if(!(n%2 == 0)) //if not divisible by 2, then,
        {
            odd = true; //its odd
        }
        if(odd)
        {
            for(int i = 0; i<middle; i++) //enqueue the queue (first half, excluding middle)
            {
                queue.enqueue(input[i]);
            }
            for(int i = middle+1; i<n; i++) //build the stack (second half, excluding middle)
            {
                stack.push(input[i]);
            }
        }
        else
        {
            for(int i = 0; i<middle; i++) //enqueue the queue (first half, excluding middle)
            {
                queue.enqueue(input[i]);
            }
            for(int i = middle; i<n; i++) //build the stack (second half, including middle)
            {
                stack.push(input[i]);
            }
        }
        boolean isPalindrome = true; //innocent until proven guilty
        for(int i = 0; i<middle; i++) //iterate over the number of items in the stack and queue
        {
            if(!(queue.dequeue().equals(stack.pop()))) //if the dequeue and pop are not equal, the string was not a palindrome
            {
                isPalindrome = false; //it is not a palindrome
            }
        }
        if(isPalindrome) //if it was, give the message
        {
            System.out.println(args[0]+" is a palindrome");
        }
        else //if it wasn't give another message
        {
            System.out.println(args[0]+" is NOT a palindrome");
        }
    }
}
/*
OUTPUT: (multiple trials with different arguments)

1234567890987654321 is a palindrome

racecar is a palindrome

rac$$car is a palindrome

rac$Scar is NOT a palindrome

123454320 is NOT a palindrome

tattarrattat is a palindrome

redivider is a palindrome
 */
