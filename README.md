# 훈민점음(HunMinJeomUm)
## 한국산업기술대학교 21학년도 졸업작품

### 팀원
+ 김채리(2018152011)
+ 김지안(2018152010)
+ 김용민(2018150007)
+ 이예지(2018150032) 
---------------------------------
### 훈민점음의 개발 목표
#### 시각장애인들이 전자책으로 보급되지 않은 책 또는 사진을 글과 음성, 그리고 점자로 학습할 수 있도록 하는 서비스
+ 앱 및 웹에서 원하는 문서 또는 사진을 선택해 **텍스트를 추출**한다.
+ 앱 및 웹에서 추출한 텍스트를 **음성으로 변환**한다.
+ 추출한 텍스트를 **점자로 변환**한다.
+ 앱 및 웹에서 **회원가입 기능**을 구현한다.
+ 앱 및 웹에서 **커뮤니티를 위한 게시판 기능**을 제공한다.
+ 시각장애인들이 손쉽게 사용할 수 있도록 **장애인 접근성을 고려**하여 앱 및 웹을 개발하도록 한다.


---------------------------------
### 전체 시스템 구성도
<img src="https://user-images.githubusercontent.com/74261590/122761392-25dd6500-d2d7-11eb-9add-4a93fa4ac53c.jpg">

---------------------------------
### 훈민점의 구성 및 개발자
|구성|기능|개발자|
|------|---|---|
|앱|OCR 결과 TTS, 디바이스와 블루투스 연동|김채리|
|웹|OCR 결과 TTS, 커뮤니티 관리|이예지|
|서버|OCR 동작|김용민|
|하드웨어|점자 출력|김지안|

---------------------------------
### 소프트웨어 개발 환경
<table>
  <tr>
    <th>구분</th>
    <th>항목</th>
    <th>적용 내역</th>
    <th>비고</th>
  </tr>
 <tr>
    <td>OS</td>
    <td>Linux</td>
    <td>웹을 동작시키기 위한 가상환경 OS</td>
    <td>Ubuntu 18.0.4.LTS</td>
  </tr>
  <tr>
    <td  rowspan=2>서버</td>
    <td>AWS EC2</td>
    <td>웹을 동작시키기 위한 가상환경 OS</td>
    <td>EC2 AMI, Ubuntu Server 18.04 LTS.SSD Volume Type</td>
  </tr>
  <tr>
    <td>PhpMyAdmin</td>
    <td>PHP서버, Apache, Mysql을 함께 사용한 앱 개발 서버</td>
    <td>5.1.0</td>
  </tr>
  <tr>
    <td  rowspan=4>개발도구</td>
    <td> Android Studio</td>
    <td>앱 개발 IDE</td>
    <td>3.6.2.Android 6.0</td>
  </tr>
  <tr>
    <td>MySQL</td>
    <td>DB SQL IDE</td>
    <td>8.0 GPL 라이선스</td>
  </tr>
  <tr>
    <td>VS Code</td>
    <td>Python IDE</td>
    <td>1.51.0 MIT 라이선스</td>
  </tr>
  <tr>
    <td>eclipse</td>
    <td>웹 개발 IDE</td>
    <td>http, jsp, java 2.0 라이선스 4.12.0</td>
  </tr>
  <tr>
    <td  rowspan=3>개발언어</td>
    <td>Python</td>
    <td>Google Vision API를 이용한 OCR 기능과 전처리 기능 및 음성 기능 구현</td>
    <td>-</td>
  </tr>
  <tr>
    <td>SQL</td>
    <td>DB 언어</td>
    <td>-</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>웹과 앱 구현</td>
    <td>-</td>
  </tr>
    <tr>
    <td  rowspan=3>패키지</td>
    <td>Google vision API</td>
    <td>OCR 기능을 Google Vision API를 사용하여 텍스트 추출</td>
    <td>-</td>
  </tr>
  <tr>
    <td>OpenCV</td>
    <td>OpenCV를 이용하여 이진화 및 잡티제거 알고리즘으로 전처리</td>
    <td>-</td>
  </tr>
  <tr>
    <td>Google Speech API</td>
    <td>Google Speech API를 이용하여 TTS 구현</td>
    <td>-</td>
  </tr>
  <tr>
    <td>프레임워크</td>
    <td>Bootstrap</td>
    <td>웹 디자인 구현</td>
    <td>MIT 라이선스</td>
  </tr>
</table>

---------------------------------
### 하드웨어 개발 환경
<table>
  <tr>
    <th>구분</th>
    <th>항목</th>
    <th>적용 내역</th>
    <th>비고</th>
  </tr>
  <tr>
    <td>OS</td>
    <td>Ubuntu Linux</td>
    <td>라즈베리파이 OS</td>
    <td>-</td>
  </tr>
  <tr>
    <td  rowspan=3>디바이스</td>
    <td>Arduino UNO</td>
    <td>아두이노 점자 모듈 제어</td>
    <td>Arduino Uno R3, 5V</td>
  </tr>
  <tr>
    <td>Raspberry Pi</td>
    <td>아두이노 제어 및 수신량 증가</td>
    <td>5V 3A</td>
  </tr>
  <tr>
    <td>아두이노 점자 표시 모듈</td>
    <td>점자를 표현하기 위한 것으로 3개당 한 글자 표현</td>
    <td>5V, 6A(총 18A)</td>
  </tr>
  <tr>
    <td>SMPS</td>
    <td>전원 공급 장치</td>
    <td>-</td>
    <td>출력 전압 5V, 출력 전류 18A</td>
  </tr>
  <tr>
    <td>개발도구</td>
    <td>ARDUINO IDE</td>
    <td>아두이노 개발 툴</td>
    <td>1.8.1 GPL 라이선스</td>
  </tr>
  <tr>
    <td>개발언어</td>
    <td>C</td>
    <td>아두이노 구현</td>
    <td>-</td>
  </tr>
  <tr>
    <td>Python</td>
    <td>라즈베리파이 구현</td>
    <td>-</td>
    <td>-</td>
  </tr>
    <tr>
    <td>클래스</td>
    <td>Braille</td>
    <td>점자 모듈 클래스</td>
    <td>-</td>
  </tr>
</table>

---------------------------------
### 데모
+ [웹사이트 링크](http://leeyj1116.cafe24.com/)
