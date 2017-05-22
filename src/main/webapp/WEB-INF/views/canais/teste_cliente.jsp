<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../commons/header.jsp"/>
	
	<div class="container-fluid cover-100">
		<div id="frame" class="video-frame cover-100">
			<div class="cover-100">
				<script src="https://content.jwplatform.com/libraries/DbXZPMBQ.js"></script>
				<script src="//content.jwplatform.com/libraries/meaTg5dN.js"></script>
				<div id="video_container" class="cover-100"></div>
				<script type="text/javaScript">
					var h = window.screen.availHeight - 110;
					var w = window.screen.availWidth - 20;
					jwplayer("video_container").setup({
						file: "${canal.url}",
						width: w,
						height: h,
						autostart: "true",
						fallback: "true",
						type: "hls",
						abouttext: "TVMais",
						aboutlink: "http://www.tvmais.com.br",
						allowfullscreen: "true",
						abouttext: "::::  TVMais  ::::",
						aboutlink: "http://www.tvmais.com.br"
					});
					jwplayer("video_container").on("play", function(){
						setTimeout(function(){
						var textAlt = document.querySelectorAll("#" + jwplayer().id + " .jw-controlbar-center-group .jw-text-alt")[0];
						if(textAlt) textAlt.innerHTML = "tvmais.com.br";
						},300);
					});
					jwplayer("video_container").on("error", function() {
						$(".jw-title-primary").html(" ");
					});
				</script>
			</div>
		</div>
	</div>
	
<jsp:include page="../commons/footer.jsp"/>