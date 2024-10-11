package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    private Customer customer;
    private IRoom iRoom;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation(Customer customer, IRoom iRoom, LocalDate checkInDate, LocalDate checkOutDate) {
        this.customer = customer;
        this.iRoom = iRoom;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public IRoom getiRoom() {
        return iRoom;
    }

    public void setiRoom(IRoom iRoom) {
        this.iRoom = iRoom;
    }

    @Override
    public String toString() {
        return "\n Reservation{" +
                customer +
                ", Room= " + iRoom.getRoomNumber() +
                ", checkInDate= " + checkInDate +
                ", checkOutDate= " + checkOutDate + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) && Objects.equals(iRoom, that.iRoom) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, iRoom, checkInDate, checkOutDate);
    }
}
