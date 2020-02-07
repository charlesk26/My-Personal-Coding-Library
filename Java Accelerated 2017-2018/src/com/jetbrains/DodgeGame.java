package com.jetbrains;

/**
 * Charles Kolozsvary
 * Period 1 ELIA
 * GAME_CK
 * 5-10-17
 * Blue Jay Version 4.1.2 java version 1.8.0_144
 * Description:
 * A driver class that uses supporting ADT's Balls and SafeZone which allows for a player to
 * control a ball, and tries to get to the safe zones displayed
 * As the plauyer gets to the safe zone, the score increases and a ball is added randomly to the
 * arena aloing with a new random location for the safe zone
 * EDMODO POST:
 * Web Exercise 46: Game. Implement the game dodge using StdDraw: move
 * a blue disc within the unit square to touch a randomly placed green disc
 * , while avoiding the moving red discs. After each touch, add a new moving
 * red disc. Implement your Doge Game using OOD.
 */
import java.awt.Font; //import fonts
import java.util.ArrayList; //import array list
import java.util.Scanner; //imports scanner
public class DodgeGame
{
    public static void main(String args[])
    {
        //the following is a set off numbers that can be used as keycodes to detect keyboard input --> useful for later in another program
        //40 is down
        //37 is left
        //tab is 9
        //up is 38
        //right is 39
        //enter is 10
        //shift is 20
        boolean yesno = true; //boolean for resetting game--> wether the player wants to restart or not
        int score = 0; //player score
        double xPos; //position off player
        double yPos;//position off player
        boolean running = true;
        StdDraw.setCanvasSize(1000,1000); //canvas size
        StdDraw.setXscale(-10,10); //setting scale of canvas
        StdDraw.setYscale(-10,10);
        StdDraw.enableDoubleBuffering(); //drawing --> smoother image //
        ArrayList<Balls> balls = new ArrayList<>(); //create Array List of Balls
        SafeZone safe = new SafeZone(SafeZone.getRandom(), SafeZone.getRandom()); //create safeZone Object
        Font f = new Font("Arial", Font.BOLD, 70); //create Font ffor score
        StdDraw.setFont(f); //initialize font
        while(running)
        {

            //COMPUTAION COMPUTATION COMPUTAION COMPUTATION COMPUTAION COMPUTATION COMPUTAION COMPUTATION COMPUTAION COMPUTATION COMPUTAION COMPUTATION

            //get player coordinates
            xPos = StdDraw.mouseX();
            yPos = StdDraw.mouseY();
            //move all the balls
            for(int i = 0; i<balls.size(); i++)
            {
                if (Math.abs(balls.get(i).getRX() + balls.get(i).getVX()) > 10-.479){ //when the square goes to the bounds of the scaling(a little before but whatever)(hits the sides)
                    balls.get(i).setVX(-balls.get(i).getVX()); //lateral movement reversed
                }
                if (Math.abs(balls.get(i).getRY() + balls.get(i).getVY()) > 10-.479) { //when the square hits the top/bottom
                    balls.get(i).setVY(-balls.get(i).getVY()); //now its horizontal movement is reversed
                }
                // update position
                balls.get(i).setRX(balls.get(i).getRX() + balls.get(i).getVX());
                balls.get(i).setRY(balls.get(i).getRY() + balls.get(i).getVY());
            }

            //check if player got a safe zone
            if(safe.Collide(xPos, yPos, safe, .5))
            {
                balls.add(new Balls(0,0));
                score++;
                safe.setNewZone(Balls.getRandom(xPos, yPos, 1.225), Balls.getRandom(xPos, yPos, 1.225));
            }

            //go through balls and check if collide with player and also reset game if they do collide
            for(int i = 0; i<balls.size(); i++)
            {
                if(balls.get(i).Collide(xPos, yPos, balls.get(i), .5)) //if a bouncing ball collides with player
                {
                    running = false;
                    yesno = true;
                    while(yesno)
                    {
                        if (StdDraw.isKeyPressed(10)) //reset game
                        {
                            running = true;
                            yesno = false;
                            balls.clear();
                            score = 0;
                            safe.setNewZone(Balls.getRandom(xPos, yPos, 1.225), Balls.getRandom(xPos, yPos, 1.225));
                        }
                    }
                }
            }

            //DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING DRAWING

            //clear board
            StdDraw.clear(StdDraw.DARK_GRAY);

            //draw safeZone
            StdDraw.setPenColor(100,150,220);
            StdDraw.filledCircle(safe.getxPos(), safe.getyPos(), safe.getRadius());

            //draw player
            StdDraw.setPenColor(10,150,10);
            StdDraw.filledCircle(xPos, yPos, .5);

            //draws balls
            StdDraw.setPenColor(255,198,122);
            for(int i = 0; i<balls.size(); i++)
            {
                StdDraw.filledCircle(balls.get(i).getRX(), balls.get(i).getRY(), .175);
            }

            //Draw Score
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(safe.getxPos()-.04, safe.getyPos()-.2, ""+score);

            //pause
            StdDraw.pause(15);

            //show everything
            StdDraw.show();
        }
    }
}
/*
 * No OUTPUT
 * VIDEO ATTACHED OF GAMEPLAY
 */