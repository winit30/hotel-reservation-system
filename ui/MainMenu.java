package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    public static final Scanner scanner = new Scanner(System.in);
    private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    public static void init() {
        printMenu();
        String message = "Please select a number for the menu option";
        String [] requiredOutPuts = {"1", "2", "3", "4", "5"};
        String value = Utility.checkForValidScanInput(message, requiredOutPuts);

        int selection = Integer.parseInt(value);

        switch(selection) {
            case 1:
                findAndReserveARoom();
                break;
            case 2:
                seeMyReservations();
                break;
            case 3:
                createAnAccount();
                break;
            case 4:
                AdminMenu.init();
                break;
        }
    }

    public static void findAndReserveARoom() {
        Collection<IRoom> allRooms = hotelResource.getAllRooms();

        if(allRooms.isEmpty()) {
            System.out.println("Hotel does not have any rooms yet. Please check after sometime");
            System.out.println(" ");
            init();
        }

        System.out.println("Please enter a check in Date - dd/mm/yyyy (20/02/2021)");
        Date checkInDate = Utility.validateDate(scanner);

        System.out.println("Please enter a check out Date - dd/mm/yyyy (21/02/2021)");
        Date checkoutDate = Utility.validateDate(scanner);

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkoutDate);

        if (availableRooms.isEmpty()) {
            System.out.println("No Rooms Available for the given date. Finding alternative dates...");
            ArrayList<Date> alternativeDates = hotelResource.findAlternativeDates(checkInDate, checkoutDate);

            System.out.println(" ");
            String messageAltRooms = "Would you like to see rooms on alternative dates? y/n" + "\n" +
                    "checkInDate: " + formatter.format(alternativeDates.get(0)) +  "\n" +
                    "checkOutDate: " + formatter.format(alternativeDates.get(1));

            String[] noRoomsOutPutArr = {"y", "n"};
            String noRoomsOutPut = Utility.checkForValidScanInput(messageAltRooms, noRoomsOutPutArr);

            if (Objects.equals(noRoomsOutPut, "n")) {
                init();
            } else if (Objects.equals(noRoomsOutPut, "y")){
                Collection<IRoom> alternateRooms = hotelResource.findARoom(alternativeDates.get(0), alternativeDates.get(1));
                System.out.println(alternateRooms);
                startReservation(alternateRooms, alternativeDates.get(0), alternativeDates.get(1));
            }
        } else {
            startReservation(availableRooms, checkInDate, checkoutDate);
        }
    }

    public static void startReservation(Collection<IRoom> availableRooms, Date checkInDate, Date checkoutDate) {
        System.out.println(" ");
        for (IRoom room: availableRooms) {
            System.out.println(room);
        }
        System.out.println(" ");
        String isBookingMessage = "Would you like to book a room? y/n";
        String[] isBookingOutPutArr = {"y", "n"};
        String isBooking = Utility.checkForValidScanInput(isBookingMessage, isBookingOutPutArr);

        if (Objects.equals(isBooking, "n")) {
            init();
            return;
        } else if (Objects.equals(isBooking, "y")) {

            String isUserMessage = "Do you have an account with us? y/n";
            String[] isUserOutPutArr = {"y", "n"};
            String isUser = Utility.checkForValidScanInput(isUserMessage, isUserOutPutArr);

            if (Objects.equals(isUser, "y")) {

                System.out.println("Please enter your email");
                String email = Utility.validateEmail(scanner);

                Customer customer = hotelResource.getCustomer(email);

                if (customer == null) {
                    System.out.println("There is no account associated with this email");
                    createAccountPrompt();
                    return;
                }

                reserveARoom(email, availableRooms, checkInDate, checkoutDate);

            } else if (Objects.equals(isUser, "n")) {
                //Create an account
                createAccountPrompt();
                return;
            }
        }
    }

    public static void reserveARoom(String email, Collection<IRoom> availableRooms, Date checkInDate, Date checkoutDate) {
        IRoom selectedRoom = null;
        System.out.println("What room number would you like to reserve?");
        String roomNumber = scanner.nextLine();

        boolean isValidRoom = false;

        for (IRoom room : availableRooms) {
            isValidRoom = Objects.equals(room.getRoomNumber(), roomNumber);
            if (isValidRoom) {
                System.out.println(room.hashCode());
                selectedRoom = room;
                break;
            }
        }

        if (!isValidRoom) {
            System.out.println("Error: Invalid Room Number");
            reserveARoom(email, availableRooms, checkInDate, checkoutDate);
        } else {
            //reserve A Room here
            Reservation roomReserved = hotelResource.bookARoom(email, selectedRoom, checkInDate, checkoutDate);
            System.out.println(roomReserved);
            init();
        }
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
        Customer customer = hotelResource.getCustomer(email);
        if(customer == null) {
            System.out.println("There is no account associated with this email");
            createAccountPrompt();
            return;
        }
        Collection<Reservation> list  = hotelResource.getCustomerReservations(email);
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
        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Hello " + firstName + ", your account has been created");
        System.out.println(" ");
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