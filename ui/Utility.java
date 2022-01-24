package ui;

import java.util.Arrays;
import java.util.Scanner;

public class Utility {
    public static String checkForValidScanInput(String message, String[] output) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String outPutValue = scanner.nextLine();
        boolean contains = Arrays.stream(output).anyMatch(outPutValue::equals);
        while (!contains) {
            System.out.println("Please provide a valid input");
            System.out.println(message);
            outPutValue = scanner.nextLine();
            contains = Arrays.stream(output).anyMatch(outPutValue::equals);
        }
        return outPutValue;
    }
}
