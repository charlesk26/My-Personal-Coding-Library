package com.jetbrains;
/**
 * Charles Kolozsvary
 * Period 2 Algorithms
 * Java Version 4.2.1
 * Java SDK: 12.01.0
 * Description:
 * NOTE: Watch the videos at the bottom of this post!
 * Percolation. Given a composite system comprised of randomly distributed insulating and metallic materials:
 * what fraction of the materials need to be metallic so that the composite system is an electrical conductor?
 * Given a porous landscape with water on the surface (or oil below), under what conditions
 * will the water be able to drain through to the bottom (or the oil to gush through to the surface)?
 * Scientists have defined an abstract process known as percolation to model such situations.
 * Check the links below to fully understand all the different components.
 * Submit all your work here.
 * ‌
 * Assignment full description:
 * https://www.cs.princeton.edu/courses/archive/fall15/cos226/assignments/percolation.html
 * ‌
 * Assignment checklist:
 * http://www.cs.princeton.edu/courses/archive/fall15/cos226/checklist/percolation.html
 * ‌
 * 1.5 Case Study: Union-Find
 * https://algs4.cs.princeton.edu/15uf/
 * ‌
 * Data files:
 * ftp://ftp.cs.princeton.edu/pub/cs226/
 * ‌
 * Professor Sedgewick's videos:
 * Dynamic Connectivity
 * https://www.youtube.com/watch?v=gfSpPbJWzVs&list=PLRdD1c6QbAqJn0606RlOR6T3yUqFWKwmX&index=2
 * ‌
 * Quick Find
 * https://www.youtube.com/watch?v=X_4Qn7MNp7A&list=PLRdD1c6QbAqJn0606RlOR6T3yUqFWKwmX&index=3
 * ‌
 * Quick Union
 * https://www.youtube.com/watch?v=BcRLmCS8pfw&list=PLRdD1c6QbAqJn0606RlOR6T3yUqFWKwmX&index=4
 * ‌
 * Improvements
 * https://www.youtube.com/watch?v=Wme8SDUaBx8&list=PLRdD1c6QbAqJn0606RlOR6T3yUqFWKwmX&index=5
 * ‌
 * Applications: Percolation
 * https://www.youtube.com/watch?v=fJe11uNfLJw&list=PLRdD1c6QbAqJn0606RlOR6T3yUqFWKwmX&index=6
 *
 * Josh Hug's Video
 * https://www.youtube.com/watch?v=o60oHXesOuA
 */
/*
PERCOLATION CLASS
This class will provide the basis for modeling systems which will or will not percolate
instances of this class are simulations of a given region composed of
n by n sites which are either open or closed in a 2 dimensional plane
 */
public class Percolation_CK
{
    /*
    CLASS VARIABLES (instance fields)
     */
    private final int n; //n by n grid with therefore n squared sites
    private int virtual_top; //index which represents the site which is connected to all top sites
    private int virtual_bottom; //same as above just at the bottom
    private boolean[][] open_close; //2D array of booleans which models the state of each site (true if open, false if not)
    private int num_open; //total number of sites open
    private UF uf; //unionFind object which will manage the sites
    /*
    CONSTRUCTOR
     */
    //package private
    Percolation_CK(int n) //create N-by-N grid, with all sites initially blocked
    {
        this.n = n;
        this.virtual_top = n*n;
        this.virtual_bottom = n*n+1;
        uf = new UF((n * n)+2); //make a new union find object which keeps track of connections
        //The two additional sites are for the virtual top and bottom sites
        open_close = new boolean[n][n]; // initialize array which keeps track which are open and which are closed
        num_open = 0; //initialize counter which keeps track the total number of sites opened
        for(int i =0; i<n; i++)//iterate through the top sites
        {
            uf.union((n*n), i); //connect the site with the virtual top
        }
        for(int i = ((n*n)-n); i<(n*n); i++) //iterate through the bottom sites
        {
            uf.union((n*n)+1,i); //connect to virtual bottom
        }
    }
    /*
    SUPPLEMENTAL METHODS FOR EASE OF IMPLEMENTATION
     */
    /*
    converts the two dimensional site to a 1 dimensional pointer/ indicator
     */
    private static int twoDim_to1D(int x, int y, int n)
    {
        return x*n+y;
    }
    /*
    validate the adjacent column sent, if it is out of bounds return false, else return true
     */
    private static boolean validAdjacent(int row, int col, int n)
    {
        return ((!(row>=n)) && (!(col>=n)) && (!(row<0)) && (!(col<0)));
    }
    /*
    check if two sites are connected
     */
    private boolean connected(int p, int q)
    {
        return uf.find(p) == uf.find(q);
    }
    /*
    looks at the surrounding site
     */
    private void checkAdjacent(int row, int col, int prime_r, int prime_c) //union the adjacent site (row_p, col_p) if open
    {
        if(validAdjacent(prime_r,prime_c, n)) //if the site is valid (not out of bounds)
        {
            if(isOpen(prime_r,prime_c)) //and if it's open
            {
                uf.union(twoDim_to1D(row,col, n),twoDim_to1D(prime_r,prime_c, n)); //union the two together
            }
        }
    }
    /*
    ORIGINAL METHOD LIST
     */
    /*
    Open a a site
     */
    void open(int row, int col)
    {
        if(!(isOpen(row,col))) //only open the site if it is not already open
        {
            open_close[row][col] = true; //OPEN SITE
            num_open++; //increase the number of open sites
            //Look around the newly opened site to see if there are other open sites, if so, they should be unioned with this site
            checkAdjacent(row,col,row,col+1); //check above
            checkAdjacent(row,col,row,col-1); //check below
            checkAdjacent(row,col,row-1, col); //check to the left
            checkAdjacent(row,col,row+1,col); //check to the right
        }
    }
    /*
    Is the site open?
     */
    private boolean isOpen(int row, int col)
    {
        return open_close[row][col]; //nothing to really see here
    }
    /*
    is the site (row, col) full?
     */
    private boolean isFull(int row, int col)
    {
        if(isOpen(row,col)) //is the site open to begin with?
        {
            return uf.find(twoDim_to1D(row,col,n))==uf.find(virtual_top); //is it connected to one of the top sites/ virtual top
        }
        else
        {
            return false;
        }
    }
    /*
    return number of open sites
     */
    int numberOfOpenSites() //package private
    {
        return num_open;
    }
    /*
    does the system percolate?
     */
    boolean percolates() //package private
    {
        return connected(virtual_top,virtual_bottom); //if the virtual top and bottom are connected the system percolates (Easy)
    }
    /*
    Run simulations of percolation and print results for a given N and T trials
     */
    private static void runStats_test(int N, int T)
    {
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("When N = "+N+", T = "+T);
        System.out.println("mean()                  = "+stats.mean());
        System.out.println("stdDev()                = "+stats.stdDev());
        System.out.println("confidenceLow()         = "+stats.confidenceLow());
        System.out.println("confidenceHigh()        = "+stats.confidenceHigh()+"\n");
    }
    /*
    MAIN
     */
    public static void main(String[] args)
    {
        /*
        Unit testing
         */
        Percolation_CK percolation = new Percolation_CK(5);
        System.out.println("Is an un-opened site open? "+percolation.isOpen(0,1)); //false
        System.out.println("Does the system percolate? "+percolation.percolates()); //false
        System.out.println("Is an un-opened site full? "+percolation.isFull(0,0)); //false
        percolation.open(1,0);
        percolation.open(1,1);
        System.out.println("Is an un-opened site open (next to open sites)? "+percolation.isOpen(1,2)); //false
        System.out.println("Is an un-opened site full (next to open sites)? "+percolation.isFull(1,2)); //false
        percolation.open(2,1);
        System.out.println("Is an open site full (not next to open sites)? "+percolation.isFull(2,2)); //false
        percolation.open(2,1);
        System.out.println("are 1,0 and 2,1 now connected? "+percolation.connected(5,11)); //true
        percolation.open(0,0); //open top allow flow
        System.out.println("Is 2,1 now full? "+percolation.isFull(2,1)); //true
        percolation.open(3,1); //open sites down to bottom
        percolation.open(4,1);
        System.out.println("Does the system percolate? "+percolation.percolates()); //true
        percolation.open(4,4);
        System.out.println("Is there back wash? "+percolation.isFull(4,4)); //true
        //(above)I think I'll come back to this if i have time, but for Now I move on
        //I think its better I get ahead with the other assignments and get this over with
        //rather than stick here too long
        System.out.println("\n\n");

        /*
        PercolationStats Testing
         */

        runStats_test(3,10000); //BEWARE this one takes some time (not too much but enough)

    }
}
/*
PERCOLATION STATS
(class for running statistics on percolation simulation)
 */
class PercolationStats //package private
{
    /*
    Class Variables (instance fields)
     */
    private double open_sites_to_non_open_total; //ratio of open to non open sites, accumulated from each trial
    private double[] percentages; //percentage of open to non open for each experimental trial
    private int T; //number of trials
    /*
    CONSTRUCTOR
     */
    //package private
    PercolationStats(int N, int T)   // perform T independent experiments on an N-by-N grid
    {
        this.T = T; //initialize instance fields
        this.percentages = new double[T];
        this.open_sites_to_non_open_total = 0;
        for(int i = 0; i<T; i++) //iterate for T trials
        {
            Percolation_CK percolation = new Percolation_CK(N); //create a new percolation object
            while(!(percolation.percolates())) //keep iterating while the system of sites does not percolate
            {
                percolation.open(StdRandom.uniform(N),StdRandom.uniform(N)); //open a random site every iteration
            }
            open_sites_to_non_open_total += (double)percolation.numberOfOpenSites()/(N*N); //when a given simulation among T simulations is complete, add the ratio of open sites to closed sites to the total
            //(above) this makes it easy to find the average percentage for percolation
            percentages[i] = (double)percolation.numberOfOpenSites()/(N*N); //keep track of individual percentages
        }
    }
    /*
    return the mean percentage (average)
     */
    double mean() //package private
    {
        return open_sites_to_non_open_total/T; //just divide by T trials (see constructor/instance fields)
    }
    /*
    return the standard deviation
     */
    double stdDev() //package private
    {
        double percent_minus_Mean_Squared_Total = 0; //double keeping track of the totals of Math.pow(x1(percent from a given run) - mew (average or mean percent),2)
        for (double percentage : percentages) //for each
        {
            percent_minus_Mean_Squared_Total += Math.pow((percentage - mean()), 2);
        }
        double o_squared = percent_minus_Mean_Squared_Total/(T-1);
        return Math.sqrt(o_squared);
    }
    /*
    return the low endpoint of 95% confidence interval
     */
    //package private
    double confidenceLow() // low  endpoint of 95% confidence interval
    {
        return (mean() - (1.96*stdDev())/(Math.sqrt(T))); //these equations were taken straight from the website
    }
    /*
    return the high endpoint of 95% confidence interval
     */
    //package private
    double confidenceHigh() // high endpoint of 95% confidence interval
    {
        return (mean() + (1.96*stdDev())/(Math.sqrt(T))); //these equations were taken straight from the website
    }
}
/*
 * UNION FIND CLASS
 * with path compression and weighted trees
 * (based on Robert Sedgewick's lectures)
 */
class UF //package private
{
    /*
    Class Variables (instance fields)
     */
    private int[] id; //array of ints representing sites
    private int[] sz; //the size of each tree/ node series

    /*
    CONSTRUCTOR
     */
    UF(int n) //package private
    {
        id = new int[n]; //for size n make n sites
        sz = new int[n]; //also make n indexes which correspond to n sites size
        for (int i = 0; i < n; i++)
        {
            id[i] = i; //set id of each object to itself
            sz[i] = 1; //initially every node is separate and therefore the size is 1
        }
    }
    /*
    FIND THE PARENT SITE
     */
    int find(int i) //package private
    {
        while (i != id[i]) //chase parent pointers until the root is reached
        {
            id[i] = id[id[i]]; //implement path compression
            //(above) just after computing the root of i (a node) set the
            //id of each examined node to point to that root
            //this keeps the tree almost completely flat
            i = id[i];
        }
        return i; //depth of i array accesses
    }
    /*
    Union Method
    (make a connection between two sites, one becomes a child of the other)
     */
    void union(int p, int q) { //package private
        int i = find(p);
        int j = find(q);
        if (i == j)
        {
            return; //no need for union, already connected just leave
        }
        //implement weighted quick-union
        // weighted implementation prioritizes the parents
        // of larger trees to be built on,
        // rather than smaller ones
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
}
/*
OUTPUT:
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=57606:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Personal Coding Library/Algorithms and Data Structures quarter 2/out/production/Algorithms and Data Structures quarter 2" com.jetbrains.Percolation_CK
Is an un-opened site open? false
Does the system percolate? false
Is an un-opened site full? false
Is an un-opened site open (next to open sites)? false
Is an un-opened site full (next to open sites)? false
Is an open site full (not next to open sites)? false
are 1,0 and 2,1 now connected? true
Is 2,1 now full? true
Does the system percolate? true
Is there back wash? true



When N = 200, T = 100
mean()                  = 0.5939715000000001
stdDev()                = 0.008670337232615165
confidenceLow()         = 0.5922721139024075
confidenceHigh()        = 0.5956708860975927

When N = 200, T = 100
mean()                  = 0.5923827500000001
stdDev()                = 0.008400456708814508
confidenceLow()         = 0.5907362604850724
confidenceHigh()        = 0.5940292395149277

When N = 2, T = 100000
mean()                  = 0.667005
stdDev()                = 0.1177315530441475
confidenceLow()         = 0.6662752922742267
confidenceHigh()        = 0.6677347077257733

When N = 500, T = 1000
mean()                  = 0.5930808800000004
stdDev()                = 0.004842723699575522
confidenceLow()         = 0.5927807248753975
confidenceHigh()        = 0.5933810351246033


Process finished with exit code 0
 */