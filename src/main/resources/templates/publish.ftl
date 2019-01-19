<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>

    <div class="container" xmlns="http://www.w3.org/1999/html">
        <ul class="nav nav-tabs">
            <span class="bought-text-head">内容发布</span>
        </ul>

        <div class="div-pubic">
            <form id="form-public" action="/item/create" method="post" onsubmit="return checkPub(this)" enctype="multipart/form-data">
                <table class="table-publish">
                    <tbody>
                    <tr class="tr-publish">
                        <td class="table-span-title" >
                            <span>标题：</span>
                        </td>
                        <td>
                            <input name="title" class="input-publish" placeholder="2-80字符" aria-describedby="basic-addon1"/>
                        </td>
                        <td rowspan="3">
                            <img id="selectPic" class="img-preshow-publish"/>
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span>摘要：</span>
                        </td>
                        <td>
                            <input name="summary"  class="input-publish" placeholder="2-140字符" aria-describedby="basic-addon1"/>
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span>图片：</span>
                        </td>
                        <td>
                                <input type="radio" name="singleRadio" value="1" onclick="radioClick();" checked>图片地址&nbsp;
                                <input type="radio" name="singleRadio" value="2" onclick="radioClick();">本地上传
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span></span>
                        </td>
                        <td>
                            <div id="div-img-url"><input id="imgUrl" name="imgUrl" class="input-publish" placeholder="图片地址" /></div>
                            <div id="div-btn-file" style="display: none">
                                <!-- <form id="form-upload-img" action="/item/uploadImage" method="post"> -->
                                    <input class="input-file form-control" id="fileId" type="file" name="entImg" onchange="getPhoto(this)"/>
                                    <button type="submit" class="btn-upload btn btn-default btn-buy" onclick="upload()">上传</button>
                                <!-- </form> -->
                            </div>
                        </td>
                        <td>
                            <span></span>
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span>正文：</span>
                        </td>
                        <td colspan="2">
                            <input name="description"  class="input-publish input-long-text" placeholder="2-1000个字符" aria-describedby="basic-addon1"/>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <span class="title-short-text">价格：</span>
                        </td>
                        <td>
                            <input name="price" class="input-publish input-short-text" aria-describedby="basic-addon1"/>&nbsp;元
                        </td>

                    </tr>
                    <tr>
                        <td>

                        </td>
                        <td>
                            <button id="btnPub" type="submit" class="btn-publish btn btn-default btn-buy">发布</button>
                        </td>

                    </tr>
                    </tbody>
                </table>

            </form>
        </div>
    </div>

    <script>
        function radioClick(){

            var show="";
            var apm = document.getElementsByName("singleRadio");
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

        function upload(){
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

    </script>


</@defaultLayout.layout>
