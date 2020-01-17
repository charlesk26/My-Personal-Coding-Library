package com.jetbrains;

import java.awt.*;

public class Mandel
{
    private static int mand(double x0, double y0, int max, double size)
    {
        double x = x0;
        double y = y0;
        for(int i = 0; i<max; i++)
        {
            if(Math.hypot(x,y)>size)
            {
                return i;
            }
            //place holders
            double x1 = Math.pow(x,2)-Math.pow(y,2)+x0; //cleaner?
            double y1 = 2*x*y+y0;

            x = x1;
            y = y1;
        }


        return 0;
    }



    public static void main(String[] args)
    {
        final double startX = Double.parseDouble(args[0]);
        final double endX = Double.parseDouble(args[1]);
        final double startY = Double.parseDouble(args[2]);
        final double endY = Double.parseDouble(args[3]);
        final double size = Double.parseDouble(args[4]);



        final int width = 1024;
        final int height = 1024;
        final int max = 100;
        int[] colors = new int[max];
        for (int i = 0; i<max; i++)
        {
            colors[i] = Color.HSBtoRGB((i/256f),1f,i/(i+8f));
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

    }
}

