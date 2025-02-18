package com.collin;

class Building {
    // number of floors in the building
    static int floors;
    // elevator objects
    private Elevator[] elevators;

    // Constructor
    Building(int numFloors, Elevator[] buildingElevators) {
        floors = numFloors;
        elevators = buildingElevators;
    }

    // Elevator has veen called from a certain floor
    void elevatorCalled(int callFloor){
        System.out.println("Someone has called an elevator on floor " + callFloor);
        // Determine which elevator to receive the request
        for(int i = 0; i< elevators.length; i++) {
            // if elevator is currently going down and the current floor is above the call floor
            if (elevators[i].downDirection && elevators[i].currentFloor > callFloor) {
                elevators[i].destinationSet(callFloor);
                break;
            }
            // if elevator is going up and the current floor is below the call floor
            else if (!elevators[i].downDirection && elevators[i].currentFloor < callFloor) {
                elevators[i].destinationSet(callFloor);
                break;
            }
            // otherwise add to first elevator queue
            if (i == elevators.length -1) {
                // if no elevator is on the way, just add it to the first elevator queue
                elevators[0].destinationSet(callFloor);
            }
        }
        // print out status of each elevator
        int count = 0;
        for(Elevator elevator : elevators){
            System.out.println("Status for Elevator " + count);
            elevator.printElevatorStatus();
            count++;
        }
    }

}