package com.jetbrains;

/**
 *Charles Kolozsvary
 * Period 1 ELIA
 * GAME_CK
 * 5-10-17
 * Blue Jay Version 4.1.2 java version 1.8.0_144
 * Description:
 * A class that creates and monitors ball objects with many different instance fields
 * instance ffields are private and has get and set methods to acess/mutate the information of the balls
 * also includes a collide method and random method.
 * Collide method is used to determine wether the balls (really ball) collides with the player
 * Random method generates a random number for the x and y position of the balls as they are spawned in.
 * EDMODO POST:
 * Web Exercise 46: Game. Implement the game dodge using StdDraw: move
 * a blue disc within the unit square to touch a randomly placed green disc
 * , while avoiding the moving red discs. After each touch, add a new moving
 * red disc. Implement your Doge Game using OOD.
 */
public class Balls
{
    // instance variables - replace the example below with your own
    //private for ENCAPSULATION
    private double rx, ry;     // position (bottom left)
    private double vx, vy;     // velocity(Kinda Slow) vx is the lateral movement per loop, vy is the vertical movement per loop
    private double radius;           // radius
    /**
     * Constructor for objects of class Balls
     */
    public Balls(double px, double py) //constructor
    {
        // initialise instance variables
        this.rx = -px;
        this.vx = Math.random() / 30;
        this.ry = -py;
        this.vy = Math.random() / 30;
        this.radius = 0.005; //.175
    }
    public double getRX() //get Methods
    {
        return rx;
    }
    public double getRY() //get Methods
    {
        return ry;
    }
    public double getVX() //get Methods
    {
        return vx;
    }
    public double getVY() //get Methods
    {
        return vy;
    }
    public double getRadius() //get Methods
    {
        return radius;
    }
    public void setVX(double val) //set Methods
    {
        this.vx = val;
    }
    public void setVY(double val) //set Methods
    {
        this.vy = val;
    }
    public void setRX(double val) //set Methods
    {
        this.rx = val;
    }
    public void setRY(double val) //set Methods
    {
        this.ry = val;
    }
    public boolean Collide(double x1, double y1, Balls ball, double r1) //colide method
    {
        double xd = x1 - ball.getRX(); // get the distance between x's
        double yd = y1 - ball.getRY(); // get the distance between y's
        double sumRadius = r1 + ball.getRadius(); // get sum of radii
        double sqrRadius = sumRadius * sumRadius; // get the value of the square radii
        double distSqr = (xd * xd) + (yd * yd); //get the distance squared
        if (distSqr <= sqrRadius)
        {
            return true; //they collided
        }
        return false; //didnt collide
    }
    public static double getRandom(double xPos, double yPos, double r1) //custom random method to return nice values for the arena
    { //takes three values
        boolean running = true; //runs untill good value ffound which isnt directly ontop of player
        double rand = 0; //random value
        while (running){
            rand = (((double)(Math.random() * (7.9)))+1);
            if(Math.random() < .5)
            {
                rand = -rand;
            }
            //check if the new ball coordinates would collide with player
            double xd = xPos - rand;
            double yd = yPos - rand;
            double sumRadius = .5 + r1;
            double sqrRadius = sumRadius * sumRadius;
            double distSqr = (xd * xd) + (yd * yd);
            if (distSqr <= sqrRadius)
            {
                running = true;
            }
            else
            {
                running = false;
            }
        }
        return rand;
    }
}
/*
 * No OUTPUT
 */