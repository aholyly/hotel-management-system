import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to save the information about users and login/register to the system
 */
public class User {

    private int userType = 1; //1-Guest(default) 2-Receptionist
    private String name, surname, username, password;
    private ArrayList<String> users = new ArrayList<>();

    private final String FILENAME = "users.csv";

    /**
     * Constructor does nothing
     */
    public User() {

    }

    /**
     * Getter for user type
     * @return userType 1-Guest 2-Receptionist
     */
    public int getUserType() {
        return userType;
    }

    /**
     * Getter for username
     * @return username of the user/receptionist
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for name
     * @return name of the user/receptionist
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for surname
     * @return surname of the user/receptionist
     */
    public String getSurname() {
        return surname;
    }

    /**
     * This method asks to user username and password for login to system
     */
    public void login(Scanner sc)  {
        int found = 0;

        do {
            System.out.println("Username: ");
            username = sc.next();

            System.out.println("Password: ");
            password = sc.next();

            try {
                BufferedReader in = new BufferedReader(new FileReader(FILENAME));
                String line;

                while ( (line = in.readLine() ) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[1].equals(username) && tokens[2].equals(password)) {
                        found = 1;
                        name = tokens[3];
                        surname = tokens[4];
                        System.out.println("Login successful!");
                        userType = Integer.parseInt(tokens[0]);
                    }
                }
                if (found == 0)
                    System.out.println("Username or Password is wrong! Try again!");
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        while (found == 0);
    }

    /**
     * This method asks to user name, surname, username and password for register in to system
     */
    public void register(Scanner sc) {
        System.out.println("Name: ");
        this.name = sc.next();

        System.out.println("Surname: ");
        this.surname = sc.next();

        readUsers();

        System.out.println("Username: ");
        String temp = sc.next();

        for (int i = 0; i < users.size(); i++) {
            if (temp.equals(users.get(i))) {
                System.out.println("Username already exist!");
                return;
            }
        }
        this.username = temp;


        System.out.println("Password ");
        this.password = sc.next();

        String str = userType + "," + username + "," + password + "," + name + "," + surname + "\n";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true));
            writer.append(str);

            writer.close();

            System.out.println("Registration successful!");
        }
        catch (IOException e)
        {
            System.out.println(e);
        };
    }

    /**
     * toString method of Room class
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public void readUsers() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("users.csv"));
            String line;

            while ( (line = in.readLine() ) != null) {
                String[] tokens = line.split(",");
                users.add(tokens[1]);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
