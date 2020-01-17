package com.jetbrains;

/**
 Charles Kolozsvary
 Period 2 Algorithms
 Java Version 4.2.1
 Java SDK: 12.01.0
 Description:
 Use your ArrayStackOfIntegers ADT for the following assignment: Design and implement a client to calculate the following postfix expression: in the attached image.
 ‌
 Polish Notation Calculator (If you cannot access the website, I attached a pdf)
 http://algorithms.mrseliasclasses.org/polish-notation-stack-calculator/
 ‌
 ‌
 https://www.youtube.com/watch?v=UU5UhVQhYkY
 Reverse Polish, or Postfix notation is commonly used in Computer Science, particularly in reference to Stacks - but what are stacks and how does postfix work? Professor David Brailsford takes us through it.
 https://www.youtube.com/watch?v=7ha78yWRDlE

 Postfix Operation: 8 4 -3 * 1 5 + / *
 Result: -16
 see output for more
 */
public class Postfix_CK
{
    public static void main(String[] args)
    {
        ArrayStackOfIntegers stack = new ArrayStackOfIntegers();
        boolean notDone = true;
        while (notDone) //I couldnt get !StdIn.IsEmpty() to work so I just did this instead
        {
            String token = StdIn.readString();
            switch (token) { //Switch statement is used insead of if because why not
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break; //each of these break statements are present to serve the same funcation as the else if statement
                case "+": //if a case is met, no need to check the other cases
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "/":
                    stack.push((int)(1.0 / stack.pop() * (double)stack.pop()));
                    break;
                case "stop":
                    notDone = false; //no more postfix inputs to be read, stop reading and pop the most recent value
                    break;
                default: //this would be the "else" statement in an if else chain
                    stack.push(Integer.parseInt(token)); //in this case, a number is simply pushed onto the stack
                    break;
            }
        }
        StdOut.println(stack.pop());
    }
}
/*
 * OUTPUT:
 *
Charless-MacBook-Pro-2:Algorithms Quarter 2 charleskolozsvary$ java Postfix_CK
8 4 -3 * 1 5 + / * stop
8 pushed onto stack
4 pushed onto stack
-3 pushed onto stack
-3 poped from stack
4 poped from stack
-12 pushed onto stack
1 pushed onto stack
5 pushed onto stack
5 poped from stack
1 poped from stack
6 pushed onto stack
6 poped from stack
-12 poped from stack
-2 pushed onto stack
-2 poped from stack
8 poped from stack
-16 pushed onto stack
-16 poped from stack
-16

Test runs:
Charless-MacBook-Pro-2:Algorithms Quarter 2 charleskolozsvary$ java Postfix_CK
2 4 * 2 / -6 *
2 pushed onto stack
4 pushed onto stack
4 poped from stack
2 poped from stack
8 pushed onto stack
2 pushed onto stack
2 poped from stack
8 poped from stack
4 pushed onto stack
-6 pushed onto stack
-6 poped from stack
4 poped from stack
-24 pushed onto stack
stop
-24 poped from stack
-24

Charless-MacBook-Pro-2:Algorithms Quarter 2 charleskolozsvary$ java Postfix_CK
1 1 + 1 1 + * 2 /
1 pushed onto stack
1 pushed onto stack
1 poped from stack
1 poped from stack
2 pushed onto stack
1 pushed onto stack
1 pushed onto stack
1 poped from stack
1 poped from stack
2 pushed onto stack
2 poped from stack
2 poped from stack
4 pushed onto stack
2 pushed onto stack
2 poped from stack
4 poped from stack
2 pushed onto stack
stop
2 poped from stack
2


 */