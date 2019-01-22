<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>


	<#-- 调用布局指令 -->
	<@defaultLayout.layout>
		<script>
			function showBtn() {
				$(".item-border").mouseenter(function(){
					$(this).children(".div-btn-delete").show();
				});
			}

			function hideBtn() {
				$(".item-border").mouseleave(function(){
					$(this).children(".div-btn-delete").hide();
				});
			}

		</script>
		<!--body整体-->
		<div class="container" style="margin-bottom: 50px">
			<div class="all-in" style="position: relative;">
				<div id="div-index-content" class="div-detail-all" >
					<!--buyer body公共头-->
					<ul class="nav nav-tabs">
						<#if user??>
							<#if user.getType() == 1>
								<li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
								<li role="presentation"><a class="select-tab" href="/?type=1">未购买的内容</a></li>
							<#else>
								<li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
							</#if>
						<#else>
							<li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
						</#if>
					</ul>

					<div class="tb-div">
						<table class="table-item-show" style="margin-left: 113px">
							<#if itemList?? >
								<#list itemList as item>
									<#if (item_index + 1) % 4 ==1><tr class="tr-item-show"></#if>
										<td class="td-item-show">
											<div class="row" >
												<#assign flag = 0>
													<#if soldList?seq_contains(item.getId()) == false>
														<#assign flag = flag + 1>
													</#if>
													<div id="card" class="item-border col-sm-6 col-md-4" <#if flag == 1> onmouseover="showBtn()" onmouseout="hideBtn()" </#if> >
														<a class="item-href" href="/showDetail?id=${(item.getId())!}">
														<div id="thumbnail" class="thumbnail">
															<div class="img-container">
																<img class="item-img" src="${(item.getImgUrl())!}">
																<#if boughtList?? >
																	<#if boughtList?seq_contains(item.getId()) == true>
																		<div class="item-triangle">
																			<span class="item-triangle-text">已购买</span>
																		</div>
																	</#if>
																</#if>
																<#if soldList?? >
																	<#if soldList?seq_contains(item.getId()) == true>
																		<div class="item-triangle">
																			<span class="item-triangle-text">已售出</span>
																		</div>
																	</#if>
																</#if>
															</div>
															<div class="div-item-title">
																<span>${item.getTitle()!}<br/></span>
																<span>￥</span> <span class="span-item-price">${(item.getPrice())?string("0.##")!}</span>
																<#if soldList?? >
																	<#if soldList?seq_contains(item.getId()) == true>
																		<span style="float:right;margin-top: 3px">已售：${(item.getSales())!}件</span>
																	</#if>
																</#if>
															</div>
														</div>
														</a>
														<#if soldList?? >
															<#if soldList?seq_contains(item.getId()) == false>
																<div id="div-delete" class="div-btn-delete" style="display: none">
																	<button class="btn btn-default btn-publish btn-buy" type="button" onclick="del(${(item.getId())!})" style="height: 30px;margin-left: 140px;margin-top: -60px;">删&nbsp;除</button>
																</div>
															</#if>
														</#if>
													</div>

											</div>
										</td>
									<#if (item_index + 1) % 4 == 0> </tr></#if>
								</#list>
							</#if>
						</table>
					</div>
				</div>
				<!--
				<div id="div-bg-confirm" style="position:absolute;left:-500;top:-500;z-index:2;width:2300px;height: 1350px;background-color:rgba(0,0,0,0.7);">


				</div>
				<div id="div-confirm" style="position:absolute;width: 300px;height:200px;left:900px;top:800px;z-index:3;background-color: #FFFFFF;border-radius: 5px;opacity:1;display: none">
					<h4 class="h4-text">提示</h4>
					<hr class="hr-line"/>
					<p class="confirm-text">确定删除该商品吗？</p>
					<button class="btn-confirm btn btn-default btn-buy" onclick="del()">确定</button>
					<button class="btn-cancel btn btn-default" onclick="dispearConfirmDialog()">取消</button>
				</div>-->
			</div> <!-- all in -->
	</div>

		<script>
			// $(document).ready(function(){
			// 	$(".item-border").mouseenter(function(){
			// 		$(this).children(".div-btn-delete").show();
			// 	});
			// });

			function showConfirmDialog(num) {
				delNo = num; //变更要删除的编号
				document.getElementById("div-bg-confirm").style.display="block";
				document.getElementById("div-confirm").style.display="block";
			}
			function dispearConfirmDialog() {
				document.getElementById("div-bg-confirm").style.display="none";
				document.getElementById("div-confirm").style.display="none";
			}
			
			function del(delNo) {
				if(confirm("确定删除该商品吗？") == false){
					return;
				}
				// document.getElementById("div-bg-confirm").style.display="none";
				// document.getElementById("div-confirm").style.display="none";

				$.ajax({
					type: 'GET',
					url: "http://localhost:8080/item/delete?id=" + delNo,
					success: function(res){
						if(res == "success"){
							alert("删除成功");
							location.reload();
						}else{
							alert("删除失败");
						}

					}
				});
			}
			

		</script>

	</@defaultLayout.layout>



