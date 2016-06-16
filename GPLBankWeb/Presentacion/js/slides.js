Presentacion = [{
	title : "slide 1",
	timeOut : 2000,
	render : function(canvas){
		var html = "<div class='container-fluid fade-in' id='logo'>";
				html += "<img class='img-responsive center-img-in-div' draggable='false' src='resources/slide1/gplbank-logo.png'/>";
			html += "</div>";
			html += "<div class='container-fluid fade-in' id='logo-centro'>"
				html += "<img class='img-responsive center-img-in-div' draggable='false' src='resources/slide1/logoeuropeonuevo.png'/>";
			html += "</div>";
			html += "<h1 id='nombre' class='responsive-text center-text swipe-from-left'> José María Jiménez Pérez </h1>";
			html += "<h3 id='ciclo' class='responsive-text center-text swipe-from-left'> Desarrollo de aplicaciones multiplataforma </h3>";
			html += "<h4 id='curso' class='responsive-text center-text swipe-from-left'> Curso 2015 - 2016 </h4>";
			html += "<div id='comenzar' class='center-container animated infinite bounce'>";
				html += "<input id='comenzar-btn' type='button' value='Comenzar' onclick='goToSlide2()'/>";
			html += "</div>";

		$(canvas).html(html);

		setTimeout(function(){$("#logo").addClass("animate")}, 200);
		setTimeout(function(){$("#logo-centro").addClass("animate")}, 400);
		setTimeout(function(){$("#nombre").addClass("animate")}, 800);
		setTimeout(function(){$("#ciclo").addClass("animate")}, 1000);
		setTimeout(function(){$("#curso").addClass("animate")}, 1200);
		setTimeout(function(){$("#comenzar").addClass("animate")}, 1300);
	},

	end : function(){
		console.log("entro al end");
		$("#logo").removeClass("fade-in animate");
		$("#logo").addClass("animated hinge");
		setTimeout(function(){$("#logo-centro").addClass("end")}, 200);
		setTimeout(function(){$("#nombre").addClass("end")}, 500);
		setTimeout(function(){$("#ciclo").addClass("end")}, 700);
		setTimeout(function(){$("#curso").addClass("end")}, 900);
		setTimeout(function(){$("#comenzar").addClass("end")}, 1200);
	}
},
{
	title : "slide 3",
	timeOut : 400,
	render : function(canvas){
		var html = "<img id='slide3-captura' class='img-responsive center-img-in-div fade-in' draggable='false' src='resources/slide3/captura1.png'/>";

		$(canvas).html(html);
		setTimeout(function(){$("#slide3-captura").addClass("animate")}, 200);
	},

	end : function(){
		$("#slide3-captura").addClass("end");
	}
},
{
	title : "raspberri pi",
	timeOut: 600,
	render : function(canvas){
		var html = "<div id='raspberri-img' class='fade-in'></div>";

		$(canvas).html(html);
		setTimeout(function(){$("#raspberri-img").addClass("animate")}, 200);

	},

	end : function(){
		setTimeout(function(){$("#raspberri-img").addClass("end")}, 0);
	}
},
{
	title : "slide 2",
	timeOut: 1000,
	render : function(canvas){
		var html = "<h1 id='slide2-title' class='swipe-from-left responsive-text center-text big-title'>Presentación 100% interactiva</h1>";
			html += "<ul id='slide2-ul'>";
				html += "<li id='slide2-li1' class='swipe-from-left'> Desarrollada íntegramente en JavaScript</li>";
				html += "<li id='slide2-li2' class='swipe-from-left'> Utilizando lenguajes preprocesados </li>";
				html += "<li id='slide2-li3' class='swipe-from-left'> Empleando gestor de tareas </li>";
			html += "</ul>";
		$(canvas).html(html);

		setTimeout(function(){$("#slide2-title").addClass("animate")}, 200);
		setTimeout(function(){$("#slide2-li1").addClass("animate")}, 400);
		setTimeout(function(){$("#slide2-li2").addClass("animate")}, 600);
		setTimeout(function(){$("#slide2-li3").addClass("animate")}, 800);

	},

	end : function(){
		setTimeout(function(){$("#slide2-title").addClass("end")}, 0);
		setTimeout(function(){$("#slide2-li1").addClass("end")}, 200);
		setTimeout(function(){$("#slide2-li2").addClass("end")}, 400);
		setTimeout(function(){$("#slide2-li3").addClass("end")}, 800);
	}
},
{
	title : "slide 4",
	timeOut: 1500,
	render : function(canvas){
		var html = "<div id='slide4-title' class='swipe-from-left responsive-text center-text big-title'>¿Cómo está hecha la presentación?</div>";
			html += "<div class='half-viewport'>";
				html += "<div id='btn-tactil' class='half-row swipe-from-left'>";
					html += "<ul><li>Implementando el control táctil</li></ul>";
					html += "<div id='captura-tactil' class='animated bounce infinite'></div>";
				html += "</div>";
				html += "<div id='btn-slideNavigate' class='half-row swipe-from-left'>";
					html += "<ul><li>Cambiando de slide</li></ul>";
					html += "<div id='captura-navigate' class='animated bounce infinite'></div>";
				html += "</div>";
			html += "</div>";
			html += "<div class='half-viewport'>";
				html += "<div id='btn-slide' class='half-row swipe-from-left'>";
					html += "<ul><li>Configuración de las slides</li></ul>";
					html += "<div id='slides-conf' class='animated bounce infinite'></div>";
				html += "</div>";
				html += "<div id='btn-configure-index' class='half-row swipe-from-left'>";
					html += "<ul><li>Poniéndolo todo a funcionar</li></ul>";
					html += "<div id='configure-index' class='animated bounce infinite'></div>";
				html += "</div>";
			html += "</div>";

		$(canvas).html(html);

		$("#btn-tactil").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide4/touch-controller.png'/> </div>";
			$("#canvas").append(zoom);
			$("#captura-tactil").removeClass("animated");
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#btn-slideNavigate").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide4/slide-navigate.png'/> </div>";
			$("#canvas").append(zoom);
			$("#captura-navigate").removeClass("animated");
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#btn-slide").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide4/slides-conf.png'/> </div>";
			$("#canvas").append(zoom);
			$("#slides-conf").removeClass("animated");
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#btn-configure-index").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide4/configure-index.png'/> </div>";
			$("#canvas").append(zoom);
			$("#configure-index").removeClass("animated");
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		setTimeout(function(){$("#slide4-title").addClass("animate")}, 200);
		setTimeout(function(){$("#btn-tactil").addClass("animate")}, 800);
		setTimeout(function(){$("#btn-slideNavigate").addClass("animate")}, 500);
		setTimeout(function(){$("#btn-slide").addClass("animate")}, 1400);
		setTimeout(function(){$("#btn-configure-index").addClass("animate")}, 1100);

	},
	end : function(){
		setTimeout(function(){$("#slide4-title").addClass("end")}, 200);
		setTimeout(function(){$("#btn-tactil").addClass("end")}, 800);
		setTimeout(function(){$("#btn-slideNavigate").addClass("end")}, 500);
		setTimeout(function(){$("#btn-slide").addClass("end")}, 1400);
		setTimeout(function(){$("#btn-configure-index").addClass("end")}, 1100);
	}
},
{
	title : "slide 5",
	timeOut : 600,
	render : function(canvas){
		var html = "<div id='slide5-title' class='responsive-text big-title center-text swipe-from-left'>Pasando de less y jade a css y html</div>";
			html += "<div id='img-gulp1' class='half-row fade-in'></div>";
			html += "<div id='img-gulp2' class='half-row fade-in'></div>";


		$(canvas).html(html);

		setTimeout(function(){$("#slide5-title").addClass("animate");}, 200);
		setTimeout(function(){$("#img-gulp1").addClass("animate");}, 400);
		setTimeout(function(){$("#img-gulp2").addClass("animate");}, 600);


		$("#img-gulp1").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide5/gulp1.png'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#img-gulp2").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide5/gulp2.png'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});
	},

	end : function(){
		setTimeout(function(){$("#slide5-title").addClass("end");}, 0);
		setTimeout(function(){$("#img-gulp1").addClass("end");}, 200);
		setTimeout(function(){$("#img-gulp2").addClass("end");}, 400);
	}
},

{
	title: "slide 6",
	timeOut : 1500,
	render : function(canvas){
		var html = "<div id='slide6-title' class='swipe-from-left responsive-text big-title center-text'>Funcionamiento general del proyecto </div>";
			html += "<div class='half-row'>";
				html += "<ul id='slide6-ul1' class='swipe-from-left'> Lado cliente";
					html += "<li id='slide6-li1' class='swipe-from-left'>La vista instancia controladores</li>";
					html += "<li id='slide6-li2' class='swipe-from-left'>Los controladores instancian DAOs</li>";
					html += "<li id='slide6-li3' class='swipe-from-left'>Los DAOs hacen peticiones HTTP</li>";
				html += "</ul>";
			html += "</div>";
			html += "<div class='half-row'>";
				html += "<ul id='slide6-ul2' class='swipe-from-left'> Lado servidor";
					html += "<li id='slide6-li4' class='swipe-from-left'>Las peticiones pasan por un filtro</li>";
					html += "<li id='slide6-li5' class='swipe-from-left'>En el filtro se reconstruyen objetos</li>";
					html += "<li id='slide6-li6' class='swipe-from-left'>Se meten en sesión</li>";
					html += "<li id='slide6-li7' class='swipe-from-left'>El servlet instancia Servicios</li>";
					html += "<li id='slide6-li8' class='swipe-from-left'>Los servicios instancian DAOs</li>";
					html += "<li id='slide6-li9' class='swipe-from-left'>Los DAOs acceden a base de datos</li>";
				html += "</ul>";
			html += "</div>";

		$(canvas).html(html);

		setTimeout(function(){$("#slide6-title").addClass("animate");}, 200);
		setTimeout(function(){$("#slide6-ul2").addClass("animate");}, 400);
		setTimeout(function(){$("#slide6-ul1").addClass("animate");}, 500);
		setTimeout(function(){$("#slide6-li4").addClass("animate");}, 600);
		setTimeout(function(){$("#slide6-li1").addClass("animate");}, 700);
		setTimeout(function(){$("#slide6-li5").addClass("animate");}, 800);
		setTimeout(function(){$("#slide6-li2").addClass("animate");}, 900);
		setTimeout(function(){$("#slide6-li6").addClass("animate");}, 1000);
		setTimeout(function(){$("#slide6-li3").addClass("animate");}, 1100);
		setTimeout(function(){$("#slide6-li7").addClass("animate");}, 1200);
		setTimeout(function(){$("#slide6-li8").addClass("animate");}, 1300);
		setTimeout(function(){$("#slide6-li9").addClass("animate");}, 1400);

	},

	end : function(){
		setTimeout(function(){$("#slide6-title").addClass("end");}, 0);
		setTimeout(function(){$("#slide6-ul2").addClass("end");}, 200);
		setTimeout(function(){$("#slide6-ul1").addClass("end");}, 300);
		setTimeout(function(){$("#slide6-li4").addClass("end");}, 400);
		setTimeout(function(){$("#slide6-li1").addClass("end");}, 500);
		setTimeout(function(){$("#slide6-li5").addClass("end");}, 600);
		setTimeout(function(){$("#slide6-li2").addClass("end");}, 700);
		setTimeout(function(){$("#slide6-li6").addClass("end");}, 800);
		setTimeout(function(){$("#slide6-li3").addClass("end");}, 900);
		setTimeout(function(){$("#slide6-li7").addClass("end");}, 1000);
		setTimeout(function(){$("#slide6-li8").addClass("end");}, 1100);
		setTimeout(function(){$("#slide6-li9").addClass("end");}, 1200);
	}
},

{
	title : "slide 7",
	timeOut: 1300,
	render : function(canvas){
		var html = "<div id='slide7-title' class='swipe-from-left responsive-text center-text big-title'>La importancia del objeto Info</div>";
			html += "<div class ='half-row'>";
				html += "<ul>";
					html += "<li id='slide7-li1' class='swipe-from-left'>Forma de comunicación común</li>";
					html += "<li id='slide7-li2' class='swipe-from-left'>Informa si la petición ha ido bien</li>";
					html += "<li id='slide7-li3' class='swipe-from-left'>El que detecta el error, establece el mensaje</li>";
					html += "<li id='slide7-li4' class='swipe-from-left'>Permite transmitir objetos en el mensaje</li>";
				html += "</ul>";
			html += "</div>";
			html += "<div id='objeto-info' class='half-row'>";
			html += "</div>";

		$(canvas).html(html);

		setTimeout(function(){$("#slide7-title").addClass("animate");}, 200);
		setTimeout(function(){$("#slide7-li1").addClass("animate");}, 400);
		setTimeout(function(){$("#slide7-li2").addClass("animate");}, 500);
		setTimeout(function(){$("#slide7-li3").addClass("animate");}, 600);
		setTimeout(function(){$("#slide7-li4").addClass("animate");}, 700);
		setTimeout(function(){
			$("#objeto-info").css({"visibility" : "visible"});
			$("#objeto-info").addClass("animated bounceIn");
		}, 900);

		$("#objeto-info").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide7/objeto-info.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});
	},

	end : function(){
		setTimeout(function(){$("#slide7-title").addClass("end");}, 200);
		setTimeout(function(){$("#slide7-li1").addClass("end");}, 400);
		setTimeout(function(){$("#slide7-li2").addClass("end");}, 500);
		setTimeout(function(){$("#slide7-li3").addClass("end");}, 600);
		setTimeout(function(){$("#slide7-li4").addClass("end");}, 700);
		setTimeout(function(){
			$("#objeto-info").removeClass("bounceIn");
			$("#objeto-info").addClass("bounceOut");
		}, 900);
	}
},

{
	title: "slide 8",
	timeOut: 1200,
	render : function(canvas){
		var html = "<div id='slide8-title' class='swipe-from-left responsive-text center-text big-title'>Comunicación cifrada</div>";
			html += "<div class='half-viewport'>";
				html += "<div class='half-row'>";
					html += "<ul>";
						html += "<li id='slide8-li1' class='swipe-from-left'>Toda las peticiones van encriptadas simétricamente en ambos sentidos</li>";
						html += "<li id='slide8-li2' class='swipe-from-left'>Se utiliza la clase SecretKey de java</li>";
						html += "<li id='slide8-li3' class='swipe-from-left'>Reutilización de tareas usando una única instancia</li>";
					html += "</ul>"
				html += "</div>";
				html += "<div class='half-row'>";
					html += "<div id='cifrar-comunicacion'></div>"
				html += "</div>";
			html += "<div>";
			html += "<div class='half-viewport'>";
				html += "<div id='passwordhash-secretkey' class='half-row'>";
				html += "<div>";
				html += "<div class='half-row'>";
				html += "<div>";
			html += "<div>";

		$(canvas).html(html);

		$("#cifrar-comunicacion").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide8/cifrar-comunicacion.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#passwordhash-secretkey").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide8/passwordhash-secretkey.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		setTimeout(function(){$("#slide8-title").addClass("animate");}, 200);
		setTimeout(function(){$("#slide8-li1").addClass("animate");}, 400);
		setTimeout(function(){$("#slide8-li2").addClass("animate");}, 500);
		setTimeout(function(){$("#slide8-li3").addClass("animate");}, 600);
		setTimeout(function(){
			$("#cifrar-comunicacion").css({"visibility" : "visible"});
			$("#cifrar-comunicacion").addClass("animated bounceIn");
		}, 800);
		setTimeout(function(){
			$("#passwordhash-secretkey").css({"visibility" : "visible"});
			$("#passwordhash-secretkey").addClass("animated bounceIn");
		}, 1000);

	},

	end : function(){
		setTimeout(function(){$("#slide8-title").addClass("end");}, 0);
		setTimeout(function(){$("#slide8-li1").addClass("end");}, 200);
		setTimeout(function(){$("#slide8-li2").addClass("end");}, 300);
		setTimeout(function(){$("#slide8-li3").addClass("end");}, 400);
		setTimeout(function(){
			$("#cifrar-comunicacion").removeClass("bounceIn");
			$("#cifrar-comunicacion").addClass("bounceOut");
		}, 600);
		setTimeout(function(){
			$("#passwordhash-secretkey").removeClass("bounceIn");
			$("#passwordhash-secretkey").addClass("bounceOut");
		}, 800);
	}
},

{
	title : "slide 9",
	timeOut : 600,
	render : function(canvas){
		var html = "<div id='slide9-title' class='swipe-from-left responsive-text center-text big-title'>¿De dónde se obtiene la SecretKey?<div>";
			html += "<ul id='slide9-ul'>";
				html += "<li id='slide9-li1' class='swipe-from-left'>La proporciona el servidor con la primera llamada</li>";
				html += "<li id='slide9-li2' class='swipe-from-left'>Es segura: es única para cliente</li>";
				html += "<li id='slide9-li3' class='swipe-from-left'>Se transmite de manera segura: cifrado asimétrico</li>";
			html += "</ul>";

			//html += "<h1 id='slide9-ejemplo'>Veamos un ejemplo</h1>";

		$(canvas).html(html);

		setTimeout(function(){$("#slide9-title").addClass("animate");}, 200);
		setTimeout(function(){$("#slide9-li1").addClass("animate");}, 400);
		setTimeout(function(){$("#slide9-li2").addClass("animate");}, 500);
		setTimeout(function(){$("#slide9-li3").addClass("animate");}, 600);
		/*setTimeout(function(){
			$("#slide9-ejemplo").css({"visibility" : "visible"});
			$("#slide9-ejemplo").addClass("animated rollIn");
		}, 1000);*/
	},

	end : function(){
		setTimeout(function(){$("#slide9-title").addClass("end");}, 0);
		setTimeout(function(){$("#slide9-li1").addClass("end");}, 200);
		setTimeout(function(){$("#slide9-li2").addClass("end");}, 300);
		setTimeout(function(){$("#slide9-li3").addClass("end");}, 400);
		/*setTimeout(function(){
			$("#slide9-ejemplo").removeClass("rollIn");
			$("#slide9-ejemplo").addClass("rollOut");
		}, 500);*/
	}
},

/*	title : "secretkey1",
	timeOut : 400,
	render : function(canvas){
		var html = "<div id='secretkey-ejemplo1'></div>";


		$(canvas).html(html);

		setTimeout(function(){
			$("#secretkey-ejemplo1").css({"visibility" : "visible"});
			$("#secretkey-ejemplo1").addClass("animated slideInRight");
		}, 200);

	},

	end: function(){
		setTimeout(function(){
			$("#secretkey-ejemplo1").removeClass("slideInRight");
			$("#secretkey-ejemplo1").addClass("slideOutLeft");
		}, 200);
	}
},

{
	title : "secretkey2",
	timeOut : 400,
	render : function(canvas){
		var html = "<div id='secretkey-ejemplo2'></div>";


		$(canvas).html(html);

		setTimeout(function(){
			$("#secretkey-ejemplo2").css({"visibility" : "visible"});
			$("#secretkey-ejemplo2").addClass("animated slideInRight");
		}, 200);

	},

	end: function(){
		setTimeout(function(){
			$("#secretkey-ejemplo2").removeClass("slideInRight");
			$("#secretkey-ejemplo2").addClass("slideOutLeft");
		}, 200);
	}
},

{
	title : "secretkey3",
	timeOut : 400,
	render : function(canvas){
		var html = "<div id='secretkey-ejemplo3'></div>";


		$(canvas).html(html);

		setTimeout(function(){
			$("#secretkey-ejemplo3").css({"visibility" : "visible"});
			$("#secretkey-ejemplo3").addClass("animated slideInRight");
		}, 200);

	},

	end: function(){
		setTimeout(function(){
			$("#secretkey-ejemplo3").removeClass("slideInRight");
			$("#secretkey-ejemplo3").addClass("slideOutLeft");
		}, 200);
	}
},

{
	title : "secretkey4",
	timeOut : 400,
	render : function(canvas){
		var html = "<div id='secretkey-ejemplo4'></div>";


		$(canvas).html(html);

		setTimeout(function(){
			$("#secretkey-ejemplo4").css({"visibility" : "visible"});
			$("#secretkey-ejemplo4").addClass("animated slideInRight");
		}, 200);

	},

	end: function(){
		setTimeout(function(){
			$("#secretkey-ejemplo4").removeClass("slideInRight");
			$("#secretkey-ejemplo4").addClass("slideOutLeft");
		}, 200);
	}
},

{
	title : "secretkey5",
	timeOut : 400,
	render : function(canvas){
		var html = "<div id='secretkey-ejemplo5'></div>";


		$(canvas).html(html);

		setTimeout(function(){
			$("#secretkey-ejemplo5").css({"visibility" : "visible"});
			$("#secretkey-ejemplo5").addClass("animated slideInRight");
		}, 200);

	},

	end: function(){
		setTimeout(function(){
			$("#secretkey-ejemplo5").removeClass("slideInRight");
			$("#secretkey-ejemplo5").addClass("slideOutLeft");
		}, 200);
	}
},

{
	title : "secretkey6",
	timeOut : 400,
	render : function(canvas){
		var html = "<div id='secretkey-ejemplo6'></div>";


		$(canvas).html(html);

		setTimeout(function(){
			$("#secretkey-ejemplo6").css({"visibility" : "visible"});
			$("#secretkey-ejemplo6").addClass("animated slideInRight");
		}, 200);

	},

	end: function(){
		setTimeout(function(){
			$("#secretkey-ejemplo6").removeClass("slideInRight");
			$("#secretkey-ejemplo6").addClass("slideOutLeft");
		}, 200);
	}
},

{
	title : "secretkey7",
	timeOut : 400,
	render : function(canvas){
		var html = "<div id='secretkey-ejemplo7'></div>";


		$(canvas).html(html);

		setTimeout(function(){
			$("#secretkey-ejemplo7").css({"visibility" : "visible"});
			$("#secretkey-ejemplo7").addClass("animated slideInRight");
		}, 200);

	},

	end: function(){
		setTimeout(function(){
			$("#secretkey-ejemplo7").removeClass("slideInRight");
			$("#secretkey-ejemplo7").addClass("slideOutLeft");
		}, 200);
	}
},*/
{
	title : "slide10",
	timeOut : 1200,

	render : function(canvas){
		var html = "<div id='slide10-title' class='swipe-from-left responsive-text center-text big-title'>Más seguridad: HTTP Session</div>";
			html += "<div class='half-viewport'>";
				html += "<ul>";
					html += "<li id='slide10-li1' class='swipe-from-left'>Cuando el servidor entrega la SecretKey, la mete en la sesión.</li>";
					html += "<li id='slide10-li2' class='swipe-from-left'>Cuando el cliente recibe la SecretKey, la mete en su contexto.</li>";
					html += "<li id='slide10-li3' class='swipe-from-left'>Para el resto de la comunicación, se utilizará la misma clave.</li>";
				html += "</ul>";
			html += "</div>";
			html += "<div class='half-viewport'>";
				html += "<div id='slide10-half1' class = 'half-row fade-in'>";
					html += "<div id='btn-session-cliente'></div>";
				html += "</div>";
				html += "<div id='slide10-half2' class = 'half-row fade-in'>";
					html += "<div id='btn-session-servidor'></div>";
				html += "</div>";
			html += "</div>";

		$(canvas).html(html);

		setTimeout(function(){$("#slide10-title").addClass("animate");}, 200);
		setTimeout(function(){$("#slide10-li1").addClass("animate");}, 400);
		setTimeout(function(){$("#slide10-li2").addClass("animate");}, 500);
		setTimeout(function(){$("#slide10-li3").addClass("animate");}, 600);
		setTimeout(function(){$("#slide10-half1").addClass("animate");}, 800);
		setTimeout(function(){$("#slide10-half2").addClass("animate");}, 1000);

		$("#btn-session-cliente").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide10/session-cliente.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#btn-session-servidor").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide10/session-servidor.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});
	},

	end : function(){
		setTimeout(function(){$("#slide10-title").addClass("animate");}, 0);
		setTimeout(function(){$("#slide10-li1").addClass("animate");}, 200);
		setTimeout(function(){$("#slide10-li2").addClass("animate");}, 300);
		setTimeout(function(){$("#slide10-li3").addClass("animate");}, 400);
		setTimeout(function(){$("#slide10-half1").addClass("animate");}, 600);
		setTimeout(function(){$("#slide10-half2").addClass("animate");}, 800);
	}
},

/*{
	title : "slide 11",
	timeOut: 200,
	
	render : function(canvas){
		var html = "<div class='responsive-text center-text big-title'>Petición de ejemplo</div>";
			html += "<div class='half-row' style='height: 90%'>";
				html += "<div id='peticion-ejemplo'></div>";
				html += "<div id='peticion-ejemplo2'></div>";
			html += "</div>";
			html += "<div class='half-row' style='height: 90%'>";
				html += "<div id='peticion-ejemplo-servidor'></div>";
				html += "<div id='peticion-ejemplo-servidor2'></div>";
			html += "</div>";

		$(canvas).html(html);


		$("#peticion-ejemplo").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide11/peticion-ejemplo.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#peticion-ejemplo2").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide11/peticion-ejemplo2.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#peticion-ejemplo-servidor").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide11/peticion-ejemplo-servidor.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});

		$("#peticion-ejemplo-servidor2").on("click", function(){
			var zoom= "<div id='zoom' class='popup'><img class='img-responsive center-img-in-div' src='resources/slide11/peticion-ejemplo-servidor2.PNG'/> </div>";
			$("#canvas").append(zoom);
			$("#zoom").on("click", function(){
				$(this).remove();
			});
		});
	},

	end: function(){

	}
}*/

{
	title : "herencia-bbdd",
	timeOut : 1100,
	render : function(canvas){
		var html = "<div id='herencia-bbdd' class='swipe-from-left responsive-text center-text big-title'>La herencia y las tablas MySQL</div>";
			html += "<ul>";
				html += "<li id='herencia-li1' class='swipe-from-left'>Los productos finales extienden de una clase padre Producto</li>";
				html += "<li id='herencia-li2' class='swipe-from-left'>Una tabla para la información común y otra para cada producto<ul><li id='herencia-li3' class='swipe-from-left'>Producto final con foreign key del Padre</li></ul></li>";
				html += "<li id='herencia-li4' class='swipe-from-left'>La inserción se hace en dos pasos<ul><li id='herencia-li5' class='swipe-from-left'>Inserción en el padre</li><li id='herencia-l61' class='swipe-from-left'>Se recoge el id del padre que se acaba de insertar y se pone de foreing key en el hijo</li></ul></li>";
				html += "<li id='herencia-li7' class='swipe-from-left'>Reconstrucción del producto en sentido inverso</li>";
				html += "<li id='herencia-li8' class='swipe-from-left'>PROBLEMA: clase Producto es abstracta</li>";
			html += "</ul>";

		$(canvas).html(html);

		setTimeout(function(){$("#herencia-bbdd").addClass("animate");}, 200);
		setTimeout(function(){$("#herencia-li1").addClass("animate");}, 400);
		setTimeout(function(){$("#herencia-li2").addClass("animate");}, 500);
		setTimeout(function(){$("#herencia-li3").addClass("animate");}, 600);
		setTimeout(function(){$("#herencia-li4").addClass("animate");}, 700);
		setTimeout(function(){$("#herencia-li5").addClass("animate");}, 800);
		setTimeout(function(){$("#herencia-li6").addClass("animate");}, 900);
		setTimeout(function(){$("#herencia-li7").addClass("animate");}, 1000);
		setTimeout(function(){$("#herencia-li8").addClass("animate");}, 1100);

	},

	end: function(){
		setTimeout(function(){$("#herencia-bbdd").addClass("end");}, 0);
		setTimeout(function(){$("#herencia-li1").addClass("end");}, 200);
		setTimeout(function(){$("#herencia-li2").addClass("end");}, 300);
		setTimeout(function(){$("#herencia-li3").addClass("end");}, 400);
		setTimeout(function(){$("#herencia-li4").addClass("end");}, 500);
		setTimeout(function(){$("#herencia-li5").addClass("end");}, 600);
		setTimeout(function(){$("#herencia-li6").addClass("end");}, 700);
		setTimeout(function(){$("#herencia-li7").addClass("end");}, 800);
		setTimeout(function(){$("#herencia-li8").addClass("end");}, 900);

	}
},

{
	title : "end",
	timeOut : 200,
	render : function(canvas){
		var html = "<div id='probar'><span>¡A probar!</span></div>";

		$(canvas).html(html);

		setTimeout(function(){
			$("#probar").addClass("animated rollIn");
			$("#probar").css({"visibility" : "visible"});
		}, 200);

	},

	end : function(){

	}
}
];