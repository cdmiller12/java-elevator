package com.collin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }


    /**
     * Elevator is on floor 5
     * Someone calls elevator to floor 4
     * Someone calls elevator to floor 2
     * Expect queue to be [4,2]
     */
    public void testElevatorDownLogic()
    {
        System.out.println("");
        System.out.println("testElevatorDownLogic");
        System.out.println("");
        Elevator elevator1 = new Elevator(6, 0);
        Elevator[] elevators = new Elevator[1];
        elevator1.currentFloor = 5;
        elevators[0] = elevator1;
        Building building = new Building(6, elevators);
        building.elevatorCalled(4);
        building.elevatorCalled(2);
        assertTrue( elevator1.downQueue.size() == 2 );
        assertTrue( elevator1.downQueue.peek() == 4 );
    }

    

    /**
     * Elevator 1 is on floor 6 going down / Down queue is [4,1]
     * Elevator 2 is on floor 4 going up / Up queue is [6]
     * Someone calls elevator to floor 2
     * Expect Elevator 1 to add to down queue and for it to be [4,1,2]
     */
    public void test2ElevatorsWithDownLogic()
    {
        System.out.println("");
        System.out.println("test2ElevatorsWithDownLogic");
        System.out.println("");
        Elevator elevator1 = new Elevator(6, 0);
        Elevator elevator2 = new Elevator(6, 1);
        Elevator[] elevators = new Elevator[2];
        elevator1.currentFloor = 6;
        elevator1.downDirection = true;
        elevator1.downQueue.add(1);
        elevator1.downQueue.add(4);
        elevator2.currentFloor = 4;
        elevator2.downDirection = false;
        elevator2.upQueue.add(6);
        elevators[0] = elevator1;
        elevators[1] = elevator2;
        Building building = new Building(6, elevators);
        building.elevatorCalled(2);
        assertTrue( elevator1.downQueue.size() == 3 );
        assertTrue( elevator1.downQueue.contains(4));
        assertTrue( elevator1.downQueue.contains(1));
        assertTrue( elevator1.downQueue.contains(2));

        
        assertTrue( elevator2.upQueue.contains(6));
    }

    
    /**
     * Elevator 1 is on floor 6 going down / Down queue is [4,1]
     * Elevator 2 is on floor 4 going up / Up queue is [6]
     * Someone calls elevator to floor 8
     * Expect Elevator 2 to add to up queue and for it to be [6,8]
     */
    public void test2ElevatorsWithUpLogic()
    {
        System.out.println("");
        System.out.println("test2ElevatorsWithUpLogic");
        System.out.println("");
        Elevator elevator1 = new Elevator(9, 0);
        Elevator elevator2 = new Elevator(9, 1);
        Elevator[] elevators = new Elevator[2];
        elevator1.currentFloor = 6;
        elevator1.downDirection = true;
        elevator1.downQueue.add(1);
        elevator1.downQueue.add(4);
        elevator2.currentFloor = 4;
        elevator2.downDirection = false;
        elevator2.upQueue.add(6);
        elevators[0] = elevator1;
        elevators[1] = elevator2;
        Building building = new Building(6, elevators);
        building.elevatorCalled(8);
        assertTrue( elevator2.upQueue.size() == 2 );
        assertTrue( elevator2.upQueue.contains(6));
        assertTrue( elevator2.upQueue.contains(8));

        assertTrue( elevator1.downQueue.contains(4));
        assertTrue( elevator1.downQueue.contains(1));

        
    }
}
