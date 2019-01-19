<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <!--body整体-->



    <div class="container" xmlns="http://www.w3.org/1999/html">

        <div class="all-in" style="position: relative;">
            <div class="div-detail-all">
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
                                       <button id="lessNum" class="btn-quantity-change" type="button"><span class="span-quantity-change" onclick="change(-1)">-</span></button>
                                        <span id="nowNum">1</span>
                                       <button id="moreNum" class="btn-quantity-change" type="button"><span class="span-quantity-change" onclick="change(1)">+</span></button>
                                   </td>
                               </tr>
                               <#if user??>
                                   <#if (user.getType())! == 1>
                                       <tr>
                                           <td><button type="button" class="btn-add-cart btn btn-default btn-buy" onclick="showConfirmDialog()">加入购物车</button></td>
                                       </tr>
                                   <#elseif (user.getType())! == 0>
                                       <tr>
                                           <td><button type="button" class="btn-edit btn btn-default btn-buy">编&nbsp;辑</button></td>
                                       </tr>
                                   </#if>

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

                   <div id="div-bg-confirm" style="position:absolute;left:-500;top:-500;z-index:2;width:2300px;height: 1350px;background-color:rgba(0,0,0,0.7);display: none">

                   <div id="div-confirm" style="position:absolute;width: 300px;height:200px;left:900px;top:800px;z-index:3;background-color: #FFFFFF;border-radius: 5px;opacity:1;display: none">
                       <form id="form-addToCart" method="post" action="/cart/add">
                           <div style="display: none">
                               <input id="itemTitle" name="itemTitle" value="${(item.getTitle())!}" />
                               <input id="itemId" name="itemId" value="${(item.getId())!}" />
                               <input id="cart-num" name="quantity" value="1" />
                               <input id="currentPrice" name="currentPrice" value="${(item.getPrice())!}" />
                           </div>

                           <h4 class="h4-text">提示</h4>
                           <hr class="hr-line"/>
                           <p class="confirm-text">确定加入购物车吗？</p>
                           <button class="btn-confirm btn btn-default btn-buy" onclick="add()">确定</button>
                           <button class="btn-cancel btn btn-default" onclick="dispearConfirmDialog();return false;">取消</button>
                       </form>

                   </div>

                   </div>
        </div> <!-- all-in -->

    </div><!-- container -->

    <script>
        function showConfirmDialog() {
            document.getElementById("div-bg-confirm").style.display="block";
            document.getElementById("div-confirm").style.display="block";
        }
        function dispearConfirmDialog() {
            document.getElementById("div-bg-confirm").style.display="none";
            document.getElementById("div-confirm").style.display="none";
        }
        function add() {
            document.getElementById("div-bg-confirm").style.display="none";
            document.getElementById("div-confirm").style.display="none";

             $("#form-addToCart").ajaxForm(function(data){
                 alert("成功加入购物车！");
                 location.reload();
             });
        }

        var change = function(num){
            //数量的变化
            var span = document.getElementById('nowNum'),
                spanNum = span.innerText*1;

            spanNum += num;

            if(spanNum<1){
                span.innerText = '1';
                alert("商品数量不能再减少！");
            } else {
                span.innerText = ''+spanNum;
                document.getElementById("cart-num").value = spanNum;
            }

            // //总价的变化
            // var spanEachPrice = document.getElementById('eachPrice');
            //
            // if(!spanEachPrice == null){
            //     var eachPrice = spanEachPrice.innerText*1;
            //     var Price;
            //     Price = eachPrice * spanNum;
            //
            //     var spanPrice = document.getElementById('nowPrice');
            //     spanPrice.innerText = '' + Price;
            // }
        }

    </script>




</@defaultLayout.layout>



