package userInterface;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;
import model.Room;
import model.RoomType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {

    private final AdminResource adminResource=new AdminResource();
    private final HotelResource hotelResource=new HotelResource();

    Scanner sc = new Scanner(System.in);
    String input;
    RoomType roomType;
    Collection<IRoom> availableRoomByType;
    LocalDate checkInDate;
    LocalDate checkOutDate;

    public void mainMenu() {
        System.out.println("****************************************************************************************");
        System.out.println("1. Find and reserve a room.");
        System.out.println("2. See my reservations.");
        System.out.println("3. Create an account.");
        System.out.println("4. Admin.");
        System.out.println("5. Exit.");
        System.out.println("****************************************************************************************\n");

        System.out.println("Choose an option by typing a number between 1 and 5.\n");
        input = sc.nextLine();

        switch (input) {
            case "1"://Find and reserve a room.

                System.out.println("If you are registered type your email.");
                System.out.println("If you are not registered type 1 to create a new account.");
                String email = sc.nextLine();

                //Create a new email.
                while (!InputValidation.emailPattern.matcher(email).matches()||hotelResource.getCustomer(email)==null) {
                    if(email.contains("1")) {
                        System.out.println("Type your name:");
                        String validName=InputValidation.validString(sc.nextLine().trim());

                        System.out.println("Type your last name:");
                        String validLastName=InputValidation.validString(sc.nextLine().trim());

                        System.out.println("Type your email:");
                        email=InputValidation.checkExistingEmail(InputValidation.checkEmailInput(sc.nextLine().trim()),adminResource);

                        hotelResource.createCustomer(validName, validLastName,email);
                        System.out.println("Account created successfully.");

                    }else{
                        System.out.println("Please, type a valid email address or type 1 to create a new account.\n");
                        email = sc.nextLine();
                    }
                }

                System.out.println("Type check-in date: (Format: Year/month/Day)");
                String checkInDateToValidate =String.valueOf(InputValidation.isActualDate(InputValidation.checkDateInput(sc.nextLine())));
                checkInDate =LocalDate.parse(checkInDateToValidate);

                System.out.println("Type check-out date: (Format: Year/month/Day)");
                String checkOutDateToValidate =String.valueOf(InputValidation.isActualDate(InputValidation.checkDateInput(sc.nextLine())));
                checkOutDate =InputValidation.validDate(checkInDateToValidate,checkOutDateToValidate);
                     		
                System.out.println("Type (s) for single or (d) for double bed room");
                RoomType singleOrDoubleInput=InputValidation.formatRoomType(InputValidation.singleOrDouble(sc.nextLine()));
                

                //Print the available rooms
                Collection<IRoom> roomsAvailable=hotelResource.findARoom(checkInDate,checkOutDate);
                
                Collection<IRoom> availableRoomByType = checkIfRoomAvailableMatchesType(roomsAvailable,checkInDate,checkOutDate,singleOrDoubleInput);

                

                //Choosing room for reservation.
                System.out.println("(Choose a room by typing it´s number)");
                String roomNumber =InputValidation.validInteger(sc.nextLine());

                boolean isValid=false;
                while (!isValid){
                    for(IRoom room : availableRoomByType){
                        if(room.getRoomNumber().equals(roomNumber)){
                            isValid=true;
                            break;
                        }
                    }
                    if(!isValid){
                        System.out.println("Please insert a valid room number that was recommended:");
                        for(IRoom room : availableRoomByType){
                            System.out.println(room);
                        }
                        roomNumber =InputValidation.validInteger(sc.nextLine());
                    }
                }

                //Reserve the room.
                Reservation reservation=hotelResource.bookARoom(email,hotelResource.getRoom(roomNumber),checkInDate,checkOutDate);
                System.out.println("Room booked successfully:");
                System.out.println(reservation.toString());

                this.mainMenu();
                break;
            case "2"://See my reservations.
                System.out.println("Please enter your email address:");
                String reservationEmail=InputValidation.checkEmailInput(sc.nextLine().trim());
                if(hotelResource.getCustomersReservations(reservationEmail)==null||hotelResource.getCustomersReservations(reservationEmail).isEmpty()){
                    System.out.println("No reservations found.");
                    
                }else{
                    for (Reservation reservation1:hotelResource.getCustomersReservations(reservationEmail)){
                        System.out.println(reservation1.toString());
                    }

                }
                this.mainMenu();
                break;
            case "3"://Create an account.
                System.out.println("Type your name:");
                String validName=InputValidation.validString(sc.nextLine().trim());

                System.out.println("Type your last name:");
                String validLastName=InputValidation.validString(sc.nextLine().trim());

                System.out.println("Type your email:");
                String newEmail=InputValidation.checkExistingEmail(InputValidation.checkEmailInput(sc.nextLine().trim()),adminResource);

                hotelResource.createCustomer(validName, validLastName,newEmail);
                System.out.println("Account created successfully.");

                this.mainMenu();
                break;
            case "4"://Admin.
                new AdminMenu().adminMenu();
                break;
            case "5"://Exit.
                System.exit(0);
                break;
            default:
                System.out.println("Please enter a valid option!");
                this.mainMenu();
                break;
        }

    }


	private Collection<IRoom> checkIfRoomAvailableMatchesType(Collection<IRoom> roomsAvailable, LocalDate checkInDate, LocalDate checkOutDate, RoomType singleOrDoubleInput) {
		
		availableRoomByType = new HashSet<>();
		
		//Check if the roomsAvailable is empty.
        if(roomsAvailable.isEmpty()){
           
        	//Try to suggest 7 days later.
            roomsAvailable=hotelResource.findARoom(checkInDate.plusDays(7),checkOutDate.plusDays(7));
            
		//Check if the availableRooms are empty even after the suggested days. 
        if(roomsAvailable.isEmpty()) {
        	
        	System.out.println("No rooms available on these dates and no suggestions for the next 7 days.");
        	this.mainMenu();
        	
        }	
      
        //Check if the room types available matches the room type requested.
        	for(IRoom room:roomsAvailable) {
            	
        		if(room.getRoomType().equals(singleOrDoubleInput)) {
            		
        			availableRoomByType.add(room);

        		}

        	}
            
        	if(availableRoomByType.isEmpty()) {
    			System.out.println("No rooms available on these dates with the type requested (Single/Double) and no suggestions for the next 7 days.");
    			this.mainMenu();
    		}
        	
        	this.checkInDate=this.checkInDate.plusDays(7);
        	this.checkOutDate=this.checkOutDate.plusDays(7);
        	System.out.println("There are no available rooms on theses dates with the type requested (Single/Double).");
        	System.out.println("Here an alternative room on: " + this.checkInDate + " - " + this.checkOutDate );
        	System.out.println("Rooms available:");

        	for(IRoom roomA : availableRoomByType){
                System.out.println(roomA);
            }

    //If the availableRooms is not empty on first search.    

    }else{
    	
    	//Check if the room types available matches the room type requested.
    	for(IRoom room:roomsAvailable) {
        	
    		if(room.getRoomType().equals(singleOrDoubleInput)) {
        		
    			availableRoomByType.add(room);

    		}

    	}
    	//If the room type doesn't matches the request.
    	if(availableRoomByType.isEmpty()) {
			
			//Try to suggest 7 days later.
            roomsAvailable=hotelResource.findARoom(checkInDate.plusDays(7),checkOutDate.plusDays(7));
            
            if(roomsAvailable.isEmpty()) {
            	System.out.println("No rooms available on these dates and no suggestions for the next 7 days.");
            	this.mainMenu();
            }
            
            
            for(IRoom room:roomsAvailable) {
            	
        		if(room.getRoomType().equals(singleOrDoubleInput)) {
            		
        			availableRoomByType.add(room);

        		}

        	}
            
            if(availableRoomByType.isEmpty()) {
            	System.out.println("No rooms available on these dates with the type requested (Single/Double) and no suggestions for the next 7 days.");
            	this.mainMenu();
            }
            
            this.checkInDate=this.checkInDate.plusDays(7);
        	this.checkOutDate=this.checkOutDate.plusDays(7);
        	System.out.println("There are no available rooms on theses dates with the room type (Single/Double) requested.");
        	System.out.println("Here an alternative room on: " + this.checkInDate + " - " + this.checkOutDate );
        	System.out.println("Rooms available:");
        	for(IRoom room : availableRoomByType){
                System.out.println(room);
            }
            
		}else {
    	
    	System.out.println("Rooms available:");
      for(IRoom room1 : availableRoomByType){
          System.out.println(room1);
      }
      }
    	
    }
		return availableRoomByType;
        }
	

}
