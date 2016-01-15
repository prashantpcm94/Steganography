
public class GuiUtils {
    
    /** Creates a new instance of GuiUtils */
    public GuiUtils() {
    }
    public static javax.swing.JFileChooser getImageFileChooser(){
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(".");
        chooser.setFileFilter( new javax.swing.filechooser.FileFilter(){
            public boolean accept(java.io.File file){
                if( file.isDirectory())  return true;
                if(file.getName().endsWith(".au"))  return true;
                return false;
            }
            public String getDescription(){
                return "au files";
            }
            
        });
        return chooser;
    }  
     
}
