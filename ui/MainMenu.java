package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class MainMenu {

    public static void init() {
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

    public static void findAndReserveARoom() {
        Scanner scanner = new Scanner(System.in);
        boolean askAgain = true;
        do {
            System.out.println("Please enter a check in Date - mm/dd/yyyy (02/20/2021)");
            Date checkInDate = Utility.validateDate(scanner);

            System.out.println("Please enter a check out Date - mm/dd/yyyy (02/20/2021)");

            Date checkoutDate = Utility.validateDate(scanner);

            Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkoutDate);

            for (IRoom room: availableRooms) {
                System.out.println(room);
            }

            if (availableRooms.stream().count() == 0) {

                String messageNoRooms = "No Rooms Available for the given date. Press 1 for try again. Press 2 for main menu";
                String[] noRoomsOutPutArr = {"1", "2"};
                String noRoomsOutPut = Utility.checkForValidScanInput(messageNoRooms, noRoomsOutPutArr);

                int action = Integer.parseInt(noRoomsOutPut);
                if (action == 2) {
                    askAgain = false;
                    init();
                }
            } else {

                String isBookingMessage = "Would you like to book a room? y/n";
                String[] isBookingOutPutArr = {"y", "n"};
                String isBooking = Utility.checkForValidScanInput(isBookingMessage, isBookingOutPutArr);

                if (Objects.equals(isBooking, "n")) {
                    askAgain = false;
                    init();
                } else if (Objects.equals(isBooking, "y")) {

                    String isUserMessage = "Do you have an account with us? y/n";
                    String[] isUserOutPutArr = {"y", "n"};
                    String isUser = Utility.checkForValidScanInput(isUserMessage, isUserOutPutArr);
                    IRoom selectedRoom = null;
                    if (Objects.equals(isUser, "y")) {

                        System.out.println("Please enter your email");
                        String email = Utility.validateEmail(scanner);

                        Customer customer = HotelResource.getCustomer(email);

                        if (customer == null) {
                            System.out.println("There is no account associated with this email");
                            createAccountPrompt();
                            return;
                        }

                        System.out.println("What room number would you like to reserve?");
                        String roomNumber = scanner.nextLine();

                        boolean isValidRoom = false;

                        for (IRoom room : availableRooms) {
                            isValidRoom = Objects.equals(room.getRoomNumber(), roomNumber);
                            if (isValidRoom) {
                                selectedRoom = room;
                                break;
                            }
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
                    } else if (Objects.equals(isUser, "n")) {
                        //Create an account
                        createAccountPrompt();
                        return;
                    }
                }
            }
        } while (askAgain);
    }

    public static void createAccountPrompt() {
        String createAccMessage = "Would you like to create an account? y/n";
        String [] createAccOutPutArr = {"y", "n"};
        String isCreateAccount = Utility.checkForValidScanInput(createAccMessage, createAccOutPutArr);
        if (Objects.equals(isCreateAccount,"y")) {
            createAnAccount();
        } else if (Objects.equals(isCreateAccount,"n")) {
            init();
        }
    }

    public static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your email");
        String email = Utility.validateEmail(scanner);
        Customer customer = HotelResource.getCustomer(email);
        if(customer == null) {
            System.out.println("There is no account associated with this email");
            createAccountPrompt();
            return;
        }
        Collection<Reservation> list  = HotelResource.getCustomerReservations(email);
        for (Reservation res: list) {
            System.out.println(res);
        }
        init();
    }

    public static void createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter First Name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter Last Name");
        String lastName = scanner.nextLine();
        System.out.println("Please enter Email");
        String email = Utility.validateEmail(scanner);
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