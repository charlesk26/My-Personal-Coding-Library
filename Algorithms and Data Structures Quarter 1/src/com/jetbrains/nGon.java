package com.jetbrains;

/**
 * ANIMATION VIDEO LINK: https://youtu.be/FOnpx9rqla4
 * Charles Kolozsvary *THIS IS NOT MY CODE, THE ONLY THING I MADE WERE THE COMMENTS AND THE CALCULATE AREA METHOD*
 Period 2 Algorithms
 9-25-19
 Java Version 4.2.1
 Description:
 */
/*
Regular n-gons. Ngon_YI.java takes a command-line argument or from a Scanner object n and draws a regular n-gon using turtle graphics or StdDraw. By taking n to a sufficiently large value,
we obtain a good approximation to a circle represented with polygons. Assume the polygon is inscribed in the circle.

Show your images as n increases. The program Ngon_YI.java finds the smallest value for n to get the best approximation to a circle. Recursion is required for full credit.
The input argument's purpose is to determine how many sides you want to start with.

Explain clearly what your approach is in your documentation. This documentation is worth 25% of the assignment's grade.

NOTE: you can use StdDraw or Turtle.

To be clear your assignment is not complete unless you submit three parts:

a. An animation showing how the polygon becomes a circle as the number of sides increases. This part does not require recursion.

b. A recursive computation of the area of the polygons.

c. Justify your final number of sides that approximate the area of the circle. You must showing explicitly why it is in fact the least number of sides.

This site from Khan Academy might help you understand the general idea.
 */

//imports
import java.awt.Color;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class nGon
{
    /*
        INSTANCE FIELDS
     */
    private double angle; //direction
    private double step; //side length of polygon
    private double x, y; //current position of point on polygon
    private int n; //number of sides of polygon

    /*
        CONSTRUCTOR
     */
    public nGon(double angle, double step, double x, double y, int n)
    {
        this.angle = angle;
        this.step = step;
        this.x = x;
        this.y = y;
        this.n = n;
    }

    /*
        GETTER METHODS
     */
    public double getAngle()
    {
        return angle;
    }
    public double getStep()
    {
        return step;
    }
    public int getN()
    {
        return n;
    }


    /*
        TURN METHOD
        changes the angle, which determines the direction of the next line drawn
    */
    public void turn(double delta)
    {
        this.angle += delta;
    }


    /*
        DRAWING A SINGLE POLYGON METHOD
        DRAWS THE FIRST LINE OF THE POLYGON (singular) , CHANGES DIRECTION, DRAWS ANOTHER LINE, ETC
     */
    public void draw()
    {
        if(n<100) //i just stop drawing them if n > 75 because you can no longer actually see the image update anymore; its not useful to animate further
        {
            for (int i = 0; i < n; i++) { //draws as many lines as there are sides of the polygon
                StdDraw.pause(-1000); //pause for some time so that it looks animated, but as the number of sides increases pause for less and less time, until no longer pausing (when .pause() takes a negative, it counts as 0)
                double oldx = x; //keeping track of previous to draw new line
                double oldy = y;
                x += step * Math.cos(Math.toRadians(angle)); // determine where the next coordinate will go based on current direction (angle)
                y += step * Math.sin(Math.toRadians(angle));
                StdDraw.line(oldx, oldy, x, y); //draw line
                turn(360.0/n); //change direction for next line drawn
            }
        }

    }

    /*
    DRAW ALL THE POLYGONS
     */
    public static void drawNGons(ArrayList<nGon> polygons) //this is static because it utilizes multiple Ngon objects and requires an array list of Ngons as a parameter
    {
        StdDraw.setCanvasSize(900,900); //number of pixels the window displays by
        StdDraw.setXscale(-.6,.6); //set scale
        StdDraw.setYscale(-.6,.6);
        StdDraw.clear(new Color(100,100,230)); //make the back round blue ish
        StdDraw.setPenColor(new Color(255,255,255)); //set pen color to make the outer circle white
        StdDraw.circle(0,0,.50); //draw the circle encompassing the polygons
        StdDraw.setPenColor(new Color(0,0,0));//make the polygon lines black
        /*
        (ABOVE) SET UP STD.DRAW / THE SCREEN BEFORE DRAWING THE POLYGONS AND CIRCLE
         */

        for (nGon polygon: polygons) //iterate for the number of polygons (for each loop)
            {
                polygon.draw(); //draw the polygon
            }
        StdDraw.setPenColor(new Color(255,255,50)); //set pen color to make the outer circle yellow
        StdDraw.circle(0,0,.50); //draw the circle encompassing the polygons after drawing all the polygons because the black lines of the polygons sides cover up the original encompassing circle
    }

    /*
    CALCULATE AREA METHOD
    PART B
     */
    public static String calculateArea(ArrayList<nGon> polygons, int i) //i is n for each polygon
    {
        if(i < 3) //terminating condition
        {
            return ""; //return nothing (nothing necessary, since the area of a 2 sided gon is 0)
        }

        //(below) calculate teh area of a regular polygon with n sides and a side length
        double area0 = .25*i*Math.pow(polygons.get(i-3).getStep(),2)*(1/(Math.tan(Math.PI/i))); //A = (1/4)na^2 cot(π/n) tan(π/n) where a is the side length and n is the number of sides
        BigDecimal difference = new BigDecimal(100*((.5*.5*Math.PI/area0)-1)).round(new MathContext(5)); //make a big decimal to round the difference
        //difference is the percent deviation from the area of the circle
        BigDecimal area = new BigDecimal(area0).round(new MathContext(5)); //express the area as a big decimal to round to 5 places

        //ONLY FOR FORMATTING | | | | doing this because you cant use print f while using recursion, its for formatting reasons to make sure the numbers line up (its kinda ugly)
        //                    v v v v
        if(i == 4) //this is here because if the shape is a square then its area only has one decimal place
        {
            return "\n\n         N -> "+i+"    Area -> "+area + "         D -> " + difference+calculateArea(polygons, i-1);
        }
        if(i > 9) //this is to account for the spacing change from n = 9 to n = 10, i need to remove one space between i and "Area -->"
        {
            return "\n\n         N -> "+i+"   Area -> "+area + "     D -> " + difference+calculateArea(polygons, i-1);
        }
        //                    ^ ^ ^ ^
        //ONLY FOR FORMATTING | | | |


        return "\n\n         N -> "+i+"    Area -> "+area + "     D -> " + difference+"%"+calculateArea(polygons, i-1);
        //(above) main return statement, which is recursive, allowing me to calculate the area of each polygon and relate it to the area of the circle so I can find a point where the difference between the two is less than .001 percent
    }



    /*
    MAIN METHOD
     */
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]); //receive the number of polygons / largest number of sides (n) though args[]
        ArrayList<nGon> polygons = new ArrayList<>(); //create arrayList of type nGon
        for(int i = 3; i<=n; i++) //start making polygons with 3 sides or more, since a two sided polygon is just a line
        {
            polygons.add(new nGon(180.0/i, Math.sin(Math.toRadians(180.0/i)), 0,-.5, i)); //create each polygon object then add it to the array list of polygons
            /*
            Description of polygons.add(...) (above)
            @Param 1
            the first parameter (180.0/i) is the angle which must be in terms of i, as when iterating over i in this scenario, i is n for that particular polygon
            @Param 2
            The "step" (Math.sin(Math.to ...)) or side length of that polygon based on the equation for determining side length based on n (again i is n for each polygon)
            @Param 3
            X position (0) of the first point on polygon before drawing line then turning
            @Param 4
            Y Position (-.5) of the first point on polygon before drawing line then turning
            @Param 5
            This is the n (number of sides/ i) for the particular polygon
             */
        }

        drawNGons(polygons); //draw the polygons
        //(below) print the areas calculated from the method and the chart setup
        System.out.println("                    INFORMATION CHART BELOW\n(D is the percent deviation from the area of the enclosing circle)\n         |               |               |\n         |               |               |\n         v               v               v");
        System.out.println(calculateArea(polygons, n));

    }
}
/**
 * PART C
 * MY ANSWER:
 * The smallest value n for a n sided regular polygon to approximate the area of the circle (with radius .5 units) which encompasses each regular polygon is 812
 * because the percent deviation of a 812 sided polygon from the actual area of the encompassing circle is less than .001%
 */
/*
(PART A INCLUDED AS A VIDEO HERE'S THE LINK: https://youtu.be/FOnpx9rqla4)
(when args[0] = "812")
OUTPUT:
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=53471:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Code/Algorithms quarter 1/out/production/Algorithms quarter 1" com.jetbrains.nGon 812
                    INFORMATION CHART BELOW
(D is the percent deviation from the area of the enclosing circle)
         |               |               |
         |               |               |
         v               v               v


         N -> 812   Area -> 0.78539     D -> 0.00099793

         N -> 811   Area -> 0.78539     D -> 0.0010004

         N -> 810   Area -> 0.78539     D -> 0.0010029

         N -> 809   Area -> 0.78539     D -> 0.0010053

         N -> 808   Area -> 0.78539     D -> 0.0010078

         N -> 807   Area -> 0.78539     D -> 0.0010103

         N -> 806   Area -> 0.78539     D -> 0.0010128

         N -> 805   Area -> 0.78539     D -> 0.0010154

         N -> 804   Area -> 0.78539     D -> 0.0010179

         N -> 803   Area -> 0.78539     D -> 0.0010204

         N -> 802   Area -> 0.78539     D -> 0.0010230

         N -> 801   Area -> 0.78539     D -> 0.0010255

         N -> 800   Area -> 0.78539     D -> 0.0010281

         N -> 799   Area -> 0.78539     D -> 0.0010307

         N -> 798   Area -> 0.78539     D -> 0.0010333

         N -> 797   Area -> 0.78539     D -> 0.0010358

         N -> 796   Area -> 0.78539     D -> 0.0010384

         N -> 795   Area -> 0.78539     D -> 0.0010411

         N -> 794   Area -> 0.78539     D -> 0.0010437

         N -> 793   Area -> 0.78539     D -> 0.0010463

         N -> 792   Area -> 0.78539     D -> 0.0010490

         N -> 791   Area -> 0.78539     D -> 0.0010516

         N -> 790   Area -> 0.78539     D -> 0.0010543

         N -> 789   Area -> 0.78539     D -> 0.0010570

         N -> 788   Area -> 0.78539     D -> 0.0010596

         N -> 787   Area -> 0.78539     D -> 0.0010623

         N -> 786   Area -> 0.78539     D -> 0.0010650

         N -> 785   Area -> 0.78539     D -> 0.0010678

         N -> 784   Area -> 0.78539     D -> 0.0010705

         N -> 783   Area -> 0.78539     D -> 0.0010732

         N -> 782   Area -> 0.78539     D -> 0.0010760

         N -> 781   Area -> 0.78539     D -> 0.0010787

         N -> 780   Area -> 0.78539     D -> 0.0010815

         N -> 779   Area -> 0.78539     D -> 0.0010843

         N -> 778   Area -> 0.78539     D -> 0.0010871

         N -> 777   Area -> 0.78539     D -> 0.0010899

         N -> 776   Area -> 0.78539     D -> 0.0010927

         N -> 775   Area -> 0.78539     D -> 0.0010955

         N -> 774   Area -> 0.78539     D -> 0.0010983

         N -> 773   Area -> 0.78539     D -> 0.0011012

         N -> 772   Area -> 0.78539     D -> 0.0011040

         N -> 771   Area -> 0.78539     D -> 0.0011069

         N -> 770   Area -> 0.78539     D -> 0.0011098

         N -> 769   Area -> 0.78539     D -> 0.0011127

         N -> 768   Area -> 0.78539     D -> 0.0011156

         N -> 767   Area -> 0.78539     D -> 0.0011185

         N -> 766   Area -> 0.78539     D -> 0.0011214

         N -> 765   Area -> 0.78539     D -> 0.0011243

         N -> 764   Area -> 0.78539     D -> 0.0011273

         N -> 763   Area -> 0.78539     D -> 0.0011302

         N -> 762   Area -> 0.78539     D -> 0.0011332

         N -> 761   Area -> 0.78539     D -> 0.0011362

         N -> 760   Area -> 0.78539     D -> 0.0011392

         N -> 759   Area -> 0.78539     D -> 0.0011422

         N -> 758   Area -> 0.78539     D -> 0.0011452

         N -> 757   Area -> 0.78539     D -> 0.0011482

         N -> 756   Area -> 0.78539     D -> 0.0011512

         N -> 755   Area -> 0.78539     D -> 0.0011543

         N -> 754   Area -> 0.78539     D -> 0.0011574

         N -> 753   Area -> 0.78539     D -> 0.0011604

         N -> 752   Area -> 0.78539     D -> 0.0011635

         N -> 751   Area -> 0.78539     D -> 0.0011666

         N -> 750   Area -> 0.78539     D -> 0.0011697

         N -> 749   Area -> 0.78539     D -> 0.0011729

         N -> 748   Area -> 0.78539     D -> 0.0011760

         N -> 747   Area -> 0.78539     D -> 0.0011792

         N -> 746   Area -> 0.78539     D -> 0.0011823

         N -> 745   Area -> 0.78539     D -> 0.0011855

         N -> 744   Area -> 0.78539     D -> 0.0011887

         N -> 743   Area -> 0.78539     D -> 0.0011919

         N -> 742   Area -> 0.78539     D -> 0.0011951

         N -> 741   Area -> 0.78539     D -> 0.0011983

         N -> 740   Area -> 0.78539     D -> 0.0012016

         N -> 739   Area -> 0.78539     D -> 0.0012048

         N -> 738   Area -> 0.78539     D -> 0.0012081

         N -> 737   Area -> 0.78539     D -> 0.0012114

         N -> 736   Area -> 0.78539     D -> 0.0012147

         N -> 735   Area -> 0.78539     D -> 0.0012180

         N -> 734   Area -> 0.78539     D -> 0.0012213

         N -> 733   Area -> 0.78539     D -> 0.0012246

         N -> 732   Area -> 0.78539     D -> 0.0012280

         N -> 731   Area -> 0.78539     D -> 0.0012313

         N -> 730   Area -> 0.78539     D -> 0.0012347

         N -> 729   Area -> 0.78539     D -> 0.0012381

         N -> 728   Area -> 0.78539     D -> 0.0012415

         N -> 727   Area -> 0.78539     D -> 0.0012449

         N -> 726   Area -> 0.78539     D -> 0.0012484

         N -> 725   Area -> 0.78539     D -> 0.0012518

         N -> 724   Area -> 0.78539     D -> 0.0012553

         N -> 723   Area -> 0.78539     D -> 0.0012587

         N -> 722   Area -> 0.78539     D -> 0.0012622

         N -> 721   Area -> 0.78539     D -> 0.0012657

         N -> 720   Area -> 0.78539     D -> 0.0012693

         N -> 719   Area -> 0.78539     D -> 0.0012728

         N -> 718   Area -> 0.78539     D -> 0.0012763

         N -> 717   Area -> 0.78539     D -> 0.0012799

         N -> 716   Area -> 0.78539     D -> 0.0012835

         N -> 715   Area -> 0.78539     D -> 0.0012871

         N -> 714   Area -> 0.78539     D -> 0.0012907

         N -> 713   Area -> 0.78539     D -> 0.0012943

         N -> 712   Area -> 0.78539     D -> 0.0012979

         N -> 711   Area -> 0.78539     D -> 0.0013016

         N -> 710   Area -> 0.78539     D -> 0.0013053

         N -> 709   Area -> 0.78539     D -> 0.0013089

         N -> 708   Area -> 0.78539     D -> 0.0013126

         N -> 707   Area -> 0.78539     D -> 0.0013164

         N -> 706   Area -> 0.78539     D -> 0.0013201

         N -> 705   Area -> 0.78539     D -> 0.0013238

         N -> 704   Area -> 0.78539     D -> 0.0013276

         N -> 703   Area -> 0.78539     D -> 0.0013314

         N -> 702   Area -> 0.78539     D -> 0.0013352

         N -> 701   Area -> 0.78539     D -> 0.0013390

         N -> 700   Area -> 0.78539     D -> 0.0013428

         N -> 699   Area -> 0.78539     D -> 0.0013467

         N -> 698   Area -> 0.78539     D -> 0.0013505

         N -> 697   Area -> 0.78539     D -> 0.0013544

         N -> 696   Area -> 0.78539     D -> 0.0013583

         N -> 695   Area -> 0.78539     D -> 0.0013622

         N -> 694   Area -> 0.78539     D -> 0.0013661

         N -> 693   Area -> 0.78539     D -> 0.0013701

         N -> 692   Area -> 0.78539     D -> 0.0013740

         N -> 691   Area -> 0.78539     D -> 0.0013780

         N -> 690   Area -> 0.78539     D -> 0.0013820

         N -> 689   Area -> 0.78539     D -> 0.0013860

         N -> 688   Area -> 0.78539     D -> 0.0013901

         N -> 687   Area -> 0.78539     D -> 0.0013941

         N -> 686   Area -> 0.78539     D -> 0.0013982

         N -> 685   Area -> 0.78539     D -> 0.0014023

         N -> 684   Area -> 0.78539     D -> 0.0014064

         N -> 683   Area -> 0.78539     D -> 0.0014105

         N -> 682   Area -> 0.78539     D -> 0.0014146

         N -> 681   Area -> 0.78539     D -> 0.0014188

         N -> 680   Area -> 0.78539     D -> 0.0014230

         N -> 679   Area -> 0.78539     D -> 0.0014272

         N -> 678   Area -> 0.78539     D -> 0.0014314

         N -> 677   Area -> 0.78539     D -> 0.0014356

         N -> 676   Area -> 0.78539     D -> 0.0014399

         N -> 675   Area -> 0.78539     D -> 0.0014441

         N -> 674   Area -> 0.78539     D -> 0.0014484

         N -> 673   Area -> 0.78539     D -> 0.0014527

         N -> 672   Area -> 0.78539     D -> 0.0014570

         N -> 671   Area -> 0.78539     D -> 0.0014614

         N -> 670   Area -> 0.78539     D -> 0.0014658

         N -> 669   Area -> 0.78539     D -> 0.0014701

         N -> 668   Area -> 0.78539     D -> 0.0014746

         N -> 667   Area -> 0.78539     D -> 0.0014790

         N -> 666   Area -> 0.78539     D -> 0.0014834

         N -> 665   Area -> 0.78539     D -> 0.0014879

         N -> 664   Area -> 0.78539     D -> 0.0014924

         N -> 663   Area -> 0.78539     D -> 0.0014969

         N -> 662   Area -> 0.78539     D -> 0.0015014

         N -> 661   Area -> 0.78539     D -> 0.0015059

         N -> 660   Area -> 0.78539     D -> 0.0015105

         N -> 659   Area -> 0.78539     D -> 0.0015151

         N -> 658   Area -> 0.78539     D -> 0.0015197

         N -> 657   Area -> 0.78539     D -> 0.0015243

         N -> 656   Area -> 0.78539     D -> 0.0015290

         N -> 655   Area -> 0.78539     D -> 0.0015337

         N -> 654   Area -> 0.78539     D -> 0.0015384

         N -> 653   Area -> 0.78539     D -> 0.0015431

         N -> 652   Area -> 0.78539     D -> 0.0015478

         N -> 651   Area -> 0.78539     D -> 0.0015526

         N -> 650   Area -> 0.78539     D -> 0.0015574

         N -> 649   Area -> 0.78539     D -> 0.0015622

         N -> 648   Area -> 0.78539     D -> 0.0015670

         N -> 647   Area -> 0.78539     D -> 0.0015718

         N -> 646   Area -> 0.78539     D -> 0.0015767

         N -> 645   Area -> 0.78539     D -> 0.0015816

         N -> 644   Area -> 0.78539     D -> 0.0015865

         N -> 643   Area -> 0.78539     D -> 0.0015914

         N -> 642   Area -> 0.78539     D -> 0.0015964

         N -> 641   Area -> 0.78539     D -> 0.0016014

         N -> 640   Area -> 0.78539     D -> 0.0016064

         N -> 639   Area -> 0.78539     D -> 0.0016114

         N -> 638   Area -> 0.78539     D -> 0.0016165

         N -> 637   Area -> 0.78539     D -> 0.0016216

         N -> 636   Area -> 0.78539     D -> 0.0016267

         N -> 635   Area -> 0.78539     D -> 0.0016318

         N -> 634   Area -> 0.78539     D -> 0.0016369

         N -> 633   Area -> 0.78539     D -> 0.0016421

         N -> 632   Area -> 0.78539     D -> 0.0016473

         N -> 631   Area -> 0.78539     D -> 0.0016526

         N -> 630   Area -> 0.78539     D -> 0.0016578

         N -> 629   Area -> 0.78539     D -> 0.0016631

         N -> 628   Area -> 0.78539     D -> 0.0016684

         N -> 627   Area -> 0.78539     D -> 0.0016737

         N -> 626   Area -> 0.78538     D -> 0.0016791

         N -> 625   Area -> 0.78538     D -> 0.0016844

         N -> 624   Area -> 0.78538     D -> 0.0016898

         N -> 623   Area -> 0.78538     D -> 0.0016953

         N -> 622   Area -> 0.78538     D -> 0.0017007

         N -> 621   Area -> 0.78538     D -> 0.0017062

         N -> 620   Area -> 0.78538     D -> 0.0017117

         N -> 619   Area -> 0.78538     D -> 0.0017172

         N -> 618   Area -> 0.78538     D -> 0.0017228

         N -> 617   Area -> 0.78538     D -> 0.0017284

         N -> 616   Area -> 0.78538     D -> 0.0017340

         N -> 615   Area -> 0.78538     D -> 0.0017397

         N -> 614   Area -> 0.78538     D -> 0.0017453

         N -> 613   Area -> 0.78538     D -> 0.0017510

         N -> 612   Area -> 0.78538     D -> 0.0017568

         N -> 611   Area -> 0.78538     D -> 0.0017625

         N -> 610   Area -> 0.78538     D -> 0.0017683

         N -> 609   Area -> 0.78538     D -> 0.0017741

         N -> 608   Area -> 0.78538     D -> 0.0017799

         N -> 607   Area -> 0.78538     D -> 0.0017858

         N -> 606   Area -> 0.78538     D -> 0.0017917

         N -> 605   Area -> 0.78538     D -> 0.0017976

         N -> 604   Area -> 0.78538     D -> 0.0018036

         N -> 603   Area -> 0.78538     D -> 0.0018096

         N -> 602   Area -> 0.78538     D -> 0.0018156

         N -> 601   Area -> 0.78538     D -> 0.0018217

         N -> 600   Area -> 0.78538     D -> 0.0018277

         N -> 599   Area -> 0.78538     D -> 0.0018338

         N -> 598   Area -> 0.78538     D -> 0.0018400

         N -> 597   Area -> 0.78538     D -> 0.0018461

         N -> 596   Area -> 0.78538     D -> 0.0018523

         N -> 595   Area -> 0.78538     D -> 0.0018586

         N -> 594   Area -> 0.78538     D -> 0.0018648

         N -> 593   Area -> 0.78538     D -> 0.0018711

         N -> 592   Area -> 0.78538     D -> 0.0018775

         N -> 591   Area -> 0.78538     D -> 0.0018838

         N -> 590   Area -> 0.78538     D -> 0.0018902

         N -> 589   Area -> 0.78538     D -> 0.0018966

         N -> 588   Area -> 0.78538     D -> 0.0019031

         N -> 587   Area -> 0.78538     D -> 0.0019096

         N -> 586   Area -> 0.78538     D -> 0.0019161

         N -> 585   Area -> 0.78538     D -> 0.0019227

         N -> 584   Area -> 0.78538     D -> 0.0019293

         N -> 583   Area -> 0.78538     D -> 0.0019359

         N -> 582   Area -> 0.78538     D -> 0.0019425

         N -> 581   Area -> 0.78538     D -> 0.0019492

         N -> 580   Area -> 0.78538     D -> 0.0019560

         N -> 579   Area -> 0.78538     D -> 0.0019627

         N -> 578   Area -> 0.78538     D -> 0.0019695

         N -> 577   Area -> 0.78538     D -> 0.0019763

         N -> 576   Area -> 0.78538     D -> 0.0019832

         N -> 575   Area -> 0.78538     D -> 0.0019901

         N -> 574   Area -> 0.78538     D -> 0.0019971

         N -> 573   Area -> 0.78538     D -> 0.0020040

         N -> 572   Area -> 0.78538     D -> 0.0020110

         N -> 571   Area -> 0.78538     D -> 0.0020181

         N -> 570   Area -> 0.78538     D -> 0.0020252

         N -> 569   Area -> 0.78538     D -> 0.0020323

         N -> 568   Area -> 0.78538     D -> 0.0020395

         N -> 567   Area -> 0.78538     D -> 0.0020467

         N -> 566   Area -> 0.78538     D -> 0.0020539

         N -> 565   Area -> 0.78538     D -> 0.0020612

         N -> 564   Area -> 0.78538     D -> 0.0020685

         N -> 563   Area -> 0.78538     D -> 0.0020759

         N -> 562   Area -> 0.78538     D -> 0.0020833

         N -> 561   Area -> 0.78538     D -> 0.0020907

         N -> 560   Area -> 0.78538     D -> 0.0020982

         N -> 559   Area -> 0.78538     D -> 0.0021057

         N -> 558   Area -> 0.78538     D -> 0.0021132

         N -> 557   Area -> 0.78538     D -> 0.0021208

         N -> 556   Area -> 0.78538     D -> 0.0021285

         N -> 555   Area -> 0.78538     D -> 0.0021361

         N -> 554   Area -> 0.78538     D -> 0.0021439

         N -> 553   Area -> 0.78538     D -> 0.0021516

         N -> 552   Area -> 0.78538     D -> 0.0021594

         N -> 551   Area -> 0.78538     D -> 0.0021673

         N -> 550   Area -> 0.78538     D -> 0.0021752

         N -> 549   Area -> 0.78538     D -> 0.0021831

         N -> 548   Area -> 0.78538     D -> 0.0021911

         N -> 547   Area -> 0.78538     D -> 0.0021991

         N -> 546   Area -> 0.78538     D -> 0.0022071

         N -> 545   Area -> 0.78538     D -> 0.0022152

         N -> 544   Area -> 0.78538     D -> 0.0022234

         N -> 543   Area -> 0.78538     D -> 0.0022316

         N -> 542   Area -> 0.78538     D -> 0.0022398

         N -> 541   Area -> 0.78538     D -> 0.0022481

         N -> 540   Area -> 0.78538     D -> 0.0022565

         N -> 539   Area -> 0.78538     D -> 0.0022648

         N -> 538   Area -> 0.78538     D -> 0.0022733

         N -> 537   Area -> 0.78538     D -> 0.0022817

         N -> 536   Area -> 0.78538     D -> 0.0022903

         N -> 535   Area -> 0.78538     D -> 0.0022988

         N -> 534   Area -> 0.78538     D -> 0.0023075

         N -> 533   Area -> 0.78538     D -> 0.0023161

         N -> 532   Area -> 0.78538     D -> 0.0023248

         N -> 531   Area -> 0.78538     D -> 0.0023336

         N -> 530   Area -> 0.78538     D -> 0.0023424

         N -> 529   Area -> 0.78538     D -> 0.0023513

         N -> 528   Area -> 0.78538     D -> 0.0023602

         N -> 527   Area -> 0.78538     D -> 0.0023692

         N -> 526   Area -> 0.78538     D -> 0.0023782

         N -> 525   Area -> 0.78538     D -> 0.0023872

         N -> 524   Area -> 0.78538     D -> 0.0023964

         N -> 523   Area -> 0.78538     D -> 0.0024055

         N -> 522   Area -> 0.78538     D -> 0.0024148

         N -> 521   Area -> 0.78538     D -> 0.0024240

         N -> 520   Area -> 0.78538     D -> 0.0024334

         N -> 519   Area -> 0.78538     D -> 0.0024428

         N -> 518   Area -> 0.78538     D -> 0.0024522

         N -> 517   Area -> 0.78538     D -> 0.0024617

         N -> 516   Area -> 0.78538     D -> 0.0024712

         N -> 515   Area -> 0.78538     D -> 0.0024809

         N -> 514   Area -> 0.78538     D -> 0.0024905

         N -> 513   Area -> 0.78538     D -> 0.0025002

         N -> 512   Area -> 0.78538     D -> 0.0025100

         N -> 511   Area -> 0.78538     D -> 0.0025198

         N -> 510   Area -> 0.78538     D -> 0.0025297

         N -> 509   Area -> 0.78538     D -> 0.0025397

         N -> 508   Area -> 0.78538     D -> 0.0025497

         N -> 507   Area -> 0.78538     D -> 0.0025598

         N -> 506   Area -> 0.78538     D -> 0.0025699

         N -> 505   Area -> 0.78538     D -> 0.0025801

         N -> 504   Area -> 0.78538     D -> 0.0025903

         N -> 503   Area -> 0.78538     D -> 0.0026006

         N -> 502   Area -> 0.78538     D -> 0.0026110

         N -> 501   Area -> 0.78538     D -> 0.0026214

         N -> 500   Area -> 0.78538     D -> 0.0026319

         N -> 499   Area -> 0.78538     D -> 0.0026425

         N -> 498   Area -> 0.78538     D -> 0.0026531

         N -> 497   Area -> 0.78538     D -> 0.0026638

         N -> 496   Area -> 0.78538     D -> 0.0026746

         N -> 495   Area -> 0.78538     D -> 0.0026854

         N -> 494   Area -> 0.78538     D -> 0.0026963

         N -> 493   Area -> 0.78538     D -> 0.0027072

         N -> 492   Area -> 0.78538     D -> 0.0027182

         N -> 491   Area -> 0.78538     D -> 0.0027293

         N -> 490   Area -> 0.78538     D -> 0.0027405

         N -> 489   Area -> 0.78538     D -> 0.0027517

         N -> 488   Area -> 0.78538     D -> 0.0027630

         N -> 487   Area -> 0.78538     D -> 0.0027743

         N -> 486   Area -> 0.78538     D -> 0.0027858

         N -> 485   Area -> 0.78538     D -> 0.0027973

         N -> 484   Area -> 0.78538     D -> 0.0028088

         N -> 483   Area -> 0.78538     D -> 0.0028205

         N -> 482   Area -> 0.78538     D -> 0.0028322

         N -> 481   Area -> 0.78538     D -> 0.0028440

         N -> 480   Area -> 0.78538     D -> 0.0028558

         N -> 479   Area -> 0.78538     D -> 0.0028678

         N -> 478   Area -> 0.78538     D -> 0.0028798

         N -> 477   Area -> 0.78538     D -> 0.0028919

         N -> 476   Area -> 0.78538     D -> 0.0029040

         N -> 475   Area -> 0.78538     D -> 0.0029163

         N -> 474   Area -> 0.78538     D -> 0.0029286

         N -> 473   Area -> 0.78538     D -> 0.0029410

         N -> 472   Area -> 0.78537     D -> 0.0029535

         N -> 471   Area -> 0.78537     D -> 0.0029660

         N -> 470   Area -> 0.78537     D -> 0.0029787

         N -> 469   Area -> 0.78537     D -> 0.0029914

         N -> 468   Area -> 0.78537     D -> 0.0030042

         N -> 467   Area -> 0.78537     D -> 0.0030171

         N -> 466   Area -> 0.78537     D -> 0.0030300

         N -> 465   Area -> 0.78537     D -> 0.0030431

         N -> 464   Area -> 0.78537     D -> 0.0030562

         N -> 463   Area -> 0.78537     D -> 0.0030694

         N -> 462   Area -> 0.78537     D -> 0.0030827

         N -> 461   Area -> 0.78537     D -> 0.0030961

         N -> 460   Area -> 0.78537     D -> 0.0031096

         N -> 459   Area -> 0.78537     D -> 0.0031231

         N -> 458   Area -> 0.78537     D -> 0.0031368

         N -> 457   Area -> 0.78537     D -> 0.0031505

         N -> 456   Area -> 0.78537     D -> 0.0031644

         N -> 455   Area -> 0.78537     D -> 0.0031783

         N -> 454   Area -> 0.78537     D -> 0.0031923

         N -> 453   Area -> 0.78537     D -> 0.0032064

         N -> 452   Area -> 0.78537     D -> 0.0032206

         N -> 451   Area -> 0.78537     D -> 0.0032349

         N -> 450   Area -> 0.78537     D -> 0.0032493

         N -> 449   Area -> 0.78537     D -> 0.0032638

         N -> 448   Area -> 0.78537     D -> 0.0032784

         N -> 447   Area -> 0.78537     D -> 0.0032931

         N -> 446   Area -> 0.78537     D -> 0.0033079

         N -> 445   Area -> 0.78537     D -> 0.0033228

         N -> 444   Area -> 0.78537     D -> 0.0033377

         N -> 443   Area -> 0.78537     D -> 0.0033528

         N -> 442   Area -> 0.78537     D -> 0.0033680

         N -> 441   Area -> 0.78537     D -> 0.0033833

         N -> 440   Area -> 0.78537     D -> 0.0033987

         N -> 439   Area -> 0.78537     D -> 0.0034142

         N -> 438   Area -> 0.78537     D -> 0.0034298

         N -> 437   Area -> 0.78537     D -> 0.0034455

         N -> 436   Area -> 0.78537     D -> 0.0034614

         N -> 435   Area -> 0.78537     D -> 0.0034773

         N -> 434   Area -> 0.78537     D -> 0.0034933

         N -> 433   Area -> 0.78537     D -> 0.0035095

         N -> 432   Area -> 0.78537     D -> 0.0035258

         N -> 431   Area -> 0.78537     D -> 0.0035421

         N -> 430   Area -> 0.78537     D -> 0.0035586

         N -> 429   Area -> 0.78537     D -> 0.0035752

         N -> 428   Area -> 0.78537     D -> 0.0035920

         N -> 427   Area -> 0.78537     D -> 0.0036088

         N -> 426   Area -> 0.78537     D -> 0.0036258

         N -> 425   Area -> 0.78537     D -> 0.0036429

         N -> 424   Area -> 0.78537     D -> 0.0036601

         N -> 423   Area -> 0.78537     D -> 0.0036774

         N -> 422   Area -> 0.78537     D -> 0.0036948

         N -> 421   Area -> 0.78537     D -> 0.0037124

         N -> 420   Area -> 0.78537     D -> 0.0037301

         N -> 419   Area -> 0.78537     D -> 0.0037479

         N -> 418   Area -> 0.78537     D -> 0.0037659

         N -> 417   Area -> 0.78537     D -> 0.0037840

         N -> 416   Area -> 0.78537     D -> 0.0038022

         N -> 415   Area -> 0.78537     D -> 0.0038205

         N -> 414   Area -> 0.78537     D -> 0.0038390

         N -> 413   Area -> 0.78537     D -> 0.0038576

         N -> 412   Area -> 0.78537     D -> 0.0038764

         N -> 411   Area -> 0.78537     D -> 0.0038953

         N -> 410   Area -> 0.78537     D -> 0.0039143

         N -> 409   Area -> 0.78537     D -> 0.0039335

         N -> 408   Area -> 0.78537     D -> 0.0039528

         N -> 407   Area -> 0.78537     D -> 0.0039722

         N -> 406   Area -> 0.78537     D -> 0.0039918

         N -> 405   Area -> 0.78537     D -> 0.0040115

         N -> 404   Area -> 0.78537     D -> 0.0040314

         N -> 403   Area -> 0.78537     D -> 0.0040515

         N -> 402   Area -> 0.78537     D -> 0.0040716

         N -> 401   Area -> 0.78537     D -> 0.0040920

         N -> 400   Area -> 0.78537     D -> 0.0041125

         N -> 399   Area -> 0.78537     D -> 0.0041331

         N -> 398   Area -> 0.78537     D -> 0.0041539

         N -> 397   Area -> 0.78537     D -> 0.0041748

         N -> 396   Area -> 0.78537     D -> 0.0041960

         N -> 395   Area -> 0.78537     D -> 0.0042172

         N -> 394   Area -> 0.78536     D -> 0.0042387

         N -> 393   Area -> 0.78536     D -> 0.0042603

         N -> 392   Area -> 0.78536     D -> 0.0042820

         N -> 391   Area -> 0.78536     D -> 0.0043040

         N -> 390   Area -> 0.78536     D -> 0.0043261

         N -> 389   Area -> 0.78536     D -> 0.0043483

         N -> 388   Area -> 0.78536     D -> 0.0043708

         N -> 387   Area -> 0.78536     D -> 0.0043934

         N -> 386   Area -> 0.78536     D -> 0.0044162

         N -> 385   Area -> 0.78536     D -> 0.0044392

         N -> 384   Area -> 0.78536     D -> 0.0044623

         N -> 383   Area -> 0.78536     D -> 0.0044856

         N -> 382   Area -> 0.78536     D -> 0.0045092

         N -> 381   Area -> 0.78536     D -> 0.0045329

         N -> 380   Area -> 0.78536     D -> 0.0045567

         N -> 379   Area -> 0.78536     D -> 0.0045808

         N -> 378   Area -> 0.78536     D -> 0.0046051

         N -> 377   Area -> 0.78536     D -> 0.0046296

         N -> 376   Area -> 0.78536     D -> 0.0046542

         N -> 375   Area -> 0.78536     D -> 0.0046791

         N -> 374   Area -> 0.78536     D -> 0.0047041

         N -> 373   Area -> 0.78536     D -> 0.0047294

         N -> 372   Area -> 0.78536     D -> 0.0047549

         N -> 371   Area -> 0.78536     D -> 0.0047805

         N -> 370   Area -> 0.78536     D -> 0.0048064

         N -> 369   Area -> 0.78536     D -> 0.0048325

         N -> 368   Area -> 0.78536     D -> 0.0048588

         N -> 367   Area -> 0.78536     D -> 0.0048853

         N -> 366   Area -> 0.78536     D -> 0.0049120

         N -> 365   Area -> 0.78536     D -> 0.0049390

         N -> 364   Area -> 0.78536     D -> 0.0049662

         N -> 363   Area -> 0.78536     D -> 0.0049936

         N -> 362   Area -> 0.78536     D -> 0.0050212

         N -> 361   Area -> 0.78536     D -> 0.0050490

         N -> 360   Area -> 0.78536     D -> 0.0050771

         N -> 359   Area -> 0.78536     D -> 0.0051055

         N -> 358   Area -> 0.78536     D -> 0.0051340

         N -> 357   Area -> 0.78536     D -> 0.0051628

         N -> 356   Area -> 0.78536     D -> 0.0051919

         N -> 355   Area -> 0.78536     D -> 0.0052212

         N -> 354   Area -> 0.78536     D -> 0.0052507

         N -> 353   Area -> 0.78536     D -> 0.0052805

         N -> 352   Area -> 0.78536     D -> 0.0053105

         N -> 351   Area -> 0.78536     D -> 0.0053409

         N -> 350   Area -> 0.78536     D -> 0.0053714

         N -> 349   Area -> 0.78536     D -> 0.0054022

         N -> 348   Area -> 0.78536     D -> 0.0054333

         N -> 347   Area -> 0.78536     D -> 0.0054647

         N -> 346   Area -> 0.78535     D -> 0.0054963

         N -> 345   Area -> 0.78535     D -> 0.0055282

         N -> 344   Area -> 0.78535     D -> 0.0055604

         N -> 343   Area -> 0.78535     D -> 0.0055929

         N -> 342   Area -> 0.78535     D -> 0.0056257

         N -> 341   Area -> 0.78535     D -> 0.0056587

         N -> 340   Area -> 0.78535     D -> 0.0056920

         N -> 339   Area -> 0.78535     D -> 0.0057257

         N -> 338   Area -> 0.78535     D -> 0.0057596

         N -> 337   Area -> 0.78535     D -> 0.0057938

         N -> 336   Area -> 0.78535     D -> 0.0058284

         N -> 335   Area -> 0.78535     D -> 0.0058632

         N -> 334   Area -> 0.78535     D -> 0.0058984

         N -> 333   Area -> 0.78535     D -> 0.0059339

         N -> 332   Area -> 0.78535     D -> 0.0059697

         N -> 331   Area -> 0.78535     D -> 0.0060058

         N -> 330   Area -> 0.78535     D -> 0.0060423

         N -> 329   Area -> 0.78535     D -> 0.0060790

         N -> 328   Area -> 0.78535     D -> 0.0061162

         N -> 327   Area -> 0.78535     D -> 0.0061536

         N -> 326   Area -> 0.78535     D -> 0.0061914

         N -> 325   Area -> 0.78535     D -> 0.0062296

         N -> 324   Area -> 0.78535     D -> 0.0062681

         N -> 323   Area -> 0.78535     D -> 0.0063070

         N -> 322   Area -> 0.78535     D -> 0.0063462

         N -> 321   Area -> 0.78535     D -> 0.0063858

         N -> 320   Area -> 0.78535     D -> 0.0064258

         N -> 319   Area -> 0.78535     D -> 0.0064662

         N -> 318   Area -> 0.78535     D -> 0.0065069

         N -> 317   Area -> 0.78535     D -> 0.0065480

         N -> 316   Area -> 0.78535     D -> 0.0065895

         N -> 315   Area -> 0.78535     D -> 0.0066314

         N -> 314   Area -> 0.78535     D -> 0.0066737

         N -> 313   Area -> 0.78535     D -> 0.0067165

         N -> 312   Area -> 0.78535     D -> 0.0067596

         N -> 311   Area -> 0.78534     D -> 0.0068031

         N -> 310   Area -> 0.78534     D -> 0.0068471

         N -> 309   Area -> 0.78534     D -> 0.0068915

         N -> 308   Area -> 0.78534     D -> 0.0069363

         N -> 307   Area -> 0.78534     D -> 0.0069816

         N -> 306   Area -> 0.78534     D -> 0.0070273

         N -> 305   Area -> 0.78534     D -> 0.0070734

         N -> 304   Area -> 0.78534     D -> 0.0071200

         N -> 303   Area -> 0.78534     D -> 0.0071671

         N -> 302   Area -> 0.78534     D -> 0.0072147

         N -> 301   Area -> 0.78534     D -> 0.0072627

         N -> 300   Area -> 0.78534     D -> 0.0073112

         N -> 299   Area -> 0.78534     D -> 0.0073602

         N -> 298   Area -> 0.78534     D -> 0.0074097

         N -> 297   Area -> 0.78534     D -> 0.0074596

         N -> 296   Area -> 0.78534     D -> 0.0075101

         N -> 295   Area -> 0.78534     D -> 0.0075611

         N -> 294   Area -> 0.78534     D -> 0.0076127

         N -> 293   Area -> 0.78534     D -> 0.0076647

         N -> 292   Area -> 0.78534     D -> 0.0077173

         N -> 291   Area -> 0.78534     D -> 0.0077704

         N -> 290   Area -> 0.78534     D -> 0.0078241

         N -> 289   Area -> 0.78534     D -> 0.0078784

         N -> 288   Area -> 0.78534     D -> 0.0079332

         N -> 287   Area -> 0.78534     D -> 0.0079886

         N -> 286   Area -> 0.78533     D -> 0.0080445

         N -> 285   Area -> 0.78533     D -> 0.0081011

         N -> 284   Area -> 0.78533     D -> 0.0081582

         N -> 283   Area -> 0.78533     D -> 0.0082160

         N -> 282   Area -> 0.78533     D -> 0.0082744

         N -> 281   Area -> 0.78533     D -> 0.0083334

         N -> 280   Area -> 0.78533     D -> 0.0083930

         N -> 279   Area -> 0.78533     D -> 0.0084533

         N -> 278   Area -> 0.78533     D -> 0.0085142

         N -> 277   Area -> 0.78533     D -> 0.0085758

         N -> 276   Area -> 0.78533     D -> 0.0086381

         N -> 275   Area -> 0.78533     D -> 0.0087010

         N -> 274   Area -> 0.78533     D -> 0.0087646

         N -> 273   Area -> 0.78533     D -> 0.0088290

         N -> 272   Area -> 0.78533     D -> 0.0088940

         N -> 271   Area -> 0.78533     D -> 0.0089598

         N -> 270   Area -> 0.78533     D -> 0.0090263

         N -> 269   Area -> 0.78533     D -> 0.0090935

         N -> 268   Area -> 0.78533     D -> 0.0091615

         N -> 267   Area -> 0.78533     D -> 0.0092303

         N -> 266   Area -> 0.78533     D -> 0.0092998

         N -> 265   Area -> 0.78532     D -> 0.0093701

         N -> 264   Area -> 0.78532     D -> 0.0094412

         N -> 263   Area -> 0.78532     D -> 0.0095132

         N -> 262   Area -> 0.78532     D -> 0.0095859

         N -> 261   Area -> 0.78532     D -> 0.0096595

         N -> 260   Area -> 0.78532     D -> 0.0097340

         N -> 259   Area -> 0.78532     D -> 0.0098093

         N -> 258   Area -> 0.78532     D -> 0.0098855

         N -> 257   Area -> 0.78532     D -> 0.0099626

         N -> 256   Area -> 0.78532     D -> 0.010041

         N -> 255   Area -> 0.78532     D -> 0.010119

         N -> 254   Area -> 0.78532     D -> 0.010199

         N -> 253   Area -> 0.78532     D -> 0.010280

         N -> 252   Area -> 0.78532     D -> 0.010362

         N -> 251   Area -> 0.78532     D -> 0.010445

         N -> 250   Area -> 0.78532     D -> 0.010528

         N -> 249   Area -> 0.78531     D -> 0.010613

         N -> 248   Area -> 0.78531     D -> 0.010699

         N -> 247   Area -> 0.78531     D -> 0.010786

         N -> 246   Area -> 0.78531     D -> 0.010874

         N -> 245   Area -> 0.78531     D -> 0.010963

         N -> 244   Area -> 0.78531     D -> 0.011053

         N -> 243   Area -> 0.78531     D -> 0.011144

         N -> 242   Area -> 0.78531     D -> 0.011236

         N -> 241   Area -> 0.78531     D -> 0.011329

         N -> 240   Area -> 0.78531     D -> 0.011424

         N -> 239   Area -> 0.78531     D -> 0.011520

         N -> 238   Area -> 0.78531     D -> 0.011617

         N -> 237   Area -> 0.78531     D -> 0.011715

         N -> 236   Area -> 0.78531     D -> 0.011815

         N -> 235   Area -> 0.78530     D -> 0.011915

         N -> 234   Area -> 0.78530     D -> 0.012017

         N -> 233   Area -> 0.78530     D -> 0.012121

         N -> 232   Area -> 0.78530     D -> 0.012226

         N -> 231   Area -> 0.78530     D -> 0.012332

         N -> 230   Area -> 0.78530     D -> 0.012439

         N -> 229   Area -> 0.78530     D -> 0.012548

         N -> 228   Area -> 0.78530     D -> 0.012658

         N -> 227   Area -> 0.78530     D -> 0.012770

         N -> 226   Area -> 0.78530     D -> 0.012883

         N -> 225   Area -> 0.78530     D -> 0.012998

         N -> 224   Area -> 0.78530     D -> 0.013115

         N -> 223   Area -> 0.78529     D -> 0.013232

         N -> 222   Area -> 0.78529     D -> 0.013352

         N -> 221   Area -> 0.78529     D -> 0.013473

         N -> 220   Area -> 0.78529     D -> 0.013596

         N -> 219   Area -> 0.78529     D -> 0.013720

         N -> 218   Area -> 0.78529     D -> 0.013846

         N -> 217   Area -> 0.78529     D -> 0.013974

         N -> 216   Area -> 0.78529     D -> 0.014104

         N -> 215   Area -> 0.78529     D -> 0.014236

         N -> 214   Area -> 0.78529     D -> 0.014369

         N -> 213   Area -> 0.78528     D -> 0.014504

         N -> 212   Area -> 0.78528     D -> 0.014641

         N -> 211   Area -> 0.78528     D -> 0.014780

         N -> 210   Area -> 0.78528     D -> 0.014922

         N -> 209   Area -> 0.78528     D -> 0.015065

         N -> 208   Area -> 0.78528     D -> 0.015210

         N -> 207   Area -> 0.78528     D -> 0.015357

         N -> 206   Area -> 0.78528     D -> 0.015507

         N -> 205   Area -> 0.78528     D -> 0.015658

         N -> 204   Area -> 0.78527     D -> 0.015812

         N -> 203   Area -> 0.78527     D -> 0.015969

         N -> 202   Area -> 0.78527     D -> 0.016127

         N -> 201   Area -> 0.78527     D -> 0.016288

         N -> 200   Area -> 0.78527     D -> 0.016451

         N -> 199   Area -> 0.78527     D -> 0.016617

         N -> 198   Area -> 0.78527     D -> 0.016785

         N -> 197   Area -> 0.78527     D -> 0.016956

         N -> 196   Area -> 0.78526     D -> 0.017130

         N -> 195   Area -> 0.78526     D -> 0.017306

         N -> 194   Area -> 0.78526     D -> 0.017485

         N -> 193   Area -> 0.78526     D -> 0.017666

         N -> 192   Area -> 0.78526     D -> 0.017851

         N -> 191   Area -> 0.78526     D -> 0.018038

         N -> 190   Area -> 0.78526     D -> 0.018229

         N -> 189   Area -> 0.78525     D -> 0.018422

         N -> 188   Area -> 0.78525     D -> 0.018619

         N -> 187   Area -> 0.78525     D -> 0.018818

         N -> 186   Area -> 0.78525     D -> 0.019021

         N -> 185   Area -> 0.78525     D -> 0.019228

         N -> 184   Area -> 0.78525     D -> 0.019437

         N -> 183   Area -> 0.78524     D -> 0.019650

         N -> 182   Area -> 0.78524     D -> 0.019867

         N -> 181   Area -> 0.78524     D -> 0.020087

         N -> 180   Area -> 0.78524     D -> 0.020311

         N -> 179   Area -> 0.78524     D -> 0.020538

         N -> 178   Area -> 0.78524     D -> 0.020770

         N -> 177   Area -> 0.78523     D -> 0.021005

         N -> 176   Area -> 0.78523     D -> 0.021245

         N -> 175   Area -> 0.78523     D -> 0.021488

         N -> 174   Area -> 0.78523     D -> 0.021736

         N -> 173   Area -> 0.78523     D -> 0.021988

         N -> 172   Area -> 0.78522     D -> 0.022244

         N -> 171   Area -> 0.78522     D -> 0.022505

         N -> 170   Area -> 0.78522     D -> 0.022771

         N -> 169   Area -> 0.78522     D -> 0.023041

         N -> 168   Area -> 0.78522     D -> 0.023316

         N -> 167   Area -> 0.78521     D -> 0.023596

         N -> 166   Area -> 0.78521     D -> 0.023882

         N -> 165   Area -> 0.78521     D -> 0.024172

         N -> 164   Area -> 0.78521     D -> 0.024468

         N -> 163   Area -> 0.78520     D -> 0.024769

         N -> 162   Area -> 0.78520     D -> 0.025076

         N -> 161   Area -> 0.78520     D -> 0.025388

         N -> 160   Area -> 0.78520     D -> 0.025707

         N -> 159   Area -> 0.78519     D -> 0.026031

         N -> 158   Area -> 0.78519     D -> 0.026362

         N -> 157   Area -> 0.78519     D -> 0.026699

         N -> 156   Area -> 0.78519     D -> 0.027042

         N -> 155   Area -> 0.78518     D -> 0.027392

         N -> 154   Area -> 0.78518     D -> 0.027749

         N -> 153   Area -> 0.78518     D -> 0.028113

         N -> 152   Area -> 0.78517     D -> 0.028484

         N -> 151   Area -> 0.78517     D -> 0.028863

         N -> 150   Area -> 0.78517     D -> 0.029249

         N -> 149   Area -> 0.78517     D -> 0.029643

         N -> 148   Area -> 0.78516     D -> 0.030045

         N -> 147   Area -> 0.78516     D -> 0.030456

         N -> 146   Area -> 0.78516     D -> 0.030874

         N -> 145   Area -> 0.78515     D -> 0.031302

         N -> 144   Area -> 0.78515     D -> 0.031738

         N -> 143   Area -> 0.78515     D -> 0.032184

         N -> 142   Area -> 0.78514     D -> 0.032639

         N -> 141   Area -> 0.78514     D -> 0.033103

         N -> 140   Area -> 0.78513     D -> 0.033578

         N -> 139   Area -> 0.78513     D -> 0.034063

         N -> 138   Area -> 0.78513     D -> 0.034559

         N -> 137   Area -> 0.78512     D -> 0.035065

         N -> 136   Area -> 0.78512     D -> 0.035583

         N -> 135   Area -> 0.78511     D -> 0.036112

         N -> 134   Area -> 0.78511     D -> 0.036653

         N -> 133   Area -> 0.78511     D -> 0.037206

         N -> 132   Area -> 0.78510     D -> 0.037772

         N -> 131   Area -> 0.78510     D -> 0.038352

         N -> 130   Area -> 0.78509     D -> 0.038944

         N -> 129   Area -> 0.78509     D -> 0.039550

         N -> 128   Area -> 0.78508     D -> 0.040171

         N -> 127   Area -> 0.78508     D -> 0.040806

         N -> 126   Area -> 0.78507     D -> 0.041457

         N -> 125   Area -> 0.78507     D -> 0.042123

         N -> 124   Area -> 0.78506     D -> 0.042805

         N -> 123   Area -> 0.78506     D -> 0.043504

         N -> 122   Area -> 0.78505     D -> 0.044220

         N -> 121   Area -> 0.78505     D -> 0.044955

         N -> 120   Area -> 0.78504     D -> 0.045707

         N -> 119   Area -> 0.78503     D -> 0.046479

         N -> 118   Area -> 0.78503     D -> 0.047270

         N -> 117   Area -> 0.78502     D -> 0.048082

         N -> 116   Area -> 0.78501     D -> 0.048915

         N -> 115   Area -> 0.78501     D -> 0.049770

         N -> 114   Area -> 0.78500     D -> 0.050647

         N -> 113   Area -> 0.78499     D -> 0.051548

         N -> 112   Area -> 0.78499     D -> 0.052473

         N -> 111   Area -> 0.78498     D -> 0.053423

         N -> 110   Area -> 0.78497     D -> 0.054399

         N -> 109   Area -> 0.78496     D -> 0.055402

         N -> 108   Area -> 0.78496     D -> 0.056433

         N -> 107   Area -> 0.78495     D -> 0.057493

         N -> 106   Area -> 0.78494     D -> 0.058583

         N -> 105   Area -> 0.78493     D -> 0.059705

         N -> 104   Area -> 0.78492     D -> 0.060859

         N -> 103   Area -> 0.78491     D -> 0.062047

         N -> 102   Area -> 0.78490     D -> 0.063270

         N -> 101   Area -> 0.78489     D -> 0.064530

         N -> 100   Area -> 0.78488     D -> 0.065828

         N -> 99   Area -> 0.78487     D -> 0.067165

         N -> 98   Area -> 0.78486     D -> 0.068543

         N -> 97   Area -> 0.78485     D -> 0.069964

         N -> 96   Area -> 0.78484     D -> 0.071430

         N -> 95   Area -> 0.78483     D -> 0.072943

         N -> 94   Area -> 0.78481     D -> 0.074504

         N -> 93   Area -> 0.78480     D -> 0.076116

         N -> 92   Area -> 0.78479     D -> 0.077780

         N -> 91   Area -> 0.78477     D -> 0.079500

         N -> 90   Area -> 0.78476     D -> 0.081278

         N -> 89   Area -> 0.78475     D -> 0.083115

         N -> 88   Area -> 0.78473     D -> 0.085016

         N -> 87   Area -> 0.78472     D -> 0.086983

         N -> 86   Area -> 0.78470     D -> 0.089019

         N -> 85   Area -> 0.78468     D -> 0.091127

         N -> 84   Area -> 0.78467     D -> 0.093311

         N -> 83   Area -> 0.78465     D -> 0.095575

         N -> 82   Area -> 0.78463     D -> 0.097922

         N -> 81   Area -> 0.78461     D -> 0.10036

         N -> 80   Area -> 0.78459     D -> 0.10288

         N -> 79   Area -> 0.78457     D -> 0.10551

         N -> 78   Area -> 0.78455     D -> 0.10823

         N -> 77   Area -> 0.78453     D -> 0.11106

         N -> 76   Area -> 0.78450     D -> 0.11401

         N -> 75   Area -> 0.78448     D -> 0.11707

         N -> 74   Area -> 0.78445     D -> 0.12026

         N -> 73   Area -> 0.78443     D -> 0.12358

         N -> 72   Area -> 0.78440     D -> 0.12704

         N -> 71   Area -> 0.78437     D -> 0.13064

         N -> 70   Area -> 0.78434     D -> 0.13441

         N -> 69   Area -> 0.78431     D -> 0.13833

         N -> 68   Area -> 0.78428     D -> 0.14244

         N -> 67   Area -> 0.78425     D -> 0.14673

         N -> 66   Area -> 0.78421     D -> 0.15121

         N -> 65   Area -> 0.78418     D -> 0.15590

         N -> 64   Area -> 0.78414     D -> 0.16082

         N -> 63   Area -> 0.78410     D -> 0.16597

         N -> 62   Area -> 0.78405     D -> 0.17137

         N -> 61   Area -> 0.78401     D -> 0.17705

         N -> 60   Area -> 0.78396     D -> 0.18300

         N -> 59   Area -> 0.78391     D -> 0.18927

         N -> 58   Area -> 0.78386     D -> 0.19586

         N -> 57   Area -> 0.78381     D -> 0.20280

         N -> 56   Area -> 0.78375     D -> 0.21012

         N -> 55   Area -> 0.78369     D -> 0.21784

         N -> 54   Area -> 0.78363     D -> 0.22600

         N -> 53   Area -> 0.78356     D -> 0.23462

         N -> 52   Area -> 0.78349     D -> 0.24375

         N -> 51   Area -> 0.78341     D -> 0.25342

         N -> 50   Area -> 0.78333     D -> 0.26368

         N -> 49   Area -> 0.78325     D -> 0.27457

         N -> 48   Area -> 0.78316     D -> 0.28615

         N -> 47   Area -> 0.78306     D -> 0.29848

         N -> 46   Area -> 0.78296     D -> 0.31163

         N -> 45   Area -> 0.78285     D -> 0.32567

         N -> 44   Area -> 0.78273     D -> 0.34067

         N -> 43   Area -> 0.78261     D -> 0.35674

         N -> 42   Area -> 0.78247     D -> 0.37398

         N -> 41   Area -> 0.78233     D -> 0.39249

         N -> 40   Area -> 0.78217     D -> 0.41242

         N -> 39   Area -> 0.78200     D -> 0.43391

         N -> 38   Area -> 0.78182     D -> 0.45712

         N -> 37   Area -> 0.78163     D -> 0.48225

         N -> 36   Area -> 0.78142     D -> 0.50951

         N -> 35   Area -> 0.78119     D -> 0.53915

         N -> 34   Area -> 0.78094     D -> 0.57146

         N -> 33   Area -> 0.78066     D -> 0.60677

         N -> 32   Area -> 0.78036     D -> 0.64545

         N -> 31   Area -> 0.78003     D -> 0.68797

         N -> 30   Area -> 0.77967     D -> 0.73484

         N -> 29   Area -> 0.77927     D -> 0.78668

         N -> 28   Area -> 0.77882     D -> 0.84421

         N -> 27   Area -> 0.77833     D -> 0.90831

         N -> 26   Area -> 0.77778     D -> 0.98001

         N -> 25   Area -> 0.77716     D -> 1.0606

         N -> 24   Area -> 0.77646     D -> 1.1515

         N -> 23   Area -> 0.77567     D -> 1.2547

         N -> 22   Area -> 0.77476     D -> 1.3725

         N -> 21   Area -> 0.77373     D -> 1.5077

         N -> 20   Area -> 0.77254     D -> 1.6641

         N -> 19   Area -> 0.77116     D -> 1.8462

         N -> 18   Area -> 0.76955     D -> 2.0600

         N -> 17   Area -> 0.76764     D -> 2.3135

         N -> 16   Area -> 0.76537     D -> 2.6172

         N -> 15   Area -> 0.76263     D -> 2.9853

         N -> 14   Area -> 0.75930     D -> 3.4376

         N -> 13   Area -> 0.75518     D -> 4.0021

         N -> 12   Area -> 0.75000     D -> 4.7198

         N -> 11   Area -> 0.74338     D -> 5.6522

         N -> 10   Area -> 0.73473     D -> 6.8959

         N -> 9    Area -> 0.72314     D -> 8.6100

         N -> 8    Area -> 0.70711     D -> 11.072

         N -> 7    Area -> 0.68410     D -> 14.807

         N -> 6    Area -> 0.64952     D -> 20.920

         N -> 5    Area -> 0.59441     D -> 32.131

         N -> 4    Area -> 0.5         D -> 57.080

         N -> 3    Area -> 0.32476     D -> 141.84



 */

