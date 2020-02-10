package com.jetbrains;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Mandel
{
    public static int power = -2; //for fun stuff change this ints and + or -
    public static final double scale = 2.5;
    private static double z0r = -0.8;
    private static double z0i = 0.156;
    /*
    private static double z0r = 0.355534;
    private static double z0i = -0.337292;

    private static double z0r = 0.37;
    private static double z0i = 0.1;

    private static double z0r = -.54;
    private static double z0i = .54;

    private static double z0r = .355;
    private static double z0i = .355;

    private static double z0r = -.4;
    private static double z0i = -.59;

    private static double z0r = .34;
    private static double z0i = -.05;

    private static double z0r = 0.0;
    private static double z0i = -0.8;

    private static double z0r = -0.4;
    private static double z0i = 0.6;

    //very cool
    private static double z0r = -0.8;
    private static double z0i = 0.156;

    power = 3;
    private static double z0r = -1;
    private static double z0i = 0;

    private static double z0r = .285;
    private static double z0i = .01;

    private static double z0r = 0.3;
    private static double z0i = 0.7;

    private static double z0r = 0.5;
    private static double z0i = 0.4;


     */
    private static int mand(double x0, double y0, int max, double size)
    {
        double x = x0;
        //double y = -y0; //for the other thing make this negative
        double y = y0; //for the other thing make this negative
        for(int i = 0; i<max; i++)
        {
            if(Math.hypot(x,y)>size)
            {
                return i;
            }
            //place holders
            double x1 = Math.pow(x,2)-Math.pow(y,2)+x0; //cleaner?
            double y1 = 2*x*y+y0;
            double[] realIm = calculateZ_to_the_N_power(x,y,power);


            /*double real1 = realIm[0]+z0r;
            double imaginary1 = realIm[1]+z0i;*/
            double real1 = realIm[0]+x0;
            double imaginary1 = realIm[1]+y0;

            x = real1;
            //y = -imaginary1; ///and make this negative too
            y = imaginary1; ///and make this negative too
        }


        return 0;
    }
    private static double[] calculateZ_to_the_N_power(double re, double im, int n)
    {
        double r = Math.sqrt((re*re)+(im*im)); //got this formula from
        double r_N = Math.pow(r,n);
        double theta = Math.atan2(im,re);
        double realSub = Math.cos(theta*n);
        double imaginarySub = Math.sin(theta*n);
        return new double[] {r_N*realSub,r_N*imaginarySub};
    }



    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        long startTime = System.nanoTime();
        final double startX = -scale;
        final double endX = scale;
        final double startY = scale;
        final double endY = -scale;
        final double size = 4;
        final float saturation = .8f;
        final int width = 1000;
        final int height = 1000;
        final int max = 100; //number of iterations how many times you do the function
        int[] colors = new int[max];
        for (int i = 0; i<max; i++)
        {
            colors[i] = Color.HSBtoRGB((i/256f),saturation,i/(i+8f));
        }

        Picture picture = new Picture(width,height);

        for(int i = 0; i<width; i++)
        {
            for(int j = 0; j<height; j++)
            {
                double x0 = startX + Math.abs(startX-endX)*i/width;
                double y0 = startY - Math.abs(startY-endY)*j/height;

                picture.setRGB(i,j,colors[mand(x0,y0,max,size)]);
                picture.show();
            }
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1000000000);


        //picture.save("Time (seconds): "+(double)totalTime/1000000000+" scale: "+scale+" c: "+z0r+"+"+z0i+"i saturation "+saturation+"f iterations: "+max+" size: "+size+"-Julia z^2+c.png");



    }
}

