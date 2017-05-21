<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jsp:include page="../commons/header.jsp"/>
<jsp:include page="../commons/navbar.jsp"/>

		<div class="container-fluid">
			<div class="row">
				<a id="cadastrar-pacote" href="#" class="float-right" onclick="mostrarTelaDeCadastroDePacote()">Cadastrar pacote</a><br>
				<a id="btn-home" href="home" class="float-right">Home</a>
				<div class="col-xs-8 col-xs-offset-2 text-center">
					${mensagem}
		    	</div>
		  	</div>
			<div class="row div-pesquisa">
				<div class="col-xs-8 col-xs-offset-2 text-center">
					<form method="post" action="pacotes">
						<input id="pesquisa" type="text" class="form-control text-center" name="pesquisa" value="${pesquisa}">
						<button class="btn-block btn-white form-control info" type="submit">Procurar pelo pacote</button>
					</form>
				</div>
			</div>
		  	<div class="container-fluid">
				<c:forEach var="pacote" items="${pacotes}">
					<div class="row pacote-row">
						<div class="col-md-3">
							<div id="pacote${pacote.id}" class="panel pacote">
								<div class="panel-heading panel-default center-block">
									<p class="nome-do-pacote">${pacote.nome}</p>
								</div>
								<div class="panel-body pacote-panel-body">
									<img alt="Imagem do Pacote" src="mostrarImagemLogoDoPacote?id=${pacote.id}" class="imagem-do-pacote">
								</div>
								<div class="panel-footer">
	                                <a href="#" onclick="desejaRemoverPacote(${pacote.id})">Remover</a><br>
	                                <a href="#"  onclick="verpacote(${pacote.id})">Atualizar</a>
	                            </div>
							</div>
						</div>
					</div>
				</c:forEach>
            </div>
        </div>

        <c:forEach var="pacote" items="${pacotes}">
        	<div id="form-atualizar-pacote${pacote.id}" class="escondido" title="Atualizar pacote">
	            <form method="post" action="atualizarPacote" class="form-group" enctype="multipart/form-data">
	            	<input type="hidden" name="id" value="${pacote.id}">
	                Nome: 
	                <input type="text" id="atualizar_pacote_nome" name="nome" class="form-control" placeholder="Nome" value="${pacote.nome}" required>
	                Logo: 
	                <input type="file" accept="image/*" id="atualizar_pacote_logo" name="logo" class="form-control" title="Escolha uma imagem">
	                Valor: 
	                <input type="text" id="atualizar_pacote_valor" name="valor" class="form-control currency" placeholder="0.00" value="${pacote.fmtValor}" required>
	                Canais: 
	                <input type="hidden" id="atualizar_pacote_canais${pacote.id}" name="canais" class="form-control" placeholder="Lista de canais" value="${pacote.canais}" required>
	                <button type="button" class="btn-block btn-white form-control info" onclick="mostraTelaDeSelecaoDeCanais('${pacote.id}','atualizar_canais_selecionados${pacote.id}','atualizar_pacote_canais${pacote.id}')">Selecionar canais</button>
	                Canais selecionados: <b><span id="atualizar_canais_selecionados${pacote.id}">0</span></b>
	                <hr>
	                <button class="btn-block btn-white form-control info" type="submit">Atualizar</button>
	            </form>
	            <script type="text/javascript">
            		var canaisSelecionados${pacote.id} = "${pacote.canais}";
            		var can_split${pacote.id} = canaisSelecionados${pacote.id}.split(",");
            		$("#atualizar_canais_selecionados${pacote.id}").text(""+can_split${pacote.id}.length);
            	</script>
	        </div>
        </c:forEach>

        <div id="form-cadastrar-pacote" class="escondido" title="Cadastrar novo pacote">
            <form method="post" action="cadastrarPacote" class="form-group" enctype="multipart/form-data">
                Nome: 
                <input type="text" id="cadastrar_pacote_nome" name="nome" class="form-control" placeholder="Nome" required>
                Logo: 
                <input type="file" accept="image/*" id="cadastrar_pacote_logo" name="logo" class="form-control" title="Escolha uma imagem">
                Valor: 
                <input type="text" id="cadastrar_pacote_valor" name="valor" class="form-control currency" placeholder="0.00" required>
                Canais: 
                <input type="hidden" id="cadastrar_pacote_canais" name="canais" class="form-control" placeholder="Lista de canais" readonly required>
                <button type="button" class="btn-block btn-white form-control info" onclick="mostraTelaDeSelecaoDeCanais('','cadastrar_canais_selecionados','cadastrar_pacote_canais')">Selecionar canais</button>
                Canais selecionados: <b><span id="cadastrar_canais_selecionados">0</span></b>
                <hr>
                <button class="btn-block btn-white form-control info" type="submit">Cadastrar</button>
            </form>
        </div>
        
        <div id="remover_pacote_dialog" class="escondido" title="Deseja remover este pacote?">
            <h5>Deseja remover este pacote?</h5>
            <form id="remover_pacote_form" action="removerPacote" method="get">
                <input id="remover_pacote_input" name="id" type="hidden" value="0">
            </form>
        </div>

        <div id="seletor-de-canais-dialog" class="escondido" title="Selecionar Canais">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <div class="table">
                            <table class="table tabela-tokens">
                                <tbody class="text-center">
                                    <tr class="table-tr-header text -center">
                                        <th class="text-center" colspan="2">Canais Cadastrados</th>
                                    </tr>
                                    <tr class="table-tr text -center">
                                        <th class="table-th text-center">Selecionado</th>
                                        <th class="table-th text-center">Nome</th>
                                    </tr>
                                    <c:forEach var="canal" items="${canais}">
                                    	<tr role="row" class="table-tr text-center">
	                                        <td role="gridcell" class="table-td text-center">
	                                            <select id="${canal.id}" class="seletor_de_canais">
	                                                <option value=false>Não</option>
	                                                <option value=true>Sim</option>
	                                            </select>
	                                        </td>
	                                        <td role="gridcell" class="table-td text-center">${canal.nome}</td>
	                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <c:forEach var="pacote" items="${pacotes}">
        	<div id="seletor-de-canais-dialog${pacote.id}" class="escondido" title="Selecionar Canais">
	            <div class="container-fluid">
	                <div class="row">
	                    <div class="col-xs-8 col-xs-offset-2">
	                        <div class="table">
	                            <table class="table tabela-tokens">
	                                <tbody class="text-center">
	                                    <tr class="table-tr-header text -center">
	                                        <th class="text-center" colspan="2">Canais Cadastrados</th>
	                                    </tr>
	                                    <tr class="table-tr text -center">
	                                        <th class="table-th text-center">Selecionado</th>
	                                        <th class="table-th text-center">Nome</th>
	                                    </tr>
	                                    <c:set var="splited_channels" value="${fn:split(pacote.canais, ',')}"/>
	                                    <c:forEach var="canal" items="${canais}">
	                                    	<tr role="row" class="table-tr text-center">
		                                        <td role="gridcell" class="table-td text-center">
		                                            <select id="${canal.id}" class="seletor_de_canais">
		                                            	<c:set var="contains" value="false"/>
		                                            	<c:forEach var="id" items="${splited_channels}">
		                                            		<c:if test="${id eq canal.id}">
		                                            			<c:set var="contains" value="true"/>
		                                            		</c:if>
		                                            	</c:forEach>
		                                            	<c:if test="${contains eq 'true'}">
		                                            		<option value=true>Sim</option>
		                                            	</c:if>
		                                            	<option value=false>Não</option>
		                                                <option value=true>Sim</option>
		                                            </select>
		                                        </td>
		                                        <td role="gridcell" class="table-td text-center">${canal.nome}</td>
		                                    </tr>
	                                    </c:forEach>
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
        </c:forEach>
		
<jsp:include page="../commons/footer.jsp"/>