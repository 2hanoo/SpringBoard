<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<script type="text/javascript">
	function chksps(val) {

		return val.trim();
	}
</script>
</head>
<body>
	<table style="width:500px; ">
		<tr>
			<td><c:if test="${!empty sid }">
					<div style="float: left; color: blue;">${sname}님환영합니다.</div>
					<div align="right">
						<a href="../member/logout.do">로그아웃</a> <a
							href="../member/delMember.do">회원탈퇴</a>
					</div>
				</c:if> <c:if test="${empty sid }">
					<div align="right">
						<a href="../member/login.do">로그인</a> <a href="../member/openMemberJoin.do">회원가입</a>
					</div>
				</c:if></td>
		</tr>
	</table>
	<table width="500" align="center">
		<tr>
			<td align="right" style="font-family: Gulim; font-size: 12px;">총&nbsp;${count}건
				${currentPage}페이지</td>
		</tr>
	</table>
	<table width="500" border="1" cellpadding="0" cellspacing="0"
		align="center">
		<tr>
			<th style="font-family: Gulim; font-size: 12px;">번호</th>
			<th style="font-family: Gulim; font-size: 12px;">제목</th>
			<th style="font-family: Gulim; font-size: 12px;">글쓴이</th>
			<th style="font-family: Gulim; font-size: 12px;">날짜</th>
			<th style="font-family: Gulim; font-size: 12px;">조회</th>
		</tr>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(list) > 0}">
					<c:forEach var="board" items="${list }">
						<tr>
							<td align="center" width="50" style="font-size: 13px;">${number}<c:set
									var="number" value="${number-1}" /></td>
							<td width="210" style="font-family: Gulim; font-size: 12px;"><a
								href="#this" name="title" style="text-decoration: none;">&nbsp;${board.TITLE}</a>
								<input type="hidden" id="SEQ" value="${board.SEQ}"></td>

							<td align="center" width="70"
								style="font-family: Gulim; font-size: 12px;">${board.NAME}</td>
							<td width="120" style="font-family: Gulim; font-size: 12px;"
								align="center"><fmt:formatDate value="${board.REGDATE}"
									pattern="yyyy/MM/dd HH:mm" /></td>
							<td width="50" align="center"
								style="font-family: Gulim; font-size: 12px;">${board.HIT}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5" align="center"
							style="font-family: Gulim; font-size: 12px;">게시물이 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<form action="openBoardList.do" name="search" method="post">
		<table width="500" align="center">
			<tr>
				<td align="center" width="90%"><select name="keyField">
						<option value="title">제목</option>
						<option value="name">이름</option>
						<option value="content">내용</option>
						<option value="all">전체</option>
				</select> <input type="text" size="16" name="keyWord"
					onchange="this.value=chksps(this.value)"> <input
					type="submit" value="검색"
					style="font-family: Gulim; font-size: 12px;"></td>
				<td align="right"><input type="button" value="글쓰기"
					style="font-family: Gulim; font-size: 12px;" id="write"></td>
			</tr>
		</table>
	</form>
	<table align="center">
		<c:if test="${count>0}">
			<tr>
				<td align="center">${pagingHtml}</td>
			</tr>
		</c:if>
	</table>


	<%@ include file="/WEB-INF/include/include-body.jspf"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#write").on("click", function(e) { //글쓰기 버튼
				e.preventDefault();
				fn_openBoardWrite();
			});
			$("a[name='title']").on("click", function(e) { //제목 
				e.preventDefault();
				fn_openBoardDetail($(this));
			});
		});

		function fn_openBoardWrite() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/openBoardWrite.do' />");
			comSubmit.submit();
		}
		function fn_openBoardDetail(obj) {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/openBoardDetail.do' />");
			comSubmit.addParam("SEQ", obj.parent().find("#SEQ").val());
			comSubmit.submit();
		}
	</script>
</body>
</html>
