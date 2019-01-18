<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <!--body整体-->
    <div class="container" xmlns="http://www.w3.org/1999/html">
        <div class="div-detail">
            <table>
                <tbody>
                <tr>
                    <td rowspan="6">
                        <img class="detail-img" src="${(item.getImgUrl())!}"/>
                    </td>
                    <td rowspan="6">
                        <div class="blank-for-layout"></div>
                    </td>
                    <td><div class="div-detail-title"><span class="detail-title">${(item.getTitle())!}</span></div></td>
                </tr>
                <tr>
                    <td><span class="detail-summary">${(item.getSummary())!}</span></td>
                </tr>
                <tr>
                    <td><span>￥</span> <span class="span-detail-price">${(item.getPrice())!}</span></td>
                </tr>
                <tr>
                    <td>
                        <span>购买数量：&nbsp;&nbsp;</span>
                        <button id="lessNum" class="btn-quantity-change" type="button"><span class="span-quantity-change">-</span></button>
                        <span id="nowNum">1</span>
                        <button id="moreNum" class="btn-quantity-change" type="button"><span class="span-quantity-change">+</span></button>
                    </td>
                </tr>
                <#if user??>
                    <tr>
                        <td><button type="button" class="btn-edit btn btn-default btn-buy">编&nbsp;辑</button></td>
                    </tr>
                </#if>

                </tbody>



            </table>


        </div>

        <ul class="nav-detail nav nav-tabs">
            <span class="bought-text-head">详细信息</span>
        </ul>
        <div class="div-detail-description">
            <span>${(item.getDescription())!}</span>
        </div>

    </div>






</@defaultLayout.layout>



