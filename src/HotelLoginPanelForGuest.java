import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * This class is a derived class from abstract class HotelLoginPanelAbstract
 * and used for provides login screen for Guest
 */
public class HotelLoginPanelForGuest extends HotelLoginPanelAbstract {

    /**
     * Calls constructor of super
     * @param user takes a user object for user information
     * @param sc scanner input
     */
    public HotelLoginPanelForGuest(User user, Scanner sc) {
        super(user,sc);
    }

    /**
     * This method(Second Panel) shows up after user log in to the system
     */
    public void secondPanel() {
        System.out.println("##############################");
        System.out.println("# 1-Book a room              #");
        System.out.println("# 2-Cancel reservation       #");
        System.out.println("# 3-Exit                     #");
        System.out.println("##############################");

        getChoiceFromConsole(3);

        readRooms();
        readRecords();

        if (choice == 1) {
            bookingPanel();
        }
        if (choice == 2) {
            cancelationPanel();
        }
        if (user.getUserType() == 3)
            System.exit(0);
    }

    /**
     * After second panel; if Receptionist chooses cancellation option, this method shows up
     * In this method, Receptionist enters the guest's username and cancel room for guest
     */
    public void cancelationPanel() {
        int numberOfReservations = 0, found = -1;

        for (int i = 0; i < records.size(); i++) {
            if(records.get(i).getUsername().equals(user.getUsername()) && records.get(i).getStatus() == 1) {
                numberOfReservations++;
                found = i;
            }
        }

        if (numberOfReservations == 0) {
            System.out.println("You don't have any reservation!");
        }
        else {
            System.out.println("Your reserved room: "+records.get(found).getRoomId());
            System.out.println("Do you really want to cancel your reservation for Room "+records.get(found).getRoomId()+"?");

            char answer = 'x';
            int error = 1;
            while (error == 1 ) {
                error = 0;

                System.out.println("'y' for yes, 'n' for no:");
                try {
                    answer = sc.next().charAt(0);
                    if (answer == 'y' || answer == 'Y') {
                        for (int i = 0; i < rooms.size(); i++) {
                            if (rooms.get(i).getId() == records.get(found).getRoomId()) {
                                rooms.get(i).setStatus(0);
                            }
                        }
                        records.remove(found);
                        writeRecords();
                        writeRooms();
                        System.out.println("Your reservation has cancelled!");
                    }
                }
                catch (Exception e) {
                    error = 1;
                    System.out.println("'y' for yes, 'n' for no:");
                }



                break;
            }
        }
    }
}
