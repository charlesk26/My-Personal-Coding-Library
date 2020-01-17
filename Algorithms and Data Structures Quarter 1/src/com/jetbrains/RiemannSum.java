package com.jetbrains;

public class RiemannSum
{
    public static void main(String[] args)
    {
        calcRiemanns();
        calcDeltaXn();
    }
    public static void calcDeltaXn()
    {
        double deltaF = 1.25;
        double[] functionVals = {8,8.75,6.75,5.25,4.75,4.5,4.25,4.15,4.5,5,5.75,6.5,8.5,9.875,15.25,17.25,34.5};
        double[] xVals = {.0007,.0028,.0062,.0108,.0164,.023,.0303,.038,.046,.054,.0617,.069,.0756,.0812,.0858,.0892,.0913};
        double[] thetas = {10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170};
        int n = xVals.length;
        double[] test = new double[17];
        double[] deltaXn = new double[xVals.length];
        double l = 0.046;
        double deltaTheta = Math.PI/90;
        for(int i = 0; i<thetas.length; i++)
        {
            thetas[i] *= Math.PI/180;
        }

        for(int i = 0; i<xVals.length;i++)
        {
            deltaXn[i] = (Math.abs(xVals[i]-(l-(l*Math.cos(thetas[i]+deltaTheta))))+Math.abs(xVals[i]-(l-(l*Math.cos(thetas[i]-deltaTheta)))))/2;
        }
        for(int i = 0; i<deltaXn.length; i++)
        {
            System.out.println("Δx"+(i+1)+" = ±"+deltaXn[i]+" meters");
        }
        double sumPlusL = 0;
        double sumMinusL = 0;
        double sumPlusR = 0;
        double sumMinusR = 0;
        for(int i = 0; i<n-1; i++)
        {
            sumPlusL += ((xVals[i+1]+deltaXn[i+1])-(xVals[i]+deltaXn[i]))*(functionVals[i]+deltaF);
            sumMinusL += ((xVals[i+1]-deltaXn[i+1])-(xVals[i]-deltaXn[i]))*(functionVals[i]-deltaF);
        }
        for(int i = 0; i<n-1; i++)
        {
            sumPlusR += ((xVals[i+1]+deltaXn[i+1])-(xVals[i]+deltaXn[i]))*(functionVals[i+1]+deltaF);
            sumMinusR += ((xVals[i+1]-deltaXn[i+1])-(xVals[i]-deltaXn[i]))*(functionVals[i+1]-deltaF);
        }
        double wL = .5776;
        double wR = .6651;
        double deltaWl = (Math.abs(wL-sumPlusL)+Math.abs(wL-sumMinusL))/2;
        double deltaWR = (Math.abs(wR-sumPlusR)+Math.abs(wR-sumMinusR))/2;
        //System.out.println(deltaWl+"\n"+deltaWR);
        double wNet = .6214;
        double dWNet = (Math.abs(wNet-((wL+deltaWl+wR+deltaWR)/2))+Math.abs(wNet-((wL-deltaWl+wR-deltaWR)/2)))/2;
        //System.out.println(dWNet);
        double v = 19.119;
        double mass = .0034;
        double dV = (Math.abs(v - Math.sqrt(2*(wNet+dWNet)/mass))+Math.abs(v - Math.sqrt(2*(wNet-dWNet)/mass)))/2;
        System.out.println("\n\nΔWL = ±"+deltaWl+" Joules \nΔWR = ±"+deltaWR+" Joules \nΔWN = ±"+dWNet+" Joules \nΔV = ±"+dV+" m/s");

    }
    public static void calcRiemanns()
    {
        double[] functionVals = {8,8.75,6.75,5.25,4.75,4.5,4.25,4.15,4.5,5,5.75,6.5,8.5,9.875,15.25,17.25,34.5};
        double[] xVals = {.0007,.0028,.0062,.0108,.0164,.023,.0303,.038,.046,.054,.0617,.069,.0756,.0812,.0858,.0892,.0913};
        double sumL = 0;
        double sumR = 0;
        int n = xVals.length;
        for(int i = 0; i<n-1; i++)
        {
            sumL += (xVals[i+1]-xVals[i])*functionVals[i];
        }
        for(int i = 0; i<n-1; i++)
        {
            sumR += (xVals[i+1]-xVals[i])*functionVals[i+1];
        }
        System.out.println("Left endpoint:"+sumL+"\nRight endpoint: "+sumR+"\n");
    }
}
