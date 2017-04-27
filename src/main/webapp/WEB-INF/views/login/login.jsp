<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../commons/header.jsp"/>

		<div class="container-fluid">
		  <div class="row main-row">
		    <div class="col-md-4 col-md-offset-4">
		      <div class="panel">
		      	<div class="panel-heading center-block">
		            <div id="loginImage" class="login-image"></div>
		        </div>
		        <div class="panel-body">
		        	<form class="form-group" method="post" action="entrar">
		            	<input name="nome" type="text" class="form-control" placeholder="Login ou E-mail" required autofocus>
		                <input name="senha" type="password" class="form-control" placeholder="Senha" required>
		                <button class="btn-block btn-white form-control info" type="submit">Entrar</button>
		            </form>
		        </div>
		      </div>
		    </div>
		  </div>
		</div>
		
<jsp:include page="../commons/footer.jsp"/>