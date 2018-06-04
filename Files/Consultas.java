
package servlet;

import servlet.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Esli
 */
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
    public boolean verificarCorreo (String correo){
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            String consulta="select correo from empleado where correo=? ";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, correo);
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
    
    
  public boolean registrar(String nombre, String apellido_p,String apellido_m,String correo,String password,int id_rool,int id_area,String pk){
      PreparedStatement pst = null;
      try {
           String consulta="INSERT INTO empleado (nombre, apellido_p, apellido_m, correo, password, id_rool, id_area, pk) \n" +
                                "VALUES (?, ?,?,?,?,?,?,?)";
           pst = getConnection().prepareStatement(consulta);
           pst.setString(1, nombre);
           pst.setString(2, apellido_p);
           pst.setString(3, apellido_m);
           pst.setString(4, correo);
           pst.setString(5, password);
           pst.setInt(6, id_rool);
           pst.setInt(7, id_area);
           pst.setString(8, pk);
           System.out.println("Insertanod "+ pst);
           if(pst.executeUpdate()==1){
               return true;
           }
      } catch (Exception e) {
          System.out.println("Error"+ e);
      }finally{
            try {
                if(getConnection() != null) getConnection().close();
                if(pst != null) pst.close();
            } catch (Exception e) {
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
    public ArrayList<String> obtenerArchivosAprobados (String usuario){
        PreparedStatement pst=null;
        ResultSet rs=null;
        ArrayList <String> datos=new ArrayList<>(); 
        try {
            String consulta=""+
                "select em.id_empleado as id, em.nombre as Aprobo,"
                +    "d.nombre as documento,t.nombre as tipo,"
                +    "es.nombre as estado "
                +"from tipo_doc t inner join documento d on d.id_tipo=t.id_tipo "
                +    "inner join doc_estado e on e.id_doc=d.id_doc "
                +    "inner join empleado em on em.id_empleado=e.id_aprobo " 
                +    "inner join estado es on es.id_estado=e.id_estado "
                +"where em.correo=?";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, usuario);
            rs = pst.executeQuery();
            while(rs.next()){
                String doc=rs.getString("documento");
                String tipo=rs.getString("tipo");
                String estado=rs.getString("estado");
                datos.add(doc+","+tipo+","+estado);
            }
            return datos;
            
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
    
    public ArrayList<String> obtenerArchivosElaborados (String usuario){
        PreparedStatement pst=null;
        ResultSet rs=null;
        ArrayList <String> datos=new ArrayList<>(); 
        try {
            String consulta=""+
                "select em.id_empleado as id, em.nombre as Aprobo,"
                +    "d.nombre as documento,t.nombre as tipo,"
                +    "es.nombre as estado "
                +"from tipo_doc t inner join documento d on d.id_tipo=t.id_tipo "
                +    "inner join doc_estado e on e.id_doc=d.id_doc "
                +    "inner join empleado em on em.id_empleado=e.id_elaboro " 
                +    "inner join estado es on es.id_estado=e.id_estado "
                +"where em.correo=?";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, usuario);
            rs = pst.executeQuery();
            while(rs.next()){
                String doc=rs.getString("documento");
                String tipo=rs.getString("tipo");
                String estado=rs.getString("estado");
                datos.add(doc+","+tipo+","+estado);
            }
            return datos;
            
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
    
    public int obtenerId(String usuario){
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            String consulta="select id_empleado from empleado where correo=?";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, usuario);
            rs = pst.executeQuery();
            if(rs.absolute(1)){
                int id=rs.getInt("id_empleado");
                    return id;  
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
        return 0;
    }  
   
    public boolean documento(String nombre, int id_proyecto,int id_tipo, String descripcion, int id_elaboro, int id_aprobo, int id_estado){
      PreparedStatement pst = null;
      ResultSet rs=null;
      try {
           String consulta="INSERT INTO documento(nombre, id_proyecto, id_tipo, descripcion)\n" +
                                "VALUES (?,?,?,?)";
           pst = getConnection().prepareStatement(consulta);
           pst.setString(1, nombre);
           pst.setInt(2, id_proyecto);
           pst.setInt(3, id_tipo);
           pst.setString(4, descripcion);
           System.out.println("Insertando "+ pst);
           if(pst.executeUpdate()==1){
               String consulta2="SELECT id_doc from documento where nombre=? and  id_proyecto= ?";
               pst = getConnection().prepareStatement(consulta2);
               pst.setString(1, nombre);
               pst.setInt(2, id_proyecto);
               rs = pst.executeQuery();
               if(rs.absolute(1)){
                    int id_doc=rs.getInt("id_doc");
                    String consulta3="INSERT INTO doc_estado(id_elaboro, id_aprobo, id_doc, id_estado)\n" +
                                "VALUES (?,?,?,?)";
                    pst = getConnection().prepareStatement(consulta3);
                    pst.setInt(1, id_elaboro);
                    pst.setInt(2, id_aprobo);
                    pst.setInt(3, id_doc);
                    pst.setInt(4, id_estado);
                    System.out.println(pst);
                    if(pst.executeUpdate()==1){
                        return true;
                    }else{
                        return false;
                    }
               }else{
                   return false;
               }
           }
      } catch (Exception e) {
          System.out.println("Error"+ e);
      }finally{
            try {
                if(getConnection() != null) getConnection().close();
                if(pst != null) pst.close();
                if(rs !=null) rs.close();
            } catch (Exception e) {
            }
        }
      return false;
  }
  
    public int getId_emp (String correo){
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            String consulta="select id_empleado from empleado where correo=? ";
            pst = getConnection().prepareStatement(consulta);
            pst.setString(1, correo);
            rs = pst.executeQuery();
            if(rs.absolute(1)){
                int id=rs.getInt("id_empleado");
                return id;
            }
        } catch (Exception e) {
            System.out.println("Error:" +e);
        }finally{
            try {
                if(getConnection()!= null) getConnection().close();
                if(pst != null) pst.close();
                if(rs !=null) rs.close();
                if(rs !=null) rs.close();
            } catch (Exception e) {
                System.out.println("Error:" +e);
            }
        }
        return 0;
    }
    
    public String obtener_keys(int id){
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            String consulta="select pk from empleado where id_empleado=? ";
            pst = getConnection().prepareStatement(consulta);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.absolute(1)){
                String keys=rs.getString("pk");
                return keys;
            }
        } catch (Exception e) {
            System.out.println("Error:" +e);
        }finally{
            try {
                if(getConnection()!= null) getConnection().close();
                if(pst != null) pst.close();
                if(rs !=null) rs.close();
                if(rs !=null) rs.close();
            } catch (Exception e) {
                System.out.println("Error:" +e);
            }
        }
        return null;
    }
  
    public static void main(String[] args) {
        Consultas con= new Consultas();
       // System.out.println(con.autenticacion("esli@gmail.com","esli"));
       // System.out.println(con.registrar("Margarita", "Rodriguez", "Perez", "margarita@gamil.com", "margarita", 2, 2, "margarita.key"));
       //System.out.println(con.obtenerJefe("fer@gmail.com"));
        //System.out.println(con.verificarCorreo("esli@gmail.com"));
       //System.out.println(con.documento( "prueba2", 1, 1, "Aqui ando",1,6 ,3));
        //System.out.println(con.getId_emp("fer@gmail.com"));
        System.out.println(con.obtener_keys(1));
    }
    
}
