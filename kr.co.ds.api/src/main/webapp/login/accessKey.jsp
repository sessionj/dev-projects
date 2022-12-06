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
        
        function fn_egov_link_traceTest(){
        	document.keyForm.action = "<c:url value='/login/access-key.json'/>";
           	document.keyForm.submit();
        }
        //-->
    </script>
</head>

<body style="text-align:center; margin:0 auto;/*  display:inline; */ padding-top:30px;">

	<!-- mybatis -->
	<div style="margin-top: 50px; margin-left: 50px;">
		<p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 20px; color: red;">
	    	# GET Access Key  - 
	    </p>
	    <hr>
	    <p style="text-align: left; line-height: 30px; margin-bottom: 20px; font-weight: bold; font-size: 16px;">
	    	1. 인증정보 [전화번호 필요] 를 입력하여 인증 정보를 가져온다.
	    </p>
	    <form:form modelAttribute="fSearchEntity" id="keyForm" name="keyForm" method="get">
	    	<p style="text-align: left; line-height: 30px; margin-bottom: 20px;">
	    		<input type="text" id = "searchKeyword" name ="searchKeyword" value="01045627914"/> 전화번호 </BR>
	    	</p>
	    	<p style="text-align: left; line-height: 30px;">
	    		<input type="button" value="인증번호 받기" onclick="javascript:fn_egov_link_traceTest();" style="height: 30px; width: 130px; padding-left: 10px; padding-right: 10px;"/>
	    	</p>
	    </form:form>
	    <br><br>
	    <hr>
	    
    </div>
    
    
    
</body>
</html>
