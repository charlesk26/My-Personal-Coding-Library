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
public class Polygon
{
    private double centerX; //the center x coordinate of the polygon
    private double centerY; //the center y coordinate of the polygon
    private double sideL; //the side length of the polygon
    private double radius; //the radius of the polygon
    private int n; //the number of sides of the polygon
    private double apothem; //the apothem of the polygon(distance from center to side, smaller than radius)

    /*
    CONSTRUCTOR
     */
    public Polygon(double cX, double cY, double sL, int n) //constructor
    {
        this.centerX = cX;
        this.centerY = cY;
        this.n = n;
        this.radius = sL / (2*Math.sin(Math.PI/n));
        this.apothem = sL/(2*Math.tan(Math.PI/n));
        this.sideL = sL;
    }
    /*
    GETTER METHODS
     */
    public double getSideL()
    {
        return sideL;
    }
    public double getCenterX()
    {
        return centerX;
    }
    public double getCenterY()
    {
        return centerY;
    }
    public double getRadius()
    {
        return this.radius;
    }
    public int getN()
    {
        return this.n;
    }
    public double getApothem()
    {
        return apothem;
    }
    /*
    DRAWING METHOD
     */
    public void draw() //to draw each polygon I just use its center and radius to find the first point on the polygon and rotate that point around for as many times as needed, adding each point to make a polygon
    {
        double[] xCoordinates = new double[n]; //create new double array for x coordinates
        double[] yCoordinates = new double[n]; //same for y
        for(double theta = Math.PI*2, i = 0; theta>0.0 && i < n; theta-=Math.PI*2/n, i++) //iterate a for loop starting at 2 pi subtracting 2 pi / n each time until theta !> 0.0
        {
            double[] p = Transform2D.rotate(centerX, centerY-radius, centerX, centerY, theta); //for each theta, rotate the point below the center of the polygon a certain degree, at first not rotating it at all since theta = 2 pi
            xCoordinates[(int) i] = p[0]; //assign the coordinate to the array
            yCoordinates[(int) i] = p[1];
        }

        StdDraw.polygon(xCoordinates, yCoordinates); //draw the single polygon
    }
}
/*
No output - This is a supporting class
 */
