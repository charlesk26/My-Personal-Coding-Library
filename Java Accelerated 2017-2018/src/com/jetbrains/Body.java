package com.jetbrains;
/**
 * Charles Kolozsvary
 * Period 1 ELIA
 * NBodySimulation_CK
 * 5-25-17
 * Blue Jay Version 4.1.2 java version 1.8.0_144
 * Description:
 * This class creates objects of the Bodies with their respective
 * instancefields and also does all the calculations necessary
 * to properly animate the movement of the bodies within their
 * specified systems
 *
 * EDMODO POST:
 * 1. Read thoroughly the project description from the 2 links below.

 2. Read understand the computations resources in the class website.

 3. Read through the pseudocode in the class website.

 4. Do the assignment in the following post: NBody Simulation Numerical Output.

 5. Put comments at the start of all methods.

 6. Your application must be OOD

 7. Submit the following:
 a. Good Header:
 ----description of the assignment,
 ----your name,
 ----date, java version,
 ----how to run your program.
 ----2 blank lines
 ----A paragraph clearly explaining your algorithm.
 b. All the ".java" files except the Std files.
 c. A short video of your NBody simulation.

 NOTE:
 If you are having problems with the sound, here is a BlueJ package with some resources you are welcome to use.
 */
public class Body {
    // Newtons' gravitational constant
    public static final double G = 6.67E-11;
    private double xPos; //the x coordinate
    private double yPos; //the y coordinate
    private double vx; //the velocity in the x
    private double vy; //the velocity in the y
    private double m; //the mass of the body in kilograms
    private double xForce; // The gravitational force in the x direction
    private double yForce; // The gravitational force in the y direction
    private double ax; //the acceleration in the x
    private double ay; //the acceleration in the y
    private String img; //the image file name which is an image of the body
    //CONTRUCTOR
    public Body(double xPos, double yPos, double vx, double vy, double m, String img){
        this.xPos = xPos;
        this.yPos = yPos;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
        this.img = img;
    }
    //GET METHODS
    public double getxPos()
    {
        return this.xPos;
    }
    public double getyPos()
    {
        return this.yPos;
    }
    public double getVX()
    {
        return this.vx;
    }
    public double getVY()
    {
        return this.vy;
    }
    public double getM()
    {
        return this.m;
    }
    public double getXForce()
    {
        return this.xForce;
    }
    public double getYForce()
    {
        return this.yForce;
    }



    public double calcForce(Body body){ //calculate the total force
        return (G * this.m * body.getM()) / Math.pow(calcDistance(body), 2);
    }
    public double calcXForce(Body body){ //calculate the x force
        double dx = body.getxPos() - this.xPos;
        return calcForce(body) * dx / calcDistance(body);
    }
    public double calcYForce(Body body){ //calculate the y force
        double dy = body.getyPos() - this.yPos;
        return calcForce(body) * dy / calcDistance(body);
    }
    public double calcDistance(Body body){ //calculate the distance
        double dx = body.getxPos() - this.xPos;
        double dy = body.getyPos() - this.yPos;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
    public void  setForces(Body[] bodies){ //set the forces for the bodies
        xForce = 0; //both equal to 0 at begining
        yForce = xForce;
        for (Body body : bodies){
            if (this == body){
                continue; //dont compare the same bodies to themselves
            } else {
                xForce += this.calcXForce(body); //add the xforce to existing xforce
                yForce += this.calcYForce(body); //add the yforce to existing yforce
            }
        }
    }
    public void drawIt(){ //draw the image of the body
        StdDraw.picture(xPos, yPos, img); //img is the image //draw image
    }
    public void update(double dt){ //update the ax, ay, vx, vy, and coordinate position of the body
        ax = xForce / m;
        ay = yForce / m;
        vx = vx + dt * ax;
        vy = vy + dt * ay;
        xPos = xPos + dt * vx;
        yPos = yPos + dt * vy;
    }
}
/*
 * NO OUTPUT VIDEO PROVIDED
 */




