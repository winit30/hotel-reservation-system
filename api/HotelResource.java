package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class HotelResource {
    public static Customer getCustomer(String email){
        return CustomerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return null;
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkoutDate) {
        Customer customer = getCustomer(customerEmail);
        return ReservationService.reserveARoom(customer, room, checkInDate, checkoutDate);
    }

    public static Collection<Reservation> getCustomerReservations(String customerEmail) {
        return ReservationService.getCustomerReservation(customerEmail);
    }

    public static Collection<IRoom> findARoom(Date checkInDate, Date checkoutDate) {
        return findBookedRooms(checkInDate,checkoutDate);
    }

    public static Collection<IRoom> findBookedRooms(Date in, Date out) {
        Collection<Reservation> reservations = ReservationService.printAllReservation();
        Collection<IRoom> bookedRooms = new LinkedList<>();
        Collection<IRoom> availableRooms = new LinkedList<>();
        Collection<IRoom> allRooms = ReservationService.getAllRooms();

        for (Reservation reservation : reservations) {
            if(in.before(reservation.getCheckOutDate())  && out.after(reservation.getCheckInDate())) {
                bookedRooms.add(reservation.getRoom());
            }
        }

        for (IRoom room: allRooms) {
            if(!bookedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

}
