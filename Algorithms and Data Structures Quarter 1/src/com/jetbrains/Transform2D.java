package com.jetbrains;
/**
 Charles Kolozsvary
 Period 2 Algorithms
 10-24-19
 Java Version 4.2.1
 Description:
 https://www.youtube.com/watch?v=kbKtFN71Lfs&t=407s

 Goal. This assignment consists of three parts. First, write a library of static methods that performs geometric transforms on polygons. Next, write a program that plots a Sierpinski triangle. Finally, develop a program that plots a recursive pattern of your own design.
 http://algorithms.mrseliasclasses.org/recursion-sierpinski/

 Before you start developing, take a look at the checklist:
 http://algorithms.mrseliasclasses.org/recursion-in-graphics-sierpinski-triangle-checklist/
 */
public class Transform2D
{
    public static double scale(double a)
    {
        return a;
    }
    public static double[] translate(double px, double py, double dx, double dy)
    {
        return new double[]{px + dx, py + dy};
    }
    public static double[] rotate(double px, double py, double centerX, double centerY, double theta)
    {
        // translate given point back to origin:
        px -= centerX;
        py -= centerY;
        // rotate point
        double xNew = px * Math.cos(theta) - py * Math.sin(theta);
        double yNew = px * Math.sin(theta) + py * Math.cos(theta);
        // translate point back:
        px = xNew + centerX;
        py = yNew + centerY;
        return new double[] {px,py};
    }
}
/*
No output - just a helper class
 */
