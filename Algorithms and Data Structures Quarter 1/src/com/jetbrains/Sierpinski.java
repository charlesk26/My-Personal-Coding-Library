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
import java.awt.Color;
public class Sierpinski
{
    /*
    Instance fields
     */
    private double startCenterX;
    private double startCenterY;
    private double startSide; //initial side length of the polygon
    private int order;
    private int n;
    /*
    Constructor
     */
    public Sierpinski(double sX, double sY, double sL, int n, int order)
    {
        this.startCenterX = sX;
        this.startCenterY = sY;
        this.startSide = sL;
        this.n = n;
        this.order = order;
    }
    /*
    Getter Methods
     */
    public double getStartCenterX()
    {
        return startCenterX;
    }

    public double getStartCenterY() {
        return startCenterY;
    }

    public double getStartSide() {
        return startSide;
    }

    public int getN() {
        return n;
    }

    public int getOrder() {
        return order;
    }
    /*
    Drawing methods
     */
    public void drawSurrounding(Polygon middlePoly, double deg)
    {
        for (double theta = Math.PI * 2, i = 0; theta > 0.0 && i < n; theta -= Math.PI * 2 / (n), i++)
        {
            new Polygon(middlePoly.getCenterX() + Math.cos(Math.PI / 2 + (theta)) * middlePoly.getApothem()*2, middlePoly.getCenterY() + Math.sin(Math.PI / 2 + (theta)) * middlePoly.getApothem()*2, middlePoly.getSideL()/ (2), middlePoly.getN()).draw(); //mess around with what you divide by for cool changes to the fractal
        }
    }
    /*
    RECURSION
     */
    public void sierpinski(int order, Polygon gon, double deg)
    {
        if(order == 0) //base case (stop drawing polygons, return to last call on stack)
        {

        }
        else
        {
            //gon.draw(); //draw the "center" polygon (relative center)
            drawSurrounding(gon, deg-1); //draw the surrounding polygons to the relative center
            for (double theta = Math.PI * 2, i = 0; theta > 0.0 && i < n; theta -= Math.PI * 2 / (n), i++)
            {
                //this monstrous line below calls sierpinski again with order-1 and creates a new polygon to pass to sierpinski
                //this new polygon is one of the surrounding polygons that we just drew, with each surrounding polygon drawing its own
                //surrounding polygons order number of times
                sierpinski(order - 1, new Polygon(gon.getCenterX() + Math.cos(Math.PI / 2 + (theta)) * gon.getApothem()*2, gon.getCenterY() + Math.sin(Math.PI / 2 + (theta)) * gon.getApothem()*2, gon.getSideL()/ (2), gon.getN()), deg-1); //mess around with what you divide by for cool changes to the fractal (maybe try using deg in the statements idk
            }
        }
    }
    public static void main(String[] args)
    {
        int n = 3; //the number of sides of the polygon
        int order = 5; //the number of iterations for the fractal
        double startL = 1.0; //the starting side length for the polygon
        double scale = (startL / (2*Math.sin(Math.PI/n)))*6; //make the scale be 4 times the radius *based on side length)
        double startX = 0; //starting center
        double startY = 0;

        StdDraw.setCanvasSize(900,900); //set up std draw
        StdDraw.setXscale(-scale,scale);
        StdDraw.setYscale(-scale,scale);
        StdDraw.clear(new Color(100,255,205)); //set back round

        Sierpinski s = new Sierpinski(startX, startY, startL, n, order);
        Polygon poly = new Polygon(s.getStartCenterX(),s.getStartCenterY(),s.getStartSide(),s.getN()); //create the center polygon
        s.sierpinski(s.getOrder(), poly, 7); //run the recursive method starting with the first polygon, and order
    }
}
/*
Output included as screen shots
 */
