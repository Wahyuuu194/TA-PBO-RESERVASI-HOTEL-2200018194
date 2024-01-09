public class DeluxeRoom extends HotelRoom {
    private int capacity;

    public DeluxeRoom(int roomNumber, String roomType, double pricePerNight, int capacity, String facilities) {
        super(roomNumber, roomType, pricePerNight, facilities);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void displayRoomInfo() {
        super.displayRoomInfo();
        System.out.println("Capacity: " + capacity + " person(s)");
    }
}
