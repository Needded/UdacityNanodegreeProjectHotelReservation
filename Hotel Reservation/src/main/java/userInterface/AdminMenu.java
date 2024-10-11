package userInterface;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminMenu {

    private final AdminResource adminResource=new AdminResource();
    private final HotelResource hotelResource=new HotelResource();
    RoomType roomType;
    Scanner sc = new Scanner(System.in);
    String input;

    public void adminMenu() {
        System.out.println("****************************************************************************************");
        System.out.println("1. See all customers.");
        System.out.println("2. See all rooms.");
        System.out.println("3. See all reservations.");
        System.out.println("4. Add a room.");
        System.out.println("5. Back to main menu.");
        System.out.println("****************************************************************************************\n");

        System.out.println("Choose an option by typing a number between 1 and 5.\n");
        input = sc.nextLine();

        switch (input) {
            case "1"://See all customers
                List<Customer> allCustomers;
                if(adminResource.getAllCustomers()==null) {
                    System.out.println("No customers found.");
                }else {
                    allCustomers = new ArrayList<>(adminResource.getAllCustomers());
                    allCustomers.forEach(System.out::println);
                }
                this.adminMenu();
                break;
            case "2"://See all rooms.
                List<IRoom> allRooms;
                if(adminResource.getAllRooms()==null){
                    System.out.println("No rooms found.");
                }else{
                    allRooms= new ArrayList<>(adminResource.getAllRooms());
                    allRooms.forEach(System.out::println);

                }
                this.adminMenu();
            case "3"://See all reservations.
                    adminResource.displayAllReservations();
                    this.adminMenu();
                break;
            case "4"://Add a room.
            	List <IRoom> roomNumbersList=new ArrayList<>();
            	
                System.out.println("Please enter a room number to add.");
                String roomNumber=InputValidation.checkExistingRoom(InputValidation.validInteger(sc.nextLine()));

                System.out.println("Please enter the room type: S for single or D for double.");
                RoomType validRoomType=InputValidation.validRoomType(InputValidation.validString(sc.nextLine()),roomType);

                System.out.println("Please enter the room price.");
                double roomPrice=(double) InputValidation.validDouble(sc.nextLine());

                roomNumbersList.add(new Room(roomNumber,validRoomType,roomPrice));
                adminResource.addRoom(roomNumbersList);

                System.out.println("Room added successfully.");

                this.adminMenu();
                break;
            case "5"://Back to main menu.
                new MainMenu().mainMenu();
                break;
            default:
                System.out.println("Please enter a valid option!");
                this.adminMenu();
                break;
        }

    }
}
