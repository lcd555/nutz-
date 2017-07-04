$(document).ready(function () {
   
 
    $("#l_submit").click(checkUserName);  
});
function checkUserName()//登录前，校验用户信息
{    
      var a=$('#uid').val();
      var b=$('#pwd').val();
     
      if(a==""){alert("请输入考号");return;      }
      //var re=/^\d{4}$|^\d{8}$/;
      //if(!re.test(a)){alert("考号格式错误");return;     }
      if(b==""||b==undefined){alert("请输入登录密码");return;}
      $.ajax({
          url : "user/doLogin",
          data :{"uid":a,"pwd":b},// $("#login_form").serialize(),
          type:"POST",
          success : function (res) {
                if (res == "5") {
                      window.location.href="user/goIndex";
                }else if(res=="1")
                {
                	window.location.href="user/goAdmin";
                }else{alert(res);             }
                return false;
          },
          error : function(res) {alert("系统错误！");      }
    });
}