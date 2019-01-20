


<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">


        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <span>${(session.getAttribute("ticket"))!}</span>


            <!-- user不为空，已登录 -->
            <#if user??>
            <#-- <#if (viewInfo.getStatus())! == "success"> -->
                <#if (user.getType())! == 1>
                    <ul class="nav-buyer nav nav-pills">
                        <li style="padding-top: 10px"><span class="span-nav-text">买家你好，${(user.getName())!}！</span></li><li role="presentation"><a href="/user/logout"><span class="span-nav-text">[退出]</span></a></li>
                        <li role="presentation" class="nav-buyer-pills-each"><a href="/"><span class="span-nav-text">首页</span></a></li><li style="padding-top: 9px">|</li>
                        <li role="presentation" ><a href="/finance/show"><span class="span-nav-text">财务</span></a></li><li style="padding-top: 9px">|</li>
                        <li role="presentation" ><a href="/cart/show"><span class="span-nav-text">购物车</span></a></li>
                    </ul>
                <#elseif (user.getType())! == 0>
                    <ul class="nav-seller nav nav-pills">
                        <li style="padding-top: 10px"><span class="span-nav-text">卖家你好，${(user.getName())!}！</span></li><li role="presentation"><a href="/user/logout"><span class="span-nav-text">[退出]</span></a></li>
                        <li role="presentation" class="nav-seller-pills-each"><a href="/"><span class="span-nav-text">首页</span></a></li><li style="padding-top: 9px">|</li>
                        <li role="presentation" ><a href="/publish"><span class="span-nav-text">发布</span></a></li>
                    </ul>
                </#if>
            <#else>
                    <ul class="nav-notlogin nav nav-pills">
                        <li style="padding-top: 10px"><span class="span-nav-text">请先</span></li>
                        <li role="presentation"><a href="/login"><span class="span-nav-text">[登录]</span></a></li>
                        <li role="presentation" class="nav-pills-each"><a href="/"><span class="span-nav-text">首页</span></a></li>
                    </ul>
            </#if>
         </div> <!-- /.navbar-collapse -->




    </div><!-- /.container-fluid -->
</nav>