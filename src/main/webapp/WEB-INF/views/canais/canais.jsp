<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../commons/header.jsp"/>
<jsp:include page="../commons/navbar.jsp"/>

		<div class="container-fluid">
			<div class="row main-row">
				<div class="col-md-4 col-md-offset-4">
					${mensagem}
		      		<div class="panel">
		      			<div class="panel-heading center-block">
		            		<p class="nome-do-canal">Nome do canal</p>
		        		</div>
		        		<div class="panel-body">
				        	<img alt="Imagem do Canal" src="<c:url value="resources/images/modelos/canais/item-canal.png" />" class="imagem-do-canal">
				        	<p class="descricao-do-canal">Descrição do canal: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis non eros at tellus porttitor pellentesque. Aliquam malesuada ex vel enim tristique dictum. Suspendisse lacinia, dolor sed elementum consequat, neque erat convallis eros, sit amet vestibulum purus lacus a enim. Suspendisse bibendum felis id tellus ornare aliquam. Donec eu ex nisl. Duis augue ante, vestibulum non aliquet eget, feugiat vitae velit. Nulla facilisi. Morbi euismod convallis ex a venenatis. Morbi aliquet faucibus arcu, vitae viverra velit rhoncus in. Vestibulum est felis, pellentesque sit amet turpis dignissim, finibus rhoncus purus. Mauris ac sagittis nulla. Pellentesque porta lacinia ante, sit amet scelerisque tellus dignissim sit amet. Quisque eget placerat dolor.</p>
		        		</div>
		      		</div>
		    	</div>
		  	</div>
		</div>
		
<jsp:include page="../commons/footer.jsp"/>