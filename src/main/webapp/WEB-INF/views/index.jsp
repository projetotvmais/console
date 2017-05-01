<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="commons/header.jsp"/>

		<button id="navbar-toggler" type="button" class="navbar-toggle">
            <span class="sr-only">Alternar Navegação</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
		
		<nav id="menu" class="menu-lateral">
            <ul class="menu">
                <li>
                    <div id="canais" class="menu-item">
                        <p>Canais</p>
                    </div>
                </li>
                <li class="divider"></li>
                <li>
					<div id="pacotes" class="menu-item">
						<p>Pacotes</p>
					</div>
				</li>
                <li class="divider"></li>
                <li>
					<div id="clientes" class="menu-item">
						<p>Clientes</p>
					</div>
				</li>
                <li class="divider"></li>
                <li>
					<div id="faturas" class="menu-item">
						<p>Faturas</p>
					</div>
				</li>
            </ul>
        </nav>
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="row text-center">
					${mensagem}
				</div>
				<div class="row">

				</div>
			</div>
		</div>
	
<jsp:include page="commons/footer.jsp"/>