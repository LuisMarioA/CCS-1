<%-- 
    Document   : registrar
    Created on : 29/05/2018, 10:55:44 PM
    Author     : Esli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head >
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>CCS-Registrar</title>

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
                        SII
                    </div>
                </li>
                <li><a href="index.html"><i class="fa fa-home"></i> <span class="nav-label">Home</span></a></li>
                              
                <li class="active"><a href="#"><i class="fa fa-files-o"></i> <span class="nav-label">Rregister user</span></a></li>
                              
            </ul>

        </div>
    </nav>
    <nav th:replace="dch/verticalbar :: verticalnavbar(6)"></nav>
    <div id="page-wrapper" class="gray-bg">
        <div th:replace="dch/navbar :: horizontalnavbar('Sistema de Control de Asistencia')"></div>
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-sm-6">
                <h2>Registrar Docente</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="#" th:href="@{/dch}">Inicio</a>
                    </li>
                    <li class="active">
                        <strong>Registrar Docente</strong>
                    </li>
                </ol>
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content">
                <div class="tabs-container">
                    <ul th:replace="dch/navtabs :: navbar(1)"></ul>
                    <div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
                            <div class="panel-body">
                                <form class="form-horizontal text-right" action="#"
                                      th:action="@{/dch/registrar/docente}" method="post">
                                    <div class="form-group">
                                        <label class="col-lg-4 ">Department: </label>
                                        <div class="col-lg-4">
                                            <label>
                                                <select class="form-control m-b" name="account" required>
                                                    <option></option>
                                                    <option>Analysis</option>
                                                    <option>Desing</option>
                                                    <option>Development</option>
                                                    <option>Arquitecture</option>
                                                    <option>Database</option>
                                                    <option>QA</option>
                                                </select>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-4 ">Business position: </label>
                                        <div class="col-lg-4">
                                            <label>
                                                <select class="form-control m-b" name="account" required>
                                                    <option></option>
                                                    <option>Employee</option>
                                                    <option>Area Leader</option>
                                                    <option>Project Leader</option>
                                                </select>
                                            </label>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-lg-4 ">First Name: </label>
                                        <div class="col-lg-4">
                                            <input type="text" placeholder="Nombre (s)" class="form-control" required>
                                            <span class="help-block m-b-none text-danger"> * Dato no valido</span>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-4 ">Laste Name: </label>
                                        <div class="col-lg-4">
                                            <input type="text" placeholder="Nombre (s)" class="form-control" required>
                                            <span class="help-block m-b-none text-danger"> * Dato no valido</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-4 ">Last name: </label>
                                        <div class="col-lg-4">
                                            <input type="text" placeholder="Nombre (s)" class="form-control" required>
                                            <span class="help-block m-b-none text-danger"> * Dato no valido</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-4 ">e-mail: </label>
                                        <div class="col-lg-4">
                                            <input type="email" placeholder="Numero de Empleado" class="form-control"
                                                   required>
                                            <span class="help-block m-b-none text-danger"> * Dato no valido</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-4 ">Password: </label>
                                        <div class="col-lg-4">
                                            <input type="password" placeholder="Numero de Empleado" class="form-control"
                                                   required>
                                            <span class="help-block m-b-none text-danger"> * Dato no valido</span>
                                        </div>
                                    </div>
                                  
                                 
                                    <div class="row">
                                    <div class="col-sm-5"> <label>Enter Your Public Key</label></div>
                                    <div class="col-sm-4">
                                        <div class="form-group">
                                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                                <span class="btn btn-default btn-file"><span class="fileinput-new">Key</span>
                                                <span class="fileinput-exists">Change</span><input type="file" name="..."/></span>
                                                <span class="fileinput-filename"></span>
                                                <a href="#" class="close fileinput-exists" data-dismiss="fileinput" style="float: none">×</a>
                                            </div> 
                                        </div>
                                    </div>
                                </div>
                                 
                                    <div class="form-group">
                                        <div class="col-lg-offset-3 col-lg-6">
                                            <button type="submit" class="btn btn-block btn-outline btn-primary">
                                                REGISTRAR
                                            </button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-offset-1 col-sm-3">
                                            <span class="help-block m-b-none text-danger">
                                                * No se registro al usuario
                                            </span>
                                        </div>
                                        <div class="col-sm-3">
                                            <span class="help-block m-b-none text-danger">
                                                * Complete todos los campos
                                                obligatorios
                                            </span>
                                        </div>
                                        <div class="col-sm-3">
                                            <span class="help-block m-b-none text-danger">
                                                * Numero de empleado ya registrado
                                            </span>
                                        </div>
                                    </div>
                                </form>
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
</body>
</html>
