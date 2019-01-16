<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

	<#-- 调用布局指令 -->
	<@defaultLayout.layout>
		<!--body整体-->
		<div class="container">
			<!--buyer body公共头-->
			<ul class="nav nav-tabs">
				<li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
				<li role="presentation"><a class="select-tab" href="#">未购买的内容</a></li>
			</ul>
			<div class="tb-div">
				<table class="table-item-show">
					<tr class="tr-item-show">
						<td class="td-item-show">
							<div class="row" >
								<a class="item-href" href="/show?id=1">
									<div class="item-border col-sm-6 col-md-4">

										<div class="thumbnail">
											<div class="img-container">
												<img class="item-img" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368">
												<div class="item-triangle">
													<span class="item-triangle-text">已售出</span>
												</div>
											</div>
											<div class="div-item-title">
												<span>SCIP<br/></span>
												<span>￥</span> <span class="span-item-price">120</span>
											</div>
											</div>
										</div>
									</div>
								</a>
							</div>
						</td>
						<td class="td-item-show">
							<div class="row">
								<a class="item-href" href="/show?id=1">
									<div class="item-border col-sm-6 col-md-4">
										<div class="thumbnail">
											<div class="img-container">
												<img class="item-img" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368" alt="SICP">
												<div class="item-triangle">
													<span class="item-triangle-text">已购买</span>
												</div>
											</div>
											<div class="div-item-title">
												<span>SCIP<br/></span>
												<span>￥</span> <span class="span-item-price">120</span>
											</div>
										</div>
									</div>
								</a>
							</div>
						</td>
						<td class="td-item-show">
							<div class="row">
								<a class="item-href" href="/show?id=1">
									<div class="item-border col-sm-6 col-md-4">
										<div class="thumbnail">
											<div class="img-container">
												<img class="item-img" style="width:200px; height:200px" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368" alt="SICP">
											</div>
											<div class="div-item-title">
												<span>SCIP<br/></span>
												<span>￥</span> <span class="span-item-price">120</span>
											</div>
										</div>
									</div>
								</a>
							</div>
						</td>
						<td class="td-item-show">
							<div class="row">
								<a class="item-href" href="/show?id=1">
									<div class="item-border col-sm-6 col-md-4">
										<div class="thumbnail">
											<div class="img-container">
												<img class="item-img" style="width:200px; height:200px" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368" alt="SICP">
											</div>
											<div class="div-item-title">
												<span>SCIP<br/></span>
												<span>￥</span> <span class="span-item-price">120</span>
											</div>
										</div>
									</div>
								</a>
							</div>
						</td>
					</tr>

					<tr class="tr-item-show">
						<td class="td-item-show">
							<div class="row">
								<a class="item-href" href="/show?id=1">
									<div class="item-border col-sm-6 col-md-4">
										<div class="thumbnail">
											<div class="img-container">
												<img class="item-img" style="width:200px; height:200px" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368" alt="SICP">
												<div class="item-triangle">
													<span class="item-triangle-text">已售出</span>
												</div>
											</div>
											<div class="div-item-title">
												<span>SCIP<br/></span>
												<span>￥</span> <span class="span-item-price">120</span>
											</div>
										</div>
									</div>
								</a>
							</div>
						</td>
					</tr>

				</table>
			</div>
			<!--
			<p> <#-- ${(userModel.getName())!}  --></p>
			<p> <#-- ${(CommonReturnType.getStatus())!} --></p>-->



	</div>

	</@defaultLayout.layout>



