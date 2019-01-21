<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <div class="container" style="margin-bottom: 30px">
        <div class="all-in" style="position: relative;">
            <div class="div-detail-all">
                <ul class="nav nav-tabs">
                    <span class="bought-text-head">已添加到购物车的内容</span>
                </ul>

                <table id="cart-table" class="table-bought table table-striped" style="margin-left: 123px">
                    <thead>
                    <tr class="table-bought-th">
                        <th style="display: none">id</th>
                        <th>内容名称</th>
                        <th>数量</th>
                        <th>价格</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#if cartList?? >
                            <#list cartList as item>
                                <tr>
                                    <td style="display: none"><span id="id${item_index+1}">${(item.getItemId())!}</span></td>
                                    <td><span id="title${item_index+1}">${(item.getItemTitle())!}</td>
                                    <td>
                                        <button id="lessNum" class="btn-quantity-change" type="button" onclick="change(-1, ${item_index+1})"><span class="span-quantity-change">-</span></button>
                                        <span id="nowNum${item_index+1}">${(item.getQuantity())!}</span>
                                        <button id="moreNum" class="btn-quantity-change" type="button" onclick="change(1, ${item_index+1})"><span class="span-quantity-change">+</span></button>
                                    </td>
                                    <td>￥<span id="nowPrice${item_index+1}">${(item.getCurrentPrice())?string("0.##")!}</span><span id="eachPrice" style="display:none">${(item.getCurrentPrice())?string("0.##")!}</span></td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
                <div class="div-settle-btn" style="margin-left: 400px">
                    <button type="button" class="btn-exit btn btn-default" onclick="back()">退&nbsp;出</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn-buy btn btn-default" <#if cartList?? >onclick="showConfirmDialog()" </#if>>购&nbsp;买</button>
                </div>
            </div>


            <div id="div-bg-confirm" style="position:absolute;left:-500;top:-500;z-index:2;width:2300px;height: 1350px;background-color:rgba(0,0,0,0.7);display: none">

                <div id="div-confirm" style="position:absolute;width: 300px;height:200px;left:900px;top:800px;z-index:3;background-color: #FFFFFF;border-radius: 5px;opacity:1;display: none">
                    <h4 class="h4-text">提示</h4>
                    <hr class="hr-line"/>
                    <p class="confirm-text">确定全部购买吗？</p>
                    <button class="btn-confirm btn btn-default btn-buy" onclick="buy()">确定</button>
                    <button class="btn-cancel btn btn-default" onclick="dispearConfirmDialog();return false;">取消</button>
                </div>
            </div>
        </div> <!-- all in -->
    </div>
    <script>

        var change = function(num, idnum){
            //数量的变化
            var span = document.getElementById('nowNum' + idnum),
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

        function back() {
            window.history.back(-1);
        }

        function showConfirmDialog() {
            document.getElementById("div-bg-confirm").style.display="block";
            document.getElementById("div-confirm").style.display="block";
        }
        function dispearConfirmDialog() {
            document.getElementById("div-bg-confirm").style.display="none";
            document.getElementById("div-confirm").style.display="none";
        }


        function buy() {
            document.getElementById("div-bg-confirm").style.display="none";
            document.getElementById("div-confirm").style.display="none";

            var table = document.getElementById("cart-table");

            var list=[];

            if(table.rows.length != 0){
                for (var i = 1; i < table.rows.length; i++) {
                    var obj={};
                    obj.id = $("#id"+i).text();
                    obj.title = $("#title"+i).text();
                    obj.quantity = $("#nowNum"+i).text();
                    obj.price = $("#nowPrice"+i).text();
                    list.push(obj);
                }
            }

            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/buy',
                contentType: 'application/json;charset=UTF-8',
                traditional: true,
                data: JSON.stringify({cartList: list}),
                success: function(res){
                    if(res == "success"){
                        alert("购买成功");
                        window.location.href="http://localhost:8080/finance/show";
                    }else{
                        alert("购买出错了")
                    }

                }
            });
        }
    </script>

</@defaultLayout.layout>
