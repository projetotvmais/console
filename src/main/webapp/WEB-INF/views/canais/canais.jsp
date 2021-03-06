<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../commons/header.jsp"/>
<jsp:include page="../commons/navbar.jsp"/>

		<div class="container-fluid">
			<div class="row">
				<a id="cadastrar-canal" href="javascript:void(0);" class="float-right">Cadastrar canal</a><br>
				<a id="btn-home" href="home" class="float-right">Home</a><br>
				<select id="selecionar_tipo_canal" class="float-right form-control" style="width:150px;" onchange="selecionarCanaisPorStatus(this)">
                    <option value="tudo">Mostrando tudo</option>
                    <option value="quebrados">Mostrando s� os quebrados</option>
                    <option value="funcionando">Mostrando s� os funcionais</option>
                </select>

				<div class="col-xs-8 col-xs-offset-2 text-center">
					${mensagem}
		    	</div>
		    	
		  	</div>
			<div class="row div-pesquisa">
				<div class="col-xs-8 col-xs-offset-2">
					<form method="post" class="form-inline" action="pesquisarCanal">
                        <input id="pesquisa" type="text" class="form-control" name="pesquisa" placeholder="Pesquise por nome" style="width:85%;" value="${pesquisa}">
                        <button class="btn-block btn-white form-control info" type="submit" class="float-right" style="width:100px;">Filtrar</button>
					</form>
                    <form class="form-inline">
                        <select id="selecionar_canais_por_classificacao" class="form-control" style="width:100%;" onchange="selecionarCanaisPorClassificacao(this)">
                            <option value="0">Selecionar por classifica��o</option>
                            <c:forEach var="classificacao" items="${classificacoes}">
                            	<option value="${classificacao.nome}">${classificacao.nome}</option>
                            </c:forEach>
                        </select>
					</form>
				</div>
			</div>
		  	<div class="container-fluid">
				<c:forEach var="canal" items="${canais}">
					
					<div class="row canal-row">
						<div class="col-md-3">
							<c:if test="${canal.funcionando eq true}">
								<div id="canal${canal.id}" class="panel canal">
							</c:if>
							<c:if test="${canal.funcionando eq false}">
								<div id="canal${canal.id}" class="panel canal quebrado">
							</c:if>
								<input type="hidden" class="classificacao-canal" value="${canal.classificacao.nome}">
								<div class="panel-heading panel-default center-block escondido">
									<p class="nome-do-canal">${canal.nome}</p>
								</div>
								<div class="panel-body canal-panel-body">
									<img alt="Imagem do Canal" src="carregarLogoDoCanal?id=${canal.id}" class="imagem-do-canal">
									<p class="descricao-do-canal escondido">${canal.observacoes}</p>
								</div>
								<div class="panel-footer panel-default canal-panel-footer escondido">
									<c:if test="${canal.funcionando eq true}">
										<p>Status: <b>funcionando</b></p>
									</c:if>
									<c:if test="${canal.funcionando eq false}">
										<p>Status: <b>quebrado</b></p>
									</c:if>
									<a href="javascript:void(0);" id="ver-canal${canal.id}" onclick="vercanal(${canal.id})">Ver informa��es</a><br>
									<a href="testarCanal?id=${canal.id}">Testar canal</a>
									<div id="form-atualizar-canal${canal.id}" class="escondido" title="Atualizar dados de canal">
										<form method="post" action="atualizarCanal" class="form-group" enctype="multipart/form-data">
											<input type="hidden" name="id" value="${canal.id}">
											Nome: 
											<input type="text" name="nome" class="form-control" placeholder="Nome" value="${canal.nome}">
											Logo: 
											<input type="file" accept="image/*" id="imagem" name="imagem" class="form-control" title="Escolha uma imagem">
											Url: 
											<input type="text" name="url" class="form-control" placeholder="Url" value="${canal.url}">
											Classifica��o: 
											<select name="classificacao_id" class="form-control">
												<option value="${canal.classificacao.id}">${canal.classificacao.nome}</option>
												<c:forEach var="classif" items="${classificacoes}">
													<option value="${classif.id}">${classif.nome}</option>
												</c:forEach>
											</select>
											Descri��o: 
											<input type="text" name="observacoes" class="form-control" placeholder="Descri��o" value="${canal.observacoes}" required>
											Status:
											<select name=funcionando class="form-control">
												<option value="true">Sim</option>
												<option value="false">N�o</option>
											</select>
											<hr>
											<button class="btn-block btn-white form-control info" type="submit">Atualizar</button>
										</form>
									</div>
								</div>
							</div>
						</div>
				  	</div>
				</c:forEach>
			</div>
			<div id="form-cadastrar-canal" class="escondido" title="Cadastrar novo canal">
				<form method="post" action="cadastrarCanal" class="form-group" enctype="multipart/form-data">
					Nome: 
					<input type="text" name="nome" class="form-control" placeholder="Nome">
					Logo: 
					<input type="file" accept="image/*" id="imagem" name="imagem" class="form-control" title="Escolha uma imagem">
					Url: 
					<input type="text" name="url" class="form-control" placeholder="Url">
					Classifica��o: 
					<select name="classificacao_id" class="form-control">
						<c:forEach var="classificacao" items="${classificacoes}">
							<option value="${classificacao.id}">${classificacao.nome}</option>
						</c:forEach>
					</select>
					Descri��o: 
					<input type="text" name="observacoes" class="form-control" placeholder="Descri��o">
					<hr>
					<button class="btn-block btn-white form-control info" type="submit">Cadastrar</button>
				</form>
			</div>
		</div>
		
<jsp:include page="../commons/footer.jsp"/>