<#--Here used FREEMARKER
    use common parts of pages like base-->

<#macro page>
    <!DOCTYPE HTML>
    <html lang="en">
    <head>
        <title>Electro</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" href="/static/style.css">
    </head>
    <body>
    <#nested >
    </body>
    </html>
</#macro>