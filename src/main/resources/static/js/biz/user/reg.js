$(function () {
    $("#regFrm").bootstrapValidator({
        live: 'enabled',// 验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
        excluded: [':disabled', ':hidden', ':not(:visible)'],// 排除无需验证的控件，比如被禁用的或者被隐藏的
        submitButtons: '#btn_reg',// 指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面
        message: '通用的验证失败消息',// 好像从来没出现过
        feedbackIcons: {// 根据验证结果显示的各种图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	loginName: {
                validators: {
                    notEmpty: {// 检测非空,radio也可用
                        message: '请输入账号'
                    },
                    stringLength: {// 检测长度
                        min: 6,  
                        max: 30,  
                        message: '长度必须在6-30之间'  
                    },
                    hreshold :  6 , //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    remote: {// ajax验证。server result:{"valid",true or false}
								// 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
	                    url: '/reg/validateLoginName',// 验证地址
	                    dataFilter:function(data,type){
                            return data.valid;
                        },
	                    message: '用户已存在',// 提示消息
	                    delay :  2000,// 每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                    type: 'POST'// 请求方式
                    }
                }
            },
            password: {
            	validators: {
            		notEmpty: {// 检测非空,radio也可用
                        message: '请输入密码'
                    },  
                    stringLength: {// 检测长度
                        min: 6,
                        max: 30,
                        message: '长度必须在6-30之间'
                    }
            	}
            },
            repassword: {
            	validators: {
            		notEmpty: {// 检测非空,radio也可用
                        message: '请输入密码'
                    },
                    stringLength: {// 检测长度
                        min: 6,
                        max: 30,
                        message: '长度必须在6-30之间'
                    },
                    identical: {
                        field: 'password',
                        message: '密码和确认密码不一致'
                    }
            	}
            }
        }
    });
    
    $("#btn_reg").click(function () {// 非submit按钮点击后进行验证，如果是submit则无需此句直接验证
        $("#regFrm").bootstrapValidator('validate');// 提交验证
        if ($("#regFrm").data('bootstrapValidator').isValid()) {// 获取验证结果，如果成功，执行下面代码
        	$.ajax({
				url : "/reg/doReg",
				type : "POST",
				data : $("#regFrm").serialize(),
				dataType : "json",
				success : function(data) {
					alert(data.message);
				},
				error : function() {
					alert("012121");
				}
			});
        }
    });
});