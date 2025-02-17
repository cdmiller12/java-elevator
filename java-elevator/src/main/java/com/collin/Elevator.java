package com.collin;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import com.collin.Util;

public class Elevator {

    private int id = 0;
    private int speed = 5;
    private int numFloors = 0;
    private int waitTimePerFloor = 15;
    public int currentFloor = 0;
    public boolean downDirection = false;
    private boolean running = true;
    private PriorityQueue<Integer> downQueue = new PriorityQueue<Integer>(Collections.reverseOrder()); // reverse order priority
    private PriorityQueue<Integer> upQueue = new PriorityQueue<Integer>();


    public Elevator(int floors, int elevatorId) {
        numFloors = floors;
        id = elevatorId;
    }

    void destinationSet(int destinationFloor) {
        if (currentFloor > destinationFloor) {
            if (!downQueue.contains(destinationFloor)){ 
                System.out.println("Elevator " + id + " " + " has added floor " + destinationFloor + " as a destination.");
                downQueue.add(destinationFloor);
            }
        } else {
            if (!upQueue.contains(destinationFloor)){
                System.out.println("Elevator " + id + " " + " has added floor " + destinationFloor + " as a destination.");
                upQueue.add(destinationFloor);
            } 
        }
    }

    void moveToNextFloor() throws InterruptedException {
        while(running) {
            if(downDirection ? downQueue.peek() != null : upQueue.peek() != null) {
                int nextFloor = downDirection ? downQueue.peek() : upQueue.peek();
                System.out.println("Elevator " + id + " " + "Moving from floor " + currentFloor + " to floor " + nextFloor);
                currentFloor = nextFloor;
                TimeUnit.SECONDS.sleep(speed * Math.abs(currentFloor - nextFloor));
                
                if (downDirection) {
                    downQueue.remove();
                } else {
                    upQueue.remove();
                }
                System.out.println("Elevator " + id + " " + "Arrived at floor " + currentFloor);
                System.out.println("Elevator " + id + " " + "Waiting on new passengers to input destinations.");
                TimeUnit.SECONDS.sleep(waitTimePerFloor);
                List<Integer> floorsToAdd = Util.simulateUserAddingDestination(numFloors);
                for(int floor : floorsToAdd) {
                    destinationSet(floor);
                }
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