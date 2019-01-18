

<#macro layout>
    <html>
    <head>

        <title>内容销售Demo</title>
        <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
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
   <#-- <#include "footer.ftl">-->

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
                var imgSrc = $("#selectPic").attr("src");
                if(imgSrc == "" || imgSrc==null){
                    alert("图片地址不为空！");
                    form.imgUrl.focus();
                    return false;
                }
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

        // 选择图片并显示
        var imgurl = "";

        function getPhoto(node) {
            var imgURL = "";
            try{
                var file = null;
                if(node.files && node.files[0] ){
                    file = node.files[0];
                }else if(node.files && node.files.item(0)) {
                    file = node.files.item(0);
                }

                //Firefox 因安全性问题已无法直接通过input[file].value 获取完整的文件路径

                try{
                    imgURL =  file.getAsDataURL();
                }catch(e){
                    imgRUL = window.URL.createObjectURL(file);
                }

            }catch(e){
                if (node.files && node.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        imgURL = e.target.result;
                    };
                    reader.readAsDataURL(node.files[0]);
                }
            }
            creatImg(imgRUL);//显示图片
            return imgURL;
        }

        function creatImg(imgRUL){
            document.getElementById("selectPic").src = imgRUL;
            $('#selectPic').viewer({
                url: 'src',
            });
        }

        function test(){
            var a = new FormData();
            a.append("image", $("#fileId")[0].files[0]);
            $.ajax({
                url:"http://localhost:8080/item/uploadImage",
                xhrFields:{
                    withCredentials:true
                },
                type: "POST",
                cache: false,
                data: a,
                processData: false,
                contentType:false,
                async: false,
                success: function (result) {
                    if(result == ""){
                        alert("文件格式不符，上传失败！");
                    }else{
                        alert("上传成功！");
                        document.getElementById("imgUrl").value = result;
                    }
                },
                error : function(result) {
                    alert("上传失败！");
                }
                //cache 上传文件不需要缓存，所以设置false
                //processData 因为data值是FormData对象，不需要对数据处理
                //contentType 因为是由form表单构造的FormData对象，且已声明了属性enctype，所以为false
            })
        }

        $(function() {
            if (navigator.userAgent.toLowerCase().indexOf("chrome") >= 0) {
                $(window).load(function(){
                    $('input:not(input[type=submit])').each(function(){
                        var outHtml = this.outerHTML;
                        $(this).append(outHtml);
                    });
                });
            }
        });



    </script>
    </html>
</#macro>