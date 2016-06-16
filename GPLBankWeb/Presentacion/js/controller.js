Controller = {
	Queue : [],
	CurrentSlide : null
};

Controller.presentationController = function(){

	this.canvas = null;

	this.slideNavigate = function (slide){
		var timeOut;
		if (Controller.CurrentSlide != null){
			Controller.Queue[Controller.CurrentSlide].end();
			timeOut = Controller.Queue[Controller.CurrentSlide].timeOut;
		}
		setTimeout(function(){
			Controller.CurrentSlide = slide;
			Controller.Queue[slide].render(canvas);
		}, timeOut);
		
	};


	this.configure = function (opciones){
		Controller.Queue = opciones.slides;

		$(document).on("mousedown", function(event) { startPoint = event.clientX; });
		$(document).on("mouseup", function(event) {

			endPoint = event.clientX;
			var diference = endPoint - startPoint;
			
			if(Math.abs(diference) >= 100) {
				if(diference > 0) {
					if(Controller.CurrentSlide !== 0) {
						Controller.slideNavigate(Controller.CurrentSlide - 1);
					}
				} else {
					if(Controller.Queue.length > Controller.CurrentSlide + 1) {
						Controller.slideNavigate(Controller.CurrentSlide + 1);
					}
				}
			}
		});
		
		$(document).on("touchstart", function(event) { startPoint = event.originalEvent.touches[0].pageX; endPoint = event.originalEvent.touches[0].pageX; });
		$(document).on("touchmove", function(event) { endPoint = event.originalEvent.touches[0].pageX; });
		$(document).on("touchend", function(event) {
			
			var diference = endPoint - startPoint;
			if(Math.abs(diference) >= 100) {
				if(diference > 0) {
					if(Controller.CurrentSlide !== 0) {
						Controller.slideNavigate(Controller.CurrentSlide - 1);
					}
				} else {
					if(Controller.Queue.length > Controller.CurrentSlide + 1) {
						Controller.slideNavigate(Controller.CurrentSlide + 1);
					}
				}
			}
		});
		
		this.canvas = $("#canvas");
	}

	this.run = function(){
		var slide = 0;
		this.slideNavigate(slide);
	}
};

