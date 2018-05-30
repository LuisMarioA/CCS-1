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
            String consulta="select *  from empleado where correo=?";
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
                String nombreArea="";
                String perfil="";
                if(area==1){
                    nombreArea="Análisis";
                    perfil="Analista";
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
    
    public static void main(String[] args) {
        Consultas co= new Consultas();
        System.out.println(co.infoPerfil("correo@gmail.com"));
        
    }
}