import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the abstract class for second panel after login
 */
public abstract class HotelLoginPanelAbstract implements HotelLoginPanel {

    protected User user;
    protected int choice;
    protected ArrayList<Room> rooms;
    protected ArrayList<Records> records;
    protected Scanner sc;


    /**
     * Constructor for HoHotelLoginPanelAbstract
     * @param user takes a user object for user informations
     */
    public HotelLoginPanelAbstract(User user, Scanner sc) {
        this.user = user;
        this.sc = sc;
        secondPanel();
    }

    /**
     * Setter for choice
     * @param choice of user from panel
     */
    public void setChoice(int choice) {
        this.choice = choice;
    }

    /**
     * Getter for choice
     * @return of user from panel
     */
    public int getChoice() {
        return choice;
    }

    /**
     * This method gets choice from user/receptionist via console.
     * @param MAX_INPUT max number of choices input
     */
    public void getChoiceFromConsole(int MAX_INPUT) {
        int userChoice = 3, //1-login 2-register 3-exit
            error;

        do {
            error = 0;
            try {
                userChoice = sc.nextInt();
                if (userChoice < 1 || userChoice > MAX_INPUT)
                    throw new MyException();
            }
            catch (MyException my) {
                System.out.println("Please enter a valid number!");
                error = 1;
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                sc.next(); //clear wrong input
                error = 1;
            }
        } while (error == 1);


        setChoice(userChoice);
    }

    /**
     * This is an abstract method for second panel after login
     */
    public abstract void secondPanel();

    /**
     * After second panel; if Receptionist chooses booking option, this method shows up
     * In this method, Receptionist enters the guest's username and choose room for guest,
     * if Guest choohes booking option, method checks user list and records
     */
    public void bookingPanel() {
        String username = null;
        if (user.getUserType() == 1) //GUEST
            username = user.getUsername();
        else {
            System.out.println("Enter username of guest for booking:");
            username = sc.next();
        }

        bookingPanelHelper(username);
    }

    /**
     * Helper for bookingPanel
     * @param username takes username for checks user list and records
     */
    public void bookingPanelHelper(String username) {
        ArrayList<Integer> emptyRooms = new ArrayList<>();
        int numberOfReservations = 0, found = -1;

        for (int i = 0; i < records.size(); i++) {
            if(records.get(i).getUsername().equals(username) && (records.get(i).getStatus() == 1 || records.get(i).getStatus() == 2) ){
                found = i;
                numberOfReservations++;
            }
        }

        if (numberOfReservations == 0) {
            int numOfPeople = 1, error;

            do {
                System.out.println("Enter number of people: ");
                error = 0;
                try {
                    numOfPeople = sc.nextInt();
                    if (numOfPeople < 1)
                        throw new MyException();
                }
                catch (MyException m) {
                    System.out.println("Invalid number!");
                    error = 1;
                } catch (Exception e) {
                    System.out.println("Invalid number!");
                    error = 1;
                    sc.next(); //clear wrong input
                }
            } while (error == 1);

            System.out.println("Choose from available rooms:");
            for (int i = 0; i < rooms.size(); i++) {
                if (numOfPeople <= rooms.get(i).getCapacity() && rooms.get(i).getStatus() == 0) {
                    emptyRooms.add(rooms.get(i).getId());
                    System.out.println(rooms.get(i).getId()+"[Capacity:"+rooms.get(i).getCapacity()+",Price:"+rooms.get(i).getPrice()+"]");
                }
            }
            if (emptyRooms.size() == 0)
                System.out.println("We are sorry, no rooms available!");
            else {
                int chosenRoom = 0;

                do {
                    found = -1;
                    try {
                        chosenRoom = sc.nextInt();
                        for (int i = 0; i < emptyRooms.size(); i++) {
                            if (chosenRoom == emptyRooms.get(i))
                                found = i;
                        }
                        if (found == -1)
                            throw new MyException();
                        for (int i = 0; i < rooms.size(); i++) {
                            if (chosenRoom == rooms.get(i).getId()) {
                                rooms.get(i).setStatus(1);
                                found = i;
                                writeRooms();
                            }
                        }
                    }
                    catch (MyException m) {
                        System.out.println("Invalid room number! Try again!!");
                        found = -1;
                    } catch (Exception e) {
                        System.out.println("Invalid room number! Try again!!");
                        found = -1;
                        sc.next(); //clear wrong input
                    }
                } while (found == -1);

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("records.csv", true));
                    String str = username + "," + chosenRoom + "," + rooms.get(found).getStatus() + "\n";
                    writer.append(str);

                    writer.close();

                    System.out.println("Booking successful for Room "+chosenRoom+"!");
                    if (user.getUserType() == 1)//GUEST
                        System.out.println("You needs to pay "+rooms.get(found).getPrice()+"TL");
                    if (user.getUserType() == 2)//RECEPTIONIST
                        System.out.println("Guest needs to pay "+rooms.get(found).getPrice()+"TL");
                }
                catch (IOException f) {
                    System.out.println(f);
                }
            }
        }
        else {
            if (user.getUserType() == 1) {//GUEST
                if (records.get(found).getStatus() == 1)
                    System.out.println("You have already a reservation!");
                if (records.get(found).getStatus() == 2)
                    System.out.println("You have a checked-in room!");
            }
            if (user.getUserType() == 2) {//RECEPTIONIST
                String temp = records.get(found).getUsername();
                if (records.get(found).getStatus() == 1)
                    System.out.println(temp+" has already a reservation!");
                if (records.get(found).getStatus() == 2)
                    System.out.println(temp+" has a checked-in room!");
            }

        }
    }

    /**
     * After second panel; if Receptionist chooses cancellation option, this method shows up
     * In this method, Receptionist enters the guest's username and cancel room for guest
     * If guest chooses cancellation option, this method cancel for guest
     */
    public void cancelationPanel() {
        String username = null;
        if (user.getUserType() == 1) //GUEST
            username = sc.next();
        else
            username = user.getUsername();

        cancelationPanelHelper(username);
    }

    /**
     * Helper for cancellation method
     * @param username takes username for checks user list and records
     */
    public void cancelationPanelHelper(String username) {
        int numberOfReservations = 0, found = -1;

        for (int i = 0; i < records.size(); i++) {
            if(records.get(i).getUsername().equals(username) && records.get(i).getStatus() == 1) {
                numberOfReservations++;
                found = i;
            }
        }

        if (numberOfReservations == 0) {
            if (user.getUserType() == 1)//GUEST
                System.out.println("You don't have any reservation!");
            if (user.getUserType() == 2)//RECEPTIONIST
                System.out.println("Guest doesn't have any reservation!");
        }
        else {
            if (user.getUserType() == 1) {//GUEST
                System.out.println("Your reserved Room "+records.get(found).getRoomId());
                System.out.println("Do you really want to cancel your reservation for Room "+records.get(found).getRoomId()+"?");

            }
            if (user.getUserType() == 2) {//RECEPTIONIST
                System.out.println("Guest reserved Room "+records.get(found).getRoomId());
                System.out.println("Do you really want to cancel Guest's reservation for Room "+records.get(found).getRoomId()+"?");
            }


            int error = 1;

            while (error == 1) {
                char answer = 'n';
                error = 0;
                System.out.println("'y' for yes, 'n' for no:");

                try {
                    answer = sc.next().charAt(0);
                    if (answer == 'y' || answer == 'Y') {
                        error = 0;
                        for (int i = 0; i < rooms.size(); i++) {
                            if (rooms.get(i).getId() == records.get(found).getRoomId()) {
                                rooms.get(i).setStatus(0);
                            }
                        }
                        records.remove(found);
                        writeRecords();
                        writeRooms();
                        if (user.getUserType() == 1)
                            System.out.println("Your reservation has cancelled!");
                        if (user.getUserType() == 2)
                            System.out.println("Guest's reservation has cancelled!");
                    }
                    else if (answer == 'n' || answer == 'N') {
                        error = 0;
                    }
                    else
                        throw new Exception();
                }
                catch (Exception e) {
                    error = 1;
                }
            }
        }
    }

    /**
     * Reads rooms information from "rooms.csv" file and keeps that in an arraylist
     */
    public void readRooms(){
        rooms = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new FileReader("rooms.csv"));
            String line;

            while ( (line = in.readLine() ) != null) {
                String[] tokens = line.split(",");
                rooms.add(new Room(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Writes room information from arraylist to "rooms.csv" file
     */
    public void writeRooms() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("rooms.csv", false));
            for (int i = 0; i < rooms.size(); i++) {
                writer.write(rooms.get(i).toString());
            }


            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        };
    }

    /**
     * Reads record informations from "records.csv" file and keeps that in an arraylist
     */
    public void readRecords() {
        records = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new FileReader("records.csv"));
            String line;

            while ( (line = in.readLine() ) != null) {
                String[] tokens = line.split(",");
                records.add(new Records(tokens[0],Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
            }
        }
        catch (Exception e) {
        }
    }

    /**
     * Writes record informations from arraylist to "record.csv" file
     */
    public void writeRecords() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("records.csv", false));
            for (int i = 0; i < records.size(); i++) {
                writer.write(records.get(i).toString());
            }

            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        };
    }

    /**
     * toString method of class HotelLoginPanelAbstract
     */
    @Override
    public String toString() {
        return "HotelLoginPanelAbstract{" +
                "user=" + user +
                '}';
    }
}
