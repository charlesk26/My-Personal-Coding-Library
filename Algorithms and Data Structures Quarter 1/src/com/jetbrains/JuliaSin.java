package com.jetbrains;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class JuliaSin
{
    public static int power = 2; //for fun stuff change this ints and + or -
    public static final double scale = Math.PI*2;
    private static double c_r = 1;
    private static double c_i = -.2;

    /*
    private static double c_r = 0;
    private static double c_i = 1.5;

    private static double c_r = 0.984808;
    private static double c_i = 0.173648;

    private static double c_r = -1.29904;
    private static double c_i = -0.75;

    private static double c_r = 1.87939;
    private static double c_i = 0.68404;

    private static double c_r = 1.17462;
    private static double c_i = 0.427525;

    private static double c_r = -.2;
    private static double c_i = 1;

    private static double c_r = 1;
    private static double c_i = 0;

    private static double c_r = 0;
    private static double c_i = 1;
     */
    private static int julia_cSinZ(double x0, double y0, int max, double size)
    {
        double x = x0;
        double y = y0; //for the other thing make this negative
        for(int i = 0; i<max; i++)
        {
            if(Math.hypot(x,y)>=size)
            {
                return i;
            }
            //double[] realIm = calculateZ_to_the_N_power(x,y,power);
            double[] c = {c_r,c_i};
            double[] sinZ = calculate_sinz(x,y);
            double[] cSinZ = multiply(sinZ,c);
            x = cSinZ[0];
            y = cSinZ[1]; ///and make this negative too
        }


        return 0;
    }
    private static int julia_cSin_squaredZ(double x0, double y0, int max, double size)
    {
        double x = x0;
        double y = y0; //for the other thing make this negative
        for(int i = 0; i<max; i++)
        {
            if(Math.hypot(x,y)>=size)
            {
                return i;
            }
            //double[] realIm = calculateZ_to_the_N_power(x,y,power);
            double[] c = {c_r,c_i};
            double[] sinZ = calculate_sinz(x,y);
            double[] cosZ = calculate_cosz(x,y);
            double[] tanZ = divide(sinZ,cosZ);
            //double[] sin_Squared_Z = calculateZ_to_the_N_power(sinZ[0],sinZ[1],1);
            //double[] c_sin_Squared_Z = multiply(sin_Squared_Z,c);
            double[] cTanZ = multiply(tanZ,c);
            /*x = c_sin_Squared_Z[0];
            y = c_sin_Squared_Z[1]; ///and make this negative too*/
            x = cTanZ[0];
            y = cTanZ[1]; ///and make this negative too

        }


        return 0;
    }
    private static double[] calculateZ_to_the_N_power(double re, double im, int n)
    {
        double r = Math.sqrt((re*re)+(im*im));
        double r_N = Math.pow(r,n);
        double theta = Math.atan2(im,re);
        double realSub = Math.cos(theta*n);
        double imaginarySub = Math.sin(theta*n);
        double real = r_N*realSub;
        double imaginary = r_N*imaginarySub;
        return new double[] {real,imaginary};
    }
    private static double[] calculate_sinz(double re, double im)
    {
        double real = Math.sin(re)*Math.cosh(im);
        double imaginary = Math.cos(re)*Math.sinh(im);
        return new double[] {real,imaginary};
    }
    private static double[] calculate_cosz(double re, double im)
    {
        double real = Math.cos(re)*Math.cosh(im);
        double imaginary = -Math.sin(re)*Math.sinh(im);
        return new double[] {real,imaginary};
    }
    private static double[] multiply(double[] this0, double[] that)
    {
        double real = this0[0] * that[0] - this0[1] * that[1];
        double imag = this0[0] * that[1] + this0[1] * that[0];
        return new double[]{real,imag};
    }
    private static double[] divide(double[] this0, double[] that)
    {
        double real = this0[0] * 1/that[0] - this0[1] * 1/that[1];
        double imag = this0[0] * 1/that[1] + this0[1] * 1/that[0];
        return new double[]{real,imag};
    }



    public static void main(String[] args)
    {
        //x = -1.201171875, y = -0.963541666666666
        long startTime = System.nanoTime();
        final double startX = -scale;
        final double endX = scale;
        final double startY = scale;
        final double endY = -scale;
        final double size = 10000;
        final double saturation = -3.3;
        final int width = 1000;
        final int height = 1000;
        final int max = 200;
        int[] colors = new int[max];
        for (int i = 0; i<max; i++)
        {
            colors[i] = Color.HSBtoRGB((i/256f),(float)saturation,i/(i+8f));
        }
        Picture picture = new Picture(width,height);




        for(int i = 0; i<width; i++)
        {
            for(int j = 0; j<height; j++)
            {
                double x0 = startX + Math.abs(startX-endX)*i/width;
                double y0 = startY - Math.abs(startY-endY)*j/height;

                picture.setRGB(i,j,colors[julia_cSin_squaredZ(x0,y0,max,size)]);
                picture.show();
            }
        }
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        Scanner scan = new Scanner(System.in);
        System.out.println("save?");
        String yesno = scan.nextLine();
        if(yesno.equals("y"))
        {
            picture.save("Time (seconds): "+(double)totalTime/1000000000+" scale: "+scale+" c: "+c_r+"+"+c_i+"i saturation "+saturation+"f iterations: "+max+" size: "+size+"-Julia z^2+c.png");
        }
        else
        {
            System.out.println("ok");
        }

    }
}

