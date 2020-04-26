<#--Here used FREEMARKER
    use common parts of pages like base-->

<#macro page>

    <!DOCTYPE HTML>
    <html lang="en">
    <head>
        <title>Electro</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <#--        <link rel="stylesheet" href="style.css">-->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
              crossorigin="anonymous">

        <link rel="stylesheet" href="/static/style.css">
        <script src="https://kit.fontawesome.com/2f81db4535.js" crossorigin="anonymous"></script>
    </head>
    <body>


    <#if user??>
        user exists
        <#if user.active>user active <#else >user is not active</#if>
    <#else >
        user not exists from user??
    </#if>

    <#if !user??>
        user not exists
    </#if>

    <#if (user?? && user.active) || (!user??)>
        <#include "navbar.ftl">

    <#---->


        <#nested>


        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
                integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
                crossorigin="anonymous"></script>

        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
                integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
                crossorigin="anonymous"></script>
        <script src="/static/main.js"></script>
    <#else>
        <div class="container mt-5 text-center">
            <h3>This user is not available please login</h3>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" class="btn btn-primary mt-5" value="Login"/>
            </form>
        </div>
    </#if>
    </body>
    </html>
</#macro>