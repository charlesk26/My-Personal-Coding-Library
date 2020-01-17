package com.jetbrains;

import java.util.ArrayList; //import array lists and fonts
import java.awt.Font;
/**
 *
 * Charles Kolozsvary
 * Period 7 Elias
 * AP Computer Science
 * Java Version 4.2.1
 * Date: June 10th 2019
 * Description:
 * I am making a game called Achtung do Kurve
 * Its pretty fun
 */
/*
This class --> ACHTUNG handles the drawing of the game and the interactions between player, and trial objects
MAIN CLASS ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG ACHTUNG
 */
public class Achtung
{
    //main driver program
    public static void main(String[] args)
    {
        boolean running = false; //boolean keeping track if the main game is running
        boolean roundGoin = true; //if the game round is currently in session
        boolean gameStart = false; //if the game started yet, false if its at the title menue
        final int border = 42; //the border for the game(not title)

        //STD DRAW CONSTANTS
        final int xScale2 = 50;
        final int yScale2 = 50;
        final int xScale1 = -50;
        final int yScale1 = -50;
        final int canvasSize2 = 1000;
        final int canvasSize1 = 1500;

        //STD DRAW METHODS SETTING UP THE ANIMATION
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(canvasSize2,canvasSize2);
        StdDraw.setXscale(xScale1,xScale2);
        StdDraw.setYscale(yScale1,yScale2);
        StdDraw.setPenRadius(.013);

        //SET UP TITLE SCREEN
        titleScreen();

        //initialize players
        Player p1 = new Player();
        Player p2 = new Player();

        //SET UP FONT
        Font font = new Font("TimesNewRoman", Font.PLAIN, 50);
        while(true)
        {
            //ANIMATION
            runDemo(p1,p2); //run the demo in the title screen
            if(StdDraw.isKeyPressed(10)) //if they decide to start playing the game from the title screen
            {
                p1.reset(); //reset the players
                p2.reset();
                gameStart = true;  //the game has started
                running = true; // now the main game is running
                StdDraw.setFont(font); // set the font to the in game font
                setUp(border, p1.getScore(), p2.getScore()); //set up the in game border and score disply
            }
            while(running) //while they are playing the actual game not the title screen
            {
                if(!roundGoin) //if the round ended
                {
                    if(StdDraw.isKeyPressed('Q')) //quit the game
                    {
                        running = false;
                        System.exit(35);
                    }
                    if(StdDraw.isKeyPressed(10)) //continue to the next round
                    {
                        setUp(border, p1.getScore(), p2.getScore());
                        roundGoin = true;
                    }
                }

                //MAIN GAME LOOP
                while(roundGoin) //the game is currenly being played, the round is still going and the players are moving
                {
                    p1.tick(); //increment the tick
                    p2.tick();

                    //ANIMATION
                    if(!p1.split()) //draw the player if they are not splitting
                    {
                        StdDraw.setPenColor(StdDraw.CYAN); //set to blue color
                        StdDraw.filledCircle(p1.getXpos(), p1.getYpos(), p1.getRadius()); //draw a circle
                    }
                    if(!p2.split()) //draw the player if they are not splitting
                    {
                        StdDraw.setPenColor(255,50,100); //set to red color
                        StdDraw.filledCircle(p2.getXpos(), p2.getYpos(), p2.getRadius()); //draw a circle
                    }

                    //movement
                    if(StdDraw.isKeyPressed(37)) //37 is the keycode for left arrow
                    {
                        p1.left(); //move the player to the left (same speed and constantly moving)
                    }
                    if(StdDraw.isKeyPressed('A')) //if they press the 'A' button
                    {
                        p2.left(); //move the player to the RIGHT (same speed and constantly moving)
                    }
                    if(StdDraw.isKeyPressed('S')) //if they press the 'S' button
                    {
                        p2.right(); //move the player to the left (same speed and constantly moving)
                    }
                    if(StdDraw.isKeyPressed(39)) //37 is the keycode for right arrow
                    {
                        p1.right(); //move the player to the RIGHT (same speed and constantly moving)
                    }

                    p1.move(p1.split()); //Move the player forward, and do so differently depending on if they are currently splitiing
                    p2.move(p2.split()); //REMEMBER THE PLAYER CONSTANTLY MOVES FORWARD

                    //terminating conditions
                    if ((Math.abs(p1.getXpos() + p1.getVx()) > border-.25) || (Math.abs(p1.getYpos() + p1.getVy()) > border-.25)&&(!p1.split())) //if the blue player hits the borders
                    {
                        roundGoin = false; //the round is not currently still going
                        p2.addPoint(); //the red player gets a point
                        p1.reset(); //reset the players
                        p2.reset();
                        StdDraw.setPenColor(255,50,100); //display who won
                        StdDraw.text(0, border+3.5, "RED WON");
                    }
                    if ((Math.abs(p2.getXpos() + p2.getVx()) > border-.25) || (Math.abs(p2.getYpos() + p2.getVy()) > border-.25)&&(!p2.split())) //if the red player hits the borders
                    {
                        roundGoin = false; //the round is not currently still going
                        p1.addPoint(); //blue player gets a point
                        p1.reset(); //reset players
                        p2.reset();
                        StdDraw.setPenColor(StdDraw.CYAN); //display who won
                        StdDraw.text(0, border+3.5, "BLUE WON");
                    }
                    else if (p1.collide(p2)&&(!p1.split())) //if the blue player collides with itself or the other player (p1 loses)
                    {
                        p2.addPoint();  //red gets a point
                        roundGoin = false; //round is not still going
                        p1.reset(); //reset players
                        p2.reset();
                        StdDraw.setPenColor(255,50,100); //display who won
                        StdDraw.text(0, border+3.5, "RED WON");
                    }
                    else if (p2.collide(p1)&&(!p2.split())) //if the red player collides with itself or the other player (p2 loses)
                    {
                        p1.addPoint(); //blue gets a point
                        roundGoin = false; //round is not still going
                        p1.reset();  //reset players
                        p2.reset();
                        StdDraw.setPenColor(StdDraw.CYAN); //display who won
                        StdDraw.text(0, border+3.5, "BLUE WON");
                    }
                    StdDraw.show(16); //show the animations, pause for 16 milliseconds
                }
            }
        }
    }

    private static void titleScreen() //methods which displays the title screen
    {
        final int border = 42;
        final int startBorder = 35; //start border bounds
        final int startBorderBottom = -46;
        final int startBorderTop = -8;
        Font titleFont = new Font("Impact", Font.BOLD, 100); //setting up text
        Font smallFont = new Font("TimesNewRoman", Font.PLAIN, 12);
        StdDraw.clear(StdDraw.DARK_GRAY); //clear the screen
        StdDraw.setFont(titleFont);
        StdDraw.setPenColor(255,50,100); //DO THE TEXT
        StdDraw.text(0, border, "WELCOME TO");
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.text(0, border-10, "ACHTUNG DIE KURVE");
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0, border-25, "PRESS ENTER TO BEGIN");
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(230,230,230);
        StdDraw.text(0, border-31, "THIS IS A TWO PLAYER GAME, SO GRAB A FRIEND");
        StdDraw.text(0, border-39, "AFTER A ROUND IS OVER PRESS ENTER, PRESS Q TO STOP PLAYING");
        StdDraw.text(0, border-35, "THE OBJECTIVE OF THE GAME IS TO LIVE AS LONG AS POSSIBLE");
        StdDraw.text(0, border-43, "RED MOVES WITH A AND S BLUE MOVES WITH ← AND →");
        StdDraw.text(0, border-47, "TEST OUT THE CONTROLS BELOW, HAVE FUN!");

        //DRAW THE TITLE SCREEN BOUNDARY
        StdDraw.setPenColor(100,100,100);
        StdDraw.line(-startBorder, startBorderBottom, -startBorder, startBorderTop);
        StdDraw.line(-startBorder, startBorderTop, startBorder, startBorderTop);
        StdDraw.line(startBorder, startBorderTop, startBorder, startBorderBottom);
        StdDraw.line(-startBorder, startBorderBottom, startBorder, startBorderBottom);
        StdDraw.show(); //show what was drawn
    }

    public static void setUp(int border, int p1S, int p2S) //set up the screen when the actual game is going
    {
        StdDraw.clear(StdDraw.DARK_GRAY); //clear the screen
        //DISPLAY SCORES
        StdDraw.setPenColor(255,50,100);
        StdDraw.text(-border-3.85, border+3.5, ""+p2S);
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.text(border+3.85, border+3.5, ""+p1S);
        StdDraw.setPenColor(100,100,100);
        //DRAW THE SQUARE BORDER
        StdDraw.line(-border, -border, -border, border);
        StdDraw.line(-border, border, border, border);
        StdDraw.line(border, border, border, -border);
        StdDraw.line(-border, -border, border, -border);
    }

    private static void runDemo(Player p1, Player p2) //method which runs the game while the title screen demo is playing
    {
        final int border = 42; //TITLE SCREEN BORDERS
        final int startBorder = 35;
        final int startBorderBottom = -46;
        final int startBorderTop = -8;
        final int pause = 50; //amount of time untill the players are respwaned in the demo
        //ANIMAATION

        //DRAW THE PLAYERS
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.filledCircle(p1.getXpos(), p1.getYpos(), p1.getRadius());
        StdDraw.setPenColor(255,50,100);
        StdDraw.filledCircle(p2.getXpos(), p2.getYpos(), p2.getRadius());
        if(StdDraw.isKeyPressed(37)) //37 is left arrow key
        {
            p1.left(); //same as main game, just for demo
        }
        if(StdDraw.isKeyPressed('A'))
        {
            p2.left(); //same as main game, just for demo
        }
        if(StdDraw.isKeyPressed('S'))
        {
            p2.right(); //same as main game, just for demo
        }
        if(StdDraw.isKeyPressed(39)) //39 is right arrow key
        {
            p1.right(); //same as main game, just for demo
        }
        p1.move(p1.split()); //moce the players //same as main game, just for demo
        p2.move(p2.split());

        //IF ANY OF THE PLAYERS HIT THE WALLS //not worried about who hit what, because im not keeping track of points
        if ((Math.abs(p1.getXpos() + p1.getVx()) > startBorder) || ((p1.getYpos() + p1.getVy()) > startBorderTop) ||  ((p1.getYpos() + p1.getVy()) < startBorderBottom) || (Math.abs(p2.getXpos() + p2.getVx()) > startBorder) || ((p2.getYpos() + p2.getVy()) > startBorderTop-.25) ||  ((p2.getYpos() + p2.getVy()) < startBorderBottom))
        {
            StdDraw.pause(pause);
            titleScreen();
            p1.demoReset(startBorder,startBorderTop,startBorderBottom);
            p2.demoReset(startBorder,startBorderTop,startBorderBottom);
        }
        //IF ANY OF THE PLAYERS COLLIDE //not worried about who hit who, because im not keeping track of points
        if (p1.collide(p2) || p2.collide(p1))
        {
            StdDraw.pause(pause);
            titleScreen();
            p1.demoReset(startBorder,startBorderTop,startBorderBottom);
            p2.demoReset(startBorder,startBorderTop,startBorderBottom);
        }
        StdDraw.show(16); //show what happened wait 16 miliseconds untill running another iteration
    }
}



/*
This class --> Player keeps track of the two players movements and actually influences values so that the player moves and creates trails
PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER PLAYER
 */
class Player
{
    //instance fields 241-249     268-297
    private double maxVel = .125; //This is the max speed/ constant speed of the player
    private final double turnInc = .08; //This is the speed at which the player turns //.08
    private final int maxTick = 500; //This is the longest amount of time that can occur untill a player splits //500
    private final int earliestSplit = 200; //This is the soonest time when a player can split //100
    private final double splitL = 6; //This is the amount of time a player spends "Split" when it happens //2
    private final int xScale2 = 50; //Scale of the game
    private final int yScale2 = 50;
    private final int xScale1 = -50;
    private final int yScale1 = -50;

    private int tick; //variable which increases with each animation iteration
    private int split; //variable which determines when the player split
    private int splitCount; //variable which keeps track of how many frames the player has spent split
    private int previousSplitCount; //variable which keeps track of how many times the player split before it was currently splitting
    private int score; //scores of the player, increases when the player wins a round
    private double theta; //varibale which keeps track of the direction of the player
    private double xPos; //current x position of the moving player
    private double yPos; //current y position of the moving player
    private double velX; //current x velocity of the moving player
    private double velY; //current y velocity of the moving player
    private final double radius = .215; //radius of the circle which moves
    private ArrayList<Trail> positions; //arraylist of circles which are generated behind the player but dont move themselves

    //CONSTRUCTOR
    Player() //constructs the player when player object created
    {
        this.positions = new ArrayList(); //initialize array List
        this.xPos = (xScale1+20) + Math.random()*(xScale2-20); //place the player within the bounds of the game, but not too close to the edges so they dont lose automatically
        this.yPos = (yScale1+20) + Math.random()*(yScale2-20); //place the player within the bounds of the game, but not too close to the edges so they dont lose automatically
        this.theta = Math.random()*(Math.PI*2); //generate the initial direction of the player(random)
        velX = Math.cos(theta)*maxVel; //assign the horizontal velocity based on the direction of the player, theta
        velY = Math.sin(theta)*maxVel; //assign the vertical velocity based on the direction of the player, theta
        this.split = earliestSplit+(int)(Math.random()*(maxTick-earliestSplit-(splitL*2))); //generate the split number of the player (bound it so it coincides with the final variables)
    }

    //Add a trail object besed on x and y coordinate given
    private void addPosition(double x, double y)
    {
        positions.add(new Trail(x,y));
    }

    //GETTER METHODS GETTER METHODS GETTER METHODS GETTER METHODS GETTER METHODS
    //GETTER METHODS GETTER METHODS GETTER METHODS GETTER METHODS GETTER METHODS
    //GETTER METHODS GETTER METHODS GETTER METHODS GETTER METHODS GETTER METHODS
    public double getTheta()
    {
        return theta;
    }

    double getXpos()
    {
        return xPos;
    }

    double getYpos()
    {
        return yPos;
    }

    double getVx()
    {
        return this.velX;
    }

    double getVy()
    {
        return this.velY;
    }

    double getRadius()
    {
        return radius;
    }

    public int getTick()
    {
        return this.tick;
    }

    public int getSplit()
    {
        return this.split;
    }

    public int getSplitCount()
    {
        return splitCount;
    }

    public int getPSplitCount()
    {
        return previousSplitCount;
    }

    private ArrayList<Trail> getPositions()
    {
        return positions;
    }

    void addPoint()
    {
        score++;
    }

    int getScore()
    {
        return score;
    }

    //MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION
    //MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION
    //MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION MOVEMENT + ANIMATION

    void left() //turn the player left
    {
        theta += turnInc; //increase theta, so they move left from whichever direction they are in currently
        velX = Math.cos(theta)*maxVel;
        velY = Math.sin(theta)*maxVel;
    }

    void right() //turn the player right
    {
        theta -= turnInc; //decrease theta, so they move right from whichever direction they are in currently
        velX = Math.cos(theta)*maxVel;
        velY = Math.sin(theta)*maxVel;
    }

    void tick() //iterate the animation count
    {
        this.tick++; //increase the tick
        if(tick > maxTick) //reset tick
        {
            tick = 0;
        }
    }

    boolean split() //split the player, so that it no longer generates trails and is not drawn anymore
    {
        if(this.tick >= this.split-splitL && this.tick <= this.split+splitL) //if the split number is within a certain tick range
        {
            splitCount++; //count the number of iterations the player splits
            return true; //return true to signify that the player is splitting currently
        }
        else
        {
            if(splitCount > previousSplitCount) // If the player just finished splitting (essentially)
            {
                this.split = earliestSplit+(int)(Math.random()*(maxTick-earliestSplit-(splitL*2))); //assign a new random split number
                this.previousSplitCount = splitCount; //make them equal, so that new split numbers are not contrantly being generated
                this.tick = 0; //reset the tick to 0
            }
            return false; //return false since the player is not currently splitting
        }
    }

    void move(boolean split) //move the player emthod
    {
        if(!split) //while the player is not splitting
        {
            addPosition(this.xPos,this.yPos); //add positions
        }
        this.xPos += velX; //move player (animation)
        this.yPos += velY; //move player (animation)
    }

    boolean collide(Player p) //method which checks if the player collides with itself or another players trail
    {
        if(positions.size() >= 5) //i make this condition so that the player is not condisered to be runing into itelf at the very beggining
        {
            boolean collide = false; //innocent untill proven guilty
            for(int i = 0; i<positions.size()-4; i++) //go through the players own positions
            {
                if((Math.sqrt(Math.pow(positions.get(i).getXpos() - xPos,2)+Math.pow(positions.get(i).getYpos() - yPos, 2))) <= radius) //if theier radii overlap
                {
                    collide = true; //they collided
                    break; //get out this was all that mattered
                }
            }
            for(int i = 0; i<p.getPositions().size(); i++) //go through all the opents
            {
                if((Math.sqrt(Math.pow(p.getPositions().get(i).getXpos() - xPos,2)+Math.pow(p.getPositions().get(i).getYpos() - yPos, 2))) <= radius) //if their radii overlap
                {
                    collide = true;//they collided
                    break; //get out this was all that mattered
                }
            }
            return collide; //return true;
        }
        else
        {
            return false; //if they never overlapped, they didnt collide and thus return false;
        }
    }

    void demoReset(int horiSpawn, int topSpawn, int bottomSpawn) //reset method for the player while the title screen demo is running
    {
        this.xPos = (-horiSpawn+7) + Math.random()*(horiSpawn+20); //spawn within bounds of the title demo
        this.yPos = (bottomSpawn+7) + Math.random()*(-topSpawn+20);
        this.theta = Math.random()*(Math.PI*2); //random initial direction
        this.velX = Math.cos(theta)*maxVel; //assign direction
        this.velY = Math.sin(theta)*maxVel;
        //remove the positions
        if (positions.size() > 0) {
            positions.subList(0, positions.size()).clear();
        }
    }

    void reset() //reset method for when the actual game is running
    {   this.split = earliestSplit+(int)(Math.random()*(maxTick-earliestSplit-(splitL*2))); //assign random split (within reasonable bounds)
        this.splitCount = 0; //reset split count
        this.previousSplitCount = 0; //reset previous splits
        this.tick = 0; //reset tick;
        this.xPos = (xScale1+20) + Math.random()*(xScale2+10); //spawn player within bounds (restrictions so its reasonable)
        this.yPos = (yScale1+20) + Math.random()*(yScale2+10); //spawn player within bounds (restrictions so its reasonable)
        this.theta = Math.random()*(Math.PI*2);  //random initial direction
        this.velX = Math.cos(theta)*maxVel; //horizontal and vertical velocities are assigned based on initial direction
        this.velY = Math.sin(theta)*maxVel;
        //remove the positions
        if (positions.size() > 0) {
            positions.subList(0, positions.size()).clear();
        }
    }
}

/*
I am making a game called Achtung do Kurve
Its pretty fun
This class, Trail keeps track of the circles which are generated behind the circle
its relatively simple because these "trail" objects don't move or do anything
TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL TRAIL
 */
class Trail
{
    private double xPos; //the x and y positions of the trials
    private double yPos;
    //CONSTRUCTOR
    Trail(double xPos, double yPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    //getter methods for positions of the trials
    double getXpos()
    {
        return xPos;
    }
    double getYpos()
    {
        return yPos;
    }
}
