<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <!--body整体-->
    <div class="container" style="margin-bottom: 30px">
        <!--buyer body公共头-->
        <ul class="nav nav-tabs">
            <li role="presentation"><a class="select-tab" href="/">所有内容</a></li>
            <li role="presentation" class="active"><a class="select-tab" href="#">未购买的内容</a></li>
        </ul>

        <div class="tb-div">
            <table class="table-item-show">
                <#if itemList?? >
                    <#list itemList as item>
                        <#if (item_index + 1) % 4 ==1><tr class="tr-item-show"></#if>

                        <td class="td-item-show">
                            <div class="row" >
                                <a class="item-href" href="/showDetail?id=${(item.getId())!}">
                                    <div class="item-border col-sm-6 col-md-4">

                                        <div class="thumbnail">
                                            <div class="img-container">
                                                <img class="item-img" src="${(item.getImgUrl())!}">
                                            </div>
                                            <div class="div-item-title">
                                                <span>${item.getTitle()!}<br/></span>
                                                <span>￥</span> <span class="span-item-price">${(item.getPrice())?string("0.##")!}</span>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </td>
                        <#if (item_index + 1) % 4 == 0> </tr></#if>
                    </#list>
                </#if>
            </table>
        </div>


    </div>

</@defaultLayout.layout>



