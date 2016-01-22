<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body>
	<fieldset>
		<legend>글 상세보기</legend>
		<table class="board_view">
			<colgroup>
				<col width="15%" />
				<col width="35%" />
				<col width="15%" />
				<col width="35%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">글 번호</th>
					<td>${map.SEQ }</td>
					<th scope="row">조회수</th>
					<td>${map.HIT }</td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>${map.NAME }</td>
					<th scope="row">작성시간</th>
					<td>${map.REGDATE }</td>
				</tr>
				<tr>
					<th scope="row">제목</th>
					<td colspan="3">${map.TITLE }</td>
				</tr>
				<tr>
					<td colspan="4" style="height: 400px;">${map.CONTENT }</td>
				</tr>
				<tr>
					<th scope="row">첨부파일</th>
					<td colspan="3"><c:forEach var="row" items="${list }">
							<p>
								<input type="hidden" id="FILE_SEQ" value="${row.FILE_SEQ}"> 
								<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a>
								(${row.FILE_SIZE }kb)
							</p>
						</c:forEach></td>
				</tr>
			</tbody>
		</table>
		<br>
		<div align="right">
			<a href="#this" class="btn" id="reply">답글쓰기</a> 
			<a href="#this" class="btn" id="update">수정하기</a> 
			<a href="#this" class="btn" id="delete">삭제하기</a>
			<a href="#this" class="btn" id="list">목록으로</a> 
		</div>
		<div id="boardPwdBut">
			<div id="passChk">
				<form name="f_pass" id="f_pass">
					<input type="hidden" name="SEQ" id="SEQ" value="${map.SEQ}" /> 
					<label for="b_pass" id="l_pass">비밀번호 : </label> 
					<input type="password" name="PASS" id="b_pass" /> 
					<input type="button" id="passBut" value="확인" /> 
					<span id="msg"></span>
				</form>
			</div>
		</div>
	</fieldset>


	<%@ include file="/WEB-INF/include/include-body.jspf"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#list").on("click", function(e) { //목록으로 버튼
				e.preventDefault();
				fn_openBoardList();
			});
			
			$("#reply").on("click", function(e) { //답글쓰기 버튼
				e.preventDefault();
				fn_openBoardReply();
			});

			$("#passChk").hide();
			
			$("#passBut").click(function() {
				passConfirm();
			});
			
			$("#update").on("click", function(e) { //수정하기 버튼
				e.preventDefault();
				fn_openBoardUpdate();
			});

			$("a[name='file']").on("click", function(e) { //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});

			$("#delete").on("click", function(e) { //삭제하기 버튼
				e.preventDefault();
				fn_deleteBoard();
			});
		});
		//목록으로 
		function fn_openBoardList() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
			comSubmit.submit();
		}
		
		//답글쓰기
		function fn_openBoardReply() {
			var seq = "${map.SEQ}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/openBoardReply.do' />");
			comSubmit.addParam("SEQ",$("#SEQ").val());
			comSubmit.submit();
		}

		var butChk = 0; //수정 버튼과 삭제버튼을 구별하기 위한 변수
		//파일 수정하기 펑션
		function fn_openBoardUpdate() {
			$("#passChk").show();
			$("#msg").text("작성시 입력한 비밀번호를 입력해주세요.").css("color", "#000099");
			butChk = 1;
		}

		//파일 삭제하기 펑션
		function fn_deleteBoard() {
			$("#passChk").show();
			$("#msg").text("작성시 입력한 비밀번호를 입력해주세요.").css("color", "#000099");
			butChk = 2;
		}
		//패스워드 체크 펑션
		function passConfirm() {
			var comSubmit = new ComSubmit();
			$.ajax({
				url : "passCheck.do", //전송 url
				type : "POST", //전송시 method 방식
				data : $("#f_pass").serialize(),//폼 전체 데이터 전송
				dataType : "text",
				error : function() { //실행시 오류가 발생하였을 경우
					alert('시스템 오류 입니다. 관리자에게 문의 하세요');
				},
				success : function(resultData) { //정상적으로 실행 되었을 경우
					if (resultData == 0) {//일치하지 않는경우
						$("#msg").text("작성시 입력한 비밀번호가 일치하지 않습니다.").css(
								"color", "red");
						$("#passwd").select();
					} else if (resultData == 1) {//일치할 경우
						$("#msg").text("");
						if (butChk == 1) {
							comSubmit
									.setUrl("<c:url value='/board/openBoardUpdate.do' />");
						} else if (butChk == 2) {
							comSubmit
									.setUrl("<c:url value='/board/deleteBoard.do' />");
						}
						comSubmit.addParam("SEQ", $("#SEQ").val());
						comSubmit.submit();
					}
				}
			}); /* ajax 함수 종료 */
		}
		//파일 다운로드 펑션
		function fn_downloadFile(obj) {
			var seq = obj.parent().find("#FILE_SEQ").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			if (gfn_isNull($("[name='FILE_SEQ']").val()) == false) {
				$("[name='FILE_SEQ']").remove();
			}
			comSubmit.addParam("FILE_SEQ", seq);
			comSubmit.submit();
		}
	</script>
</body>
</html>
