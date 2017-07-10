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
            navShown = false;
        }
    },ratio);
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
            navShown = true;
        }
    },ratio);
}

function navBarOuterClick(){
    if(navShown){
        hideNavBar();
    }
}

function mostrarMensagem(mensagem){
	if(mensagem != null && mensagem != ''){
		var dialog = "<div id='modal_dialog_v01' title='Mensagem' style='display:none'>"+
					 "	<p>"+mensagem+"</p>"+
					 "</div>";
		$("body").append(dialog);
			
		$("#modal_dialog_v01").dialog({
			modal: true,
			buttons: {
				Ok: function() {
			     $( this ).dialog("close");
			     $("#modal_dialog_v01").remove();
			   }
			},
			close: function(){
				$("#modal_dialog_v01").remove();
			}
		});
	}
	
}

function vercanal(canal){
    // Abre a form
    $("#form-atualizar-canal"+canal).dialog({
        height: 520,
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
    $("#form-atualizar-canal"+canal).removeClass("escondido");
    setTimeout(function(){
        $(".ui-dialog").addClass("top-60");
    },500);
}

function cadastrarToken(){
    // Abre a form
    $("#form-cadastrar-token").dialog({
        height: 260,
        width: 400,
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
    $("#form-cadastrar-token").removeClass("escondido");
    setTimeout(function(){
        $(".ui-dialog").addClass("top-60");
    },500);
}

function mostrarTelaDeCadastroDePacote(){
    // Abre a form
    $("#form-cadastrar-pacote").dialog({
        height: 380,
        width: 400,
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
    $("#form-cadastrar-pacote").removeClass("escondido");
    setTimeout(function(){
        $(".ui-dialog").addClass("top-60");
    },500);
}

function verpacote(id){
    // Abre a form
    $("#form-atualizar-pacote"+id).dialog({
        height: 380,
        width: 400,
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
    $("#form-atualizar-pacote"+id).removeClass("escondido");
    setTimeout(function(){
        $(".ui-dialog").addClass("top-60");
    },500);
}

function mostraTelaDeSelecaoDeCanais(pacote,campo,input){
    // Abre a form
    $("#seletor-de-canais-dialog"+pacote).dialog({
        height: 400,
        width: 400,
        modal: true,
        show: {
            effect: "fade",
            duration: 500
        },
        hide: {
            effect: "fade",
            duration: 500
        },
        buttons:{
            Ok:function(){
                var canaisSelecionados = [];
                var items = $(document).find(".seletor_de_canais");
                for(var i = 0;i < items.length;i++){
                    if(items[i].value == "true")
                        canaisSelecionados.push(items[i].id);
                }
                $("#"+campo).text(""+canaisSelecionados.length);
                $("#"+input).val(canaisSelecionados);
                $("#seletor-de-canais-dialog"+pacote).addClass("escondido");
                $("#seletor-de-canais-dialog"+pacote).dialog("close");
            }
        }
    });

    // Mostra o conteudo da form
    $("#seletor-de-canais-dialog"+pacote).removeClass("escondido");
    setTimeout(function(){
        $(".ui-dialog").addClass("top-60");
    },500);
}

function desejaRemoverPacote(id){
    // Abre a form
   $("#remover_pacote_dialog").dialog({
       height: 150,
       width: 400,
       modal: true,
       show: {
           effect: "fade",
           duration: 500
       },
       hide: {
           effect: "fade",
           duration: 500
       },
       buttons:{
           Ok:function(){
               $("#remover_pacote_input").val(id);
               $("#remover_pacote_form").submit();
               $("#remover_pacote_dialog").addClass("escondido");
               $("#remover_pacote_dialog").dialog("close");
           }
       }
   });

   // Mostra o conteudo da form
   $("#remover_pacote_dialog").removeClass("escondido");
   setTimeout(function(){
       $(".ui-dialog").addClass("top-60");
   },500);
}
   
function selecionarCanaisPorStatus(elem){
    var modo = elem.value;
    if(modo == 'funcionando'){
        $(document).find(".canal-row").show();
        var canais_quebrados = $(".canal-row div").find(".quebrado");
        for(var i = 0;i < canais_quebrados.length;i++){
            $(canais_quebrados[i]).parent().parent().hide();
        }
    }
    else if(modo == 'quebrados'){
        $(document).find(".canal-row").show();
        var todos_os_canais = $(".canal-row div").find(".canal");
        for(var i = 0;i < todos_os_canais.length;i++){
            if(!$(todos_os_canais[i]).hasClass("quebrado")){
                $(todos_os_canais[i]).parent().parent().hide();
            }
        }
    }
    else{
        $(document).find(".canal-row").show();
    }
}

function selecionarCanaisPorClassificacao(elem){
    var classificacao = elem.value;
    if(classificacao == '0'){
        $(document).find(".canal-row").show();
    }
    else{
        $(document).find(".canal-row").hide();
        var canais_selecionados = $(document).find(".classificacao-canal");
        for(var i = 0;i < canais_selecionados.length;i++){
            if($(canais_selecionados[i]).val() == classificacao){
                $(canais_selecionados[i]).parent().parent().parent().show();
            }
        }
    }
}

function somaTopBottom(elemento){
    var pad_bot = elemento.css("padding-bottom").replace("px","");
    var pad_top = elemento.css("padding-top").replace("px","");
    var mg_top = elemento.css("margin-top").replace("px","");
    var mg_bot = elemento.css("margin-bottom").replace("px","");

    return 1*pad_bot + 1*pad_top + 1*mg_bot + 1*mg_top;
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
            height: 520,
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
    
    // Abre a form de atualização de perfil
    $("#mudar-login").click(function(){
        // Abre a form
        $("#form-mudar-login").dialog({
            top: 60,
            height: 350,
            width: 350,
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
        $("#form-mudar-login").removeClass("escondido");
        setTimeout(function(){
            $(".ui-dialog").addClass("top-60");
        },500);
    });

    $("body").click(navBarOuterClick);

    if($(".lista-clientes").children().length > 0){
        $("#clientes_nao_cadastrados").hide();
    }
}

function exibirCliente(clienteId){
    $.get("pegarClientePorId?id="+clienteId,function(response){
    	console.log(response);
    	var cliente = {
    			id: response.usuario.id,
    			nome: response.usuario.nome,
    			identificacao: response.usuario.identificacao,
    			email: response.usuario.email,
    			telefone: response.usuario.telefone,
    			endereco: response.usuario.endereco + ', ' + response.usuario.numero + ', ' + response.usuario.bairro + ', ' + response.usuario.cidade + ', ' + response.usuario.estado + '.CEP: ' + response.usuario.cep,
    			observacoes: response.usuario.observacoes,
    			status: response.usuario.ativo
    	}
    	
    	
    	$(".dialog-cliente-foto img").attr("src","mostrarFotoDoCliente?id="+cliente.id);

        $(".dialog-cliente-info-nome").text(cliente.nome);
        $(".dialog-cliente-info-identificacao").text(cliente.identificacao);
        $(".dialog-cliente-info-email").text(cliente.email);
        $(".dialog-cliente-info-telefone").text(cliente.telefone);
        $(".dialog-cliente-info-endereco").text(cliente.endereco);
        $(".dialog-cliente-info-observacoes").text(cliente.observacoes);

        var ativo = cliente.status;
        if(ativo){
            $(".dialog-cliente-info-status").text("Ativo");
            $(".dialog-cliente-info-status").addClass("cliente-ativo");
        }
        else{
            $(".dialog-cliente-info-status").text("Desativado");
        }
        
        $("#dialog_cliente").show();
    },"json");
}

function mudarStatusDeCliente(clienteId){
	$.get("mudarStatusDeCliente?id="+clienteId,function(response){
    	var mensagem = response.mensagem;
    	mostrarMensagem(mensagem);
    },"json");
}

function esconderCliente(){
    $("#dialog_cliente").hide();
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

    // Alinha a altura das divs de foto e ações de cada cliente de acordo com a div de conteudo.
    $(".lista-clientes-item").each(function(){
        var divFoto = $($(this).children()[0]);
        var divInfo = $($(this).children()[1]);
        var divAcoes = $($(this).children()[2]);

        var pNome = $(divInfo.children()[0]);
        var textInsidePNome = pNome.text();
        if(textInsidePNome.length > 30){
            pNome.attr("title",textInsidePNome);
            textInsidePNome = textInsidePNome.substring(0,30) + "...";
            pNome.text(textInsidePNome);
        }

        var defaultHeight = divInfo.height();

        var fotoPad = somaTopBottom(divFoto);
        divFoto.height(defaultHeight - fotoPad);
        var acoesPad = somaTopBottom(divAcoes);
        divAcoes.height(defaultHeight - acoesPad);
    });

}