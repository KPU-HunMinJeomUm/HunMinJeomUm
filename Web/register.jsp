<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
<script type="text/javascript">
   function checkForm() {
      
      var regExpId = /^(?=.*[a-zA-Z])(?=.*[0-9]).{3,10}$/;  //3~10 자리의 영문 대소문자+숫자 조합
      var regExpPasswd = /^(?=.*[a-zA-Z])(?=.*[0-9]).{3,10}$/; //3~10 자리의 영문 대소문자+숫자 조합
      var regExpEmail =
             /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
      var regExpPhone = /^\d{3}-\d{3,4}-\d{4}$/;
      
      var form = document.newMember;

      if (!regExpId.test(document.newMember.id.value))
      {
    	  alert("아이디를 3~10 자리의 영문 대소문자+숫자 조합으로 입력해 주세요!");
          return false;
      }

      if (!regExpPasswd.test(document.newMember.passwd.value))
      {
         alert("비밀번호를 3~10 자리의 영문 대소문자+숫자 조합으로 입력해 주세요!");
         return false;
      }
      
      if(document.newMember.passwd.value != document.newMember.passwdCheck.value )
      {
            alert("비밀번호를 동일하게 입력해 주세요!");
            return false;
      }

      if (!document.newMember.name.value) {
         alert("이름을 입력해 주세요!");
         return false;
      }
      if(!document.newMebmer.email.value){
    	  alret("이메일을 입력해주세요!");
    	  return false;
      }
   }
   function winOpen(){
	   if(document.newMember.id.value =="" || document.newMember.id.value.length < 0){
			alert("아이디를 먼저 입력해주세요")
			document.newMember.id.focus();
		}else{
			window.open("joinIdCheck.jsp?id="+document.newMember.id.value,"","width=500, height=300");
		}
   }
   
</script>
  <title>훈민점음</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
  <link rel="stylesheet" href="css/style.css">
  
</head>
<body>

	<style>
		h2{
			font-family: "Nanum Gothic", sans-serif;
		}
	</style>
  <!-- Navigation -->
  <%
   String sessionId = (String) session.getAttribute("sessionId");
   %>
  
   <nav style="background-color:#545b62!important" class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="./home.jsp"><img src="images/logo.png" width="150px" heigt="80px"></a><button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
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

<!-- 회원가입 -->
	        <div  style="padding-top:25px" align="center"  class="container ">
            <div class="page-header">
                <div align="left" class="col-md-6 col-md-offset-3">
                <h2>회원가입</h2><hr>
                </div>
            </div>
            <div style="margin-top:25px" class="col-sm-6 col-md-offset-3">
                <form name= newMember action="MemberServlet?cmd=join" method="post" onsubmit="return checkForm()">
                    <div align="left" class="form-group">
                        <label for="InputID">아이디</label>
                        <input type="text" class="form-control" name="id" placeholder="아이디를 입력해주세요">
                        <a class="btn btn-dark float-right" onclick="winOpen()">중복확인</a>
                    </div>
                    <div align="left" class="form-group">
                        <label for="inputName">성명</label>
                        <input type="text" class="form-control" name="name" placeholder="이름을 입력해 주세요">
                        
                    </div>

                    <div align="left" class="form-group">
                        <label for="inputPassword">비밀번호</label>
                        <input type="password" class="form-control" name="passwd" placeholder="비밀번호를 입력해주세요">
                    </div>
                    <div align="left" class="form-group">
                        <label for="inputPasswordCheck">비밀번호 확인</label>
                        <input type="password" class="form-control" name="passwdCheck" placeholder="비밀번호 확인을 위해 다시한번 입력 해 주세요">
                    </div>
                    <div align="left" class="form-group">
                        <label for="inputEmail">이메일</label>
                        <input type="tel" class="form-control" name="email" placeholder="이메일을 입력해 주세요">
                    </div>

                    <div class="form-group text-center">
                       <input type="submit" name="submit" value="회원가입">
                    </div>
                </form>
            </div>
  <div class="words" style="display:none"></div>
        </div>
  	<script src="vendor/jquery/jquery.min.js"></script>
  	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  
  
</body>
<script type="text/javascript">
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