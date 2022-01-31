package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Reservation {
    Customer customer;
    IRoom room;
    Date checkInDate;
    Date checkoutDate;
    private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkoutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
    }

    public IRoom getRoom() {
        return this.room;
    }

    public Date getCheckInDate() {
        return this.checkInDate;
    }

    public Date getCheckOutDate() {
        return this.checkoutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) && Objects.equals(room, that.room) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkoutDate, that.checkoutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkoutDate);
    }

    @Override
    public String toString() {
        return "Reservation{" +  "\n" +
                "customer= " + customer + "\n" +
                ", room= " + room + "\n" +
                ", checkInDate= " + formatter.format(checkInDate) + "\n" +
                ", checkoutDate= "  + formatter.format(checkoutDate)  + "\n" +
                '}';
    }
}
