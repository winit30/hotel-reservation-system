package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static final ReservationService SINGLETON = new ReservationService();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return SINGLETON;
    }

    private final static Map<String, IRoom> rooms = new HashMap<String, IRoom>();
    private final static Map<String, ArrayList<Reservation>> reservations = new HashMap<String, ArrayList<Reservation>>();

    public static void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom() {
        return null;
    }

    public static Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkoutDate) {
        Reservation freshReservation = new Reservation(customer, room, checkInDate, checkoutDate);

        if (!reservations.containsKey(customer.getEmail())) {
            reservations.put(customer.getEmail(), new ArrayList<>());
        }
        reservations.get(customer.getEmail()).add(freshReservation);
        return freshReservation;
    }

    public static Collection<Reservation> getCustomerReservation(String email) {
        return reservations.get(email);
    }

    public static Collection<Reservation> printAllReservation() {
        Collection<Reservation> allReservations = new LinkedList<>();
        for (ArrayList<Reservation> res : reservations.values()) {
            allReservations.addAll(res);
        }
        return allReservations;
    }
}
