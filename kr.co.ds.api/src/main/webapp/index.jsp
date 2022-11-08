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

	<!-- mybatis -->

	<div style="margin-top: 50px; margin-left: 50px;">
	    <form:form modelAttribute="fSearchEntity" id="listForm" name="listForm" method="post">
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id = "searchKeyword" name ="searchKeyword" value="01071278534"/> 전화번호 </BR>
	    	</p>
	    	
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id="searchCondition" name="searchCondition" value="1" /> 받는사람 0, 보내는 사람 1    </BR>
	    	</p>
	        
			<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id="searchStartDt" name="searchStartDt" value="20221101" />&nbsp;~&nbsp;
	    		<input type="text" id="searchEndDt" name="searchEndDt" value="20221104" /> 검색 종료 일자    </BR>
	    	</p>
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="전송" onclick="javascript:fn_egov_link_traceTest();" style="height: 30px; width: 50px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>
	    
	     <form:form  id="testForm" name="testForm" method="get">
	     	<p style="text-align: left; line-height: 30px; margin-bottom: 20px; margin-top: 40px;">
	    		<input type="text" id="" name="" value="20221101" />
	    	</p>
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="전송" onclick="javascript:fn_egov_link_character();" style="height: 30px; width: 50px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>
	    
    </div>
    
    <!-- ibatis -->
    
    <div style="margin-top: 50px; margin-left: 50px;">
	    <form:form modelAttribute="fSearchEntity" id="test1Form" name="test1Form" method="post">
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id = "searchKeyword" name ="searchKeyword" value="01071278534"/> 전화번호 </BR>
	    	</p>
	    	
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id="searchCondition" name="searchCondition" value="1" /> 받는사람 0, 보내는 사람 1    </BR>
	    	</p>
	        
			<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id="searchStartDt" name="searchStartDt" value="20221101" />&nbsp;~&nbsp;
	    		<input type="text" id="searchEndDt" name="searchEndDt" value="20221104" /> 검색 종료 일자    </BR>
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
	    
    </div>
    
</body>
</html>
