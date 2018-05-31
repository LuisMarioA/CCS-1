<%-- 
    Document   : perfil
    Created on : 28/05/2018, 02:21:58 PM
    Author     : Esli
--%>

<%@page import="servlet.Consultas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  HttpSession objSession = request.getSession(true);
  String correo = (String) objSession.getAttribute("correo");
  if(correo.equals(""))
    response.sendRedirect("iniciar-sesion.jsp");
  Consultas con = new Consultas();
  String info="";
  if((info=con.infoPerfil(correo))==null)
    response.sendRedirect("iniciar-sesion.jsp");
  String[] datos=info.split(",");
%>
<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head >
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CCS Profile</title>
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="static/css/animate.css" rel="stylesheet">
    <link href="static/css/style.css" rel="stylesheet">
    <!--- PARA FILE UPLOAD-->
    <link href="static/css/plugins/dropzone/basic.css" rel="stylesheet">
    <link href="static/css/plugins/dropzone/dropzone.css" rel="stylesheet">
    <link href="static/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">
    <link href="static/css/plugins/codemirror/codemirror.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
        <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element"> <span>
                            <img alt="image" class="img-responsive" src="static/img/CB2.png" />
                             </span>
                    </div>
                    <div class="logo-element">
                        CCS
                    </div>
                </li>
                <li><a href="index.jsp"><i class="fa fa-home"></i> <span class="nav-label">Home</span></a></li>
                <li class="active"><a href="#"><i class="fa fa-user-circle-o"></i> <span class="nav-label">Profile</span></a></li>                
                <li><a href="subir.jsp"><i class="fa fa-files-o"></i> <span class="nav-label">Upload file</span></a></li>
                <li><a href="files.jsp"><i class="fa fa-folder-o"></i> <span class="nav-label"> Your Files</span></a></li> 
                <li><a href="logout"><i class="fa fa-sign-out"></i> <span class="nav-label">Sing out</span></a></li>                
            </ul>
        </div>
    </nav>
     <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
        <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
        </div>
            <ul class="nav navbar-top-links navbar-center">
                
                <li>
                    <h2>Cryptographic Corporate System</h2>
                </li>
            </ul>
        </nav>
        </div>
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-sm-4">
                    <h2>Profile</h2>
                    <ol class="breadcrumb">
                        <li>
                            <a href="index.jsp">Home</a>
                        </li>
                        <li class="active">
                            <strong>Perfil</strong>
                        </li>
                    </ol>
                </div>
                <div class="col-sm-8 text-right">
                    <% out.println("<label> " + datos[6] + ": " + datos[1]+ " " + datos[2] + " " + datos[3] + "</label>");%>
                </div>
            </div>
        <div class="wrapper wrapper-content animated fadeInRight">
             <div class="ibox-content">
                <h1 class="text-center">
                    <span class="text-navy">Profile</span>
                </h1>
            <div class="ibox-content">
                <div class="row">
                    <div class="col-lg-6">
                        <h2>Information</h2>
                        <div class="row">
                            <div class="col-sm-4 text-right">
                                <label>Employee number: </label>
                            </div>
                            <div class="col-sm-2">
                                <% out.println("<label>"+ (datos[0])+ "</label>");%>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 text-right">
                                <label>Name: </label>
                            </div>
                            <div class="col-sm-2">
                                <% out.println("<label> "
                                        + datos[1] +  " " +  datos[2] + " " +  datos[3]  
                                        + "</label>");
                                %>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 text-right">
                                <label>Department: </label>
                            </div>
                            <div class="col-sm-2">
                                <% out.println("<label>"+ (datos[5])+ "</label>");%>
                            </div>
                        </div>
                        <h2>Account</h2>
                        <div class="row">
                            <div class="col-sm-4 text-right">
                                <label>e-mail: </label>
                            </div>
                            <div class="col-sm-2">
                                <% out.println("<label>"+ (datos[4])+ "</label>");%>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4 text-right">
                                <label>Password: </label>
                            </div>
                            <div class="col-sm-2">
                                <a href="#" class="btn btn-sm btn-white" th:href="@{/personal/perfil/cambiar}">
                                    Change Password
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="row">
                            <div class="col">
                                <div class="m-b-sm">
                                    <img alt="image" style="width: 50%;height:5%; " class="img" src="static/img/CB2.png"
                                         th:src="@{/img/escom.png}">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="footer">
            <div class="pull-right">
                10GB of <strong>250GB</strong> Free.
            </div>
            <div>
                <strong>Copyright</strong> Cryptographic Corporate System &copy; 2014-2017
            </div>
        </div>
    <!-- Mainly scripts -->
    <script src="static/js/jquery-3.1.1.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    <script src="static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <!-- Jasny -->
    <script src="static/js/plugins/jasny/jasny-bootstrap.min.js"></script>
    <!-- Custom and plugin javascript -->
    <script src="static/js/inspinia.js"></script>
    <script src="static/js/plugins/pace/pace.min.js"></script>
</body>
</html>