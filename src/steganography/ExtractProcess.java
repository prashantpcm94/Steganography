import java.io.*;

public class ExtractProcess { 
    private EmbedExtractOptions eo;
    
    private static File f;	    // carrier file
    private static byte[] carrier;  // carrier data
    private static int[] coeff;     // dct values
    private static FileOutputStream fos;   // embedded file (output file)
    private static String embFileName;	// output file name
    private static String password;
    
    
    /** Creates a new instance of ExtractProcess */
    public ExtractProcess(EmbedExtractOptions eo) {
        this.eo = eo;
        
        this.f = eo.getInputFile();
        embFileName = eo.getOutputFile().getAbsolutePath();
        password = eo.getPassword();
        
    }
    
    
    public void startExtract(){

        	System.out.println("Extracting....");
        	System.out.println("Audio File" + eo.getInputFile().getAbsolutePath());
        	System.out.println("File" + embFileName);
        	System.out.println("PWD" + password);
        	
        	Stego unveil = new Stego(eo.getInputFile().getAbsolutePath(),embFileName,password.toCharArray());
			if (!unveil.decode())
  			System.out.println("Error occured during decrypt!, may be the message is too big.");
    
			System.out.println("Completed");

        
        
    }
    
}
