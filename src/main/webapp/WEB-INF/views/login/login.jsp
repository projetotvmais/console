<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../commons/header.jsp"/>

		<div class="container-fluid">
			<div class="row main-row">
				<div class="col-md-4 col-md-offset-4">
					${mensagem}
		      		<div class="panel">
		      			<div class="panel-heading center-block">
		            		<div id="loginImage" class="login-image"></div>
		        		</div>
		        		<div class="panel-body">
				        	<form id="login-form" class="form-group" method="post" action="entrar">
				            	<input name="nome" type="text" class="form-control" placeholder="Login ou E-mail" required autofocus>
				                <input name="senha" type="password" class="form-control" placeholder="Senha" required>
				                <button id="btn-entrar" class="btn-block btn-white form-control info" type="submit">Entrar</button>
				                <input id="esqueci-flag" name="esqueciFlag" type="hidden" value=false>
				                <a id="esqueci-minha-senha" class="esqueci-minha-senha">Esqueci minha senha</a>
				            </form>
		        		</div>
		      		</div>
		    	</div>
		  	</div>
		</div>
		
<jsp:include page="../commons/footer.jsp"/>