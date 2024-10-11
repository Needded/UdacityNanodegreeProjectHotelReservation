package service;

import api.HotelResource;
import model.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;


public class ReservationService {

    private static ReservationService reservationService;
    private static final Map <String, IRoom> rooms=new HashMap<>();
    private static final Set <Reservation> reservations=new HashSet<>();

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) {

        if(!rooms.containsKey(room.getRoomNumber())){
        rooms.put(room.getRoomNumber(),room);
        }else{
            System.out.println("Room ("+room.getRoomNumber()+") already exists.\n");
        }
    }

    public IRoom getARoom(String roomId) {

        try{
            return rooms.get(roomId);
        }catch(NumberFormatException e){
            return null;
        }
    }

    public Reservation reserveARoom(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);

        return reservation;
    }


    public Collection<IRoom> findRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        Set<IRoom> availableRooms = new HashSet<>(rooms.values());
        Set<IRoom> unavailableRooms = new HashSet<>();
    	
        // Has reservations.
        if (!reservations.isEmpty()) {
            // Loop through "Reservation" objects inside "reservations" Set.
            for (Reservation reservation : reservations) {
                // Loop through "Room" objects inside "rooms" Map.
                for (IRoom room : rooms.values()) {
                    // Check if the room on "rooms" and "reservation" are equals and if the dates overlap.
                    if (room.getRoomNumber().equals(reservation.getiRoom().getRoomNumber())
                            && !(checkOutDate.isBefore(reservation.getCheckInDate()) || checkInDate.isAfter(reservation.getCheckOutDate()))) {
                        // If the room is reserved within the given dates, add it to the set of unavailable rooms.
                        unavailableRooms.add(room);  
                      
                    }
                }
            }
            // Remove all unavailable rooms from the set of available rooms.
            availableRooms.removeAll(unavailableRooms);
            
        }

        return availableRooms;
    }


	public Collection <Reservation> getCustomersReservations(Customer customer) {
        List <Reservation> getReservations = new ArrayList<>();

        for(Reservation reservation : reservations) {
            if(reservation.getCustomer().equals(customer)) {
                getReservations.add(reservation);
            }
        }
        if(getReservations.isEmpty()){
            return null;
        }

        return getReservations;
    }

    public void printAllReservations() {
        if(!reservations.isEmpty()) {
            System.out.println(reservations);
        }else{
            System.out.println("No reservations found.\n");
        }

    }

    public Collection<IRoom> getAllRooms() {
        if(rooms.isEmpty()){
            return null;
        }
        return rooms.values();
    }
    

}
