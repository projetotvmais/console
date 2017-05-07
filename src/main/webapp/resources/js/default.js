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
    
    // Adiciona evento de click no link de "esqueci minha senha"
    $("#esqueci-minha-senha").click(function(){
		$("#esqueci-flag").val(true);
		$("#login-form").submit();
    });

    // Adiciona evento de mostrar descrição do canal
    $(".canal").click(function(event){
        var id_canal = "#"+event.currentTarget.id;
        var nome_do_canal = id_canal + " .panel-heading";
        var imagem_do_canal = id_canal + " .imagem-do-canal";
        var descricao_do_canal = id_canal + " .descricao-do-canal";
        var rodape = id_canal + " .canal-panel-footer";

        var deFrente = $(nome_do_canal).hasClass("escondido");
        if(deFrente){
            $(id_canal).addClass("animated flipInY");
            setTimeout(function(){
                $(id_canal).removeClass("animated flipInY");
            },500);
            $(imagem_do_canal).addClass("escondido");
            $(nome_do_canal).removeClass("escondido");
            $(descricao_do_canal).removeClass("escondido");
            $(rodape).removeClass("escondido");
        }
        else{
            $(id_canal).addClass("animated flipInY");
            setTimeout(function(){
                $(id_canal).removeClass("animated flipInY");
            },500);            
            $(nome_do_canal).addClass("escondido");
            $(descricao_do_canal).addClass("escondido");
            $(rodape).addClass("escondido");
            $(imagem_do_canal).removeClass("escondido");
        }
    });

    // Abre a form de cadastro de canal no click
    $("#cadastrar-canal").click(function(){
        // Abre a form
        $("#form-cadastrar-canal").dialog({
            top: 60,
            height: 450,
            width: 600,
            modal: true,
            show: {
                effect: "fade",
                duration: 500
            },
            hide: {
                effect: "fade",
                duration: 500
            }
        });
        // Mostra o conteudo da form
        $("#form-cadastrar-canal").removeClass("escondido");
        setTimeout(function(){
            $(".ui-dialog").addClass("top-60");
        },500);
    });
}

window.onpageshow = function(){
    // Esconde o menu lateral
    hideNavBar();

    // Ativa os listeners
    addEventListeners();

    // Verifica se a página contem scrool vertical e redimensiona a mesma
    var temScrool = window.scrollbars.visible;
    if(temScrool){
        $(".styled-body").addClass("com-scrool");
    }
}