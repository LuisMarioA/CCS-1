/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Esli
 */
@WebServlet(name = "Registro", urlPatterns = {"/Registro"})
@MultipartConfig
public class Registro extends HttpServlet {

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
       int ar=1, puesto=1;
       String area= request.getParameter("account");
       String rool= request.getParameter("rool");
       String nombre= request.getParameter("nombre");
       String apellido_p= request.getParameter("apellido_p"); 
       String apellido_m= request.getParameter("apellido_m");
       String correo= request.getParameter("correo");
       String password= request.getParameter("password");
       Part arch= request.getPart("pk");    // File---lo que recibe del formulario
       Consultas con= new Consultas();
       if(con.verificarCorreo(correo)){
           
            response.sendRedirect("registrar.jsp");
       }
//-----------------------------------Cargar archivos      
       else{
       con= new Consultas();
       String nom []= correo.split(".com");
       
       String filename= nom[0]+".txt";                 // nombre que va a la base de datos

       InputStream is = arch.getInputStream();
       String path ="D:\\esli-\\Documents\\NetBeansProjects\\ProyectoCripto\\src\\java\\servlet\\Files\\llaves\\";
       File f=new File(path+filename);
       FileOutputStream ous = new FileOutputStream(f);
       int dato= is.read();
       while(dato!=-1){
           ous.write(dato);
           dato= is.read();
       }
       ous.close();
       is.close();
      
 //-----------------------------Asignacion de pus¿esto y area     
       if(area.equals("Analysis")){ar=1;}
       else if(area.equals("Desing")){ar=2;}
       else if (area.equals("Development")){ar=3;}
       else if (area.equals("Database")){ar=4;}
       else if (area.equals("QA")){ar=5;}
       if(rool.equals("Employee")){puesto=1;}
       else if(rool.equals("Area Leader")){puesto=2;}
       else if (rool.equals("Project Leader")){puesto=3;}
       

       
       if(con.registrar(nombre, apellido_p, apellido_m, correo, password, ar, puesto, filename)){

           response.sendRedirect("iniciar-sesion.jsp");
       } 
       }
        
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
