package model;

import java.util.Objects;

public class Room implements IRoom {

    String roomNumber;
    Double price;
    RoomType enumeration;

    public Room(Double price) {
        this.price = price;
    }

    public Room(String roomNumber, double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(price, room.price) && enumeration == room.enumeration;
    }

    @Override
    public String toString() {
        return "Room {" +
                "Room Number='" + roomNumber + '\'' +
                ", Price=" + price +
                ", Room Type=" + enumeration +
                '}';
    }
}
