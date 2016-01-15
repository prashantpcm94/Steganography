import java.applet.AudioClip;
import java.net.URL;
import java.net.MalformedURLException;

public class SoundApplication { 
    SoundList soundList;
    String auFile;
    String chosenFile;
    AudioClip onceClip;
    URL codeBase;    
    

    public SoundApplication(String url, String file) {
     	chosenFile = auFile=file;
        try {
        codeBase = new URL(url);
        }catch (MalformedURLException e) {
        System.err.println(e.getMessage());}
        
        soundList = new SoundList(codeBase);
        soundList.startLoading(auFile);
        
       
        chosenFile = auFile;
        onceClip = soundList.getClip(chosenFile);
        onceClip.play();       
            
    } 
  
  /*  public static void main(String s[]) {
               new SoundApplication("file:///C://Shri//AudioHide//Audio1.4//","music.au");
    }*/
}
