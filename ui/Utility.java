package ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    public static Date validateDate(Scanner scanner) {
        DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
        boolean keepAsking = true;
        String input = null;
        Date newDate = new Date();
        while (keepAsking) {
            try {
                input = scanner.nextLine();
                newDate = formatter.parse(input);
                keepAsking = false;
            } catch (ParseException e) {
                e.getLocalizedMessage();
                System.out.println("Please provide valid date");
            }
        }
        return newDate;
    }

    public static String validateEmail(Scanner scanner) {
        String reg = "^(.+)@(.+).(.+)$";
        String input = null;
        Pattern pattern = Pattern.compile(reg);
        boolean keepAsking = true;
        while (keepAsking) {
            input = scanner.nextLine();
            if(pattern.matcher(input).matches()) {
                keepAsking = false;
            } else {
                System.out.println("Please provide valid email");
            }
        }
        return input;
    }
}
