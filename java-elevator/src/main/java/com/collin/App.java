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
            System.out.println("elevatorSpeed is the number of seconds it takes to move one floor");
            return;
        }
        int numFloors = Integer.parseInt(args[0]);
        int elevatorSpeed = Integer.parseInt(args[1]);

        Elevator elevator = new Elevator(elevatorSpeed, numFloors);
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

        while (running){
            // Generate an elevator call
            int test = random.nextInt(30);
            System.out.println("Awaiting user input /(" + test + " seconds/)");
            TimeUnit.SECONDS.sleep(test);
            int randomFloor = random.nextInt(numFloors);
            elevator.elevatorCalled(randomFloor);
        }
    }
}
