package com.lawzoom.complianceservice;

public class Test {

    public static void main(String[] args) {
        int arr[] = {2, 5, 5, 9,8,9};

        int n=6;

        int m = 4;

        int max = 0;

        for (int i = 0; i <= n - m; i++) {


            int temp = 0;

            for (int j = 0; j < m; j++) {

                temp += arr[i + j];
            }


            if (max < temp) {
                max = temp;
            }
        }

        System.out.println(max);
    }
}
