function loadPage(page){
	
	if (page != "home"){
		$("#contenido-home").removeClass("animate");
		$("#contenido-home").addClass("hidden");
		$("#next-page").load(page+".html");
		$("#next-page").removeClass("hidden");

	}

	else{
		$("#contenido-home").removeClass("hidden");
		$("#contenido-home").addClass("animate");
		$("#next-page").addClass("hidden");
	}
	
};