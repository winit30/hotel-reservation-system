package ui;

import api.AdminResource;
import model.*;
import java.util.*;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void init() {
        printMenu();

        String message = "Please select a number for the menu option";
        String [] requiredOutPuts = {"1", "2", "3", "4", "5", "6"};
        String value = Utility.checkForValidScanInput(message, requiredOutPuts);

        int selection = Integer.parseInt(value);

        switch(selection) {
            case 1:
                seeAllCustomers();
                break;
            case 2:
                seeAllRooms();
                break;
            case 3:
                seeAllReservations();
                break;
            case 4:
                addARoom();
                break;
            case 6:
                MainMenu.init();
                break;
        }
    }

    public static void seeAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        for (IRoom room : allRooms) {
            System.out.println(room);
        }
        init();
    }

    public static void seeAllCustomers()  {
        Collection<Customer> customers = adminResource.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        init();
    }

    public static void seeAllReservations()  {
        Collection<Reservation> reservations = adminResource.displayAllReservations();
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
        init();
    }

    public static void addARoom() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a room number");
        String roomNumber = scanner.nextLine();

        System.out.println("Please enter Price");
        Double price = Double.valueOf(scanner.nextLine());

        String message = "Please enter 1 for Single bed and 2 for Double bed";
        String [] requiredOutPuts = {"1", "2"};
        int bedType = Integer.parseInt(Utility.checkForValidScanInput(message, requiredOutPuts));

        RoomType type = RoomType.SINGLE;
        if (bedType == 2) {
            type = RoomType.DOUBLE;
        }

        List<IRoom> rooms = new ArrayList<IRoom>();
        Room newRoom = new Room(roomNumber, price, type);
        rooms.add(newRoom);
        adminResource.addRoom(rooms);
        AdminMenu.addAnotherRoom();
    }

    public static void addAnotherRoom() {
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
    }
}
