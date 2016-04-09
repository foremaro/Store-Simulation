/****************************************************
This object maintains the time and possibly the 
cashier number of a simulation event.  Two event types
are suported: ARRIVAL, DEPARTURE.  It is the responsibility
of a simulation to determine what arrivals and departures
mean.
<br><br>
An arrival event is instantiated with a time:
new GVevent(GVevent.ARRIVAL, 234.5);
<br><br>
A departure event is instantiated with a time and cashier 
number:
new GVevent(GVevent.DEPARTURE, 234.5, 2);

@author Scott Grissom
@version Oct 10, 2013 
****************************************************/

public class GVevent implements Comparable{
    /** time the event is to occur */
    private double myTime;
    
    /** which of two possible events is represented? */
    private int type;
    
    /** which cashier number is represented? */
    private int cashier;
    
    /** event type */
    public static int ARRIVAL = 1, DEPARTURE = 2;

/****************************************************
Constructor instantiates and event including the
optional cashier number.
@param type which type?
@param t time of the event
@param id cashier number
****************************************************/    
    public GVevent(int type, double t, int id){
        myTime = t;
        cashier = id;
        this.type = type;
    }
    
/****************************************************
Constructor instantiates and event.
@param type which type?
@param t time of the event
****************************************************/       
    public GVevent(int type, double t){
        myTime = t;
        this.type = type;
        cashier = -1;
    }
    
/****************************************************
Is this an arrival event?
@returns true if event is an Arrival
****************************************************/  
    public boolean isArrival(){
        return type == ARRIVAL;
    }

/****************************************************
Is this a departure event?
@returns true if event is a Departure
****************************************************/  
    public boolean isDeparture(){
        return type == DEPARTURE;
    }
    
/****************************************************
Which cashier?
@returns the cashier number
****************************************************/     
    public int getID(){
        return cashier;
    }
    
/****************************************************
At what time?
@returns the event time
****************************************************/     
    public double getTime(){
        return myTime;
    }
    
/******************************************************
 * This method is required for the Comparable interface
 * It returns a negative number if the first event is 
 * scheduled to occur before the second event.  It is 
 * not explicitely invoked within a program but instead
 * used when a comparison is necessary.
 * @param e is expected to be another GVevent
 *****************************************************/    
    public int compareTo(Object e){
        
        // cast to a GVevent
        GVevent otherEvent = (GVevent) e;
        
        // assume the events are equal until proven otherwise
        int result = 0;
        if (myTime > otherEvent.getTime()){
            result = 1;
        }else if (myTime < otherEvent.getTime()){
            result = -1;
        }
        return result;
    }
    
}