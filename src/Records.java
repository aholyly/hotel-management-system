/**
 * This class is used to save the information inside of "records.csv" file
 */
public class Records {

    private String username;
    private int roomId, status;

    /**
     * Contructor for Records
     * @param username username of user
     * @param roomId room id for hired room
     * @param status status of room: 0-empty 1-reserved 2-checked-in 3-checked-out
     */
    public Records(String username, int roomId, int status) {
        this.username = username;
        this.roomId = roomId;
        this.status = status;
    }

    /**
     * Getter for username
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for room id
     * @return room id of hired room
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Setter for room id
     * @param roomId room id of hired room
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Getter for room status
     * @return status of room
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter for room status
     * @param status of room
     */
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * toString method of Records class
     */
    @Override
    public String toString() {
        return        username +
                "," + roomId +
                "," + status +
                '\n';
    }
}
