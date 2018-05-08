import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is a derived class from abstract class HotelLoginPanelAbstract
 * and used for provides login screen for Receptionist
 */
public class HotelLoginPanelForReceptionist extends HotelLoginPanelAbstract {

    /**
     * Calls constructor of super
     * @param user takes a user object for user informations
     * @param sc scanner input
     */
    public HotelLoginPanelForReceptionist(User user, Scanner sc) {
        super(user,sc);
    }

    /**
     * This method(Second Panel) shows up after user log in to the system
     */
    public void secondPanel() {
        System.out.println("##############################");
        System.out.println("# 1-Book a room              #");
        System.out.println("# 2-Cancel reservation       #");
        System.out.println("# 3-Check-in                 #");
        System.out.println("# 4-Check-out                #");
        System.out.println("# 5-Exit                     #");
        System.out.println("##############################");

        getChoiceFromConsole(5);

        readRooms();
        readRecords();

        if (choice == 1) {
            bookingPanel();
        }
        if (choice == 2) {
            cancelationPanel();
        }
        if (choice == 3) {
            checkinPanel();
        }
        if (choice == 4) {
            checkoutPanel();
        }
        if (user.getUserType() == 5)
            System.exit(0);
    }

    /**
     * After second panel; if Receptionist chooses check-in option, this method shows up
     * In this method, Receptionist chooses a room from check-in list and confirms rooms
     */
    private void checkinPanel() {
        ArrayList<Records> checkinList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getStatus() == 1)
                checkinList.add(records.get(i));
        }

        int exit = 0;
        while (exit == 0) {
            if (checkinList.size() == 0) {
                System.out.println("No check-in for rooms!");
                break;
            }
            else {
                System.out.println("Choose room for check-in or enter 'e' for exit:");
                for (int i = 0; i < checkinList.size(); i++) {
                    System.out.print(checkinList.get(i).getRoomId() + " ");
                }
                System.out.println();
            }

            int error, found;

            do {
                error = 0;
                found = -1;

                String answer = sc.next();

                if (answer.charAt(0) == 'e' || answer.charAt(0) == 'E')
                    exit = 1;
                else {
                    try {
                        for (int i = 0; i < checkinList.size(); i++) {
                            if (checkinList.get(i).getRoomId() == Integer.parseInt(answer)) {
                                checkinList.remove(i);
                                found = 1;
                            }
                        }
                        if (found == -1)
                            throw new Exception();
                        for (int i = 0; i < records.size(); i++) {
                            if (records.get(i).getRoomId() == Integer.parseInt(answer) && records.get(i).getStatus() == 1)
                                records.get(i).setStatus(2);
                        }
                        writeRecords();
                        System.out.println("Check-in successful for Room "+answer);
                    }
                    catch (Exception e) {
                        System.out.println("Please enter a valid room number!");
                        error = 1;
                        answer = sc.next();
                    }
                }

            } while (error == 1);



        }
    }

    /**
     * After second panel; if Receptionist chooses check-out option, this method shows up
     * In this method, Receptionist chooses a room from check-out list and empties rooms
     */
    private void checkoutPanel() {
        ArrayList<Records> checkoutList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getStatus() == 2)
                checkoutList.add(records.get(i));
        }

        int exit = 0;
        while (exit == 0) {
            if (checkoutList.size() == 0) {
                System.out.println("No check-out for rooms!");
                break;
            }

            else {
                System.out.println("Choose room for check-out or 'e' for exit:");
                for (int i = 0; i < checkoutList.size(); i++) {
                    System.out.print(checkoutList.get(i).getRoomId() + " ");
                }
                System.out.println();
            }
            String answer = sc.next();

            if (answer.charAt(0) == 'e' || answer.charAt(0) == 'E')
                exit = 1;
            else {
                int error, found;

                do {
                    error = 0;
                    found = -1;

                    try {
                        for (int i = 0; i < checkoutList.size(); i++) {
                            if (checkoutList.get(i).getRoomId() == Integer.parseInt(answer)) {
                                checkoutList.remove(i);
                                found = 1;
                            }
                        }
                        if (found == -1)
                            throw new Exception();
                        for (int i = 0; i < records.size(); i++) {
                            if (records.get(i).getRoomId() == Integer.parseInt(answer))
                                records.get(i).setStatus(3);
                        }
                        writeRecords();
                        for (int i = 0; i < rooms.size(); i++) {
                            if (rooms.get(i).getId() == Integer.parseInt(answer))
                                rooms.get(i).setStatus(0);
                        }
                        writeRooms();
                        System.out.println("Check-out successful for Room "+answer);
                    }
                    catch (Exception e) {
                        System.out.println("Please enter a valid room number!");
                        error = 1;
                        answer = sc.next();
                    }
                } while (error == 1);
            }
        }
    }
}
