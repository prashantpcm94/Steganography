//************************************************
//   Stego.java
//************************************************
import javax.sound.sampled.*;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.security.interfaces.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.crypto.interfaces.*;
import com.sun.crypto.provider.SunJCE;
import java.util.*;
      
class Stego {
  public boolean feasible = true;
  private AudioInputStream audioInputStream;
  private AudioFormat sndFormat;
  private byte[] audioBytes;
  private byte[] buff;
  private byte[] cipherbuff;
  private byte[] clearbuff;
  private String outFile;
  char password[];
  PBEKeySpec pbeKeySpec;
  
  //Constructor 1
  public Stego(String sndFile, String ptFile, String oFile, char pwd[]){
    //pbeKeySpec=pwd;
    //pbeKeySpec = new PBEKeySpec(readPasswd(System.in));
   /*System.out.println("Sound File " +sndFile);
   System.out.println("Text File " +ptFile);
   System.out.println("op  File " + oFile);
   System.out.println("Pwd " +pwd);*/
    password=pwd;
    outFile=oFile;
    readSND(sndFile);
    feasible = possible(ptFile);
   
  }
  
  //Constructor 2
  public Stego(String sndFile, String ptFile,char pwd[]){
  	password=pwd;
  	outFile=ptFile;
    readSND(sndFile);
   //System.out.println("After readSND()");
   //System.out.println("Sound File " +sndFile);
   //System.out.println("Text File " +outFile);
   //System.out.println("op  File " + outFile);
   System.out.println("Pwd " +pwd);
  }
  
  //--------------------------------------------
  public void encode(){
    
    int k = 0;
    int i = 1; //start of plaintext in audioBytes
    int pt;
    byte pb;
    
    System.out.println("Hiding the ciphertext in AU file ...");
    // First encode the length of the plaintext
    pt = cipherbuff.length;
    for (int j=1; j<=32; j++) {
      if ((pt & 0x80000000)!=0) // MSB of pt is '1'
	  audioBytes[i] = (byte)(audioBytes[i] | 0x01);
	else if ((audioBytes[i] & 0x01)!=0){ //MSB of pt '0' and lsb of audio '1'
	  audioBytes[i] = (byte)(audioBytes[i] >>> 1);
	  audioBytes[i] = (byte)(audioBytes[i] << 1);
	}//if
	pt = (int)(pt << 1);
	i++;
    }
    
    // Now start encoding the message itself!
    //ptext.getChars(0, ptext.length(), cleartext, 0);
    while (k < cipherbuff.length){
      pb = cipherbuff[k];
      //System.out.print((char)pt);
      for (int j=1; j<=8; j++) {
	if ((pb & 0x80)!=0) // MSB of pb is '1'
	  audioBytes[i] = (byte)(audioBytes[i] | 0x01);
	else if ((audioBytes[i] & 0x01)!=0){ //MSB of pt '0' and lsb of audio '1'
	  audioBytes[i] = (byte)(audioBytes[i] >>> 1);
	  audioBytes[i] = (byte)(audioBytes[i] << 1);
	}//if
	pb = (byte)(pb << 1);
	i++;
      }//for j
      k++;
    }//while k
    System.out.println();
    // now write the byte array to an audio file
    File fileOut = new File(outFile);
    ByteArrayInputStream byteIS = new ByteArrayInputStream(audioBytes);
    AudioInputStream audioIS = new AudioInputStream(byteIS, 
    		audioInputStream.getFormat(), audioInputStream.getFrameLength());
    if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.AU, 
      					  audioIS)) {
	try {
	AudioSystem.write(audioIS, AudioFileFormat.Type.AU, fileOut);
	System.out.println("Steganographed AU file is written as " + outFile +"...");
	} catch(Exception e) {
	  System.err.println("Sound File write error");
	}
     }//if
  }//encode
  
  //----------------------------------------------
  public boolean decode(){
    boolean success = true;
    byte out = 0;
    int length = 0;
    int k = 1; //start of plaintext in audioBytes
    System.out.println("Retrieving the ciphertext from AU file ....");
    // First decode the first 32 samples to find the length of the message text
    length = length & 0x00000000;
    for (int j=1; j<=32; j++){
	length = length << 1;
	if ((audioBytes[k] & 0x01)!=0){
	  length = length | 0x00000001;
	}
	k++;
      }// for j
      buff = new byte[length];
    //System.out.println("Length: " + length);
    
    // Now decode the message!
    out = (byte)(out & 0x00);
    for (int i=0; i<length; i++){
      for (int j=1; j<=8; j++){
	out = (byte)(out << 1);
	if ((audioBytes[k] & 0x01)!=0){
	  out = (byte)(out | 0x01);
	}
	k++;
      }// for j
      buff[i] = out;
      //System.out.print((char)out);
      out = (byte)(out & 0x00);
      
    }// for i
    decrypt();
    try{
      System.out.println("Writing the decrypted hidden message to"+outFile+" ...");
      FileOutputStream outfile = new FileOutputStream(outFile);
      outfile.write(clearbuff);
      outfile.close();
    }catch(Exception e){
      System.out.println("Caught Exception: " + e);
    }
    
    return success;
  }//decode
  
  //----------------------------------------------
  // method to read the sound file
  private void readSND(String sndf) {
    File sndfile = new File(sndf);
    int totalFramesRead = 0;
    
    System.out.println("Reading (AU) sound file ...");
    try {
      audioInputStream = AudioSystem.getAudioInputStream(sndfile);
      //sndFormat = audioInputStream.getFormat();
      int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
        // Set an arbitrary buffer size of 1024 frames.
      int numBytes = 154600*bytesPerFrame; 
      audioBytes = new byte[numBytes];
      
      try {
	int numBytesRead = 0;
        int numFramesRead = 0;
        // Try to read numBytes bytes from the file.
        while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
            // Calculate the number of frames actually read.
	    numFramesRead = numBytesRead / bytesPerFrame;
            totalFramesRead += numFramesRead;
            // Here, work with the audio data that's 
            // now in the audioBytes array...	    
	}
      } catch (Exception ex) { 
          // Handle the error...
	  System.out.println("Audio file error:" + ex);
      }
    } catch (Exception e) {
        // Handle the error...
	System.out.println("Audio file error:" + e);
    }
  }//readSND
  
  // is it possible to do steganography with current file
  private boolean possible(String pt) {
    // accessing the input file
    try{
      System.out.println("Reading the plaintext file ..."+pt);
      FileInputStream fis = new FileInputStream(pt);
      BufferedInputStream bis =new BufferedInputStream(fis);
      int len = bis.available();
      buff = new byte[len];
    
      while (bis.available() != 0)
        len = bis.read(buff);
      bis.close();
      fis.close();
      
    }catch (Exception e){
      System.out.println("Could Not Read Plain Text Caught Exception: " + e);
    }
    
    try{
    encrypt();
    if ( cipherbuff.length*8 >  audioBytes.length)
      return false;
    else
      return true;
    }catch(Exception e){
    	System.out.println("******Exception: " + e);
    }
    return true;
  }// possible()
  
  //---------------------------------------------------------
  // Generated Password relavant to underlying algorithm from characters
  //---------------------------------------------------------
  private char[] generatePasswd(char []inputval) throws IOException {
  	 char[] lineBuffer;
     char[] buf;
     int i;
     buf = lineBuffer = new char[128];
     int room = buf.length;
     int offset = 0;
     int c;
     
     
     int index = 0;
     int lenofinputval = inputval.length;
     System.out.println("Debug:inputval: " + inputval);
     System.out.println("Debug:lenofinputval: " + lenofinputval);
     
     
     loop:   
     	//while (true) {
     	while (index < lenofinputval) {
       		//switch (c = in.read()) {
       		switch (c = inputval[index] ) {
       			case -1: 
       			case '\n':
       				System.out.println("Debug::Iam in NewLine or -1");
	 				break loop;
				case '\r':
					System.out.println("Debug::Iam in Carriage Return");
         			index ++;
         			int c2 = inputval[index];
         			if ((c2 != '\n') && (c2 != -1)) {
            			/*
            			if (!(in instanceof PushbackInputStream)) {
                			in = new PushbackInputStream(in);
	    				}
	    				((PushbackInputStream)in).unread(c2);
	    				*/
	    				index --;	    				
	    				System.out.println("Debug::Iam in Carriage Return IF Block");
	 				} else 
	 					break loop;
				default:
         			if (--room < 0) {
	   					buf = new char[offset + 128];
           				room = buf.length - offset - 1;
           				System.arraycopy(lineBuffer, 0, buf, 0, offset);
           				Arrays.fill(lineBuffer, ' ');
           				lineBuffer = buf;
	 				}
	 				buf[offset++] = (char) c;
         			break;
       		}//switch
       		index ++;
     	}//While
     	if (offset == 0) {
       		return null;
     	}
     	char[] ret = new char[offset];
     	System.arraycopy(buf, 0, ret, 0, offset);
     	Arrays.fill(buf, ' ');
     	System.out.println("$$$$$$$$$$::::...... Password Generated: " + ret);
     	return ret;     
  }
  //---------------------------------------------------------
  // Reads user password from given input stream.
  //---------------------------------------------------------
   private char[] readPasswd(InputStream in) throws IOException {
     char[] lineBuffer;
     char[] buf;
     int i;

     buf = lineBuffer = new char[128];

     int room = buf.length;
     int offset = 0;
     int c;

     loop:   while (true) {
       switch (c = in.read()) {
       case -1: 
       case '\n':
	 break loop;

       case '\r':
         int c2 = in.read();
         if ((c2 != '\n') && (c2 != -1)) {
            if (!(in instanceof PushbackInputStream)) {
                in = new PushbackInputStream(in);
	    }
	    ((PushbackInputStream)in).unread(c2);
	 } else 
	 break loop;

       default:
         if (--room < 0) {
	   buf = new char[offset + 128];
           room = buf.length - offset - 1;
           System.arraycopy(lineBuffer, 0, buf, 0, offset);
           Arrays.fill(lineBuffer, ' ');
           lineBuffer = buf;
	 }
	 buf[offset++] = (char) c;
         break;
       }//switch
     }
     if (offset == 0) {
       return null;
     }
     char[] ret = new char[offset];
     System.arraycopy(buf, 0, ret, 0, offset);
     Arrays.fill(buf, ' ');
     System.out.println("Password Captured: " + ret);
     return ret;
   }//read password
   
   //-------------------------------------------------
   // Password based encryption
   //-------------------------------------------------
   private void encrypt(){
    // PBEKeySpec pbeKeySpec;
     PBEParameterSpec pbeParamSpec;
     SecretKeyFactory keyFac;

     // Salt
     byte[] salt = {
                    (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
                    (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
                };

     // Iteration count
     int count = 20;

     // Create PBE parameter set
     pbeParamSpec = new PBEParameterSpec(salt, count);

     // Prompt user for encryption password.
     // Collect user password as char array (using the
     // "readPasswd" method from above), and convert
     // it into a SecretKey object, using a PBE key
     // factory.
     try{
     
       System.out.println("Password Verification: "+password);
    
    /*
     System.out.print("Enter encryption password:  ");
     System.out.flush();
     pbeKeySpec = new PBEKeySpec(readPasswd(System.in));
	 String spwd = new String("satish");	 
	 char pwd[] = spwd.toCharArray();	 
	 System.out.println("Password regeneration started .....with input val: "+ pwd);
	 generatePasswd(pwd);
	 System.out.println("Password regeneration completed");	 
     System.exit(0);     
     char ch[]=new char[100];
     password.getChars(0,password.length(),ch,0);
     pbeKeySpec = new PBEKeySpec(ch);
     */
     pbeKeySpec = new PBEKeySpec(password);
     System.out.println("Encrypting the plaintext message ...");
     
     keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
     SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

     // Create PBE Cipher
     Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

     // Initialize PBE Cipher with key and parameters
     pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

     // Our cleartext
     //byte[] cleartext = "This is another example".getBytes();

     // Encrypt the cleartext
     cipherbuff = pbeCipher.doFinal(buff);
     }catch (Exception ex) { 
          // Handle the error...
	  System.out.println("Caught Exception: " + ex);
	  ex.printStackTrace();
      }
     
   }//encrypt()
   
   //-------------------------------------------------
   //Password based decryption
   //-------------------------------------------------
   
   private void decrypt(){
     PBEKeySpec pbeKeySpec;
     PBEParameterSpec pbeParamSpec;
     SecretKeyFactory keyFac;

     // same salt as in encryption
     byte[] salt = {
                    (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
                    (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
                };

     // Same iteration count in encryption
     int count = 20;

     // Create PBE parameter set
     pbeParamSpec = new PBEParameterSpec(salt, count);

     // Prompt user for encryption password.
     // Collect user password as char array (using the
     // "readPasswd" method from above), and convert
     // it into a SecretKey object, using a PBE key
     // factory.
     try{
       System.out.println();
     System.out.print("Enter encryption password:  ");
  //   System.out.flush();
     
     //pbeKeySpec = new PBEKeySpec(readPasswd(System.in));
     pbeKeySpec = new PBEKeySpec(password);
     
     System.out.println("Decrypting the ciphertext ...");
     
     keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
     SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

     // Create PBE Cipher
     Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

     // Initialize PBE Cipher with key and parameters
     pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

     // Decrypt the cleartext
     try{
     clearbuff = pbeCipher.doFinal(buff);
     }catch (Exception ex) { 
          // Handle the error...
	  System.out.println("Possible password missmatch!!\n");
	  System.out.println("Caught Exception1: " + ex);
      }
     }catch (Exception ex) { 
          // Handle the error...
	  System.out.println("Caught Exception2: " + ex);
      }
   }// decrypt()
   
   
}//class