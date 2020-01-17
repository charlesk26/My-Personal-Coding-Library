package com.jetbrains;

/**
 *  Charles Kolozsvary
 *  Period 2 Algorithms
 *  Java Version 4.2.1
 *  Java SDK: 12.01.0
 *  Description:
 *  I just made this because of what Mr.Sakurai did during the 12/16/19 smash ultimate direct
 */
public class PrintBinary
{
    public static void main(String[] args)
    {
        int[] array = sort(new int[]{1,5,6,3,5,7,8,9,2,3,4,5,6,7,1,100,20});
        for(int num: array)
        {
            System.out.println(num);
        }
        System.out.println("            This will help you count to 31 with one hand!\n     The secret? Using Binary: put down the finger for each number\n       indicated if there is a box, if there is no box leave it up\n         therefore 0 is an open hand and 31 is a closed fist\n\n");
        System.out.println("          THUMB     POINTER     MIDDLE      RING     PINKIE");
        System.out.println("            |          |          |          |          |  ");
        System.out.println("            V          V          V          V          V  ");
        for(int i = 1; i<32; i++)
        {
            String[] binaryString = Integer.toBinaryString(i).split(""); //split the binary string into an array of strings
            int[] binaryNum = new int[5];
            for(int x = 0, w = binaryString.length-1; w>-1; x++, w--) //start from the back to the front and parse back to int
            {
                binaryNum[x] = Integer.parseInt(binaryString[w]);
            }
            if(i>9)
            {
                System.out.println(i+":         "+printBoxOrNot(binaryNum[0])+"          "+printBoxOrNot(binaryNum[1])+"          "+printBoxOrNot(binaryNum[2])+"          "+printBoxOrNot(binaryNum[3])+"          "+printBoxOrNot(binaryNum[4])+"\n");
            }
            else
            {
                System.out.println(i+":          "+printBoxOrNot(binaryNum[0])+"          "+printBoxOrNot(binaryNum[1])+"          "+printBoxOrNot(binaryNum[2])+"          "+printBoxOrNot(binaryNum[3])+"          "+printBoxOrNot(binaryNum[4])+"\n");
            }
        }
    }

    private static String printBoxOrNot(int bin)
    {
        if(bin == 0)
        {
            return " ";
        }
        else
        {
            return 	"\u25A1";
        }
    }

    static int[] sort(int[] a)
    {
        int curr = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if(a[i] > a[j]) {
                    curr = a[i];
                    a[i] = a[j];
                    a[j] = curr;
                }
            }
        }
        return a;
    }
}
/*
Output:
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=51220:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Entire Coding Library/Algorithms and Data Structures quarter 2/out/production/Algorithms and Data Structures quarter 2" com.jetbrains.PrintBinary
            This will help you count to 31 with one hand!
     The secret? Using Binary: put down the finger for each number
       indicated if there is a box, if there is no box leave it up
         therefore 0 is an open hand and 31 is a closed fist


          THUMB     POINTER     MIDDLE      RING     PINKIE
            |          |          |          |          |
            V          V          V          V          V
1:          □

2:                     □

3:          □          □

4:                                □

5:          □                     □

6:                     □          □

7:          □          □          □

8:                                           □

9:          □                                □

10:                    □                     □

11:         □          □                     □

12:                               □          □

13:         □                     □          □

14:                    □          □          □

15:         □          □          □          □

16:                                                     □

17:         □                                           □

18:                    □                                □

19:         □          □                                □

20:                               □                     □

21:         □                     □                     □

22:                    □          □                     □

23:         □          □          □                     □

24:                                          □          □

25:         □                                □          □

26:                    □                     □          □

27:         □          □                     □          □

28:                               □          □          □

29:         □                     □          □          □

30:                    □          □          □          □

31:         □          □          □          □          □


Process finished with exit code 0

 */