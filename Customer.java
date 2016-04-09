import java.util.*;
/**************************************************
 * To maintain the time customer arrived in line
 * 
 * @author Ron Foreman 
 * @version 4/4/2015
 **************************************************/

public class Customer
{

    /** arrival time */
    private double arrivalTime;

    /******************************************
    constructor method to initialize customer
    @param t
     ******************************************/
    public Customer (double t) {
        arrivalTime = t;
    }

    /******************************************
    set arrival time
    @param t
     ******************************************/
    public void setArrivalTime (double t) {
        arrivalTime = t;
    }

    /******************************************
    @return arrival time
     ******************************************/
    public double getArrivalTime () {
        return arrivalTime;

    }
    
    /******************************************
      main test method
     *******************************************/
     public static void main (String args []) {
         Customer c = new Customer(350);
         assert(350 == c.getArrivalTime()): "check constructor";
         c.setArrivalTime(500);
         assert(500==c.getArrivalTime()): "check setArrival";
         System.out.println("All tests pass!");

        }
    }
