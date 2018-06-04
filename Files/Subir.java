/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
import servlet.RSA;
//--------------------------------------------------------------------------------
/**
 *
 * @author Esli
 */
@WebServlet(name = "Subir", urlPatterns = {"/Subir"})
@MultipartConfig
public class Subir extends HttpServlet {
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
        
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        
    //********************************************************LECTURA DE CAMPOS EN EL FORMULARIO ************
        int recipient= Integer.parseInt(request.getParameter("Recipient"));
        String doc= request.getParameter("tipoDocumento" );
        int project= Integer.parseInt(request.getParameter("project"));
        String desc= request.getParameter("description"); 
        Part file= request.getPart("file");    // File---lo que recibe del formulario
        Part privkey= request.getPart("privatekey");    // la llave que subio para firmar
        String enviar =request.getParameter("enviar");
        String cancelar =request.getParameter("cancelar");
        String nombre []= file.getSubmittedFileName().split("\\\\");
        
        //---------------------------------------------------------VER CUAL BOTÓN SELECCIONA
        if(cancelar != null){
          response.sendRedirect("index.jsp");
        }else{ 
          //  out.println("Aqui vamos " + recipient + doc + "PROYECTO"+project +desc +file + nombre[nombre.length-1]);
            
        //------------------------------------------------------------COMENZANDO A CIFRAR--------
                byte[] buffer = new byte[1];
                int fin_archivo = -1, fin_archivo2 = -1;
                int caracter, caracter2, bandera = 0;
                FileReader fr = null, fr2 = null;
                String final_word = "", md5 = "", sha = "", key_n = "", key_d = "";
                int caract = 0, caract2 = 0;
        //------------------------------------------LECTURA DEL ARCHIVO SUBIDO----------------------  
                InputStream archivo = file.getInputStream();
                String path ="D:\\esli-\\Documents\\NetBeansProjects\\ProyectoCripto\\src\\java\\servlet\\Files\\";
                
                caracter = archivo.read(buffer);

                        fr = new FileReader(file.getSubmittedFileName());
                        caract = fr.read();
                        while (caract != -1) {
                            try {
                                final_word = final_word + (char) caract;
                                caract = fr.read();
                            } catch (IOException ex) {
                                out.println("Erro"+ex );
                            }
                        }
                        System.out.println("final word"+final_word);
           //-------------------------------------------LECTURA DE LLAVES PRIVADAS------------------------------- 
                InputStream key = privkey.getInputStream();
                caracter2 = key.read(buffer);

                        fr2 = new FileReader(privkey.getSubmittedFileName());
                        caract2 = fr2.read();
                        while (caract2 != -1) {
                            try {
                                if (bandera == 0 && (char) caract2 != '&') {
                                    key_n += (char) caract2;
                                } else if (bandera == 1) {
                                    key_d += (char) caract2;
                                }
                                if ((char) caract2 == '&') {
                                    bandera = 1;
                                }
                                caract2 = fr2.read();
                            } catch (IOException ex) {   
                            }
                        }
                        System.out.println("key n"+key_n);
                        System.out.println("key_d"+key_d);
        //----------------------------------------------------------------HASH---------------------------------------
            try {
                
                MessageDigest messageDigest2 = MessageDigest.getInstance("SHA"); // Inicializa SHA-1
                messageDigest2.update(final_word.getBytes());
                byte[] resumen2 = messageDigest2.digest(); // Genera el resumen SHA-1
                //Pasar los resumenes a hexadecimal
                for (int i = 0; i < resumen2.length; i++) {
                    sha += Integer.toHexString((resumen2[i] >> 4) & 0xf);
                    sha += Integer.toHexString(resumen2[i] & 0xf);
                }
                
                //-------------------------------------------------------------RSA---------------------------------------
               
                RSA rsa = new RSA();
                rsa.setD(new BigInteger(key_d));
                rsa.setN(new BigInteger(key_n));
                System.out.println("sha:"+sha);
                String ciphersha = rsa.encrypt(sha, new BigInteger(key_d), new BigInteger(key_n));
                String textandciphermd5 = final_word + "&" + ciphersha;
                System.out.println("shacifrado: "+ciphersha);
                //-----------------------------------------------------------AES USANDO OFB--------------------------------
                 //out.println("AES" );
                initAES("ECB", skey);//OFB ECB...
                stringToStream(textandciphermd5);
                
                    //Encrypting the file
                    try (FileOutputStream fos = new FileOutputStream(path+nombre[nombre.length-1]) //encrypted
                    ) {
                        //Encrypting the file
                        ///out.println("Aqui" );
                        encryptOrDecrypt(isInput, fos, ecipher);
                    }
            } catch (NoSuchAlgorithmException ex) {
                out.println("Erro"+ex );
            } catch (Exception ex) {
               out.println("Erro"+ex );
            }
        //------------------------------------ANALIZANDO PARA INSERTAR
            try {
  
            String correo=(String) request.getSession().getAttribute("correo");
            
                int td=0;
                if(doc.equals("Use Case")){td=1;}
                else if(doc.equals("Process")){td=2;}
                else if (doc.equals("Analysis")){td=3;}
                else if (doc.equals("Minuta")){td=4;}
                Consultas con= new Consultas();
                int id_empleado=con.getId_emp (correo);
                con= new Consultas();
                if(con.documento(nombre[nombre.length-1], project, td, desc,  id_empleado, recipient,  3)){
                    con = new Consultas();
                    String rol=con.obtenerJefe(correo);
                    if(rol.equals("Empleado"))
                        response.sendRedirect("files.jsp"); 
                    else if (rol.equals("Jefe"))
                       response.sendRedirect("filesJefe.jsp");
                } 

        } catch (Exception e) {
            out.println("error" + e);
            }
        
            
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
