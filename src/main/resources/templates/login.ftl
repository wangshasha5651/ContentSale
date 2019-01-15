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
                    <form action="/user/login" method="post">
                    <table class="tb-login">
                        <tr>
                            <td class="td-login" align="right"><span class="span-login">用户名:</span></td>
                            <td class="td-login">
                                <input name="username" type="text" class="input-login form-control" placeholder="" aria-describedby="basic-addon1" autofocus>
                            </td>
                        </tr>
                        <tr>
                            <td></br></td>
                            <td></br></td>
                        </tr>

                        <tr>
                            <td class="td-login" align="right"><span class="span-login">密码:</span></td>
                            <td class="td-login">
                                <input name="password" type="text" class="input-login form-control" placeholder="" aria-describedby="basic-addon1">
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
            <div class="layer" style="position:absolute;z-index:2;width:220px;height: 60px;margin-top:20%;margin-left:20%">
                <#if (viewInfo.getData().getErrMsg())! == "用户名为空">
                    <div class="div-alert-info" style="width:150px;height: 60px;border-radius: 20px;vertical-align: middle"><span>用户名不为空</span></div>
                <#elseif (viewInfo.getData().getErrMsg())! == "密码为空">
                    <div class="div-alert-info"><span>密码不为空！</span></div>
                <#elseif (viewInfo.getData().getErrMsg())! == "登录失败">
                    <div class="div-alert-info"><span>登录失败！</span></div>
                </#if>
            </div>
        </div>
    </div>



</@defaultLayout.layout>



