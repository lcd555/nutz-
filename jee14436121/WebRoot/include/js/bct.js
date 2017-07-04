
  var $backtotop = $('#backtotop');
  var toplrw = $(window).height() - $backtotop.height() - 200;

  function moveBacktotop() {
  	if(toplrw<0) toplrw=0;
    $backtotop.css({ top: toplrw, right: 0});
  }

  $backtotop.click(function () {
	  
	  dlg_h(300, 140, "退出系统？", "您确认要退出系统吗？", dlg_ok1, null, "question");
    $('html,body').animate({ scrollTop: 0 });
    return false;
  });
  $(window).scroll(function () {
    var windowHeight = $(window).scrollTop();
  $backtotop.fadeIn();
  });

  moveBacktotop();
  $(window).resize(moveBacktotop);
