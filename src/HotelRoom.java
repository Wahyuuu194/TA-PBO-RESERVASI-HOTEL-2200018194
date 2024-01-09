public class HotelRoom {
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private String facilities;

    public HotelRoom(int roomNumber, String roomType, double pricePerNight, String facilities) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public String getFacilities() {
        return facilities;
    }

    public void displayRoomInfo() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Facilities: " + facilities);
        System.out.println("Price per Night: $" + pricePerNight);
    }
}
