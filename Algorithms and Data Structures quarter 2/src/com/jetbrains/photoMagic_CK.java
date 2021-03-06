package com.jetbrains;

import java.awt.*;

/*
Charles Kolozsvary
 Period 2 Algorithms
 Java Version 4.2.1
 Java SDK: 12.01.0
 Description:
 Write a program that produces pseudo-random bits by simulating a linear-feedback shift register, and then use it to implement a simple form of encryption for digital pictures.
‌
LFSR review. A linear-feedback shift register (LFSR) is a register of bits that performs discrete step operations that
‌
shifts the bits one position to the left and
replaces the vacated bit by the exclusive or (xor) of the bit shifted off and the bit previously at a given tap position in the register.
A LFSR has three parameters that characterize the sequence of bits it produces: the number of bits n, the initial seed (the sequence of
bits that initializes the register), and the tap position tap. As in the example in Lecture 0, the following illustrates one step of an
11-bit LFSR with initial seed 01101000010 and tap positions 9.
‌
NOTE: It doesn't have to be OOP.
 */

public class photoMagic_CK
{


    private static int[] convertColorToBinary(int color) //convert the base 10 decimal to binary and account for zeroes in front for smaller numbers
    {                                                       //each index of the array is one bit
        String[] sBinaryColor = Integer.toBinaryString(color).split(""); //split the binary string into an array of strings
        int[] binaryColor = new int[8];
        for(int x = binaryColor.length-1, w = sBinaryColor.length-1; w>-1; x--, w--) //start from the back to the front and parse back to int
        {
            binaryColor[x] = Integer.parseInt(sBinaryColor[w]);
        }
        return binaryColor;
    }
    private static int[][] convertRGBtoBinary(Picture picture, int i, int j) //returns a 2 dimensional array to express the converted rgb values in binary
    {
        int r = picture.get(i,j).getRed(); //get the respective integer color values from the picture at a pixel
        int g = picture.get(i,j).getGreen();
        int b = picture.get(i,j).getBlue();
        return new int[][] {convertColorToBinary(r), convertColorToBinary(g), convertColorToBinary(b)};
    }
    private static int[] xorColorAndBinary(int[] binaryColor, int[] key) //XOR the key with the binary color
    {
        int[] xor = new int[binaryColor.length];
        for(int i = 0; i<key.length; i++) //for each corresponding index
        {
            xor[i] = (binaryColor[i] ^ key[i]); // the ^ symbol is XOR
        }
        return xor;
    }
    private static int convertXORtoInt(int[] xor) //convert the xor between binary and key back to decimal base 10
    {
        StringBuilder concatenate = new StringBuilder();
        for(int val: xor)
        {
            concatenate.append(val); //concatenate to string then
        }
        return Integer.parseInt(String.valueOf(concatenate),2); //convert to parse with radix 2 to make binary back to decimal (binary is base 2)
    }
    private static Color xORandSet(int[][] binaryRGBs, int[] seed, int tap)
    {
        int[] xorRED = xorColorAndBinary(binaryRGBs[0], generateNewKey(seed, tap)); //assign the XOR between the 8 digit key (newly generated for each one) and the rgb converted into binary
        int[] xorGREEN = xorColorAndBinary(binaryRGBs[1], generateNewKey(seed, tap)); //for each color
        int[] xorBLUE = xorColorAndBinary(binaryRGBs[2], generateNewKey(seed, tap));
        return new Color(convertXORtoInt(xorRED), convertXORtoInt(xorGREEN),convertXORtoInt(xorBLUE));
    }
    private static int[] generateSeed(String stringSeed) //initializes the seed from the commandline argument
    {
        String[] seedStringList = stringSeed.split("");
        int[] seed = new int[seedStringList.length+1];
        for (int i = seedStringList.length - 1, x = 1; i > -1; i-- , x++) //start from back to beginning to orient the line right for linear feedback shift
        {
            seed[x] = Integer.parseInt(seedStringList[i]);
        }
        seed[0] = 0;
        return seed;
    }
    private static int[] generateNewKey(int[] seed, int tap) //generates a new key picking up from where the seed was last
    {
        int[] key = new int[8]; //this is size 8 because all the rgb values in binary can be expressed in 8 digits 255 = 11111111 3 = 00000011
        for(int i = 0; i<key.length; i++) //run for as many times as there are key digits (8 digits)
        {
            key[i] = (seed[seed.length - 1] ^ seed[tap]); //XOR
            seed[0] = key[i]; //set the first index to value extracted for code
            System.arraycopy(seed, 0, seed, 1, seed.length - 1); //SHIFT everything
        }
        return key;
    }
    public static void main(String[] args)
    {
        Picture picture = new Picture(args[0]);
        int[] seed = generateSeed(args[1]); //get the seed based on the commandline seed
        int tap = Integer.parseInt(args[2]); //get the tap value
        int[] key; //initialize the 8 digit LFSR generated code (no assignment)
        /*
        The for loop iterates over every pixel
        at each pixel the following is done:
        1. convert each color (r individually, g individually, etc) to binary
        2. store and send the converted colors as a 2D array
        3. iterate through each color(in binary) and XOR with a newly generated key
        4. return the product of XOR while also converting the 8 digit array back into an integer which is 0-255
        5. set the pixel at the point of iteration to the new rgb value received

        Step 2
        At each iteration, convert the rgb values to binary
         */
        //System.out.println(Integer.toBinaryString(43));
        for(int i = 0; i<picture.width(); i++) //go through every pixel
        {
            for(int j = 0; j<picture.height(); j++)
            {
                picture.set(i,j,xORandSet(convertRGBtoBinary(picture,i,j), seed, tap)); //at each pixel convert the pixel rgb values to binary, then xor them with a newly generated 8 digit code, convert the altered binary back to decimal (0-255) then re-color the pixel as rgb
            }
        }
        picture.show(); //show the picture once everything is done
        picture.save(args[0]); //creates a new picture with the change

    }
}
/*
OUTPUT:
Video Included: Link ----> https://youtu.be/jxF1XhiyGI4
What was run in command line during video:

first png demonstrated:
"totoroCute.png" "001000" "2"

Second:
"pipe.png" "01101000010100010000" "17"

:)
 */

