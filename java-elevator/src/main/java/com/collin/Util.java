package com.collin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
    public static List<Integer> simulateUserAddingDestination(int floorMax) {
        Random random = new Random();
        List<Integer> floorDestinationsToAdd = new ArrayList<Integer>();
        // simulates number of people (no more than 3 for testing purposes
        int numOfPassengers = random.nextInt(3);
        for(int i = 0; i < numOfPassengers; i++){
            //generate random floor destination
            floorDestinationsToAdd.add(random.nextInt(floorMax));
        }
        return floorDestinationsToAdd;
    }
}
