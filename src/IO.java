import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class IO {
    
    public Scanner s;
    public String[] line;
    public Sample newSample;
    public ArrayList<Sample> sampleList = new ArrayList<Sample>();
    
    public int iDnum;
    public String sampleName;
    public String sourceName;
    public String sourceLabware;
    public String sampleType;
    public int sourceWellNumber;
    public double targetDilution;
    public int numberOfSerialDilutions;
    public double serialDilutionFactor;
    public int dilutionReplicates;
    public int assayReplicates;
    public boolean includeInAssay;
    public String[] inputHeaders;
    
    public IO () {
    	//openFile(filename);
    }
    public PrintWriter openOutputStream (String filename) throws IOException {
    	PrintWriter out = new PrintWriter(filename);
    	return out;
    }
    public void closeOutputStream(PrintWriter pw) {
    	if (pw != null) {
	    	pw.close();
    	}
    }
    public String[] getHeaders() {
    	return this.inputHeaders;
    }
    public ArrayList<Sample> getSampleList() {
    	return this.sampleList;
    }
    
    public ArrayList<Sample> openFile(String filename) {
         
    	try {
            s = new Scanner(new BufferedReader(new FileReader(filename)));
            inputHeaders = s.nextLine().split(",");
            while (s.hasNextLine()) {
            	//Split string into pre-defined components
            	line = s.nextLine().split(",");
            	iDnum = Integer.parseInt(line[0]);
                sampleName = line[1];         
                sourceName = line[2];         
                sourceLabware = line[3];      
                sampleType = line[4];         
                sourceWellNumber = Integer.parseInt(line[6]);   
                targetDilution = Double.parseDouble(line[7]);     
                numberOfSerialDilutions = Integer.parseInt(line[8]);
                serialDilutionFactor = Double.parseDouble(line[9]);
                dilutionReplicates = Integer.parseInt(line[10]);
                assayReplicates = Integer.parseInt(line[11]);       
                includeInAssay = Boolean.parseBoolean(line[12]);
                
                //Create new sample with required components and add to the sample list
                //newSample = new Sample(sampleName, targetDilution);
                //sampleList.add(newSample);
                
                //create complete sample with all details
                newSample = new Sample(iDnum, sampleName, sourceName,
                		sourceLabware, sampleType, sourceWellNumber,
                		targetDilution, numberOfSerialDilutions,
                		serialDilutionFactor, dilutionReplicates,
                		assayReplicates, includeInAssay);
            	sampleList.add(newSample);
                for (int i = 0; i < line.length; i++) {
                	System.out.print(line[i] + ", ");
                }
            	
                System.out.println();
            }
            return sampleList;
        } catch (IOException e1) {
            System.out.println("Caught IOException: " + e1.getMessage());
        } catch (Exception e2) {
            System.out.println("Exception: " + e2.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
        return null;
    }
    
}