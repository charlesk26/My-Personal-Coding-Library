package com.jetbrains;

/******************************************************************************
 *  Compilation:  javac ConwaysGameOfLife_CK.java
 *  Execution:    java ConwaysGameOfLife_CK n(integer for dimensions)
 *  Dependencies: StdDraw.java
 *  Period 1 Java MS.Elia
 *  Charles Kolozsvary
 *  1-11-18
 *  Program: ConwaysGameoLife_CK
 *  Description:
 *  Any live cell with fewer than two live neighbours dies (referred to as underpopulation or exposure[1]).
 Any live cell with more than three live neighbours dies (referred to as overpopulation or overcrowding).
 Any live cell with two or three live neighbours lives, unchanged, to the next generation.
 Any dead cell with exactly three live neighbours will come to life.

 Edmodo Stuff:
 Requirements:
 1. Take as input the size of the grid.
 2. Show the live cells in a color other than black.

 Challenges:
 1. Take as input the coordinates for the seed. You must include in your documentation the coordinates for the glider seed.
 2. Change the color of the dead cells to a faded color for at least one generation after dying.
 3. Take as input the option to speed or slow down the simulation.

 Submit your work here.
 Include a Readme.txt file with instructions on how to run the application.
 Don't forget the documentation.
 Also, include output or/and screen shot.

 *
 *
 ******************************************************************************/

public class ConwaysGameOfLife_CK
{
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]); //takes comand line n value to determine the dimensions/sclaing
        StdDraw.setCanvasSize(800,800); //set canvas size
        StdDraw.setXscale(1, n+1); //set veiwing scale
        StdDraw.setYscale(1, n+1);
        StdDraw.setPenColor(StdDraw.BLACK); //default pen color
        StdDraw.enableDoubleBuffering(); //smoother display/ faster processing
        int[][] proximitycounter = new int[n+2][n+2]; //variable for keeping track of how many live cells a block is surrounded by(+2 becuase of edge)
        boolean[][] dedoalive = new boolean[n+2][n+2]; //array keeping track if a cell is alive or ded(+2 because of edge)

        //initial cells which are alive
        //Glider GUN Glider GUN Glider GUN Glider GUN Glider GUN Glider GUN Glider GUN
        dedoalive[2][n-5] = true;//left block
        dedoalive[2][n-6] = true;
        dedoalive[3][n-5] = true;
        dedoalive[3][n-6] = true;

        dedoalive[11+1][n-5] = true;  // left semi circle
        dedoalive[11+1][n-6] = true;
        dedoalive[11+1][n-7] = true;

        dedoalive[12+1][n-4] = true;
        dedoalive[12+1][n-8] = true;
        dedoalive[13+1][n-3] = true;
        dedoalive[14+1][n-3] = true;
        dedoalive[13+1][n-9] = true;
        dedoalive[14+1][n-9] = true;

        dedoalive[15+1][n-6] = true; //little dot
        dedoalive[16+1][n-4] = true;
        dedoalive[16+1][n-8] = true;
        dedoalive[17+1][n-5] = true;
        dedoalive[17+1][n-6] = true;//right thing
        dedoalive[17+1][n-7] = true;
        dedoalive[18+1][n-6] = true;

        dedoalive[21+1][n-3] = true;
        dedoalive[21+1][n-4] = true;
        dedoalive[21+1][n-5] = true;
        dedoalive[22+1][n-3] = true;
        dedoalive[22+1][n-4] = true;
        dedoalive[22+1][n-5] = true; //weird right bug on its side
        dedoalive[23+1][n-2] = true;
        dedoalive[23+1][n-6] = true;
        dedoalive[25+1][n-1] = true;
        dedoalive[25+1][n-2] = true;
        dedoalive[25+1][n-6] = true;
        dedoalive[25+1][n-7] = true;

        dedoalive[35+1][n-3] = true;
        dedoalive[35+1][n-4] = true;
        dedoalive[36+1][n-3] = true;
        dedoalive[36+1][n-4] = true;

        //MAIN ANIMATION LOOP
        while (true)
        {
            //ALL THIS DOES IS LOOK AT EACH (LIVE / DEAD) CELL AND FIGURE OUT HOW MANY LIVE CELLS ARE AROUND IT
            for (int i = 1; i<n+1;i++)
            { //this line + one below goes through the board at each value(each cell)
                for(int x = 1; x<n+1; x++)
                {
                    if(dedoalive[i][x])
                    { //if one of them is alive...
                        for(int r = -1; r <= 1; r+=1)
                        { //runs for loop for all squares around the block
                            for(int r2 = -1; r2 <=1; r2+=1)
                            {
                                if(r == 0 && r2 == 0)
                                { //skips if 0,0 (dont need to count the cell initially picked)
                                    continue; //skips it i its 0,0
                                }
                                if(dedoalive[(i)+(r)][(x)+(r2)])
                                { //a cell around the block was alive
                                    proximitycounter[i][x]++; //counter goes up for number of live cells around that live cell
                                }
                            }
                        }
                    }
                    if(!dedoalive[i][x])
                    {//finds a ded cell
                        for(int r = -1; r <= 1; r+=1)
                        { //runs for loop for all squares around the block
                            for(int r2 = -1; r2 <=1; r2+=1)
                            {
                                if(r == 0 && r2 == 0)
                                { //skips if 0,0 (don't need to count the cell initially picked)
                                    continue;
                                }
                                if(dedoalive[(i)+(r)][(x)+(r2)])
                                { //a cell around the block was alive
                                    proximitycounter[i][x]++; //counter go up
                                }
                            }
                        }
                    }
                }
            }

            //kill em if they had a certain number round em
            for (int i = 1; i<n+1;i++)
            { //this line + one below goes through the block list
                for(int x = 1; x<n+1; x++)
                {
                    if((proximitycounter[i][x] < 2 || proximitycounter[i][x] > 3) && (dedoalive[i][x]))
                    {
                        dedoalive[i][x] = false; //kill it
                    }
                    if((proximitycounter[i][x] == 3) && (!dedoalive[i][x]))
                    {
                        dedoalive[i][x] = true; //rebirth
                    }
                }
            }
            //reset numbers
            for (int i = 1; i<n+1;i++)
            { //this line + one below goes through the proximity counter list
                for(int x = 1; x<n+1; x++)
                {
                    proximitycounter[i][x] = 0;
                }
            }

            //plot grid/ clear state
            StdDraw.clear(StdDraw.DARK_GRAY);
            StdDraw.setPenColor(StdDraw.BLACK);
            for (int i = 0; i <= n+2; i++)
            {
                StdDraw.line(0,i,n+2, i);
            }
            for (int x = 0; x <= n+2; x++)
            {
                StdDraw.line(x, 0, x, n+2);
            }

            //draw the alive squares:
            StdDraw.setPenColor(StdDraw.CYAN);
            for (int x = 1; x <n+1; x++)
            {
                for (int y = 1; y < n+1; y++)
                {
                    if (dedoalive[x][y])
                    {
                        StdDraw.filledSquare(x+.5,y+.5,.499999);
                    }
                }
            }
            // copy offscreen buffer to onscreen
            StdDraw.show();

            // pause for 35 ms
            StdDraw.pause(10);
        }
    }
}
/*
Different presets here
//Glider GUN Glider GUN Glider GUN Glider GUN Glider GUN Glider GUN Glider GUN
        dedoalive[2][n-5] = true;//left block
        dedoalive[2][n-6] = true;
        dedoalive[3][n-5] = true;
        dedoalive[3][n-6] = true;

        dedoalive[11+1][n-5] = true;  // left semi circle
        dedoalive[11+1][n-6] = true;
        dedoalive[11+1][n-7] = true;

        dedoalive[12+1][n-4] = true;
        dedoalive[12+1][n-8] = true;
        dedoalive[13+1][n-3] = true;
        dedoalive[14+1][n-3] = true;
        dedoalive[13+1][n-9] = true;
        dedoalive[14+1][n-9] = true;

        dedoalive[15+1][n-6] = true; //little dot
        dedoalive[16+1][n-4] = true;
        dedoalive[16+1][n-8] = true;
        dedoalive[17+1][n-5] = true;
        dedoalive[17+1][n-6] = true;//right thing
        dedoalive[17+1][n-7] = true;
        dedoalive[18+1][n-6] = true;

        dedoalive[21+1][n-3] = true;
        dedoalive[21+1][n-4] = true;
        dedoalive[21+1][n-5] = true;
        dedoalive[22+1][n-3] = true;
        dedoalive[22+1][n-4] = true;
        dedoalive[22+1][n-5] = true; //weird right bug on its side
        dedoalive[23+1][n-2] = true;
        dedoalive[23+1][n-6] = true;
        dedoalive[25+1][n-1] = true;
        dedoalive[25+1][n-2] = true;
        dedoalive[25+1][n-6] = true;
        dedoalive[25+1][n-7] = true;

        dedoalive[35+1][n-3] = true;
        dedoalive[35+1][n-4] = true;
        dedoalive[36+1][n-3] = true;
        dedoalive[36+1][n-4] = true;


//PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17  PULSAR 17
        dedoalive[5][3] = true;
        dedoalive[6][3] = true;
        dedoalive[7][3] = true;
        dedoalive[5][8] = true;
        dedoalive[6][8] = true;  // bottom left
        dedoalive[7][8] = true;
        dedoalive[3][5] = true;
        dedoalive[3][6] = true;
        dedoalive[3][7] = true;
        dedoalive[8][5] = true;
        dedoalive[8][6] = true;
        dedoalive[8][7] = true;

        dedoalive[10][5] = true;
        dedoalive[10][6] = true;
        dedoalive[10][7] = true;
        dedoalive[11][3] = true;
        dedoalive[12][3] = true;
        dedoalive[13][3] = true; //bottom right
        dedoalive[11][8] = true;
        dedoalive[12][8] = true;
        dedoalive[13][8] = true;
        dedoalive[15][5] = true;
        dedoalive[15][6] = true;
        dedoalive[15][7] = true;

        dedoalive[5][10] = true;
        dedoalive[6][10] = true;
        dedoalive[7][10] = true;
        dedoalive[5][15] = true;
        dedoalive[6][15] = true;  // top left
        dedoalive[7][15] = true;
        dedoalive[3][11] = true;
        dedoalive[3][12] = true;
        dedoalive[3][13] = true;
        dedoalive[8][11] = true;
        dedoalive[8][12] = true;
        dedoalive[8][13] = true;

        dedoalive[10][11] = true;
        dedoalive[10][12] = true;
        dedoalive[10][13] = true;
        dedoalive[11][10] = true;
        dedoalive[12][10] = true;
        dedoalive[13][10] = true; //bottom right
        dedoalive[11][15] = true;
        dedoalive[12][15] = true;
        dedoalive[13][15] = true;
        dedoalive[15][11] = true;
        dedoalive[15][12] = true;
        dedoalive[15][13] = true;
 */
/*
  Give a command line argument of any n integer:
  OUTPUT:
  UNABLE TO BE DISPLAYED: SCREEN SHOT PROVIDED(when command line arg passed was 17)
 */