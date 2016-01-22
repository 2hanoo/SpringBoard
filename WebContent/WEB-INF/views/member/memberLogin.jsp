<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body>
	<form action="userLogin.action" method="post"
		onsubmit="return chkjoin();">
		아이디 : <input type="text" id="id" name="id" size="20"> 패스워드 : <input
			type="password" id="pw" name="pw" size="20"> <input
			type="submit" value="로그인"> <input type="button" value="회원가입"
			id="joinForm"><br>
		<div align="center">
			<span style="color: red; font-size: 12pt;" id="msg"><s:property	value="msg" /></span>
		</div>
	</form>
	<%@ include file="/WEB-INF/include/include-body.jspf"%>
	<script type="text/javascript">
		function chkjoin() {
			var msg = document.getElementById('msg');
			var id = document.getElementById('id');
			var pw = document.getElementById('pw');

			if (id.value.trim() == '') {
				msg.innerHTML = '아이디를 입력해 주세요.';
				id.value = '';
				id.focus();
				return false;
			}
			if (pw.value.trim() == '') {
				msg.innerHTML = '비밀번호를 입력해 주세요.';
				pw.value = '';
				pw.focus();
				return false;
			}
		}
		$(function() {
			$("#joinForm").click(
					function() {
						window.open('joinForm.action', 'window',
								'width=410,height=235');
					});
		});
	</script>
</body>
</html>