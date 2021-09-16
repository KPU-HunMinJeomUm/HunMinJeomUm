<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="domain.*, java.util.List, persistence.*,java.util.Collections"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>훈민점음</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/the-big-picture.css" rel="stylesheet">
  <link rel="stylesheet" href="css/style.css">
  <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

</head>
<body>
<style>
h2{
font-family: "Nanum Gothic", sans-serif;
}
td{
font-weight:"bold";
}
pre{
font-size : "20";
overflow:visible !important;
}

	</style>

  <!-- Navigation -->
  <%
   String sessionId = (String) session.getAttribute("sessionId");
   %>
  
  <nav style="background-color:#545b62!important" class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="./home.jsp"><img src="images/logo.png" width="150px" height="80px"></a><button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
           <c:choose>
               <c:when test="${empty sessionId}">
               	<li class="nav-item">
            		<a style="color:white" class="nav-link" href="<c:url value="/loginform.jsp"/>"> 로그인</a>
         		</li>
               </c:when>
               <c:when test="${sessionId eq 'admin'}">
				<li class="nav-item">
            		<a style="color:white" class="nav-link" href="<c:url value="/adminPage.jsp"/>">회원관리</a>
         		</li>
         		<li class="nav-item">
            		<a style="color:white" class="nav-link" href="MemberServlet?cmd=logout"target="_self">로그아웃</a>
         		</li>
               </c:when>
               <c:otherwise>
                 <li class="nav-item">
            		<a style="color:white" class="nav-link" href="MemberServlet?cmd=logout"target="_self">로그아웃</a>
         		</li>
         		 <li class="nav-item">
            		<a style="color:white" class="nav-link" href="<c:url value ="/memberUpdate.jsp"/>">회원수정</a>
         		</li>
               </c:otherwise>
            </c:choose>
          <li class="nav-item">
            <a style="color:white" class="nav-link" href="<c:url value ="/introduction.jsp"/>">프로젝트 소개</a>
          </li>
          <li class="nav-item">
            <a style="color:white" class="nav-link" href="<c:url value ="/service.jsp"/>">서비스</a>
          </li>
          <li class="nav-item">
            <a style="color:white" class="nav-link" href="<c:url value ="/freeBoard.jsp"/>">커뮤니티</a>
          </li>
          <li class="nav-item">
            <a style="color:white" class="nav-link" onclick="start()">음성 인식</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  
  <!-- 내용 -->
   <div class="container">

    <div class="row">

      <div class="col-lg-3 fixed">
        <h2 class="my-4">서비스</h2>
        <div class="list-group">
          <a href="<c:url value ="/service.jsp"/>" class="list-group-item">변환</a>
           <c:choose>
               <c:when test="${empty sessionId}">
					<a href="<c:url value ="/loginform.jsp"/>" class="list-group-item active">최근 목록 보기</a>
               </c:when>
               <c:otherwise>
              	    <a href="<c:url value ="/recentList.jsp"/>" class="list-group-item active">최근 목록 보기</a>
               </c:otherwise>
            </c:choose>
        </div>
      </div>
      <div class="col-lg-9" style="margin-top:30px">
      		    <%
    	OcrVO vo = (OcrVO)request.getAttribute("ocrVO");
      	String content = vo.getContent();
  	 %>
     <div class="col-lg-9">
     	<h2 class="text-black">저장된 변환 내용</h2>
     	<br>
     	<div id="code_reddit" style="overflow:auto; height:450px; width:1000px">
		<pre style="font-size:20"><%=vo.getContent()%></pre>
        <br>
        </div>
     	<div style="position:relative; top:30px">
		<button type="submit" class="btn btn-dark btn-lg float-right mr-1" onclick="zoomOut()">확대</button>
       	<button type="submit" class="btn btn-dark btn-lg float-right mr-1" onclick="zoomReset()">기본</button>
        <button type="submit" class="btn btn-dark btn-lg float-right mr-1" onclick="zoomIn()">축소</button>  
		<button type="submit" class="btn btn-dark btn-lg float-right mr-1" onclick="finish()">멈춤</button>
				<button type="submit" class="btn btn-dark btn-lg float-right mr-1" onclick="stop()">일시정지</button>
     			<button type="submit" class="btn btn-dark btn-lg float-right mr-1" onclick="g_gout()">재생</button>
     	<div>
     	  <div class="words" style="display:none"></div>
</div>
</div>
</div>
</div>
	  </div>
    
  </div>
  
   <!-- Post-->

    

  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

<script type="text/javascript">

	var voices = [];
	function setVoiceList() {
		voices = window.speechSynthesis.getVoices();
	}
	setVoiceList();
	if (window.speechSynthesis.onvoiceschanged !== undefined) {
		window.speechSynthesis.onvoiceschanged = setVoiceList;
	}
	function speech(txt) {
		if(!window.speechSynthesis) {
		alert("음성 재생을 지원하지 않는 브라우저입니다. 크롬, 파이어폭스 등의 최신 브라우저를 이용하세요");
		return;
	}
	var lang = 'ko-KR';
	var utterThis = new SpeechSynthesisUtterance(txt);
	utterThis.onend = function (event) {
	console.log('end');
	};
	utterThis.onerror = function(event) {
		console.log('error', event);
	};
	var voiceFound = false;
	for(var i = 0; i < voices.length ; i++) {
		if(voices[i].lang.indexOf(lang) >= 0 || voices[i].lang.indexOf(lang.replace('-', '_')) >= 0) {
			utterThis.voice = voices[i];
			voiceFound = true;
		}
	}
	if(!voiceFound) {
		alert('voice not found');
		return;
	}
	utterThis.lang = lang;
	utterThis.pitch = 1;
	utterThis.rate = 1; //속도
	window.speechSynthesis.speak(utterThis);
	}
	function g_gout(){
   	 	var t = document.getElementById("code_reddit");
   	 	speech(t.innerText);
		window.speechSynthesis.resume();
	}
	function finish(){
		window.speechSynthesis.cancel();
	}
	function stop(){
		window.speechSynthesis.pause();
	}
	var code = document.querySelector("pre")
	var nowZoom = 20;
		function zoomIn() {
			nowZoom = nowZoom - 5;
			if(nowZoom <= 5) nowZoom = 5;
			zooms();
		}

		function zoomOut() {
				nowZoom = nowZoom + 5;
				if(nowZoom >= 50) nowZoom = 50;
				zooms();
		}
		
		function zoomReset(){
			nowZoom = 20; 
			zooms();
		}

		function zooms(){
			document.querySelector("pre").style.cssText  = 'font-size :'+nowZoom;
			if(nowZoom==5){
				alert ("5까지 축소 되었습니다. 더 이상 축소할 수 없습니다.");
			}
			if(nowZoom==50){
				alert ("50까지 확대 되었습니다. 더 이상 확대할 수 없습니다.");
			}
		}
		 var checkUnload = true;
		    $(window).on("beforeunload", function(){
		        if(checkUnload) window.speechSynthesis.cancel();
		    });
		    window.SpeechRecognition =
		    	  window.SpeechRecognition || window.webkitSpeechRecognition;

		    	// 인스턴스 생성
		    	const recognition = new SpeechRecognition();

		    	// true면 음절을 연속적으로 인식하나 false면 한 음절만 기록함
		    	recognition.interimResults = false;
		    	// 값이 없으면 HTML의 <html lang="en">을 참고합니다. ko-KR, en-US
		    	recognition.lang = "ko-KR";
		    	// true means continuous, and false means not continuous (single result each time.)
		    	// true면 음성 인식이 안 끝나고 계속 됩니다.
		    	recognition.continuous = false;
		    	// 숫자가 작을수록 발음대로 적고, 크면 문장의 적합도에 따라 알맞은 단어로 대체합니다.
		    	// maxAlternatives가 크면 이상한 단어도 문장에 적합하게 알아서 수정합니다.
		    	recognition.maxAlternatives = 10000;

		    	let p = document.createElement("p");
		    	p.classList.add("para");

		    	let words = document.querySelector(".words");
		    	words.appendChild(p);

		    	let speechToText = "";
		    	recognition.addEventListener("result", (e) => {
		    	  let interimTranscript = "";
		    	  for (let i = e.resultIndex, len = e.results.length; i < len; i++) {
		    	    let transcript = e.results[i][0].transcript;
		    	    if (e.results[i].isFinal) {
		    	      speechToText += transcript;
		    	    } else {
		    	      interimTranscript += transcript;
		    	    }
		    	  }
		    	  var sessionId = "<%=sessionId%>";
		    	  document.querySelector(".para").innerHTML = speechToText + interimTranscript;
		    	  console.log(speechToText);
		    	  console.log(sessionId);
		    	  if(speechToText=="회원가입"||speechToText=="회원 가입")
		    		  location.href = "register.jsp"
		    	  else if(speechToText=="로그인"){
		    		  if(sessionId=="null")
		    			  location.href = "loginform.jsp"
		    		  else{
		    			  alert('로그인된 상태입니다.');
		    			  location.reload();
		    		  }
		    	  }
		    	  else if(speechToText=="로그아웃"){
		    		  if(sessionId=="null"){
		    			  alert('비로그인 상태입니다.');
		    		  	  location.reload();
		    		  }
		    		  else{
		    			  location.href = "MemberServlet?cmd=logout"
		    		  }
		    	  }
		    	  else if(speechToText=="변환")
		    		  location.href = "service.jsp"
		    	  else if(speechToText=="최근번역목록" || speechToText=="최근 번역목록" || speechToText=="최근번역 목록" || speechToText=="최근 번역 목록"){
		    		  if(sessionId=="null"){
		    			  alert('비로그인 상태입니다.');
		    		  	  location.reload();
		    		  }
		    		  else location.href = "recentList.jsp"
		    	  }
		    	  else if(speechToText=="회원정보수정" || speechToText=="회원 정보수정" || speechToText=="회원정보 수정" || speechToText=="회원 정보 수정"){
		    		  if(sessionId=="null"){
		    			  alert('비로그인 상태입니다.');
		    		  	  location.reload();
		    		  }
		    		  else location.href = "memberUpdate.jsp"
		    	  }
		    	  else if(speechToText=="회원탈퇴" || speechToText=="회원 탈퇴"){
		    		  if(sessionId=="null"){
		    			  alert('비로그인 상태입니다.');
		    		  	  location.reload();
		    		  }
		    		  else location.href = "MemberServlet?cmd=delete&id=<%=sessionId%>"
		    	  }
		    	  else if(speechToText=="커뮤니티")
		    		  location.href = "freeBoard.jsp"
		    	  else{
		    		  alert('회원가입, 로그인, 로그아웃, 회원정보수정, 회원탈퇴, 커뮤니티, 변환, 최근번역목록 중에서 말씀해주세요');
		    		  location.reload();
		    	  }
		    	});
		    	// 음성 인식 시작
		    	function start(){
		    	recognition.start();
		    	}
</script> 

</html>
