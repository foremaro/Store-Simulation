import java.util.*;
import java.lang.*;
import java.text.*;
/********************************************
 * Simulate the arrival and departure of customers
 * throughout each day
 * 
 * @author  Ron Foreman 
 * @version 4/4/2015
 *******************************************/
public class Store
{
    /** average arrival time */
    private double avgArrivalTime;

    /** average service time */
    private double avgServiceTime;

    /** number of cashiers */
    private int numCashiers;

    /** current time within the simulation */
    private double now;

    /** display the optional queue of customers? */
    private boolean displayQ;

    /** customers waiting in line */
    private ArrayList<Customer> theLine;

    /** cashiers array*/
    private Customer[] cashiers;

    /** maintain list of future events */
    PriorityQueue<GVevent> toDo;

    /** random generator for arrival and service times */
    GVrandom rand;

    /** results string */
    private String results;

    /** opening time 7:00am */
    final int OPEN = 420;

    /** closing time 7:00pm */
    final int CLOSE = 1140;

    /** total customers served */
    private int totalCusts;

    /** cumulative customer wait time */
    private double totalWait;

    /** length of longest line */
    private int longestLine;

    /** time when longest line occurred */
    private double longestLineTime;

    /**********************************************************
    Constructor method to initialize instance variables
    and objects
    Default number of cashiers = 3
     **********************************************************/
    public Store () {
        GVrandom rand = new GVrandom();
        
        // default values 
        avgArrivalTime = 2.5;
        avgServiceTime = 6.6;
        numCashiers = 3;        

        now = OPEN;
        displayQ = false;
        
        // line instantiated
        theLine = new ArrayList<Customer>();
        
        // priority queue instantiated
        toDo = new PriorityQueue<GVevent>();
        
        results = "";
        totalCusts = 0;
        totalWait = 0.0;
        longestLine = 0;
        longestLineTime = 0;

        reset();

    }

    /**********************************************************
    Mutator method 
    @param c cashiers
    @param s avg service time
    @param a avg arrival time
    @param b optional customer queue to be displayed or not
     **********************************************************/
    public void setParameters (int c, double s, double a,
    boolean b) {
        numCashiers = c;
        avgServiceTime = s;
        avgArrivalTime = a;
        displayQ = b;

    }

    /*********************************************************
    @return number of cashiers
     *********************************************************/
    public int getNumCashiers () {
        return numCashiers;
    }

    /*********************************************************
    @return average arrival time
     *********************************************************/
    public double getArrivalTime() {
        return avgArrivalTime;
    }

    /*********************************************************
    @return average cashier service time
     *********************************************************/
    public double getServiceTime() {
        return avgServiceTime;         
    }

    /*********************************************************
    @return results of simulation
     *********************************************************/
    public String getResults() {
        return results;
    }

    /*********************************************************
    @return number of customers served in simulation
     *********************************************************/
    public int getNumCustomersServed() {
        return totalCusts;
    }

    /*********************************************************
    @return length of longest line
     *********************************************************/
    public int getLongestLineLength() {
        return longestLine;
    }

    /*********************************************************
    @return average wait time     
     **********************************************************/
    public double getAverageWait () {
        return totalWait/ totalCusts;

    }

    /*********************************************************
    reset all instance variables except params for cashiers,
    service time, and arrival time
     *********************************************************/
    private void reset() {
        rand = new GVrandom();
        cashiers = new Customer[numCashiers];
        now = OPEN;
        theLine.clear();
        toDo.clear();
        results = "";
        totalCusts = 0;
        totalWait = 0.0;
        longestLine = 0;
        longestLineTime = 0;

    }

    /*********************************************************
    returns a String representation of the provided time
    @param time 
    @return time formatted hh:mm am/pm
     *********************************************************/

    private String formatTime (double t) {
        String time = "";
        int hours = ((int)t / 60);
        int minutes = ((int)t % 60);
        
        // hours 
        if (hours ==0) {
            hours = 12;
            
        }        
        else if (hours > 12) {
            hours = hours - 12;
        }

        // minutes        
        if (minutes <= 9) {
            time = hours + ":0" + minutes;           
            
        }
        else {
            time = hours + ":" + minutes;
        }
        
        // am or pm
        if (t >= 720) {
            time += "pm";
        }
        else {
            time += "am";
        }
        
        return time;

    }

    /*********************************************************
    displays the cashiers and customers in line
     *********************************************************/
    private void displayQueue(){

        results += "\n" + formatTime(now) + " ";
        
        for (int i = 0; i < cashiers.length; i++) {        
            // if the cashier does not have a customer
            if (cashiers[i] == null) {
                results += "-";
            }
            // else cashier is occupied with customer 'C'
            else {
                results += "C";

            }
        }

        results += "";
        
        // adds asterisk for every customer in line
        for (int i = 0; i < theLine.size() - 1; i++) {
            results += "*";
        }

    }

    /*********************************************************
    returns a future time by adding current time to a random
    number 
    @param avg 
    @return future time
     *********************************************************/
    private double futureEventTime (double avg) {
        double future = Math.round(rand.nextNormal(avg));
        if (future < 0){
            future = 0;
        }
        return future + now;

    }

    /*********************************************************
    calculates the average wait time and adds to the results                                     
    variable
     *********************************************************/
    private void calcResults() {
        DecimalFormat decFmt = new DecimalFormat("0.0");

        results += "\n\nSIMULATION PARAMETERS\n";
        results += "Number of cashiers: " + numCashiers + "\n";
        results += "Average customer arrival: " + avgArrivalTime + "\n";
        results += "Average service time: " + avgServiceTime + "\n";
        results += "\nRESULTS\n";
        results += "Average wait time: " + decFmt.format(getAverageWait()) + " mins\n";
        results += "Max line length: " + longestLine + " at " + formatTime(longestLineTime) + "\n";
        results += "Customers served: " + totalCusts + "\n";
        results += "Last customer departure: " + formatTime(now);

    }

    /*********************************************************
    Available cashier
    @return index of cashier
    @return -1 if all cashiers are occupied
     *********************************************************/
    private int cashierAvailable() {
        // search through cashiers array
        for (int i = 0; i < cashiers.length ; i++) {        
            // if no customer @ cashier, return index of available cashier
            if(cashiers[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /*********************************************************
    moves a customer from front of line to cashier
    @param num index of available cashier
     *********************************************************/
    private void customerToCashier (int num) {
        // moves first cust in line to cashiers[num]
        cashiers[num] = theLine.remove(0);

        // total customers updated
        totalCusts += 1;

        // total wait time updated
        totalWait += now - cashiers[num].getArrivalTime();    

        // departure event and time generated
        double futureTime = futureEventTime(avgServiceTime);
        GVevent newDepart = new GVevent(GVevent.DEPARTURE, futureTime, num);

        // departure event added to priority queue
        toDo.add(newDepart);        

    }

    /*********************************************************
     * method simulates a customer getting in line at the 
     * prescribed time.  It invokes cashierAvailable() and
     * customerToCashier()
     * @param t time customer gets in line
     *********************************************************/
    public void customerArrives (double t){
        now = t;
        
        // new customer created and added to line
        Customer c = new Customer(t);
        theLine.add(c);

        // longest line
        if(theLine.size() > longestLine) {
            longestLine = theLine.size();
            longestLineTime = now;
        }
        
        // if cashier available
        int avail = cashierAvailable();
        if(avail >= 0 ){
            customerToCashier(avail);
        }

        // if current time less than closing
        double next = futureEventTime(avgArrivalTime);
        if (next < CLOSE) {
            GVevent newArrival = new GVevent(GVevent.ARRIVAL, next);
            toDo.add(newArrival);
        }

    }

    /*********************************************************
    This method simulates a customer completing a transaction 
    with the cashier and leaving the store. It invokes 
    customerToCashier()
    @param num cashier Array index
    @param t current time
     *********************************************************/
    public void customerDeparts (int num ,double t) {
        now = t;
        
        // if customer is waiting, move first customer to cashier[num]
        if (!theLine.isEmpty()) {
            customerToCashier(num);
        }
        // else set cashier[num] to null
        else {
            cashiers[num] = null;
        }

    }

    /*********************************************************
      Primary method thatcontrols the simulation from beginning \
      to end. Invokes service methods      
     **********************************************************/
    public void simulate() {
        reset();
        now = OPEN;
        totalCusts = 0;
        toDo = new PriorityQueue <GVevent> ();

        GVevent e = new GVevent(GVevent.ARRIVAL, OPEN);
        toDo.add(e);
        
        // while priority queue is not empty
        while (!toDo.isEmpty()) { 

            // get next event and update time
            e = toDo.poll();
            now = e.getTime();

            // if customer arrives
            if (e.isArrival()) {
                customerArrives(now);
            }

            // if customer leaves
            if (e.isDeparture()){
                customerDeparts(e.getID() , now);                
            }
            
            // if user wants queue displayed
            if(displayQ == true){
                displayQueue();
            }
            

        }

        calcResults();
    }

    /*********************************************************
     * main testing method
     **********************************************************/
    public static void main(String args[]) {
        Store s = new Store();
        s.setParameters(4, 12.0, 3.0, true);
        s.simulate();
        System.out.println(s.getResults());

    }
}

