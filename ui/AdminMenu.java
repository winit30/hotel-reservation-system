package ui;

import api.AdminResource;
import model.*;

import java.text.ParseException;
import java.util.*;

public class AdminMenu {
    public static void init() throws ParseException {
        printMenu();

        String message = "Please select a number for the menu option";
        String [] requiredOutPuts = {"1", "2", "3", "4", "5", "6"};
        String value = Utility.checkForValidScanInput(message, requiredOutPuts);

        int selection = Integer.parseInt(value);

        if(selection == 1) {
            seeAllCustomers();
        } else if(selection == 2) {
            seeAllRooms();
        } else if(selection == 3) {
            seeAllReservations();
        } else if(selection == 4) {
            addARoom();
        } else if(selection == 5) {

        } else if (selection == 6) {
            MainMenu.init();
        }
    }

    public static void seeAllRooms() throws ParseException {
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        System.out.println(allRooms);
        init();
    }

    public static void seeAllCustomers() throws ParseException {
        Collection<Customer> customers = AdminResource.getAllCustomers();
        System.out.println(customers);
        init();
    }

    public static void seeAllReservations() throws ParseException {
        Collection<Reservation> reservations = AdminResource.displayAllReservations();
        System.out.println(reservations);
        init();
    }

    public static void addARoom() throws ParseException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a room number");
        String roomNumber = scanner.nextLine();

        System.out.println("Please enter Price");
        Double price = Double.valueOf(scanner.nextLine());

        System.out.println("Please enter 1 for Single room and 2 for Double room");
        int roomType = Integer.parseInt(scanner.nextLine());

        RoomType type = RoomType.SINGLE;
        if (roomType == 2) {
            type = RoomType.DOUBLE;
        }

        List<IRoom> rooms = new ArrayList<IRoom>();
        Room newRoom = new Room(roomNumber, price, type);
        rooms.add(newRoom);
        AdminResource.addRoom(rooms);
        AdminMenu.addAnotherRoom();
    }

    public static void addAnotherRoom() throws ParseException {
        System.out.println("Do you want to add more rooms? enter y/n");
        Scanner scanner = new Scanner(System.in);
        boolean keepAskingForRoom = true;
        while(keepAskingForRoom) {
            String moreRooms = scanner.next();
            if(Objects.equals(moreRooms, "y")) {
                addARoom();
                keepAskingForRoom = false;
            } else if (Objects.equals(moreRooms, "n")) {
                init();
                keepAskingForRoom = false;
            } else {
                System.out.println("Please enter y (YES) or n (NO) ");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Admin Menu");
        System.out.println("----------------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Add Test data");
        System.out.println("6. Back to Main Menu");
        System.out.println("----------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }
}