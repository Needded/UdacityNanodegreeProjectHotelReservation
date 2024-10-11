package model;

public class FreeRoom extends Room {


    public FreeRoom(String roomNumber, RoomType roomType, double price) {
        super(roomNumber, roomType, price);
        super.price=0;
    }

    @Override
    public String toString() {
        return "FreeRoom{}";
    }
}
