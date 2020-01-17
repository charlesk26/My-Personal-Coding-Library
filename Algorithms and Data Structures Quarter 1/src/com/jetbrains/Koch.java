package com.jetbrains;

/******************************************************************************
 *  Compilation:  javac Koch.java
 *  Execution:    java Koch n
 *  Dependencies: Turtle.java
 *
 *  Plot a Koch curve of order n.
 *
 *  % java Koch 5
 *
 *  * Charles Kolozsvary *THIS IS NOT MY CODE, THE ONLY THING I MADE WERE THE COMMENTS AND THE CALCULATE AREA METHOD*
 *  * Period 2 Algorithms
 *  * 9-11-19
 *  * Java Version 4.2.1
 *  * Description:
 *  Recursive graphics. Koch_YI.java takes a command-line argument n and draws a Koch curve of order n. A Koch curve of order order 0 is a line segment. To form a Koch curve of order n:
 * Draw a Koch curve of order n−1
 * Rotate 60° counterclockwise
 * Draw a Koch curve of order n−1
 * Rotate 120° clockwise
 * Draw a Koch curve of order n−1
 * Rotate 60° counterclockwise
 * Draw a Koch curve of order n−1
 * Below are the Koch curves of order 0, 1, 2, and 3.
 *
 * As the order of the curve increases, how is the area inside the curve affected?
 * The area inside the curve increases, then approaches a maximum value of around .1732 units squared
 *
 * Modify the Koch ADT to include a method that implements the area computation to assist you in answering the question.
 *
 * Document clearly your proof.
 * Documentation below by the calculateArea() method
 *
 * Find these classes in the folder on the left side navigation:
 * A link should point you to all the code and the Standard Libraries
 * Koch.java
 * Turtle.java
 *
 ******************************************************************************/

public class Koch {

    /**
     *
     * plot Koch curve of order n, with given step size
     *
     * */
    public static void koch(int n, double step, Turtle turtle) {

        if (n == 0) {
            turtle.goForward(step);
            return;
        }
        koch(n-1, step, turtle);
        turtle.turnLeft(60.0);
        koch(n-1, step, turtle);
        turtle.turnLeft(-120.0);
        koch(n-1, step, turtle);
        turtle.turnLeft(60.0);
        koch(n-1, step, turtle);
    }

    /**
     *
     * MAIN METHOD, DRAW THE KOCH BASED ON N ITERATIONS: PRINT THE AREA UNDERNEATH
     */
    public static void main(String[] args) {
        StdDraw.setCanvasSize(800,800);
        StdDraw.setScale(-0.025,1.025); //setting scale so that the koch snowflake (segment) is centered and visible nicely
        int n = Integer.parseInt("1"); //512 is the highest integer value I can put in before it maxes out and starts sending NaN
        double step = (1.0 / Math.pow(3.0, n)); //The size/ length of the snowflake (side length of 1/3 for the largest triangle)
        Turtle turtle = new Turtle(0.0, 0.0, 0.0); //create turtle object
        koch(n, step, turtle); //draw the snowflake recursively
        System.out.println("n: "+n+"\narea: "+calculateArea(n, step)); //Calculate and print the Area of the snowflake and n

    }
    public static double calculateArea(int n, double step) {
        if (n == 0) { //terminating condition
            return 0.0; //don't need to return area because there is no area when n == 0
        }
        /**
         * The line below is the calculation for the area of the triangles at a certain iteration n
         * The way It works is that it figures out the number of additional triangles (Math.pow(4, n - 1))
         * Then calculates the side length (step) also based on the iteration n (since these are equilateral triangles,
         * Given that the side length is step, the height is step * Math.sqrt(3), meaning the area of the triangle is:
         * Math.pow(step,2) * Math.sqrt(3) * .25
         */
        double area = (Math.pow(4, n - 1)) * Math.pow(step, 2) * Math.sqrt(3) * .25;

        return area + calculateArea(n - 1, step*3); //return the sum of the areas (at the end) but also call for the next calculation and
        // decrease n by 1, but also multiply step by three because the areas calculated start on the outside and work their way in
    }
    /**
     *
     * @param n integer which indicates the number of times you iterate over the snowflake design (adding more triangles)
     * @param step The side length of the triangles for each iteration n
     * @return the total area of the snowflake
     */

}
/*
OUTPUT (Multiple Trials):
n: 1
area: 0.04811252243246881

n: 2
area: 0.06949586573578828

n: 3
area: 0.07899957387059693

n: 6
area: 0.0859350645807728

n: 512
area: 0.08660254037844396

 */