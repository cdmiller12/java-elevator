package com.collin;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        boolean running = true;
        Random random = new Random();

        if (args.length != 2) {
            System.out.println("Please enter the correct arguments. Correct Usage: java -jar numFloors elevatorSpeed");
            System.out.println("numFloors is the number of floors in the building");
            System.out.println("numElevators is the number of elevators in the building");
            return;
        }
        int numFloors = Integer.parseInt(args[0]);
        int numElevators = Integer.parseInt(args[1]);
        Elevator[] elevators = new Elevator[numElevators];

        for(int i = 0; i < numElevators; i++) {
            Elevator elevator = new Elevator(numFloors, i);
            elevators[i] = elevator;
        }
        Building building = new Building(numFloors, elevators);

        for(Elevator elevator : elevators) {
            Thread elevatorRunning = new Thread() {
                public void run() {
                    try {
                        elevator.moveToNextFloor();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            elevatorRunning.start();
        }

        //building.elevatorCalled(4);
        //building.elevatorCalled(2);
        //building.elevatorCalled(1);
//
        //int test = 0;

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
