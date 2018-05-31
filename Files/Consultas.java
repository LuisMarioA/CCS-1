package servlet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Consultas extends Conexion{
    public boolean autenticacion (String usuario, String contraseña){
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            String consulta="select correo,password from empleado where correo=? and password=?";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, usuario);
            pst.setString(2, contraseña);
            rs = pst.executeQuery();
            if(rs.absolute(1)){
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error:" +e);
        }finally{
            try {
                if(getConnection()!= null) getConnection().close();
                if(pst != null) pst.close();
                if(rs !=null) rs.close();
            } catch (Exception e) {
                System.out.println("Error:" +e);
            }
        }
        return false;
    }
    
    public String infoPerfil (String usuario){
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            String consulta="select * from empleado where correo=?";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, usuario);
            rs = pst.executeQuery();
            if(rs.absolute(1)){
                int id=rs.getInt("id_empleado");
                String nombre=rs.getString("nombre");
                String apellido=rs.getString("apellido_p");
                String apellido2=rs.getString("apellido_m");
                String correo=rs.getString("correo");
                int area=rs.getInt("id_area");
                int rool=rs.getInt("id_rool");
                String nombreArea="";
                String perfil="";
                if(area==1){
                    nombreArea="Análisis";
                    if(rool==1)
                        perfil="Analista";
                    else
                        perfil="Jefe Analista";
                }else if(area==2){
                    nombreArea="Desarrollo";
                    if(rool==7)
                        perfil="Jefe de Desarrollo";
                    else
                        perfil="Desarrollador";
                }else if(area==3){
                    nombreArea="Arquitectura";
                    if(rool==8)
                        perfil="Jefe Aarquitecto";
                    else
                        perfil="Arquitecto";
                }else if(area==4){
                    nombreArea="Bases de datos";
                    if(rool==9)
                        perfil="Jefe Bases de Datos";
                    else
                        perfil="Soporte-Base";
                }else if(area==5){
                    nombreArea="Pruebas - Calidad";
                    if(rool==10)
                        perfil="Jefe Pruebas - Calidad";
                    else
                        perfil="Pruebas-Calidad";
                }else if(area==6){
                    nombreArea="Master";
                    if(rool==11)
                        perfil="Lider de Proyecto";
                    else
                        perfil="Esto no pasa";
                }
                String resultado= id + "," + nombre + "," + apellido + "," +apellido2+ "," +correo+ "," +nombreArea+","+perfil;
                return resultado;
            }
        } catch (Exception e) {
            System.out.println("Error:" +e);
        }finally{
            try {
                if(getConnection()!= null) getConnection().close();
                if(pst != null) pst.close();
                if(rs !=null) rs.close();
            } catch (Exception e) {
                System.out.println("Error:" +e);
            }
        }
        return null;
    }
    
    public String obtenerJefe (String usuario){
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            String consulta="select id_rool from empleado where correo=?";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, usuario);
            rs = pst.executeQuery();
            if(rs.absolute(1)){
                int id=rs.getInt("id_rool");
                if(id>5)
                    return "Jefe";
                else
                    return "Empleado";  
            }
        } catch (Exception e) {
            System.out.println("Error:" +e);
        }finally{
            try {
                if(getConnection()!= null) getConnection().close();
                if(pst != null) pst.close();
                if(rs !=null) rs.close();
            } catch (Exception e) {
                System.out.println("Error:" +e);
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        Consultas con= new Consultas();
        System.out.println(con.obtenerJefe("fer@gmail.com"));
        
    }
}