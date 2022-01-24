package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    public static void init() throws ParseException {
        printMenu();

        String message = "Please select a number for the menu option";
        String [] requiredOutPuts = {"1", "2", "3", "4", "5"};
        String value = Utility.checkForValidScanInput(message, requiredOutPuts);

        int selection = Integer.parseInt(value);
        if (selection == 1) {
            findAndReserveARoom();
        } else if (selection == 2) {
            seeMyReservations();
        } else if (selection == 3) {
            createAnAccount();
        } else if (selection == 4) {
            AdminMenu.init();
        }
    }

    public static void findAndReserveARoom() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
        boolean askAgain = true;
        do {
            System.out.println("Please enter a check in Date - mm/dd/yyyy (02/20/2021)");
            String cInDate = scanner.nextLine();
            Date checkInDate = formatter.parse(cInDate);

            System.out.println("Please enter a check out Date - mm/dd/yyyy (02/20/2021)");

            String cOutDate = scanner.nextLine();
            Date checkoutDate = formatter.parse(cOutDate);

            Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkoutDate);

            System.out.println(availableRooms);

            if(availableRooms.stream().count() == 0) {

                String messageNoRooms = "No Rooms Available for the given date. Press 1 for try again. Press 2 for main menu";
                String [] noRoomsOutPutArr = {"1", "2"};
                String noRoomsOutPut = Utility.checkForValidScanInput(messageNoRooms, noRoomsOutPutArr);

                int action = Integer.parseInt(noRoomsOutPut);
                if (action == 2) {
                    askAgain = false;
                    init();
                }
            } else {

                String isBookingMessage = "Would you like to book a room? y/n";
                String [] isBookingOutPutArr = {"y", "n"};
                String isBooking = Utility.checkForValidScanInput(isBookingMessage, isBookingOutPutArr);

                if(Objects.equals(isBooking, "n")) {
                    askAgain = false;
                    init();
                } else if (Objects.equals(isBooking, "y")){

                    String isUserMessage = "Do you have an account with us? y/n";
                    String [] isUserOutPutArr = {"y", "n"};
                    String isUser = Utility.checkForValidScanInput(isUserMessage, isUserOutPutArr);
                    IRoom selectedRoom = null;
                    if (Objects.equals(isUser, "y")) {

                        System.out.println("Please enter your email");
                        String email = scanner.nextLine();

                        Customer customer = HotelResource.getCustomer(email);

                        System.out.println(customer);

                        if(customer == null) {
                            System.out.println("There is no account associated with this email");
                            createAccountPrompt();
                            return;
                        }

                        System.out.println("What room number would you like to reserve?");
                        String roomNumber = scanner.nextLine();

                        boolean isValidRoom = false;

                        for (IRoom room: availableRooms) {
                            isValidRoom = Objects.equals(room.getRoomNumber(), roomNumber);
                            System.out.println(room.getRoomNumber() + " " + roomNumber);
                            if(isValidRoom) {
                                selectedRoom = room;
                                break;
                            };
                        }

                        if (!isValidRoom) {
                            System.out.println("Error: Invalid Room Number");
                            askAgain = false;
                            init();
                        } else {
                            //reserve A Room here
                            Reservation roomReserved = HotelResource.bookARoom(email, selectedRoom, checkInDate, checkoutDate);
                            System.out.println(roomReserved);
                            init();
                            askAgain = false;
                        }
                    } else if(Objects.equals(isUser, "n")) {
                        //Create an account
                        createAccountPrompt();
                        return;
                    }
                }
            }
        } while (askAgain);
    }

    public static void createAccountPrompt() throws ParseException {
        String createAccMessage = "Would you like to create an account? y/n";
        String [] createAccOutPutArr = {"y", "n"};
        String isCreateAccount = Utility.checkForValidScanInput(createAccMessage, createAccOutPutArr);
        if (Objects.equals(isCreateAccount,"y")) {
            createAnAccount();
        } else if (Objects.equals(isCreateAccount,"n")) {
            init();
        }
    }

    public static void seeMyReservations() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your email");
        String email = scanner.nextLine();
        Customer customer = HotelResource.getCustomer(email);

        System.out.println(customer);

        if(customer == null) {
            System.out.println("There is no account associated with this email");
            createAccountPrompt();
            return;
        }
        Collection<Reservation> list  = HotelResource.getCustomerReservations(email);
        System.out.println(list);

        init();
    }

    public static void createAnAccount() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter First Name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter Last Name");
        String lastName = scanner.nextLine();
        System.out.println("Please enter Email");
        String email = scanner.nextLine();
        HotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Hello " + firstName + ", your account has been created");
        init();
    }

    public static void printMenu() {
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println(" ");
        System.out.println("----------------------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("----------------------------------------------------");
    }

}