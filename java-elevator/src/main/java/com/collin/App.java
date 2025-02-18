package com.collin;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        boolean running = true;
        Random random = new Random();

        // check if arguments are correct when running application
        if (args.length != 2) {
            System.out.println("Please enter the correct arguments. Correct Usage: java -jar numFloors elevatorSpeed");
            System.out.println("numFloors is the number of floors in the building");
            System.out.println("numElevators is the number of elevators in the building");
            return;
        }
        // set values based on arguments of start
        int numFloors = Integer.parseInt(args[0]);
        int numElevators = Integer.parseInt(args[1]);
        // create arry for elevators with a size equal to numElevators argument
        Elevator[] elevators = new Elevator[numElevators];

        // create elevators
        for(int i = 0; i < numElevators; i++) {
            Elevator elevator = new Elevator(numFloors, i);
            elevators[i] = elevator;
        }
        // instantiate building entity
        Building building = new Building(numFloors, elevators);

        // Run elevator moving logic in threads for 
        // each so they can run simultaniously
        for(Elevator elevator : elevators) {
            Thread elevatorRunning = new Thread() {
                public void run() {
                    try {
                        elevator.moveToNextFloor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            elevatorRunning.start();
        }

        /** run simulation logic
         * This simulates people calling elevator from a random floor
         * at random intervals
         * */ 
       while (running){
           // Generate an elevator call
           int test = random.nextInt(30);
           System.out.println();
           System.out.println();
           
           int randomFloor = random.nextInt(numFloors);
           building.elevatorCalled(randomFloor);
           System.out.println("Awaiting user input (" + test + " seconds)");
           TimeUnit.SECONDS.sleep(test);
       }
    }
}
