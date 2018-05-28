<%-- 
    Document   : iniciar-sesion
    Created on : 28/05/2018, 10:15:03 AM
    Author     : Esli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head >
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>CCS</title>

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

<body class="gray-bg">
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>
            <span><img alt="image" class="img-responsive" src="static/img/CB2.png"  /></span>
        </div>
        <h3>Cryptographic Corporate System</h3>
        <p>Sing Up.</p>
        <div class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
            <form action="#" th:action="@{/logincheck}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-group">
                    <label for="user-email">e-mail:</label>
                    <input type="email" placeholder="Correo electronico" class="form-control" name="email"
                           id="user-email"
                           required>
                </div>
                <div class="form-group">
                    <label for="user-password">Password:</label>
                    <input type="password" placeholder="Contraseña" class="form-control" name="password"
                           id="user-password" required>
                </div>
                <div>
                    <input type="submit" class="btn btn-sm btn-primary pull-right m-t-n-xs" value="Iniciar Sesión">
                </div>
            </form>
        </div>
        <p class="m-t">
            <small><a href="#">He olvidado mi contraseña</a></small>
        </p>
        <div th:if="${error != null}" class="alert alert-danger" role="alert">
            Constraseña o email invalidos
        </div>
    </div>
</div>

   <!--div class="footer">
            <div class="pull-right">
                10GB of <strong>250GB</strong> Free.
            </div>
            <div>
                <strong>Copyright</strong> Cryptographic Corporate System &copy; 2014-2017
            </div>
        </div-->
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

