package com.collin;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import com.collin.Util;

public class Elevator {

    private int speed = 5;
    private int numFloors = 0;
    private int waitTimePerFloor = 15;
    private int currentFloor = 0;
    private boolean downDirection = false;
    private boolean running = true;
    private PriorityQueue<Integer> downQueue = new PriorityQueue<Integer>();
    private PriorityQueue<Integer> upQueue = new PriorityQueue<Integer>();


    public Elevator(int elevatorSpeed, int floors) {
        speed = elevatorSpeed;
        numFloors = floors;
    }

    void elevatorCalled(int callFloor){
        
        System.out.println("Elevator has been called by floor " + callFloor);
        if (currentFloor > callFloor) {
            if (!downQueue.contains(callFloor)) downQueue.add(callFloor);
        } else {
            if (!upQueue.contains(callFloor)) upQueue.add(callFloor);
        }
        printElevatorStatus();
    }

    void destinationSet(int destinationFloor) {
        System.out.println("Elevator has added floor " + destinationFloor + " as a destination.");
        if (currentFloor > destinationFloor) {
            if (!downQueue.contains(destinationFloor)) downQueue.add(destinationFloor);
        } else {
            if (!upQueue.contains(destinationFloor)) upQueue.add(destinationFloor);
        }
        printElevatorStatus();
    }

    void moveToNextFloor() throws InterruptedException {
        while(running) {
            if(downDirection ? downQueue.peek() != null : upQueue.peek() != null) {
            int nextFloor = downDirection ? downQueue.peek() : upQueue.peek();
            System.out.println("Moving from floor " + currentFloor + " to floor " + nextFloor);
            TimeUnit.SECONDS.sleep(speed * Math.abs(currentFloor - nextFloor));
            currentFloor = nextFloor;
    
            if (downDirection) {
                downQueue.remove();
            } else {
                upQueue.remove();
            }
            System.out.println("Arrived at floor " + currentFloor);
            System.out.println("Waiting on new passengers to input destinations.");
            TimeUnit.SECONDS.sleep(speed * Math.abs(currentFloor - nextFloor));
            List<Integer> floorsToAdd = Util.simulateUserAddingDestination(numFloors);
            for(int floor : floorsToAdd) {
                destinationSet(floor);
            }
            System.out.println("Waiting on new passengers to input destinations.");
            TimeUnit.SECONDS.sleep(speed * Math.abs(currentFloor - nextFloor));
        }
        }
    }

    void printElevatorStatus() {
        System.out.println("--------------");
        System.out.println("Elevator is currently moving " + (downDirection ? "down" : "up") + " to floor " + (downDirection ? downQueue.peek() : upQueue.peek()));
        System.out.println("Down Queue: " + downQueue);
        System.out.println("Up Queue: " + upQueue);
        System.out.println("--------------");
    }

    void stopElevator() {
        running = false;
    }
}