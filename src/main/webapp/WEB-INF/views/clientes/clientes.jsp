<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../commons/header.jsp"/>
<jsp:include page="../commons/navbar.jsp"/>

		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 text-center">
					${mensagem}
		    	</div>
		  	</div>
			<div class="row">
                <div class="col-xs-8 col-xs-offset-2 text-center">
                    <p id="clientes_nao_cadastrados">Não há clientes cadastrados</p>
					<div class="row lista-clientes">
                        <c:forEach var="cliente" items="${clientes}">
                        	<div id="cliente${cliente.id}" class="lista-clientes-item">
	                            <div class="lista-clientes-item-foto"><img src="mostrarFotoDoCliente?id=${cliente.id}"></div>
	                            <div class="lista-clientes-item-info">
	                                <p class="lista-clientes-item-info-nome">${cliente.nome}</p>
	                                <p class="lista-clientes-item-info-identificacao">${cliente.identificacao}</p>
	                                <p class="lista-clientes-item-info-email">${cliente.email}</p>
	                                <p class="lista-clientes-item-info-telefone">${cliente.telefone}</p>
	                            </div>
	                            <div class="lista-clientes-item-acoes">
	                                <a href="javascript:void(0);" class="lista-clientes-item-acoes-desativar" title="Desativar este cliente" onclick="mudarStatusDeCliente(${cliente.id});">
	                                    <img src="<c:url value="resources/images/clientes/bt-desativar.png" />" 
	                                         onmouseover="this.src = '<c:url value="resources/images/clientes/bt-desativar-hover.png" />'"
	                                         onmouseout="this.src = '<c:url value="resources/images/clientes/bt-desativar.png" />'">
	                                </a>
	                                <br>
	                                <br>
	                                <a href="javascript:void(0);" class="lista-clientes-item-acoes-exibir" title="Exibir informações" onclick="exibirCliente(${cliente.id});">
	                                    <img src="<c:url value="resources/images/clientes/bt-info.png" />" 
	                                         onmouseover="this.src = '<c:url value="resources/images/clientes/bt-info-hover.png" />'"
	                                         onmouseout="this.src = '<c:url value="resources/images/clientes/bt-info.png" />'">
	                                </a>
	                            </div>
	                        </div>
                        </c:forEach>
                    </div>
		    	</div>
			</div>
		</div>

		<div id="dialog_cliente">
			<div class="dialog-cliente-foto"><img src=""></div>
			<a href="javascript:void(0);" onclick="esconderCliente();">&times;</a>
			<div class="dialog-cliente-info">
				<p class="dialog-cliente-info-nome"></p>
				<p class="dialog-cliente-info-identificacao"></p>
				<p class="dialog-cliente-info-email"></p>
				<p class="dialog-cliente-info-telefone"></p>
				<p class="dialog-cliente-info-endereco"></p>
				<textarea class="dialog-cliente-info-observacoes" disabled></textarea>
				<p class="dialog-cliente-info-status"></p>
			</div>
		</div>
		
<jsp:include page="../commons/footer.jsp"/>