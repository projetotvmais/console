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
				<div class="col-md-8 col-md-offset-2">
					<div id="main-content" class="row">
						
					</div>
				</div>
			</div>
		</div>
		<div id="form-mudar-login" class="escondido" title="Mudar dados de login">
			<form method="post" action="atualizarPerfil" class="form-group" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${perfil.id}">
				Nome: 
				<input type="text" name="nome" class="form-control" placeholder="Nome" value="${perfil.nome}">
				Senha:
				<input type="password" name="senha" class="form-control" placeholder="Senha" value="${perfil.senha}">
				E-mail:
				<input type="text" name="email" class="form-control" placeholder="E-mail" value="${perfil.email}">
				Telefone:
				<input type="text" name="telefone" class="form-control" placeholder="Telefone" value="${perfil.telefone}">
				
				<hr>
				<button class="btn-block btn-white form-control info" type="submit">Atualizar</button>
			</form>
		</div>
	
<jsp:include page="commons/footer.jsp"/>