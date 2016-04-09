 import static org.junit.Assert.*;
 import org.junit.*;
 
 public class StoreTest
{
    private Store theStore;
    private double TOLERANCE = 0.1; 
    
    /******************************************************
     * Instantiate a Store object.
     *
     * Called before every test case method.
     *****************************************************/
    @Before
    public void setUp()
    {
        theStore = new Store();  
    }

    /******************************************************
     * Test initial values of the constructor
     *****************************************************/
    @Test 
    public void testConstructor(){

         // confirm the default number of cashiers
         Assert.assertEquals("Store(): should start with 3 cashiers ", 
                 theStore.getNumCashiers(), 3);                  
         // confirm the default number of cashiers
         Assert.assertEquals("Store(): should start with 6.6 service time  ", 
                 theStore.getServiceTime(), 6.6, TOLERANCE);                  
         // confirm the default number of cashiers
         Assert.assertEquals("Store(): should start with 2.4 arrival time ", 
                 theStore.getArrivalTime(), 2.5, TOLERANCE);                  

    } 

    /******************************************************
     * Test larger arrival times
     *****************************************************/
    @Test 
    public void testLargerArrivalTimes(){
        theStore.setParameters(3,12, 4, false);
        theStore.simulate();
        int previous = theStore.getNumCustomersServed();

        theStore.setParameters(3, 12, 6, false);
        theStore.simulate();
        int current = theStore.getNumCustomersServed();        
        // confirm new credits
        Assert.assertTrue("Should be fewer customers given larger arrival times", 
                previous > current); 
    }

    /******************************************************
     * Test larger arrival times
     *****************************************************/
    @Test 
    public void testLargerServiceTimes(){
        theStore.setParameters(3,12, 4, false);
        theStore.simulate();
        double previous = theStore.getAverageWait();

        theStore.setParameters(3, 16, 4, false);
        theStore.simulate();
        double current = theStore.getAverageWait();        
        // confirm new credits
        Assert.assertTrue("Should be longer wait given larger service times", 
                previous < current); 
    }    
    /******************************************************
     * Test set parameters
     *****************************************************/
    @Test 
    public void testSetParameters(){
        theStore.setParameters(6,14.5, 4.8, false);
    
        // confirm cashiers
        Assert.assertEquals("Number of cashiers should be reset", 
                theStore.getNumCashiers(), 6); 
        // confirm arrival time
        Assert.assertEquals("Arriva time should be reset", 
                theStore.getArrivalTime(), 4.8, 0.1); 
        // confirm cashiers
        Assert.assertEquals("Service time should be reset", 
                theStore.getServiceTime(), 14.5, 0.1); 
    }  

    /******************************************************
     * Test reset values
     *****************************************************/
    @Test 
    public void testResetParameters(){
        theStore.setParameters(3,14.5, 4.8, false);
        theStore.simulate();
        int previous = theStore.getLongestLineLength();

        theStore.setParameters(3,4.5, 4.8, false);
        theStore.simulate();
        int current = theStore.getLongestLineLength();
        
        // confirm cashiers
        Assert.assertTrue("Number of cashiers should be reset", 
                previous > current); 
    }  

    /******************************************************
     * Test with many cashiers
     *****************************************************/
    @Test 
    public void testWithManyCashiers(){
        theStore.setParameters(10,4.5, 4.8, false);
        theStore.simulate();
        
        // confirm cashiers
        Assert.assertTrue("There should be no line", 
                theStore.getLongestLineLength() < 2); 
        Assert.assertTrue("Average wait should be zero", 
                theStore.getAverageWait() < 1); 
    }   
    
    /******************************************************
     * Test fewer cashiers
     *****************************************************/
    @Test 
    public void testFewerCashiers(){
        theStore.setParameters(2,8, 4, false);
        theStore.simulate();
        int previous = theStore.getLongestLineLength();

        theStore.setParameters(4, 8, 4, false);
        theStore.simulate();
        int current = theStore.getLongestLineLength();        
        // confirm new credits
        Assert.assertTrue("Should be shorter line given more cashiers", 
                previous > current); 
    }
    
    
}     