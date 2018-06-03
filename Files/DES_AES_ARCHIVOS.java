/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

/**
 *
 * @author Esli
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES_AES_ARCHIVOS {
    //Attributes
    static Cipher ecipher;//Encrypt
    static Cipher dcipher;//Decrypt
    
    //Key for DES
    
    static String mode;//mode = ECB,CBC,OFB,CFB
    
    
    static String input;//input string to encrypt 
    static InputStream isInput;
    static String outputFile = "output.txt";//output file of encrypting
    
    static ByteArrayOutputStream baosOutput;
    static String output;
    
    static SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("Kdy5aIxrztw="),0,Base64.getDecoder().decode("Kdy5aIxrztw=").length,"DES");//DES
    static SecretKey keyAES = new SecretKeySpec(Base64.getDecoder().decode("b+S3AP1Q/R3pONvaDnzl+A=="),0,Base64.getDecoder().decode("b+S3AP1Q/R3pONvaDnzl+A==").length,"AES");//DES
    static SecretKeySpec skey = new SecretKeySpec(keyAES.getEncoded(),"AES"); //AES
    static IvParameterSpec iv8 =  new IvParameterSpec(new byte[]{ (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A});//CBC,OFB,CFB
    static IvParameterSpec iv16 =  new IvParameterSpec(new byte[]{ (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A, (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A});//CBC,OFB,CFB
    
    //Methods
    //Constructor
    public DES_AES_ARCHIVOS() {  
    }
    
    /*Reads the stream from "is" (input file) and depending on the mode of "cipher"
        encrypts or decrypts the stream, and writes the result 
        on the outputstream "os" (output file)
    */
    public static void encryptOrDecrypt(InputStream is, OutputStream os, Cipher cipher) throws IOException {
        //Encrypt or decrypt the inputFile, with the write method
        CipherOutputStream cos = new CipherOutputStream(os, cipher);
        byte[] bytes = new byte[64];//adjust the value 64 or 128
        int numBytes;
        while((numBytes = is.read(bytes)) != -1) {
            cos.write(bytes, 0, numBytes);
        }
        cos.flush();
        cos.close();
        is.close();
        os.close();
    }
    
    //DES
    public void initDES(String mode, SecretKey key) throws Exception {
            ecipher = Cipher.getInstance("DES/"+mode+"/PKCS5Padding");
            dcipher = Cipher.getInstance("DES/"+mode+"/PKCS5Padding");
            
            if(mode.equals("ECB")){
                ecipher.init(Cipher.ENCRYPT_MODE, key);
                dcipher.init(Cipher.DECRYPT_MODE, key); 
            }
            else{
                ecipher.init(Cipher.ENCRYPT_MODE, key, iv8);
                dcipher.init(Cipher.DECRYPT_MODE, key, iv8);
            }           
    }
    
    
    //AES
    public void initAES(String mode, SecretKeySpec skey) throws Exception{
            ecipher = Cipher.getInstance("AES/"+mode+"/PKCS5Padding");
            dcipher = Cipher.getInstance("AES/"+mode+"/PKCS5Padding");
            
            if(mode.equals("ECB")){
                ecipher.init(Cipher.ENCRYPT_MODE, skey);
                dcipher.init(Cipher.DECRYPT_MODE, skey);
            }
            else{
                ecipher.init(Cipher.ENCRYPT_MODE, skey, iv16);
                dcipher.init(Cipher.DECRYPT_MODE, skey, iv16);
            }
    }
    
    
    /*Convert String to inputStream*/
    public InputStream stringToStream(String input){
        isInput = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));  
        return  isInput;
    }
    

    /*Convert String to inputStream*/
    public String streamToString(ByteArrayOutputStream outputStream){
        output = new String(outputStream.toByteArray(),StandardCharsets.UTF_8); 
        return  output;
    }
    
    public static void main(String args[]) throws Exception{
        //Just for tests
        try {
            //1. ENCRYPTION     Input=String; Output=file;
            DES_AES_ARCHIVOS da = new DES_AES_ARCHIVOS();
            da.initAES("ECB",skey);
            String content = "This string contains\nall the téxt from the original file :)";
            da.stringToStream(content);
            FileOutputStream fos = new FileOutputStream(outputFile);//encrypted
            //Encrypting the file
            da.encryptOrDecrypt(isInput, fos, ecipher);
            fos.close();
            
            
            
            //2. DECRYPTION Input= file; Output= String;
            FileInputStream fis2 = new FileInputStream(outputFile);//encrypted
            baosOutput = new  ByteArrayOutputStream();
//            //Decrypting the image
            da.encryptOrDecrypt(fis2, baosOutput, dcipher);
            da.streamToString(baosOutput);
            System.out.println("Cadena de salida descifrada:");
            System.out.println(output);
            
            
        } catch (Throwable e) {
                e.printStackTrace();
        }
    }
    
}