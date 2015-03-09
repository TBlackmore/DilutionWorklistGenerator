import java.io.*;
import java.util.Scanner;


public class IO {
    
    public Scanner s;
    public String[] line;
    
    public IO (String filename) {
    	openFile(filename);
    }
    
    public boolean openFile(String filename) {
        try {
            s = new Scanner(new BufferedReader(new FileReader(filename)));
            while (s.hasNextLine()) {
                
            	line = s.nextLine().split(",");
                System.out.print(line);
                
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