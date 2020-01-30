package com.jetbrains;


/**
 * Charles Kolozsvary
 * Period 1 ELIA
 * NBodySimulation_CK
 * 5-25-17
 * Blue Jay Version 4.1.2 java version 1.8.0_144
 * Description:
 * A driver class that uses another class (Body) and StdDraw and StdIn
 * to simulate the motion of bodies within our solar system, based on the input.
 * This method first reads the stdInput provided and makes the N number of
 * bodies accordingly then variables are initialized like dt and t which describe
 * the total time elapsed and the amount off time elapsed per move.
 * The Animation is initialized/ set and then begins with the main animation loop consisting of
 * first setting/determining the forces of each body
 * then updating all values of each body; Including position
 * then drawing each body and showing, by clearing the canvas with the starfield image provided
 *
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
public class NBodyDriver {
    //static method which initializes+returns bodies from StnInput
    public static Body getbody(StdIn in){
        double xPos = in.readDouble(); //reads for a x
        double yPos = in.readDouble(); //reads for a y
        double vx = in.readDouble(); //reads for a vx
        double vy = in.readDouble(); //reads for a vy
        double m = in.readDouble(); //reads for a m
        String name = in.readString(); //reads for a string for the file name to display the body
        return new Body(xPos, yPos, vx, vy, m, name);
    }
    //main driver method
    public static void main(String[] args){
        double t = Double.parseDouble(args[0]); //the total t elapsed
        double dt = Double.parseDouble(args[1]); //the delta t
        String filename = args[2]; //name of text file to read
        StdIn input = new StdIn(filename); //reads file
        int numbodys = input.readInt(); //first thing read is the N ffor the number of bodies
        double radius = input.readDouble(); //next it reads the radius of the universe given in text file

        //Set Scale
        StdDraw.setCanvasSize(600,600); //set canvas
        StdDraw.setScale(-radius, radius); //set radius off universe

        //add in bodys
        Body bodies[] = new Body[numbodys]; //create list of bodies
        for (int index = 0; index < numbodys; index++){
            bodies[index] = getbody(input);
        }

        //ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION LOOP
        for (double toverall = 0; toverall < t; toverall += dt){ //toverall is just overalltime
            //CALCULATE
            for (Body body : bodies){
                body.setForces(bodies); //set the forces of the body
            }
            //UPDATE
            for (Body body : bodies){
                body.update(dt); //update everything else about the body
            }
            //DRAW
            StdDraw.picture(0, 0, "starfield.png");
            for (Body body: bodies){
                body.drawIt(); //draw the body
            }
            //SHOW
            StdDraw.show(10);
        }
    }
}
/*
 * NO OUTPUT VIDEO PROVIDED
 */




