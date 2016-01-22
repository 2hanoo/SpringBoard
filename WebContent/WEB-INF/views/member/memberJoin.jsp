<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<style type="text/css">
.label {
	width: 150px;
	color: orange;
}

.inp {
	width: 200px;
}
</style>
</head>
<body>
	<fieldset>
		<form:form action="insertMember.do" modelAttribute="member">
			<legend>회원 가입</legend>
			<form:errors element="div" cssClass="fontUp colorRed" />
			<table border="1" align="center">
				<tr>
					<th class="label">아이디</th>
					<td class="inp">
					<form:input path="MEMID" onchange="this.value=chksps(this.value)" /><br> 
					<form:errors cssClass="colorRed" path="MEMID" /></td>
				</tr>
				<tr>
					<th class="label">이름</th>
					<td class="inp">
					<form:input path="MEMNAME" onchange="this.value=chksps(this.value)" /><br> 
					<form:errors cssClass="colorRed" path="MEMNAME" />></td>
				</tr>
				<tr>
					<th class="label">비밀번호</th>
					<td class="inp"><input type="password" name="mempw"
						onchange="this.value=chksps(this.value)"><br> <form:errors
							cssClass="colorRed" path="mempw" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<input type="submit" value="가입하기"> 
					<input type="button" value="목록으로"
						onclick="javascript:location.href='../board/openListBoard.do"></td>
				</tr>
			</table>
		</form:form>
	</fieldset>
	<%@ include file="/WEB-INF/include/include-body.jspf"%>
	<script type="text/javascript">
function chksps(val){
	return val.replace(/(\s*)/g,'');
}
</script>
</body>
</html>