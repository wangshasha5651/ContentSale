

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
        setTimeout("document.getElementById('prompt-fail').style.display='none'",1000);

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

        function checkPub(form) {

            // form.title.value.replace(/(^\s*)|(\s*$)/g, '') 是为了去除空格，防止没有任何内容的字符串
            if(form.title.value == '' || form.title.value.replace(/(^\s*)|(\s*$)/g, '') == ''){
                alert("标题不为空！");
                form.title.focus();
                return false;
            }
            if(form.title.value.length < 2 || form.title.value.length > 80){
                alert("标题长度为2-80！");
                form.title.focus();
                return false;
            }

            if(form.summary.value == '' || form.summary.value.replace(/(^\s*)|(\s*$)/g, '') == '' ){
                alert("摘要不为空！");
                form.summary.focus();
                return false;
            }
            if(form.summary.value.length < 2 || form.summary.value.length > 140){
                alert("摘要长度为2-140！");
                form.summary.focus();
                return false;
            }

            if(form.imgUrl.value == '' || form.imgUrl.value.replace(/(^\s*)|(\s*$)/g, '') == '' ){
                alert("图片地址不为空！");
                form.imgUrl.focus();
                return false;
            }

            if(form.description.value == '' || form.description.value.replace(/(^\s*)|(\s*$)/g, '') == '' ){
                alert("正文不为空！");
                form.description.focus();
                return false;
            }
            if(form.description.value.length < 2 || form.description.value.length > 1000){
                alert("正文长度为2-1000！");
                form.description.focus();
                return false;
            }

            if(form.price.value == '' || form.price.value.replace(/(^\s*)|(\s*$)/g, '') == ''){
                alert("价格不为空！");
                form.price.focus();
                return false;
            }
            if(form.price.value <= 0){
                alert("价格不符合要求！");
                form.price.focus();
                return false;
            }
        }

        function checkLogin(form) {
            if(form.username.value == ''){
                document.getElementById("prompt-name-null").style.display="block";
                setTimeout("document.getElementById('prompt-name-null').style.display='none'",1000);
                form.username.value.focus;
                return false;
            }

            if(form.password.value == ''){
                document.getElementById("prompt-pwd-null").style.display="block";
                setTimeout("document.getElementById('prompt-pwd-null').style.display='none'",1000);
                form.username.value.focus;
                return false;
            }

        }

        $('#img-upload').change(function(e){
            var input = $("#img-upload");
            var file = input[0].files[0];//获取input上传的文件
            if(!file.name){
                alert("未选择图片");
            }else{
                //高版本浏览器对文件上传路径进行了安全处理，无法直接通过获取input的value进行访问，故转化为获取图片的url进行安全访问
                var url = window.URL.createObjectURL(file);//将上传的文件转化为url
                $("#img-show").attr('src', url);//更新img的src属性
            };
        });


    </script>
    </html>
</#macro>