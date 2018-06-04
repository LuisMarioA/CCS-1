/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//------------------------------Cifrar y decifrar----
import static servlet.DES_AES_ARCHIVOS.key;
import static servlet.DES_AES_ARCHIVOS.outputFile;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;
/**
 *
 * @author Esli
 */
@WebServlet(name = "Verificar", urlPatterns = {"/Verificar"})
@MultipartConfig
public class Verificar extends HttpServlet {
    // Cifrar
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

    static SecretKey key = new SecretKeySpec(Base64.getDecoder().decode("Kdy5aIxrztw="), 0, Base64.getDecoder().decode("Kdy5aIxrztw=").length, "DES");//DES
    static SecretKey keyAES = new SecretKeySpec(Base64.getDecoder().decode("b+S3AP1Q/R3pONvaDnzl+A=="), 0, Base64.getDecoder().decode("b+S3AP1Q/R3pONvaDnzl+A==").length, "AES");//DES
    static SecretKeySpec skey = new SecretKeySpec(keyAES.getEncoded(), "AES"); //AES
    static IvParameterSpec iv8 = new IvParameterSpec(new byte[]{(byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A});//CBC,OFB,CFB
    static IvParameterSpec iv16 = new IvParameterSpec(new byte[]{(byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A, (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A});//CBC,OFB,CFB


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out=response.getWriter();
            //********************************************************LECTURA DE CAMPOS EN EL FORMULARIO ************
            String archivo= request.getParameter("archivo" );
            int user= Integer.parseInt(request.getParameter("user" ));
            
            String path ="D:\\esli-\\Documents\\NetBeansProjects\\ProyectoCripto\\src\\java\\servlet\\Files\\llaves\\";
            String path2 ="D:\\esli-\\Documents\\NetBeansProjects\\ProyectoCripto\\web\\Files\\";
            String path3="D:\\esli-\\Documents\\NetBeansProjects\\ProyectoCripto\\src\\java\\servlet\\Files\\";
            Consultas con = new Consultas();
             //out.println("Todo Chido2");
            //-------------------------------------------------COMENZANDO A VERIFICAR-------------------
            String textandcipher = null;
            try {
                initAES("ECB", skey);
                FileInputStream fis2 = new FileInputStream(path3+archivo);//encrypted
                baosOutput = new ByteArrayOutputStream();//Decrypting the image
                encryptOrDecrypt(fis2, baosOutput, dcipher);
                streamToString(baosOutput);
                out.println("Cadena de salida descifrada:");
                out.println(output);
                textandcipher = output;
            } catch (Exception ex) {
                out.println("Error"+ex);
            }
            String text = "", ciphershaormd5 = "";
            int bandera = 0;
            for (int i = 0; i < textandcipher.length(); i++) {
                if (textandcipher.charAt(i) != '&' && bandera == 0) {
                    text += textandcipher.charAt(i);
                } else if (textandcipher.charAt(i) != '&' && bandera == 1) {
                    ciphershaormd5 += textandcipher.charAt(i);
                } else {
                    bandera = 1;
                }
            }
            out.println("texto: " + text);
            out.println("ciphershaormd5: " + ciphershaormd5);
            
            String keys = path+con.obtener_keys(user);
            byte[] buffer = new byte[1];
            int fin_archivo = -1, fin_archivo2 = -1;
            int caracter = 0, caracter2, bander = 0;
            FileReader fr = null, fr2 = null;
            String final_word = "", md5 = "", sha = "", key_n = "", key_e = "";
            int caract = 0, caract2 = 0;
            InputStream archivo_keys = new FileInputStream(keys);
            caracter2 = archivo_keys.read(buffer);
            out.println(keys);
            fr2 = new FileReader(keys );
            caract2 = fr2.read();
            while (caract2 != -1) {
                try {
                    if (bander == 0 && (char) caract2 != '&') {
                        key_e += (char) caract2;//e
                    } else if (bander == 1) {
                        key_n += (char) caract2;//n
                    }
                    if ((char) caract2 == '&') {
                        bander = 1;
                    }
                    caract2 = fr2.read();
                } catch (IOException ex) {
                    //Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.println("Llave e"+key_e);
            out.println("Llave n"+key_n);
            RSA rsa = new RSA();
            String md5orsha = rsa.decrypt(ciphershaormd5, new BigInteger(key_e), new BigInteger(key_n));
            
            out.println("MD5 or Sha: " + md5orsha);
            
            MessageDigest messageDigest2 = MessageDigest.getInstance("SHA"); // Inicializa SHA-1
            messageDigest2.update(text.getBytes());
            byte[] resumen2 = messageDigest2.digest(); // Genera el resumen SHA-1
            //Pasar los resumenes a hexadecimal
            for (int i = 0; i < resumen2.length; i++) {
                sha += Integer.toHexString((resumen2[i] >> 4) & 0xf);
                sha += Integer.toHexString(resumen2[i] & 0xf);
            }
            String correo=(String) request.getSession().getAttribute("correo");
            if (sha.equals(md5orsha)) {
                FileOutputStream fos = new FileOutputStream(path2+archivo);
                stringToStream(output);
                fos.write(input.getBytes(StandardCharsets.UTF_8));
                fos.close();
               
                    con = new Consultas();
                    String rol=con.obtenerJefe(correo);
                    if(rol.equals("Empleado")){
                        request.setAttribute("archivo",archivo );
                        response.sendRedirect("ver-file.jsp"); 
                        request.getRequestDispatcher("ver-file.jsp").forward(request, response);
                    }else if (rol.equals("Jefe")){
                       request.setAttribute("archivo",archivo );
                       response.sendRedirect("ver-file.jsp");
                    }
            } else{
                out.println("MAL MAL MAL");
            }
            //out.println(archivo);
            
        } catch (NoSuchAlgorithmException ex) {//sha
            out.println("Error"+ex);
        }
        
    }
     
     
         public static void encryptOrDecrypt(InputStream is, OutputStream os, Cipher cipher) throws IOException {
        //Encrypt or decrypt the inputFile, with the write method
        CipherOutputStream cos = new CipherOutputStream(os, cipher);
        byte[] bytes = new byte[64];//adjust the value 64 or 128
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            cos.write(bytes, 0, numBytes);
        }
        cos.flush();
        cos.close();
        is.close();
        os.close();
    }

    //DES
    public void initDES(String mode, SecretKey key) throws Exception {
        ecipher = Cipher.getInstance("DES/" + mode + "/PKCS5Padding");
        dcipher = Cipher.getInstance("DES/" + mode + "/PKCS5Padding");

        if (mode.equals("ECB")) {
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } else {
            ecipher.init(Cipher.ENCRYPT_MODE, key, iv8);
            dcipher.init(Cipher.DECRYPT_MODE, key, iv8);
        }
    }

    //AES
    public void initAES(String mode, SecretKeySpec skey) throws Exception {
        ecipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");
        dcipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");

        if (mode.equals("ECB")) {
            ecipher.init(Cipher.ENCRYPT_MODE, skey);
            dcipher.init(Cipher.DECRYPT_MODE, skey);
        } else {
            ecipher.init(Cipher.ENCRYPT_MODE, skey, iv16);
            dcipher.init(Cipher.DECRYPT_MODE, skey, iv16);
        }
    }

    /*Convert String to inputStream*/
    public InputStream stringToStream(String input) {
        isInput = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        return isInput;
    }


    /*Convert String to inputStream*/
    public String streamToString(ByteArrayOutputStream outputStream) {
        output = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        return output;
    }



    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

   

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
  
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
