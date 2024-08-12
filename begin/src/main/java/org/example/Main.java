package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        simple();
    }

    public static void simple() {
        int n;
        Scanner cin = new Scanner(System.in);
        System.out.println("Розмір масиву");
        System.out.print("->_");
        n=cin.nextInt();
        int [] array = new int[n];
        for (int i=0;i<n;i++)
            array[i] = randValue(25,59);
        for (int item : array)
            System.out.printf("%d\t",item);
        System.out.println();

        int count =0;
        int i=0;
        while(i<n) {
            if(array[i]>45)
                count++;
            i++;
        }

        System.out.printf("Осіб старше 45 = %d\n", count);
    }

    public static int randValue(int min, int max) {
        Random random = new Random();
        // Generate random integer between min (inclusive) and max (inclusive)
        return random.nextInt((max - min) + 1) + min;
    }
}