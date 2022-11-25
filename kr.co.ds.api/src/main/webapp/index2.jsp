
<!-- 

우편번호 가이드
https://postcode.map.daum.net/guide

 -->

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovSampleList.jsp
  * @Description : Sample List 화면
  * @Modification Information   
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><spring:message code="title.sample" /></title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>
    <script type="text/javaScript" language="javascript" defer="defer">
        <!--
        /* 글 수정 화면 function */
        function fn_egov_select(id) {
        	document.listForm.selectedId.value = id;
           	document.listForm.action = "<c:url value='/updateSampleView.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 등록 화면 function */
        function fn_egov_addView() {
           	document.listForm.action = "<c:url value='/addSample.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 목록 화면 function */
        function fn_egov_selectList() {
        	document.listForm.action = "<c:url value='/egovSampleList.do'/>";
           	document.listForm.submit();
        }
        
        /* pagination 페이지 링크 function */
        function fn_egov_link_page(pageNo){
        	document.listForm.pageIndex.value = pageNo;
        	document.listForm.action = "<c:url value='/egovSampleList.do'/>";
           	document.listForm.submit();
        }
        
        
        function fn_egov_link_traceTest(){
        	
        	document.listForm.action = "<c:url value='/shippingHistoryList.json'/>";
           	document.listForm.submit();
        }
        
        
        
        function fn_egov_link_character(){
        	document.testForm.action = "<c:url value='/testjson.json'/>";
           	document.testForm.submit();
        }
        
        
		function fn_egov_link_traceTest1(){
        	
        	document.test1Form.action = "<c:url value='/shippingTraceList.json'/>";
           	document.test1Form.submit();
        }
        
        //-->
    </script>
</head>

<body style="text-align:center; margin:0 auto;/*  display:inline; */ padding-top:100px;">
	<p>카카오(다음) 주소찾기</p>
	<div>Company Address</div>
		<div style="margin-top: 50px; margin-left: 50px;">
		<p style="line-height: 30px; margin-bottom: 20px;">
	   		<input id="member_post"  type="text" style="width: 500px;" placeholder="Zip Code" readonly onclick="findAddr()">&nbsp;우편번호
	   	</p>
	   	<p style="line-height: 30px; margin-bottom: 20px;">
	   		<input id="member_addr" type="text" style="width: 500px;" placeholder="Address" readonly>&nbsp;검색주소
	   	</p>
	   	<p style="line-height: 30px; margin-bottom: 20px;">
	   		<input type="text"  style="width: 500px;" placeholder="Detailed Address">
	   	</p>
 		</div>
</body>
<script>
function findAddr(){
	new daum.Postcode({
        oncomplete: function(data) {
        	
        	console.log(data);
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var jibunAddr = data.jibunAddress; // 지번 주소 변수
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('member_post').value = data.zonecode;
            if(roadAddr !== ''){
                document.getElementById("member_addr").value = roadAddr;
            } 
            else if(jibunAddr !== ''){
                document.getElementById("member_addr").value = jibunAddr;
            }
        }
    }).open();
}
</script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</html>
