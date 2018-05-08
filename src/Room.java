/**
 * This class is used to save the information inside of "rooms.csv" file
 */
public class Room {

    private int id, capacity, price, status;

    /**
     * Constructor
     * @param id of room
     * @param capacity of room
     * @param price of room
     * @param status of room
     */
    public Room(int id, int capacity, int price, int status) {
        this.id = id;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
    }

    /**
     * Getter for capacity
     * @return value of capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Getter for id
     * @return value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for price
     * @return value of price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Getter for status
     * @return value of status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter for status
     * @param status of room
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * toString method of Room class
     */
    @Override
    public String toString() {
        return        id +
                "," + capacity +
                "," + price +
                "," + status +
                '\n';
    }
}
