import java.util.Scanner;

/**
 * This class is the main login panel for Hotel Management System.
 * Users access the second panel after login(this page)
 */
public class Hotel {

    protected Scanner sc;

    /**
     * Constructor of Hotel class
     * @param sc scanner input
     */
    public Hotel(Scanner sc) {
        this.sc = sc;
        welcomePanel();
    }

    /**
     * This method includes welcome panel of system
     */
    public void welcomePanel() {
        System.out.println("##############################");
        System.out.println("#      WELCOME TO HOTEL      #");
        System.out.println("##############################");
        System.out.println("# 1-Login                    #");
        System.out.println("# 2-Register                 #");
        System.out.println("# 3-Exit                     #");
        System.out.println("##############################");

        int error,
            login = 0,
            userChoice = 3; //1-login 2-register 3-exit

        do {
            error = 0;
            try {
                userChoice = sc.nextInt();
                if (userChoice < 1 || userChoice > 3)
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

        User user = new User();

        if (userChoice == 1) { //LOGIN
            System.out.println("LOGIN");
            login = 1;

            user.login(sc);

            if (user.getUserType() == 1) { //GUEST PANEL
                HotelLoginPanelForGuest guest = new HotelLoginPanelForGuest(user,sc);
            }
            if (user.getUserType() == 2) { //RECEPTIONIST PANEL
                HotelLoginPanelForReceptionist recp = new HotelLoginPanelForReceptionist(user,sc);
            }
            if (user.getUserType() == 3)
                System.exit(0);
        }

        if (userChoice == 2 && login == 0) { //REGISTER
            System.out.println("REGISTER");
            user.register(sc);
        }
    }
}
