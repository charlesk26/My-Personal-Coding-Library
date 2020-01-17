package com.jetbrains;
/*
Charles Kolozsvary
 Period 2 Algorithms
 Java Version 4.2.1
 Java SDK: 12.01.0
 Description:
 Write a java program, LFSRSim_YI.java to generate an output like the one below for a given seed.
‌
Output:
01101000010
00001011001 25
01100100100 4
10010011110 30
01111011011 27
01101110010 18
11001011010 26
01101011100 28
01110011000 24
01100010111 23
01011111101 29
‌
NOTE: It doesn't have to be OOP.
 */
public class LFSRSim_CK {
    private int[] seed; //the array of ints which represent the seed
    private int tap; //the position that is XOR'd with the last element of the array

    public LFSRSim_CK(String seed, int tap) { //constructor
        String[] stringSeed = seed.split("");
        this.seed = new int[stringSeed.length + 1];
        for (int i = stringSeed.length - 1, x = 1; i > -1; i-- , x++)
        {
            this.seed[x] = Integer.parseInt(stringSeed[i]);
        }
        this.seed[0] = 0;
        this.tap = tap;
    }

    public int length() { //the size of the key/ seed
        return seed.length;
    }

    public int bitAt(int i) { //gives a 1 or 0, at any point in the seed
        return seed[i];
    }

    public String toString() { //prints the seed as understood (with the last element in the array to the far left)
        String output = "";
        for (int i = length()-1; i>0; i--) {

            output += "" + seed[i];
        }
        return output;
    }

    public int step() //do a single XOR computation with the last element and the tap position and return the result of the XOR also shift
    {
        seed[0] = (seed[length() - 1] ^ seed[tap]); //XOR
        for (int i = length() - 1; i > 0; i--) //shift
        {
            seed[i] = seed[i-1];
        }
        return seed[0];
    }

    public int generate(int k) //do multiple steps and compute a number based on the result of the XOR's with the last element in the array and the tap position
    {
        int[] extractedBits = new int[k];
        int num = 0;
        for(int i = 0; i<k; i++)
        {
            extractedBits[i] = (seed[length() - 1] ^ seed[tap]); //XOR for num/bit
            num = (num*2)+extractedBits[i]; //GENERATE NUMBER
            seed[0] = (seed[length() - 1] ^ seed[tap]); //XOR
            for (int x = length() - 1; x > 0; x--) //SHIFT everything
            {
                seed[x] = seed[x - 1];
            }
        }
        return num;
    }

    public static void main(String[] args)
    {
        LFSRSim_CK lfsr1 = new LFSRSim_CK("01101000010", 9);
        System.out.println(lfsr1);

        for (int i = 0; i < 30; i++) //thirty iterations done
        {
            int bit = lfsr1.step();
            System.out.println(lfsr1 + " " + bit);
        }
        System.out.println();

        LFSRSim_CK lfsr2 = new LFSRSim_CK("01101000010", 9);
        System.out.println(lfsr2);
        for (int i = 0; i < 10; i++)
        {
            int r = lfsr2.generate(5);
            System.out.println(lfsr2 + " " + r);
        }


    }
}
/*
OUTPUT:
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=49978:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Code/Algorithms Quarter 2/out/production/Algorithms Quarter 2" com.jetbrains.LFSRSim_CK
01101000010
11010000101 1
10100001011 1
01000010110 0
10000101100 0
00001011001 1
00010110010 0
00101100100 0
01011001001 1
10110010010 0
01100100100 0
11001001001 1
10010010011 1
00100100111 1
01001001111 1
10010011110 0
00100111101 1
01001111011 1
10011110110 0
00111101101 1
01111011011 1
11110110111 1
11101101110 0
11011011100 0
10110111001 1
01101110010 0
11011100101 1
10111001011 1
01110010110 0
11100101101 1
11001011010 0

01101000010
00001011001 25
01100100100 4
10010011110 30
01111011011 27
01101110010 18
11001011010 26
01101011100 28
01110011000 24
01100010111 23
01011111101 29

Process finished with exit code 0


 */