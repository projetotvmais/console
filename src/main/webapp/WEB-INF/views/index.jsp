<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="commons/header.jsp"/>
<jsp:include page="commons/navbar.jsp"/>
		
		<div class="container-fluid">
			<div class="row">
				<a id="mudar-login" href="#" class="float-right">Mudar dados de login</a><br>
				<a href="logout" class="float-right">Logout</a>

				<div class="col-xs-8 col-xs-offset-2 text-center">
					${mensagem}
		    	</div>
		    	
		  	</div>
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2">
					<div class="table">
						<table class="table tabela-tokens">
							<tbody class="text-center">
								<tr class="table-tr-header text -center">
									<th class="text-center" colspan="2">Tokens Cadastrados</th>
									<th class="text-center"><a href="#" onclick="cadastrarToken()">Cadastrar Token</a></th>
								</tr>
								<tr class="table-tr text -center">
									<th class="table-th text-center">Token</th>
									<th class="table-th text-center">URL</th>
									<th class="table-th text-center">Ações</th>
								</tr>
								<c:forEach var="token" items="${tokens}">
									<tr role="row" id="${token.id}" class="table-tr text-center">
										<td role="gridcell" class="table-td text-center">${token.nome}</td>
										<td role="gridcell" class="table-td text-center">
											<a href="testeDeCanal?id=${token.canal.id}&token=${token.nome}">https://tvmaisconsole.herokuapp.com/testeDeCanal?id=${token.canal.id}&token=${token.nome}</a>
										</td>
										<td role="gridcell" class="table-td text-center">
											<a href="removerToken?id=${token.id}">Remover</a><br>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div id="form-mudar-login" class="escondido" title="Mudar dados de login">
			<form method="post" action="atualizarPerfil" class="form-group" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${perfil.id}">
				Nome: 
				<input type="text" name="nome" class="form-control" placeholder="Nome" value="${perfil.nome}" required>
				Senha:
				<input type="password" name="senha" class="form-control" placeholder="Senha" value="${perfil.senha}" required>
				E-mail:
				<input type="text" name="email" class="form-control" placeholder="E-mail" value="${perfil.email}" required>
				Telefone:
				<input type="text" name="telefone" class="form-control" placeholder="Telefone" value="${perfil.telefone}" required>
				
				<hr>
				<button class="btn-block btn-white form-control info" type="submit">Atualizar</button>
			</form>
		</div>
		<div id="form-cadastrar-token" class="escondido" title="Cadastrar novo token">
			<form method="post" action="cadastrarToken" class="form-group" enctype="multipart/form-data">
				Canal:
				<select name="canal" class="form-control" required>
					<c:forEach var="itemCanal" items="${listaCanais}">
						<option value="${itemCanal.id}">${itemCanal.nome}</option>
					</c:forEach>
				</select>
				Validade:
				<input type="datetime-local" name="validade" class="form-control"required>
				<hr>
				<button class="btn-block btn-white form-control info" type="submit">Cadastrar</button>
			</form>
		</div>
	
<jsp:include page="commons/footer.jsp"/>