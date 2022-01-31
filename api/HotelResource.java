package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {

    private static final HotelResource SINGLETON = new HotelResource();
    private static final int RECOMMENDED_ROOMS_DEFAULT_PLUS_DAYS = 7;
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        return SINGLETON;
    }

    public static Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return null;
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkoutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkoutDate);
    }

    public static Collection<Reservation> getCustomerReservations(String customerEmail) {
        return reservationService.getCustomerReservation(customerEmail);
    }

    public static Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }


    public static Collection<IRoom> findARoom(Date checkInDate, Date checkoutDate) {
        Collection<IRoom> availableRooms = findAvailableRooms(checkInDate,checkoutDate);
        return availableRooms;
    }

    public static ArrayList findAlternativeDates(Date checkInDate, Date checkoutDate) {
        ArrayList<Date> datesArray = new ArrayList<>();
        Collection<IRoom> availableRooms;
        Date newInDate = checkInDate;
        Date newOutDate = checkoutDate;

        do {
            newInDate = addDaysToDate(newInDate);
            newOutDate = addDaysToDate(newOutDate);
            availableRooms = findARoom(newInDate, newOutDate);
        } while(availableRooms.isEmpty());

        datesArray.add(newInDate);
        datesArray.add(newOutDate);

        return datesArray;
    }

    public static Date addDaysToDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, RECOMMENDED_ROOMS_DEFAULT_PLUS_DAYS);
        return cal.getTime();
    }

    public static Collection<IRoom> findAvailableRooms(Date in, Date out) {
        Collection<Reservation> reservations = reservationService.printAllReservation();
        Collection<IRoom> bookedRooms = new LinkedList<>();
        Collection<IRoom> availableRooms = new LinkedList<>();
        Collection<IRoom> allRooms = reservationService.getAllRooms();

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
