public class EmbedProcess {
    
    private EmbedExtractOptions eo;
    /** Creates a new instance of EmbedProcess */
    public EmbedProcess(EmbedExtractOptions eo) {
        this.eo = eo;
    }
    
    public void startEmbed(){
        java.io.File inFile = null,outFile = null;
        java.awt.Image image = null;
        java.io.FileOutputStream dataOut = null;
        String password = null,comment = null,embedFileName = null;
        String inFile1=null, outFile1=null;
        int quality;
        
        inFile = eo.getInputFile();
        outFile = eo.createOutputFile(150);
        embedFileName = eo.getEmbedFile().getAbsolutePath();
//        image = eo.getInputImage();
        password = eo.getPassword();
        comment = eo.getComment();
        quality = eo.getQuality();
        
        inFile1 = inFile.getAbsolutePath();
        outFile1 = outFile.getAbsolutePath();
        
        
                
        try {
            dataOut = new java.io.FileOutputStream(outFile);            
          /*  jpg = new jpeg.JpegEncoder(image, quality, dataOut, comment);                     
            jpg.Compress(new java.io.FileInputStream(embedFileName), password);
            */
            
            System.out.println("Reading Attributes Wait....");
            System.out.println("<Source - AudioFile> "+ inFile);
            System.out.println("<Output - AudioFile> "+ outFile);
            //System.out.println("<EmbeddFile>"+ embedFileName);
            System.out.println("<Comment           > "+ comment);
            System.out.println("<Password          > "+ password);
            //System.out.println("<inaudiofile1>"+ inFile1);
            //System.out.println("<outaudiofile1>"+ outFile1);
            
            
          /*  new Stego(inFile1,eo.getEmbedFile().getAbsolutePath(),outFile1,password);
            System.out.println("Usage:\t java StegIt -e <plaintextfile> <audiofile>");
            System.out.println("Code Goes Here for Embedding...");*/
           // Stego hide=new Stego("C:\\Shri\\AudioHide\\Test\\music.au","C:\\Shri\\AudioHide\\Test\\test.txt","C:\\Shri\\AudioHide\\Test\\stegout.au",spwd.toCharArray());
			
			
			Stego hide=new Stego(inFile1,embedFileName,outFile1,password.toCharArray());
			
			
			if (hide.feasible) {
	  			hide.encode();
	  			System.out.println("Completed ....(EmbedProcess.java)");
	  			}	  
			else
	  			System.out.println("Error occured ....(audio.java)");	  

            
            dataOut.close();            
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Error embedding audio");
            e.printStackTrace();
        }catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Error embedding audio");
            e.printStackTrace();
        }
        
    }
}
