

<#macro layout>
    <html>
    <head>

        <title>内容销售Demo</title>
        <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <script src="/static/js/jquery.min.js"></script>
        <script src="/static/js/jquery.form.js"></script>
        <script src="/static/js/bootstrap.min.js"></script>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="/static/css/mystyle.css" rel=stylesheet type=text/css>



    </head>
    <body>
    <#include "header.ftl">

    <#-- 在这里嵌入main content -->
    <#nested>


    <#include "footer.ftl">

    </body>

    </html>
</#macro>