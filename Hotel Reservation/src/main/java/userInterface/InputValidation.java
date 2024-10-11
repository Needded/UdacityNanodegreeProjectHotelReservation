package userInterface;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.RoomType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidation {

    private final static HotelResource hotelResource = new HotelResource();
    private final static String emailRegEx = "^(.+)@(.+).(.+)$";
    private final static String dateRegEx = "\\d{4}/\\d{2}/\\d{2}";
    private final static String validString="[a-zA-Z]+";
    static Scanner sc = new Scanner(System.in);
    static Pattern datePattern = Pattern.compile(dateRegEx);
    static Pattern emailPattern = Pattern.compile(emailRegEx);
    static Pattern stringPattern = Pattern.compile(validString);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");


    public static String validString(String text) {

        while (!stringPattern.matcher(text).matches()) {
            System.out.println("Please enter a valid text.\n");
            text = sc.nextLine();
        }

        return text;
    }

    public static String validInteger(String number) {
        boolean isInteger=false;
        int newNumber;

        while(!isInteger){
            try {
                newNumber = Integer.parseInt(number);
                isInteger=true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                number = sc.nextLine();
            }
        }
        return number;
    }

    public static Double validDouble(String number) {

        boolean isDouble=false;
        double newNumber;

        while(!isDouble) {
            try {
                newNumber = Double.parseDouble(number);
                isDouble=true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                number = sc.nextLine();
            }
        }
        return Double.parseDouble(number);
    }

    public static String checkEmailInput(String email) {

        while(!emailPattern.matcher(email).matches()) {
                System.out.println("Please enter a valid email address.\n");
                email = sc.nextLine().trim();
        }
        return email;
    }

    public static String checkDateInput(String date) {

        while (!datePattern.matcher(date).matches()) {
                System.out.println("Please enter a valid date. (Format: YYYY/MM/DD).\n");
                date = sc.nextLine().trim();
        }
        return date;
    }

    public static LocalDate validDate(String checkInDate, String checkOutDate) {

        LocalDate checkIn;
        LocalDate checkOut;

        try {

            checkIn = LocalDate.parse(checkInDate);
            checkOut = LocalDate.parse(checkOutDate);

            while (checkOut.isBefore(checkIn) || checkIn.isEqual(checkOut)){
                System.out.println("Please enter a valid check out date. (Format: YYYY/MM/DD).");
                System.out.println("Check in date can not be equal or before check out date.\n");

                checkOutDate = sc.nextLine().trim();
                checkOut = LocalDate.parse(checkDateInput(checkOutDate),formatter);
            }
            return checkOut;

        }catch (DateTimeParseException e) {
            System.out.println("validDate");
            return  null;
        }

    }

    public static RoomType validRoomType(String room, RoomType roomType) {

        while ((!room.equals("S")&&!room.equals("s")) && ((!room.equals("D")&&!room.equals("d")))) {
            System.out.println("Please enter a valid room type.( S for single or D for double).\n");
            room = sc.nextLine();

            if (room.equals("S") || room.equals("s")) {
                return roomType = RoomType.SINGLE;
            } else if (room.equals("D") || room.equals("d")) {
                return roomType = RoomType.DOUBLE;
            }
        }
        if (room.equals("S")||room.equals("s")) {
            return roomType = RoomType.SINGLE;
        } else{
            return roomType = RoomType.DOUBLE;
        }

    }

    public static String checkExistingRoom(String roomNumber) {

        IRoom room= hotelResource.getRoom(roomNumber);

        while (room!=null && room.getRoomNumber().equals(roomNumber)) {
                System.out.println("Room already exists.\n");
                System.out.println("Type a different number\n");
                roomNumber = sc.nextLine().trim();
                room=hotelResource.getRoom(roomNumber);

        }
        return roomNumber;
    }

    public static String checkExistingEmail(String email, AdminResource adminResource) {

            while (adminResource.getCustomer(email)!=null){
                System.out.println("Email already exists.\n");
                System.out.println("Type a different email\n");
                email=sc.nextLine();
        }
            return email;
    }

    public static LocalDate isActualDate(String inputDate) {

        LocalDate date;

        try {

            date = LocalDate.parse(inputDate, formatter);

            while (date.isBefore(LocalDate.now())
            && !date.isEqual(LocalDate.now())) {

                System.out.println("Please enter a actual date. (Format: YYYY/MM/DD).\n");
                inputDate = sc.nextLine();
                date = LocalDate.parse(checkDateInput(inputDate),formatter);
            }

            return date;
        }catch (DateTimeParseException e) {
            System.out.println("isActualDate");
            return null;
        }
    }

	public static String singleOrDouble(String singleOrDouble) {
		
		 
		 while(!singleOrDouble.equals("s")&&!singleOrDouble.equals("d")) {
         	System.out.println("Wrong input. Type (s) for single or (d) for double bed room");
         	singleOrDouble=sc.nextLine();
         }
         
		return singleOrDouble;
	}

	public static RoomType formatRoomType(String singleOrDouble) {
		
		RoomType roomType;
		
		 if (singleOrDouble.equals("S") || singleOrDouble.equals("s")) {
             return roomType = RoomType.SINGLE;
         } else if (singleOrDouble.equals("D") || singleOrDouble.equals("d")) {
             return roomType = RoomType.DOUBLE;
         }
		
		return null;
	}
}

