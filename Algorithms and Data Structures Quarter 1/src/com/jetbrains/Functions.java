package com.jetbrains;

public class Functions
{
    public static void main(String[] args)
    {
        double surfaceArea = 410.10;
        double r = Math.pow((surfaceArea/(6*Math.PI)), .5);
        double h = (surfaceArea/(2*Math.PI*r))-r;
        double volume = Math.PI*Math.pow(r,2)*h;
        System.out.print(volume);
    }
}
