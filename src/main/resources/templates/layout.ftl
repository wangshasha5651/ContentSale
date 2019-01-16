

<#macro layout>
<html>
<head>

    <title>内容销售Demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/static/css/mystyle.css" rel=stylesheet type=text/css>



</head>
<body>
    <#include "header.ftl">

    <#-- 在这里嵌入main content -->
    <#nested>

    </div>
    <#include "footer.ftl">

</body>

<script>
    var change = function(num){
        //数量的变化
        var span = document.getElementById('nowNum'),
            spanNum = span.innerText*1;

        spanNum += num;

        if(spanNum<0){
            span.innerText = '0';
            alert("您没有购买任何商品");
        } else {
            span.innerText = ''+spanNum;
        }

        //总价的变化
        var spanEachPrice = document.getElementById('eachPrice');

        if(!spanEachPrice == null){
            var eachPrice = spanEachPrice.innerText*1;
            var Price;
            Price = eachPrice * spanNum;

            var spanPrice = document.getElementById('nowPrice');
            spanPrice.innerText = '' + Price;
        }
    }

    document.getElementById('moreNum').addEventListener('click', function(e) {
        change(1);
    });
    document.getElementById('lessNum').addEventListener('click', function(e) {
        change(-1);
    });


    function radioClick(){

        var show="";
        var apm = document.getElementsByName("single-radio");
        for(var i=0;i<apm.length;i++){
            if(apm[i].checked)
                show = apm[i].value;
        }

        switch (show){
            case '1':
                document.getElementById("div-img-url").style.display="block";
                document.getElementById("div-btn-file").style.display="none";
                break;
            case '2':
                document.getElementById("div-img-url").style.display="none";
                document.getElementById("div-btn-file").style.display="block";
                break;
        }
    }


</script>
</html>
</#macro>