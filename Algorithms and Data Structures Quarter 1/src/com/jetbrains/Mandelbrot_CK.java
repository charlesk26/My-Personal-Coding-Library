/**
 *
 *
 *
 Charles Kolozsvary
 Period 2 Algorithms
 10-03-19
 Java Version 4.2.1
 Description:
 Speed up Mandelbrot by performing the computation directly instead of using Complex. Compare. Incorporate periodicity checking or boundary tracing for further improvements. Use divide-and-conquer: choose 4 corners of a rectangle and a few random points inside; if they're all the same color, color the whole rectangle that color; otherwise divide into 4 rectangles and recur.


 Mandelbrot_CK.java
 http://introcs.cs.princeton.edu/java/32class/Mandelbrot.java.html

 Look into the resources about Mandelbrot Set on this link: 3.2 Creating Data Types
 https://introcs.cs.princeton.edu/java/32class/


 Boundary tracing - Wikipedia
 https://en.wikipedia.org/wiki/Boundary_tracing
 Complex.java
 http://introcs.cs.princeton.edu/java/32class/Complex.java.html
 */

package com.jetbrains;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class Mandelbrot_CK
{
    /*
    INSTANCE FIELDS
     */
    //I decided to use these as variable definitions within non static methods so that I wouldn't have to change values within the methods for testing
    private int accuracy; //the number of random points inside the square I check
    private int max; //max number of iteration (this is convenient because if the point lies outside the set then its rgb value is between 0 and 255 which indicates how long it took for the complex number to escape)
    private int maxSide; //the width of the screen and the height(pixels)
    private double startX; //the furthest left x value calculated for the set (or a value for the complex number a+bi)
    private double endX; //furthest right x value calculated for the set (also the a value in a +bi)
    private double startY; //the greatest y value checked (the bi part in a +bi)
    private double endY; //the most negative y value checked (the bi part in a +bi)
    private double size; //the value which determines if the number is within the set or not (If the magnitude of the complex number exceeds 2 then it is outside the set)
    private Picture picture; //the picture object which I'm drawing onto

    private static double scale = 2.5;
    private static final int power = 2;
    private static double z0r = 0.355534;
    private static double z0i = -0.337292;
    /*
    private static double z0r = 0.355534;
    private static double z0i = -0.337292;

    private static double z0r = 0.37;
    private static double z0i = 0.1;

    private static double z0r = -.54;
    private static double z0i = .54;

     */
    //NOTE: Drawing onto a picture object is a lot like drawing with python graphics the upper left corner is 0,0 and the bottom left corner is (in this case) 1024,1024

    /*
    CONSTRUCTOR
     */
    private Mandelbrot_CK(int accuracy, int maxIterations, int maxSide, double startX, double endX, double startY, double endY, double size)
    {
        this.accuracy = accuracy;
        this.max = maxIterations;
        this.maxSide = maxSide;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.size = size;
        picture = new Picture(maxSide+1,maxSide+1); //I make the picture width and height one more than it should because picture objects work weirdly and I think it will take longer to do the recursion if I check the exceptions due to picture
    }
    private int inSetOrNot(int i, int j) //This takes two doubles representative of a complex number and determines weather or not it lies in the set after 255 iterations (which is max)
    {
        //convert the picture coordinate (pixel, pixel) to a complex number, where r0 is the initial real component and im0 is the initial imaginary component
        double r0 = startX + Math.abs(startX-endX)*i/maxSide;
        double im0 = startY - Math.abs(startY-endY)*j/maxSide;
        double real = r0;
        //double imaginary = im0;
        double imaginary = -im0;
        for(int c = 0; c<max; c++) //run max number of iteration to see if the complex number is in the set
        {
            if(Math.abs(Math.hypot(real,imaginary))>2) //the complex number is outside of the set (its magnitude is greater than 2
            {
                return 255; // max rgb value for color
            }
            //place holders to ensure that I perform the arithmetic in the correct sequence to manipulate the complex number in the proper manner
            /*double r1 = Math.pow(real,2)-Math.pow(imaginary,2)+r0; //square and add original for both
            double im1 = 2*real*imaginary+im0;*/
            double[] real_and_imaginary = calculateZ_to_the_N_power(real,imaginary,power);
            /*double r1 = real_and_imaginary[0]+z0r;//+r0
            double im1 = real_and_imaginary[1]+z0i;//+im0*/
            double r1 = real_and_imaginary[0]+r0;//+r0
            double im1 = real_and_imaginary[1]+im0;//+im0
            real = r1;
            //imaginary = im1;
            imaginary = -im1;
        }
        return 0; //the complex number lies in the set (it is black)
    }
    private static double[] calculateZ_to_the_N_power(double re, double im, int n)
    {
        double r = Math.sqrt((re*re)+(im*im));
        double r_N = Math.abs(Math.pow(r,n));
        double theta = Math.atan2(im,re);
        double realSub = Math.cos(n*theta);
        double imaginarySub = Math.sin(n*theta);
        double real = r_N*realSub;
        double imaginary = r_N*imaginarySub;
        return new double[] {real,imaginary};
    }
    /*
    Getter method (just for the first call in driver)
     */
    private int getMaxSide()
    {
        return maxSide;
    }

    /*
    This method checks a given region, at coordinate i, j (0 to
     */
    private void checkRegion(int i, int j, int side)
    {
        /*
        BASE CASE
         */
        if(side == 1) //just color in the 4 pixels if the side is only one pixel length long
        {
            //find weather or not the four pixels are in the set
            int color1 = inSetOrNot(i,j);
            int color2 = inSetOrNot(i,j+side);
            int color3 = inSetOrNot(i+side, j);
            int color4 = inSetOrNot(i+side, j+side);
            //color in the 4 pixels all the same color(not random so the outline is a nice red color)
            picture.set(i,j,new Color(color1,color1/4,color1/2));
            picture.set(i,j+side,new Color(color2,color2/4,color2/2));
            picture.set(i+side,j,new Color(color3,color3/4,color3/2));
            picture.set(i+side,j+side,new Color(color4,color4/4,color4/2));
            picture.show(); //show the picture after drawing something
            //after the base case happens the previous call on the stack will run to solve the other squares (back tracking)
        }
        else //IF NOT BASE CASE
        {
            /*
            BELOW CHECKS THE FOUR CORNERS, THE RANDOM POINTS INSIDE, THEN SEES IF THEY ARE ALL THE SAME
            |                                          |                                              |
            v                                          v                                              v
             */
            int[] colors = new int[accuracy + 4]; //create an array of ints which represent the values which are returned from mand 255 if outside the set 0 if inside the set

            //find weather or not the four corners of the square are in the set
            colors[colors.length - 1] = inSetOrNot(i, j);
            colors[colors.length - 2] = inSetOrNot(i+side, j+side);
            colors[colors.length - 3] = inSetOrNot(i+side,j);
            colors[colors.length - 4] = inSetOrNot(i,j+side);

            for (int x = 0; x < accuracy; x++) //iterates for the number of random points I want checked in between squares
            {
                int mini = i; //the minimum x coordinate that can be checked
                int maxi = (i + side); //the maximum x coordinate
                int maxj = (j + side); //the maximum y coordinate
                int minj = j; //the minimum y coordinate
                Random rand = new Random();
                int randI = rand.nextInt((maxi - mini) + 1) + mini;
                int randJ = rand.nextInt((maxj - minj) + 1) + minj;
                int rgb = inSetOrNot(randI, randJ);
                colors[x] = rgb;

            }
            boolean allTheSame = true; //a boolean which keeps track if they are all the same value
            for (int x = 0; x < colors.length - 1; x++) //start from the bottom but only go up to the final index-1 because you check the next one each iteration
            {
                if (!((colors[x]) == colors[x + 1]))
                {
                    allTheSame = false;
                    break; //if one is not the same, get out that's all that mattered, they are not all the same, move on no point in continuing to check
                }
            }
            /*
            ^                                          ^                                              ^
            |                                          |                                              |
            ABOVE CHECKS THE FOUR CORNERS, THE RANDOM POINTS INSIDE, THEN SEES IF THEY ARE ALL THE SAME
             */
            if (allTheSame && side != maxSide) //the points are all the same (they passed inspection)
            {
                Random rand = new Random();
                int randColor1 = rand.nextInt(3) + 1; //this just generates some nice random numbers to make the squares outside the set more colorful (you also get to see the sizes of which squares outside the set were colored)
                int randColor2 = rand.nextInt(3) + 1;
                int randColor3 = rand.nextInt(3) + 1;

                int rgb = colors[0]; //if the region is drawn, since all are in or out of the set, it doesn't matter from which index I choose
                picture.drawSquare(i, j, side, new Color(rgb/randColor1, rgb/randColor2, rgb/randColor3)); //draw a square which is that color, where i, j are the upper left coordinates of the square and the square extends out from that point
                picture.show(); //show the picture after having drawn something
            }
            else //if the square checked does not lie outside the set or not all within the set
            {
                //recur, but with smaller squares
                side /=2; //smaller squares (half size)
                checkRegion(i, j, side); //check the upper left section of the previous square
                checkRegion(i, j+side, side); //check the square below that (bottom left)
                checkRegion(i+side, j, side); //check the upper right square
                checkRegion(i+side, j+side, side); //check bottom right square
            }
        }
    }

    /*
    MAIN / Driver
     */
    public static void main(String[] args)
    {
        long startTime = System.nanoTime();
        Mandelbrot_CK mandel = new Mandelbrot_CK(50, 100,1024,-scale,scale, scale, -scale, 2); //50,500,1024,-1.5,-.5,1,-1,2
        mandel.checkRegion(0,0,mandel.getMaxSide()); //check the first square (the entire screen)
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1000000000); //seconds
        //2.826275593

    }
}
/*
Here's the link to a video: (This is much slower than it would run normally since Im recording the screen)
https://youtu.be/A33cmUWQdaY (You can watch in 4K!)

 */
