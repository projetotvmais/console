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
    
}

window.onpageshow = function(){
    // Esconde o menu lateral
    hideNavBar();

    // Ativa os listeners
    addEventListeners();
}