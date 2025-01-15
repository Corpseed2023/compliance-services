package com.lawzoom.complianceservice;

import java.util.HashSet;
import java.util.Set;

public class Test {


    boolean sum () {


        int[] arr = {3, 5, 3, 7, 1};

        int target = 10;

        Set<Integer> a = new HashSet<>();

        for (int num : arr) {

            if (a.contains(target - num)) {
                return true;
            }

            a.add(num);


        }
        return  false;
    }

    public static void main(String[] args) {

        Test test = new Test();

        boolean result = test.sum();

        System.out.println("Result: " + result); // Print the result



    }
}
