<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    
<!DOCTYPE html>
<html>

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <script src="http://code.jquery.com/jquery-latest.js"></script> 
  <title>훈민점음</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
  <link rel="stylesheet" href="css/style.css">
  <link rel="stylesheet" href="css/index.css">

</head>

<body>

<style>
h2{
font-family: "Nanum Gothic", sans-serif;
}
#back{
position: absolute;
z-index: 100;
background-color: #000000;
display:none;
left:0;
top:0;
}
#loadingBar{
position:absolute;
left:50%;
top: 40%;
display:none;
z-index:200;
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
          <a href="<c:url value ="/service.jsp"/>" class="list-group-item active">변환</a>
           <c:choose>
               <c:when test="${empty sessionId}">
					<a href="<c:url value ="/loginform.jsp"/>" class="list-group-item">최근 목록 보기</a>
               </c:when>
               <c:otherwise>
              	    <a href="<c:url value ="/recentList.jsp"/>" class="list-group-item">최근 목록 보기</a>
               </c:otherwise>
            </c:choose>
        </div>
      </div>
      <form id="submit_form" action="<c:url value ="/showOcr.jsp"/>" method="post" enctype="multipart/form-data">
      <div class="content-wrap">
            <div class="content">
                <input type="file" id='imageFileOpenInput' name="file" style="border:none">
                <div id="dropZone">
                    <p>파일을 드래그하거나 클릭하세요</p>
                    <img src="images/file_icon.png" alt="">
                </div>
                <button type="submit" id="submit" class="btn  btn-dark  float-right" onclick="FunLoadingBarStart()">변환</button>
            </div>
        </div>
        <script src="js/index.js"></script>  

   </form>
    </div>
  <div class="words" style="display:none"></div>
  </div>

  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src='js/recieve.js'></script>
</body>
<script type="text/javascript"> 
	function FunLoadingBarStart() {
		var backHeight = $(document).height(); //뒷 배경의 상하 폭
		var backWidth = window.document.body.clientWidth; //뒷 배경의 좌우 폭
		var backGroundCover = "<div id='back'></div>"; //뒷 배경을 감쌀 커버
		var loadingBarImage = ''; //가운데 띄워 줄 이미지
		loadingBarImage += "<div id='loadingBar'>";
		loadingBarImage += " <img src='images/loading.gif' class='rotate270'>"; //로딩 바 이미지
		loadingBarImage += "<br>LOADING...";
		loadingBarImage += "</div>";
		$('body').append(backGroundCover).append(loadingBarImage);
		$('#back').css({ 'width': backWidth, 'height': backHeight, 'opacity': '0.3' });
		$('#back').show();
		$('#loadingBar').show();
	}
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
		  let sessionId = "<%=sessionId%>";
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
