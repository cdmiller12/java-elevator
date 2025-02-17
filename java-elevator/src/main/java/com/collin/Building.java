package com.collin;

class Building {
    static int floors;
    private Elevator elevator;

    Building(int numFloors, Elevator buildingElevator) {
        floors = numFloors;
        elevator = buildingElevator;
    }


}