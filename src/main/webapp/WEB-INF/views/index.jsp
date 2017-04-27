<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="commons/header.jsp"/>
	<div id="corpo" class="container-fluid nav-padded">
	  <div class="row">
	    <div class="col-md-2"></div>
	    <div class="col-md-8">
	    	<div class="row text-center">
	    		${mensagem}
	    		<hr>
	        </div>
	    </div>
	    <div class="col-md-2"></div>
	  </div>
	  <div class="row">
	  	<div class="col-md-2"></div>
	  	<div id="mapcontainer2" class="col-md-8">
	  		<div id="map2" class="container"></div>
	  	</div>
	  	<div class="col-md-2"></div>
	  </div>
	</div>
<jsp:include page="commons/footer.jsp"/>