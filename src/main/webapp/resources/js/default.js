var ratio = 16;
var navShown = false;

function hideNavBar(){
    var i = 0;
    var navbarInitialPosition = 0;
    var navbarFinalPosition = -30;

    var hideInterval = setInterval(function(){
        navbarInitialPosition--;
        $("#menu").css("left",navbarInitialPosition+"%");
        if(navbarInitialPosition == navbarFinalPosition){
            clearInterval(hideInterval);
        }
    },ratio);
    navShown = false;
}

function showNavBar(){
    var i = 0;
    var navbarInitialPosition = -30;
    var navbarFinalPosition = 0;

    var showInterval = setInterval(function(){
        navbarInitialPosition++;
        $("#menu").css("left",navbarInitialPosition+"%");
        if(navbarInitialPosition == navbarFinalPosition){
            clearInterval(showInterval);
        }
    },ratio);
    navShown = true;
}

function carregarPagina(pagina){
	var dominio = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + "/";
    var url = dominio + pagina;
    console.log("carregando: "+url);
    $("#main-content").load(url);
}

function addEventListeners(){
    // Adiciona função de mostrar ou esconder menu no click do botão
    $("#navbar-toggler").click(function(){
        var pos = $("#menu").css("left");
        if(pos != '0px'){
            showNavBar();
        }
        else{
            hideNavBar();
        }
    });
    
    // Evento de click no botão de mostrar o gerenciador de canais.
    $("#canais").click(function(){
        carregarPagina("canais");
    });
    
}

window.onpageshow = function(){
    // Esconde o menu lateral
    hideNavBar();

    // Ativa os listeners
    addEventListeners();

    // Apaga a mensagem aos poucos
    $("#alert-closeable").addClass("fade");
}