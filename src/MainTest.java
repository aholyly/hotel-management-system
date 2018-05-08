import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Tester class for Hotel Management System
 */
public class MainTest {
    public static void main(String[] args) {
        final int MAX_TEST = 13;
        ArrayList<File> files = new ArrayList<>();
        ArrayList<Scanner> scanners = new ArrayList<>();

        for (int i = 1; i < MAX_TEST+1; i++) {
            String pathname = "tests/test"+i;
            File temp = new File(pathname);
            files.add(temp);
        }


        try {
            for (int i = 0; i < MAX_TEST; i++) {
                Scanner temp = new Scanner(files.get(i));
                scanners.add(temp);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }

        for (int i = 0; i < MAX_TEST; i++) {
            Hotel temp = new Hotel(scanners.get(i));
        }

    }
}
