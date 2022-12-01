<%-- <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> --%>
<%-- <jsp:forward page="/dsShippingHistoryList.do"/> --%>
<%-- <jsp:forward page="/login/login.jsp"/> --%>


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
        
        
        /** mybatis function - list */
        function fn_egov_link_traceTest(){
        	
        	document.listForm.action = "<c:url value='/findUnsongList.json'/>";
           	document.listForm.submit();
        }
        /* mybatis function - article */
		function fn_egov_link_traceTest1(){
        	
        	document.articleForm.action = "<c:url value='/findUnsongArticle.json'/>";
           	document.articleForm.submit();
        }
        
        
        function fn_egov_link_codeTest(){
        	document.codeForm.action = "<c:url value='/code/list.json'/>";
           	document.codeForm.submit();
        }
        
        
        function fn_egov_link_character(){
        	document.testForm.action = "<c:url value='/testjson.json'/>";
           	document.testForm.submit();
        }
        
        
       
        
    </script>
</head>

<body style="text-align:center; margin:0 auto;/*  display:inline; */ padding-top:30px;">

	<!-- mybatis -->
	<div style="margin-top: 50px; margin-left: 50px;">
		<p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 20px; color: red;">
	    	운송정보 - 
	    </p>
	    <hr>
	    <p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 16px;">
	    	1. 운송 목록 (내 전화번호로 보낸/받은 목록 조회) 
	    </p>
	    <form:form modelAttribute="fSearchEntity" id="listForm" name="listForm" method="post">
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id = "searchKeyword" name ="searchKeyword" value="01071278534"/> 전화번호 </BR>
	    	</p>
	    	
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id="searchCondition" name="searchCondition" value="1" /> 받는사람 0, 보내는 사람 1    </BR>
	    	</p>
	        
			<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="date" id="searchStartDt" name="searchStartDt" value="2022-11-01" />&nbsp;~&nbsp;
	    		<input type="date" id="searchEndDt" name="searchEndDt" value="2022-11-04" /> 검색 종료 일자    </BR>
	    	</p>
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="전송" onclick="javascript:fn_egov_link_traceTest();" style="height: 30px; width: 50px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>
	    <br><br>
	    <hr>
	    
	     <p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 16px;">
	    	2. 운송장 상세 정보 [운송 정보 + 화물 추적 정보] 
	    </p>
	    
	    <form:form modelAttribute="fSearchEntity" id="articleForm" name="articleForm" method="post">
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id = "searchKeyword" name ="searchKeyword" value="2701201172200"/> 운송장 번호 </BR>
	    	</p>
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="전송" onclick="javascript:fn_egov_link_traceTest1();" style="height: 30px; width: 50px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>
	    
    </div>
    
    <div style="margin-top: 50px; margin-left: 50px;">
		<p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 20px; color: red;">
	    	코드 DATA - 
	    </p>
	    <hr>
	    <p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 16px;">
	    	1. 코드[master, agency, car] 
	    </p>
	    <form:form modelAttribute="fSearchEntity" id="codeForm" name="codeForm" method="post">
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id = "searchKeyword" name ="searchKeyword" value=""/> 검색어 </BR>
	    	</p>
	    	
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<select id="searchCondition" name="searchCondition">
	    			<option></option>
	    			<option value="01">mast Code</option>
	    			<option value="02">agency Code</option>
	    			<option value="03">car Code</option>
	    		</select>
	    		</BR>
	    	</p>
	    	
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id="searchCondition2" name="searchCondition2" value="1" /> 코드 0, 코드명 1    </BR>
	    	</p>
			
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="전송" onclick="javascript:fn_egov_link_codeTest();" style="height: 30px; width: 50px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>
	    <br><br>
	    <hr>
	    
    </div>
      
    <!-- ibatis -->
    <%-- <div style="margin-top: 50px; margin-left: 50px;">
    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 20px;">
	    	ibatis
	    </p>
	    <form:form modelAttribute="fSearchEntity" id="test1Form" name="test1Form" method="post">
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id = "searchKeyword" name ="searchKeyword" value="01071278534"/> 전화번호 </BR>
	    	</p>
	    	
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id="searchCondition" name="searchCondition" value="1" /> 받는사람 0, 보내는 사람 1    </BR>
	    	</p>
	        
			<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="date" id="searchStartDt" name="searchStartDt" value="2022-11-01" />&nbsp;~&nbsp;
	    		<input type="date" id="searchEndDt" name="searchEndDt" value="2022-11-23" /> 검색 종료 일자    </BR>
	    	</p>
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="전송" onclick="javascript:fn_egov_link_traceTest1();" style="height: 30px; width: 50px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>     
	    
	     <form:form  id="testForm" name="testForm" method="get">
	     	<p style="text-align: left; line-height: 30px; margin-bottom: 20px; margin-top: 40px;">
	    		<input type="text" id="" name="" value="20221101" />
	    	</p>
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="전송" onclick="javascript:fn_egov_link_traceTest1();" style="height: 30px; width: 50px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>
	    
    </div> --%>
    
</body>
</html>
