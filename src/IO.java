import java.io.*;
import java.util.Scanner;


public class IO {
    
    public Scanner s;
    
    public IO () {
        try {
            s = new Scanner(new BufferedReader(new FileReader("example_input.csv")));
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
        } catch (IOException e1) {
            System.out.println("Caught IOException: " + e1.getMessage());
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
    
}