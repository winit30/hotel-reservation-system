package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static Customer getCustomer() {
        return null;
    }

    public static void addRoom(List<IRoom> rooms) {
        for (IRoom room: rooms) {
            ReservationService.addRoom(room);
        }
    }

    public static Collection<IRoom> getAllRooms() {
        return ReservationService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    public static Collection<Reservation> displayAllReservations() {
        return ReservationService.printAllReservation();
    }
}
