package com.collin;

class Building {
    static int floors;
    private Elevator[] elevators;

    Building(int numFloors, Elevator[] buildingElevators) {
        floors = numFloors;
        elevators = buildingElevators;
    }

    void elevatorCalled(int callFloor){
        System.out.println("Someone has called an elevator on floor " + callFloor);
        for(int i = 0; i< elevators.length; i++) {
            if (elevators[i].downDirection && elevators[i].currentFloor > callFloor) {
                elevators[i].destinationSet(callFloor);
                break;
            } else if (!elevators[i].downDirection && elevators[i].currentFloor < callFloor) {
                elevators[i].destinationSet(callFloor);
                break;
            }
            if (i == elevators.length -1) {
                // if no elevator is on the way, just add it to the first elevator queue
                elevators[0].destinationSet(callFloor);
            }
        }
        int count = 0;
        for(Elevator elevator : elevators){
            System.out.println("Status for Elevator " + count);
            elevator.printElevatorStatus();
            count++;
        }
    }

}