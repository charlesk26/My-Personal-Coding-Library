package com.jetbrains;

public class Postfix_CK
{
    public static void main(String[] args)
    {
        Stack<Double> stack = new Stack<Double>();
        while (!StdIn.isEmpty())
        {
            String token = StdIn.readString();
            switch (token) {
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "/":
                    stack.push(1.0 / stack.pop() * stack.pop());
                    break;
                case "sqrt":
                    stack.push(Math.sqrt(stack.pop()));
                    break;
                default:
                    stack.push(Double.parseDouble(token));
                    break;
            }
        }
        StdOut.println(stack.pop());
    }
}
