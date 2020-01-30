package com.jetbrains;

/**
 * Charles Kolozsvary
 * Period 1 ELIA
 * GAME_CK
 * 5-10-17
 * Blue Jay Version 4.1.2 java version 1.8.0_144
 * Description:
 * A class which creates and monitors the safe zones in the game
 * Allows the driver to acess and change intance fields of the SafeZone
 * with get and Set methods and has its own collide and getRandom Methods aswell-->
 * like the Balls class
 * EDMODO POST:
 * Web Exercise 46: Game. Implement the game dodge using StdDraw: move
 * a blue disc within the unit square to touch a randomly placed green disc
 * , while avoiding the moving red discs. After each touch, add a new moving
 * red disc. Implement your Doge Game using OOD.
 */
public class SafeZone
{
    private double xPos, yPos; //instance fields ENCAPSULATION
    private double radius;
    public SafeZone(double x1, double y1) //constructor
    {
        this.xPos = x1;
        this.yPos = y1;
        this.radius = 1.225;
    }
    public static double getRandom() //another getrandom just for the initial spawn of the safe zone
    {
        double rand;
        rand = (((double)(Math.random() * (7.9)))+1);
        if(Math.random() < .5)
        {
            rand = -rand;
        }
        return rand;
    }
    public void setNewZone(double x, double y) //set method
    {
        this.xPos = x;
        this.yPos = y;
    }
    public double getxPos() //set method
    {
        return xPos;
    }
    public double getyPos() //get method
    {
        return yPos;
    }
    public double getRadius() //get method
    {
        return radius;
    }
    public boolean Collide(double x1, double y1, SafeZone s, double r1) //collide method
    {
        double xd = x1 - s.getxPos();
        double yd = y1 - s.getyPos();
        double sumRadius = r1 + s.getRadius();
        double sqrRadius = sumRadius * sumRadius;
        double distSqr = (xd * xd) + (yd * yd);
        if (distSqr <= sqrRadius)
        {
            return true;
        }
        return false;
    }
}
/*
 * No OUTPUT
 */