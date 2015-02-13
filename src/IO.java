import java.io.*;
import java.util.Scanner;


public class IO {
    
    public Scanner s;
    
    public IO () {

    }
    
    public boolean openFile(String filename) {
        try {
            s = new Scanner(new BufferedReader(new FileReader(filename)));
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
            return true;
        } catch (IOException e1) {
            System.out.println("Caught IOException: " + e1.getMessage());
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
        return false;
    }
    
}