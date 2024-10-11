package model;

public class Room implements IRoom{

    private String roomNumber;
    private RoomType roomType;
    double price;

    public Room(String roomNumber, RoomType roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }


    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room{" +
                " Room number= " + roomNumber +
                ", Room type= " + roomType +
                ", Room price= " + price +
                " }";
    }
}
