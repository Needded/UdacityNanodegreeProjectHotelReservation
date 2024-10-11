package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.Collection;

public class HotelResource {

    private final ReservationService reservationService=new ReservationService();
    private final CustomerService customerService=new CustomerService();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createCustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom (String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail),room,checkInDate,checkOutDate);
    }

    public Collection <Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomersReservations(customerService.getCustomer(customerEmail));
    }

    public Collection <IRoom> findARoom(LocalDate checkInDate, LocalDate checkOutDate) {
         return reservationService.findRooms(checkInDate, checkOutDate);
    }
}
