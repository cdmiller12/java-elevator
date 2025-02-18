package com.collin;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

public class Elevator {

    // elevator ID for logging
    private int id = 0;
    // speed at which the elevator moves
    private int speed = 5;
    // number of floors in the building
    private int numFloors = 0;
    // how long to wait at each floor to allow users to board
    private int waitTimePerFloor = 15;
    // current floor of the elevator
    public int currentFloor = 0;
    // direction the elevator is going (false = up / true = down)
    public boolean downDirection = false;
    // running flag, used to keep movement logic running
    private boolean running = true;
    // queue containing the floors to move to when moving down
    public PriorityQueue<Integer> downQueue = new PriorityQueue<Integer>(Collections.reverseOrder()); // reverse order priority
    // queue containing the floors to move to when moving up
    public PriorityQueue<Integer> upQueue = new PriorityQueue<Integer>();


    // constuctor
    public Elevator(int floors, int elevatorId) {
        numFloors = floors;
        id = elevatorId;
    }

    // add new destination to queue
    void destinationSet(int destinationFloor) {
        // if the current floor is above destination floor
        if (currentFloor > destinationFloor) {
            // if floor isnt already in the queue
            if (!downQueue.contains(destinationFloor)){ 
                System.out.println("Elevator " + id + " " + " has added floor " + destinationFloor + " as a destination.");
                downQueue.add(destinationFloor);
            }
        } 
        else {
            if (!upQueue.contains(destinationFloor)){
                System.out.println("Elevator " + id + " " + " has added floor " + destinationFloor + " as a destination.");
                upQueue.add(destinationFloor);
            } 
        }
    }

    // run logic for elevator
    void moveToNextFloor() throws InterruptedException {
        // while running flag is true
        while(running) {
            // if elevator is moving down and down queue isnt empty
            // OR
            // if elevator is moving up and up queue isnt empty
            if(downDirection ? downQueue.peek() != null : upQueue.peek() != null) {
                // determine next floor
                int nextFloor = downDirection ? downQueue.peek() : upQueue.peek();
                System.out.println("Elevator " + id + " " + "Moving from floor " + currentFloor + " to floor " + nextFloor);
                currentFloor = nextFloor;
                TimeUnit.SECONDS.sleep(speed * Math.abs(currentFloor - nextFloor));
                // remove floor from queue
                if (downDirection) {
                    downQueue.remove();
                } else {
                    upQueue.remove();
                }
                System.out.println("Elevator " + id + " " + "Arrived at floor " + currentFloor);
                System.out.println("Elevator " + id + " " + "Waiting on new passengers to input destinations.");
                // wait for users to board
                TimeUnit.SECONDS.sleep(waitTimePerFloor);
                // simulate users adding destinations inside elevator
                List<Integer> floorsToAdd = Util.simulateUserAddingDestination(numFloors);
                // add user input to queue
                for(int floor : floorsToAdd) {
                    destinationSet(floor);
                }
                // determine if elevator needs to switch directors (up/down)
                if(!downDirection && upQueue.size() == 0) {
                    downDirection = true;
                }
                if(downDirection && downQueue.size() == 0) {
                    downDirection = false;
                }
            }
        }
    }

    void printElevatorStatus() {
        System.out.println("--------------");
        System.out.println("Elevator " + id + " " + "is currently moving " + (downDirection ? "down" : "up") + " to floor " + (downDirection ? downQueue.peek() : upQueue.peek()));
        System.out.println("Elevator " + id + " " + "Down Queue: " + downQueue);
        System.out.println("Elevator " + id + " " + "Up Queue: " + upQueue);
        System.out.println("--------------");
    }

    void stopElevator() {
        running = false;
    }
}