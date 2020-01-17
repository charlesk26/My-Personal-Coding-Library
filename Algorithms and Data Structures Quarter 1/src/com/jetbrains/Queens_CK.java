package com.jetbrains;
/**
 Charles Kolozsvary
 Period 2 Algorithms
 10-08-19
 Java Version 4.2.1
 Description:
 In this exercise, you will solve the classic 8-queens problem: place 8 queens on an 8-by-8 chess board so that no two queens are in the same row, column, or diagonal. There are 8! = 40,320 ways in which no two queens are placed in the same row or column. Any permutation p[] of the integers 0 to 7 gives such a placement: put queen i in row i, column "p[i]". Your program Queens.java should take an integer command-line argument n and enumerate all solutions to the n-queens problem by drawing the location of the queens in ASCII like the two solutions below.

 Look at the attached image and the links below.

 Hint: to determine whether setting q[n] = i conflicts with q[0] through q[n-1]
 if q[i] equals q[n]: two queens are placed in the same column
 if q[i] - q[n] equals n - i: two queens are on same major diagonal
 if q[n] - q[i] equals n - i: two queens are on same minor diagonal

 https://math.stackexchange.com/questions/1872444/how-many-solutions-are-there-to-an-n-by-n-queens-problem

 http://www.brainmetrix.com/8-queens/

 WARNING: do not look for a solution on line.

 */
//imports
import java.util.ArrayList;

public class Queens_CK
{
    /*
    INSTANCE FIELDS
     */
    private int n; //the dimensions of the chess board and the number of queens
    private ArrayList<int[]> solutions; //the array list of int[] arrays which hold the row values for the solutions at each column

    /*
    CONSTRUCTOR
     */
    private Queens_CK(int n, ArrayList<int[]> solutions)
    {
        //initialize instance fields upon creating an object of Queens_CK
        this.n = n;
        this.solutions = solutions;
    }

    /*
    PRINT
     */
    private void printSolutions()
    {
        System.out.println("N: "+n+"\nTotal solutions: "+solutions.size()); //these are the total solutions, not unique since they can be rotated, flipped, etc
        for(int[] solution: solutions) //for each loop in the form for((type) variable_name: (GenericList/Array)<type>)
        {
            for(int row = n-1; row >=0; row--) //start from the upper left moving right checking if the position correlates to a queens position
            {
                for(int column = 0; column<n; column++)
                {
                    if(solution[column] == row) //if a queen is at row, column print q
                    {
                        System.out.print("Q  ");
                    }
                    else //other wise just print the astrix
                    {
                        System.out.print("*  ");
                    }
                }
                System.out.print("\n");//start a new line
            }
            System.out.println("\n");//spacing between solutions
        }

    }

    /*
    TEST
     */
    private boolean currentArrangementIsValid(int column, int[] rows) //determines weather the current arrangement of the queens is valid (given the new test case value from the previous line, columns[row] = columnVal;
    {
        for(int c = 0; c<column; c++) //iterate through each column
        {
            int difference = Math.abs(rows[c]-rows[column]); //Calculate the differences between the height values of column(s) c and the current column being inspected (Math.abs covers both major and minor diagonal)
            if(difference == 0 || difference == column - c) //if 0 they are in the same row, if diff == column -c then they intersect on either the major or minor diagonal
            {
                return false; //this arrangement is invalid, queens can intersect/ attack one another
            }
        }
        return true; //the arrangement passes the test, return that it does
    }

    /*
    ASSIGN VALUES , TEST IF IT WORKS, RECURSE
     */
    private void searchForQueens(int column, int[] rows)
    {
        //BASE CASE
        if(column == n) //base case --> when every column has been evaluated with a position for the queen
        {
            int[] copy = new int[n]; //create a copy of the rows array
            System.arraycopy(rows,0,copy,0,n); //actually copy it properly with System, rather than an assignment, as that will cause major issues
            solutions.add(copy); //add the array of column values as an additional solution for the nQueens problem
        } //after the base case is reached return back to the last call on the stack and exhaust other solutions (back tracking), or end if there are none :)

        else //if the solution is not found
        {
            for(int columnVal = 0; columnVal<n; columnVal++) //when this for loop exhausts itself entirely, (i.e after having iterated multiple different times at multiple different points of iteration) the searching is over; the program continues to the driver (is basically done)
            {
                rows[column] = columnVal; //assign a height(row) for the column
                if(currentArrangementIsValid(column,rows)) //check if that value worked
                {
                    //RECURSION (build up the stack)
                    searchForQueens(column+1,rows); //if it did, search for another queen
                }
                //if it didn't, iterate the loop and check another value
            }
        }
    }
    /*

     */

    /*
    DRIVER CLASS
     */
    private static void driver(int n)
    {
        Queens_CK nQueens = new Queens_CK(n, new ArrayList<>()); //create object of Queens which utilizes instance fields so I can reduce the number of arguments for each method
        nQueens.searchForQueens(0,new int[n]); //search + find all the solutions for the nQueens problem
        nQueens.printSolutions(); //print the solutions
    }
    /*
    MAIN
     */
    public static void main(String[] args) //main
    {
        driver(Integer.parseInt(args[0])); //take n as command line arg
    }
}
/*
OUTPUT (Multiple trials):
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=52448:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Code/Algorithms quarter 1/out/production/Algorithms quarter 1" com.jetbrains.Queens_CK 4
N: 4
Total solutions: 2
*  Q  *  *
*  *  *  Q
Q  *  *  *
*  *  Q  *


*  *  Q  *
Q  *  *  *
*  *  *  Q
*  Q  *  *



Process finished with exit code 0
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=52454:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Code/Algorithms quarter 1/out/production/Algorithms quarter 1" com.jetbrains.Queens_CK 5
N: 5
Total solutions: 10
*  *  Q  *  *
*  *  *  *  Q
*  Q  *  *  *
*  *  *  Q  *
Q  *  *  *  *


*  *  *  Q  *
*  Q  *  *  *
*  *  *  *  Q
*  *  Q  *  *
Q  *  *  *  *


*  *  *  *  Q
*  Q  *  *  *
*  *  *  Q  *
Q  *  *  *  *
*  *  Q  *  *


*  Q  *  *  *
*  *  *  *  Q
*  *  Q  *  *
Q  *  *  *  *
*  *  *  Q  *


*  *  *  *  Q
*  *  Q  *  *
Q  *  *  *  *
*  *  *  Q  *
*  Q  *  *  *


*  Q  *  *  *
*  *  *  Q  *
Q  *  *  *  *
*  *  Q  *  *
*  *  *  *  Q


*  *  *  Q  *
Q  *  *  *  *
*  *  Q  *  *
*  *  *  *  Q
*  Q  *  *  *


*  *  Q  *  *
Q  *  *  *  *
*  *  *  Q  *
*  Q  *  *  *
*  *  *  *  Q


Q  *  *  *  *
*  *  Q  *  *
*  *  *  *  Q
*  Q  *  *  *
*  *  *  Q  *


Q  *  *  *  *
*  *  *  Q  *
*  Q  *  *  *
*  *  *  *  Q
*  *  Q  *  *



Process finished with exit code 0
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=52459:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Code/Algorithms quarter 1/out/production/Algorithms quarter 1" com.jetbrains.Queens_CK 8
N: 8
Total solutions: 92
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *


*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *


*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q


*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q


*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *


*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *


*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *


*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *


*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *


*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *


*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *


*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q


*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *


*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *


*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *


*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *


*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *


*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *


*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *


*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *


*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *


*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *


*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  Q  *


*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *


*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *


*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *


*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *


*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *


*  Q  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *


*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


*  *  *  *  *  *  Q  *
Q  *  *  *  *  *  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *


*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *


*  *  Q  *  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  Q  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  Q  *  *  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  Q  *  *


*  *  *  *  *  Q  *  *
Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *


Q  *  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *


Q  *  *  *  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  *  Q  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *


Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  *  Q  *  *  *
*  *  Q  *  *  *  *  *


Q  *  *  *  *  *  *  *
*  *  *  *  *  *  Q  *
*  *  *  *  Q  *  *  *
*  *  *  *  *  *  *  Q
*  Q  *  *  *  *  *  *
*  *  *  Q  *  *  *  *
*  *  *  *  *  Q  *  *
*  *  Q  *  *  *  *  *



Process finished with exit code 0

 */