<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="commons/header.jsp"/>
<jsp:include page="commons/navbar.jsp"/>

		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="row text-center">
					<div class="col-xs-8 col-xs-offset-2">
						${mensagem}
					</div>
				</div>
				<div id="main-content" class="row">
					
				</div>
			</div>
		</div>
	
<jsp:include page="commons/footer.jsp"/>