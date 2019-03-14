<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>


    <div class="login-panel panel panel-default">
        <#--
        <#if (viewInfo.getData().getErrMsg())! == "用户名为空">
            ${(viewInfo.getData().getName())!}
        </#if>-->
        <div class="table-container" style="position:relative;">
            <div class="login-table-panel panel-body" style="position:absolute;z-index:1;">
                <form action="/user/login" method="post" onsubmit="return checkLogin(this)">
                    <table class="tb-login">
                        <tr>
                            <td class="td-login" align="right"><span class="span-login">用户名:</span></td>
                            <td class="td-login">
                                <input name="username" type="text" class="input-login form-control" placeholder="" aria-describedby="basic-addon1">
                            </td>
                        </tr>
                        <tr>
                            <td></br></td>
                            <td></br></td>
                        </tr>

                        <tr>
                            <td class="td-login" align="right"><span class="span-login">密码:</span></td>
                            <td class="td-login">
                                <input name="password" type="password" class="input-login form-control" placeholder="" aria-describedby="basic-addon1">
                            </td>
                        </tr>
                        <tr>
                            <td></br></td>
                            <td></br></td>
                        </tr>
                        <tr>
                            <td class="td-login"></td>
                            <td class="td-login" align="right">
                                <button type="submit" class="btn-login btn btn-default">登&nbsp;录</button>
                            </td>

                        </tr>
                    </table>
                </form>
            </div>

            <div id="prompt-info" class="prompt-layer">
                <#-- <#if (viewInfo.getData().getErrMsg())! == "用户名不为空"> -->
                <div id="prompt-name-null" class="div-prompt-info" style="display: none"><span class="span-prompt">用户名不为空</span></div>
                <#-- <#elseif (viewInfo.getData().getErrMsg())! == "密码不为空"> -->
                <div id="prompt-pwd-null" class="div-prompt-info" style="display: none"><span class="span-prompt">密码不为空！</span></div>
                <#-- <#elseif (viewInfo.getData().getErrMsg())! == "登录失败"> -->
                <#if (viewInfo.getStatus())! == "fail">
                    <div id="prompt-fail" class="div-prompt-info"><span class="span-prompt">登录失败！</span></div>
                </#if>
            </div>

        </div>
    </div>

    <script>

        setTimeout("document.getElementById('prompt-fail').style.display='none'",1000);

        function checkLogin(form) {
            // if($("#excelErrorDiv")){
            //     $("#excelErrorDiv").hide();
            // }

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


    </script>

</@defaultLayout.layout>



