$(function(){
	$.get("member/check", null, function(data){
		var str = "";
		if (data.mno) {
			str += '<div class="login_btn fl"><a href="#">欢迎您 &nbsp;['+data.nickName+']</a><span> | </span>';
			str += '<a href="login.html">注销</a><span> | </span><a href="register.html">注册</a></div>';
		
			getCartInfo(); // 获取购物车信息
		} else { // 说明没有登录
			str += '<div class="login_btn fl"><a href="login.html">登录</a><span> | </span><a href="register.html">注册</a></div>';
		}
		str += '<div class="user_link fl"><span> | </span><a href="user.html">用户中心</a><span> | </span>';	
        str += '<a href="front/cart.html">我的购物车</a><span> | </span><a href="front/order.html">我的订单</a></div> ';
        
        $("#head_info").append($(str));
	},"json");
})

var cart_infos = undefined;
function getCartInfo() {
	$.get("cart/getInfo", null, function(data) {
		console.info(data);
		cart_infos = data;
		$("#show_count").text(Object.keys(data).length);
	}, "json");
}