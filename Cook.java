import java.math.*;
import java.util.*;

public class Cook
{
    private boolean deepFrier;
    private boolean cookTop;
    private boolean oven;


    /*
    Default Constructor 
    */
    Cook() {
        deepFrier = false;
        cookTop = false; 
        oven = false; 
    }

    /* 
    Constructor for Cook Class
    Param: bolDeep to see if this cook uses the deep frier
    bolTop to see if this cook uses the cook top
    bolOven to see if this cook uses the oven
    */
    Cook(boolean bolDeep, boolean bolTop, boolean bolOven){
        deepFrier = bolDeep;
        cookTop = bolTop; 
        oven = bolOven; 
    }//End Constructor 

    
    /* Get methods */ 
    public boolean getDeepFrier() { return deepFrier;  }
    public boolean getCookTop() { return cookTop;  }
    public boolean getOven() { return oven;  }

    /* Setter Methods */ 
    public void setDeepFrier(boolean value) { deepFrier = value;  }
    public void setCookTop(boolean value) { cookTop = value;  }
    public void setOven(boolean value) { oven = value;  }

    /*
    Gets the time it takes to use the deep frier using getNormal function
    Param: mean and standard Deviation as doubles
    */
    public double useDeepFrier(double mean, double standardDeviation) {
        if(deepFrier) {
            return getNormal(mean, standardDeviation);
        } else {
            throw new ArithmeticException("This cook is not set to use the Deep Frier");
        }
    }//useDeepFrier

    /*
    Gets the time it takes to use the cook top using getNormal function
    Param: mean and standard Deviation as doubles
    */
    public double useCookTop(double mean, double standardDeviation) {
        if(cookTop) {
            return getNormal(mean, standardDeviation);
        } else {
            throw new ArithmeticException("This cook is not set to use the cook top");
        }
    }//end useCookTop

    /*
    Gets the time it takes to use the oven using getNormal function
    Param: mean and standard Deviation as doubles
    */
    public double useOven(double mean, double standardDeviation) {
        if(oven){
            return getNormal(mean, standardDeviation);
        } else {
            throw new ArithmeticException("This cook is not set to use the oven");
        }
    }//useOven

    /*
    This function generates 1 random double in normal distribution.  
    Param: mean and standard Deviation as doubles
    */
    public static double getNormal(double mean, double standardDeviation){
        Random generator = new Random();
        //This gets the normal distribution with regards to the mean and standard deviation in the param and rounds the number and makes it positive.
        return (double)Math.round(Math.abs(mean + generator.nextGaussian() * standardDeviation) * 100) / 100; 
    }//end getNormal

}//end Class Cook