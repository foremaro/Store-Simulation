import java.util.*;
/***********************************************************
This class extends Random.  Therefore, all methods 
provided in the Random class can also be invoked on
a GVrandom object.  However, an additional method is provided
in GVrandom to generate random number about a desired
mean using a modified gaussian distribution.

@author Scott Grissom
@version Oct 1, 2013
 *********************************************************/
public class GVrandom extends Random{
    /** modifies the normal curve by a ratio */
    private final double SD = 0.5;

    /** nothing happens in the constructor */
    public GVrandom(){
    }

    /*********************************************************
    Generates random numbers about a desired mean using a
    modifed gaussian distriution.
    @param mean the desired mean
     *********************************************************/ 
    public double nextNormal(double mean){
        double SD = mean * 0.5;
        double nextTime = SD * nextGaussian() + mean;
        return nextTime;
    }

    /*********************************************************
    Main method used for testing
     *********************************************************/     
    public static void main(String args[]){
        GVrandom r = new GVrandom();
        double total = 0;
        int MAX = 100;
        for(int i=1; i<= MAX; i++){
            double val = r.nextNormal(20.5);
            System.out.println(val);
            total += val;
        }

        System.out.println("Average: " + total/MAX);    
    }
}