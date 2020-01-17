package com.jetbrains;
/*
 * Charles Kolozsvary
 * Period 2 Algorithms
 * 9-11-19
 * Java Version 4.2.1
 * Description:
 * Create an abstract data type EuclideanPoint EuclideanPoint_YI.java that represents a d-dimensional point.
 1. Write a method so that p.distanceTo(q) returns the Euclidean distance between points p and q.
 2. Write a method so that p.midPoint(q) returns the Euclidean mid-point between points p and q.
 Include a test class with at least three pair of points with different dimensions.
 3. Turn in the paper with few examples using illustrations and computations.
 NOTE: You can work with a partner but each of you turn in your paper with your name on it.
 *
 */

/**
 * imports:
 */
import java.util.Arrays;
import java.util.ArrayList;

public class EuclideanPoint_CK
{
    private ArrayList<Double> points; //instance field
    /**
     * CONSTRUCTOR
     */
    public EuclideanPoint_CK(ArrayList<Double> points)
    {
        this.points = points;
    }

    /**
     * GETTER METHODS
     */
    public int getSize()
    {
        return points.size();
    }

    public ArrayList<Double> getPoints()
    {
        return points;
    }

    /**
     * SETTER METHOD
     */
    public void setNewPoint(ArrayList<Double> p)
    {
        this.points = p;
    }

    /**
     * DISTANCE METHOD
     */
    public double distanceTo(EuclideanPoint_CK q)
    {
        double d = 0;
        if(q.getSize() != points.size()) //checks if they do not have the same dimensions
        {
            if(q.getSize() < points.size())
            {
                for(int i = 0; i< q.getSize(); i++)
                {
                    d += Math.pow(points.get(i) - q.getPoints().get(i), 2);
                }
                for(int i = q.getSize(); i<points.size(); i++)
                {
                    d += Math.pow(points.get(i) , 2);
                }
                return d;
            }
            else
            {
                for(int i = 0; i < points.size(); i++)
                {
                    d += Math.pow(points.get(i) - q.getPoints().get(i), 2);
                }
                for(int i = points.size(); i<q.getSize(); i++)
                {
                    d += Math.pow(q.getPoints().get(i), 2);
                }
                return d;
            }
        }

        else
        {
            for(int i = 0; i<q.getSize(); i++)
            {
                d += Math.pow(points.get(i) - q.getPoints().get(i), 2);
            }
            d = Math.sqrt(d);
            return d;
        }
    }

    /**
     * MIDPOINT METHOD
     */
    public double[] midPoint(EuclideanPoint_CK q) //calculates the midpoint
    {
        if(q.getSize() != points.size()) //checks if they do not have the same dimensions
        {
            if(q.getSize() < points.size()) //if one is larger than the other
            {
                double[] p = new double[points.size()];
                for(int i = 0; i< q.getSize(); i++)
                {
                    p[i] += (points.get(i) + q.getPoints().get(i)) /2;
                }
                for(int i = q.getSize(); i<points.size(); i++)
                {
                    p[i] += (points.get(i) / 2);
                }
                return p;
            }
            else
            {
                double[] p = new double[q.getSize()];
                for(int i = 0; i < points.size(); i++)
                {
                    p[i] += (points.get(i) + q.getPoints().get(i)) /2;
                }
                for(int i = points.size(); i<q.getSize(); i++)
                {
                    p[i] += (q.getPoints().get(i) / 2);
                }
                return p;
            }
        }
        else
        {
            double[] p = new double[points.size()];
            for(int i = 0; i<q.getSize(); i++)
            {
                p[i] += (points.get(i) + q.getPoints().get(i)) /2;
            }
            return p;
        }
    }

    /**
     * STATIC METHODS (help in main (looks cleaner I guess)
     */
    public static void printDoubleArray(double[] output) //static method which does the same as the one below just that an array is given and I print the array
    {
        System.out.print("\nMidpoint: ");
        for(int i = 0, j = output.length-1; i<output.length; i++)
        {
            if(i == j)
            {
                System.out.print(output[i]+"\n\n\n");
            }
            else
            {
                System.out.print(output[i]+", ");
            }
        }
    }

    public static void printDoubleArrayList(EuclideanPoint_CK input) //static method allows me to print the d-dimensional point held as an array list within the EuclideanPoint ADT
    {
        for(int i = 0, j = input.getPoints().size()-1; i<input.getPoints().size(); i++)
        {
            if(i == j)
            {
                System.out.print(input.getPoints().get(i)+"\n");
            }
            else
            {
                System.out.print(input.getPoints().get(i)+", ");
            }
        }
    }

    public static void printDouble(double print) //i just felt like not writing System.out.print line for one of the print statements
    {
        System.out.print("Distance: "+print+" ");
    }

    /**
     * MAIN
     */
    public static void main(String[] args)
    {
        EuclideanPoint_CK p = new EuclideanPoint_CK(new ArrayList<>(Arrays.asList(1.0,2.0,3.0,5.0,9.0,120.5))); //initialize ADTs's EuclideanPoint
        EuclideanPoint_CK q = new EuclideanPoint_CK(new ArrayList<>(Arrays.asList(3.0,4.0,5.0,6.0,7.0,8.0)));
        System.out.print("Test Case 1 --> 6 dimensions \n");
        System.out.print("Point p: ");   //display points p and q
        printDoubleArrayList(p);
        System.out.print("Point q: ");
        printDoubleArrayList(q);
        printDouble(p.distanceTo(q));    //print the information, distance and midpoint
        printDoubleArray(p.midPoint(q));


        p.setNewPoint(new ArrayList<>(Arrays.asList(100.0,200.0,10.0))); //set the ADT's to a new point
        q.setNewPoint(new ArrayList<>(Arrays.asList(50.0,200.0,0.0)));
        System.out.println("Test Case 2 --> 3 dimensions");
        System.out.print("Point p: ");   //display points p and q
        printDoubleArrayList(p);
        System.out.print("Point q: ");
        printDoubleArrayList(q);
        printDouble(p.distanceTo(q));    //print the information, distance and midpoint
        printDoubleArray(p.midPoint(q));

        p.setNewPoint(new ArrayList<>(Arrays.asList(3.0,4.0,18.0,21.0,45.0,34.0,40.0,876.0,986.0,23.0,5.0))); //set the ADTs's to a new point
        q.setNewPoint(new ArrayList<>(Arrays.asList(7.0,5.0,88.0,81.0,75.0,24.0,30.0,-176.0,-986.0,-23.0,-5.0)));
        System.out.println("Test Case 3 --> 11 dimensions");
        System.out.print("Point p: ");   //display points p and q
        printDoubleArrayList(p);
        System.out.print("Point q: ");
        printDoubleArrayList(q);
        printDouble(p.distanceTo(q));
        printDoubleArray(p.midPoint(q));

        p.setNewPoint(new ArrayList<>(Arrays.asList(100.0,100.0))); //set the ADT's to a new point
        q.setNewPoint(new ArrayList<>(Arrays.asList(101.0,101.0,1.0,-1.0)));
        System.out.println("Test Case 4 --> (non equal dimensions) 2,4");
        System.out.print("Point p: ");   //display points p and q
        printDoubleArrayList(p);
        System.out.print("Point q: ");
        printDoubleArrayList(q);
        printDouble(p.distanceTo(q));    //print the information, distance and midpoint
        printDoubleArray(p.midPoint(q));
    }
}
/*
OUTPUT:

 Test Case 1 --> 6 dimensions
Point p: 1.0, 2.0, 3.0, 5.0, 9.0, 120.5
Point q: 3.0, 4.0, 5.0, 6.0, 7.0, 8.0
Distance: 112.57553020083894
Midpoint: 2.0, 3.0, 4.0, 5.5, 8.0, 64.25


Test Case 2 --> 3 dimensions
Point p: 100.0, 200.0, 10.0
Point q: 50.0, 200.0, 0.0
Distance: 50.99019513592785
Midpoint: 75.0, 200.0, 5.0


Test Case 3 --> 11 dimensions
Point p: 3.0, 4.0, 18.0, 21.0, 45.0, 34.0, 40.0, 876.0, 986.0, 23.0, 5.0
Point q: 7.0, 5.0, 88.0, 81.0, 75.0, 24.0, 30.0, -176.0, -986.0, -23.0, -5.0
Distance: 2237.7044040712794
Midpoint: 5.0, 4.5, 53.0, 51.0, 60.0, 29.0, 35.0, 350.0, 0.0, 0.0, 0.0


Test Case 4 --> (non equal dimensions) 2,4
Point p: 100.0, 100.0
Point q: 101.0, 101.0, 1.0, -1.0
Distance: 4.0
Midpoint: 100.5, 100.5, 0.5, -0.5



 */

