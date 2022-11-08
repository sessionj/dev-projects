<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Login</title>
<link rel="stylesheet" href="/comm/css/login.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />

</head>
<body>
	<form name="loginForm"
		action="<c:url value='/uat/uia/actionLogin.do'/>" method="post">
		<table border="0" cellpadding="0" cellspacing="0"
			style="width: 250px; margin-left: 20px;">
			<tr>
				<td>
					<table width="250" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="required_text" nowrap>아이디&nbsp;&nbsp;</td>
							<td><input type="text" name="id" tabindex="1" maxlength="10" /></td>
							<td />
						</tr>
						<tr>
							<td class="required_text" nowrap>비밀번호&nbsp;&nbsp;</td>
							<td><input type="password" name="password"
								onKeyDown="javascript:if (event.keyCode == 13) { actionLogin(); }" /></td>
							<td class="title"><input type="checkbox" name="checkId"
								class="check2" onClick="javascript:fnSetCookieId();" />ID저장</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><img
								src="/images/egovframework/cmm/uat/uia/bu2_left.gif" width="8"
								height="20"></td>
							<td background="/images/egovframework/cmm/uat/uia/bu2_bg.gif"
								class="text_left" nowrap><center>
									<a href="javascript:actionLogin();">로그인</a>
								</center></td>
							<td><img
								src="/images/egovframework/cmm/uat/uia/bu2_right.gif" width="8"
								height="20"></td>
						</tr>
					</table>
				</td>
			<tr>
		</table>
	</form>
</body>
</html>