<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../commons/header.jsp"/>
<jsp:include page="../commons/navbar.jsp"/>

		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-4 col-xs-offset-4 text-center">
					${mensagem}
		    	</div>
		  	</div>
		  	<div class="container-fluid">
				<c:forEach var="canal" items="${canais}">
					<div class="row canal-row">
						<div class="col-md-3">
							<div id="canal${canal.id}" class="panel canal">
								<div class="panel-heading panel-default center-block escondido">
									<p class="nome-do-canal">${canal.nome}</p>
								</div>
								<div class="panel-body">
									<img alt="Imagem do Canal" src="carregarLogoDoCanal?id=${canal.id}" class="imagem-do-canal">
									<p class="descricao-do-canal escondido">${canal.observacoes}</p>
								</div>
							</div>
						</div>
				  	</div>
				</c:forEach>
			</div>
		</div>
		
<jsp:include page="../commons/footer.jsp"/>