package com.jetbrains;

public class TestingClass
{
    static double getVel(int work)
    {
        return Math.sqrt(2*work/300);
    }
    public static void main(String[] args)
    {

        int n = 11;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        for (int i = 0; i < n; i++)
        {
            //System.out.println("i: "+i);
            count1++;
            for (int j = i+1; j < n; j++)
            {
                //System.out.println("\nj: "+j);
                count2++;
                for (int k = j+1; k < n; k++)
                {
                    //System.out.println("\nk: "+k);
                    count3++;
                }
            }

        }
        //System.out.println(count1+" "+count2+" "+count3);
        //System.out.println((n*(n-1)*(n-2))/6);
        for(int i = 1000; i<17000; i+= 1000)
        {
            System.out.println(getVel(i));
        }
    }
}
