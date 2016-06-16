function goToSlide2(){
	
	Controller.slideNavigate(1);
	var numeroDePixeles = $("#time-mask").height() * 2256 /256;
	$("#time-mask").css({
		"left" : numeroDePixeles,
		"transition" : "left 900s linear"
	});
};