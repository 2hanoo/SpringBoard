<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body>
	<form id="frm" name="frm" enctype="multipart/form-data">
		<input type="hidden" name="GROUPS" value="${map.GROUPS }">
		<!-- enctype 을 멀티파트형식으로 선언해야한다 -->
		<fieldset>
			<legend>답글쓰기</legend>
			<table class="board_view">
				<colgroup>
					<col width="15%">
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">제목</th>
						<td colspan="3"><input type="text" id="TITLE" name="TITLE"
							class="wdp_90" value="[RE]${map.TITLE }"></input></td>
					</tr>
					<tr>
						<th scope="row">이름</th>
						<td><input type="text" id="NAME" name="NAME" class="wdp_90"></input></td>
						<th scope="row">패스워드</th>
						<td><input type="password" id="PASS" name="PASS"
							class="wdp_90"></input></td>
					</tr>
					<tr>
						<td colspan="4" class="view_text"><textarea rows="20"
								cols="100" title="내용" id="CONTENT" name="CONTENT"></textarea></td>
					</tr>
				</tbody>
			</table>
			<div id="fileDiv" align="center">
				<p>
					<input type="file" id="file" name="file_0"> <a href="#this"
						class="btn" id="delete" name="delete"> 삭제 </a>
				</p>
			</div>
			<div align="right">
				<a href="#this" class="btn" id="addFile">파일 추가</a> <a href="#this"
					class="btn" id="write">작성하기</a> <a href="#this" class="btn"
					id="list">목록으로</a>
			</div>
		</fieldset>
	</form>
	<%@ include file="/WEB-INF/include/include-body.jspf"%>
	<script type="text/javascript">
		var gfv_count = 1;

		$(document).ready(function() {
			//목록으로 버튼을 눌렀을때
			$("#list").on("click", function(e) {
				e.preventDefault(); //event.preventDefault() 현재의 기본 이벤트를 중단한다
				fn_openBoardList();
			});
			//작성하기 버튼을 눌렀을때
			$("#write").on("click", function(e) { //작성하기 버튼
				e.preventDefault();
				fn_insertBoard();
			});

			$("#addFile").on("click", function(e) { //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});

			$("a[name='delete']").on("click", function(e) { //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});

		});
		//목록으로 버튼 기능
		function fn_openBoardList() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
			comSubmit.submit();
		}
		//작성하기 버튼 기능
		function fn_insertBoard() {
			var comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/board/insertReply.do' />");
			comSubmit.submit();
		}

		function fn_addFile() {
			var str = "<p><input type='file' name='file_"
					+ (gfv_count++)
					+ "'> <a href='#this' class='btn' name='delete'> 삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e) { //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}

		function fn_deleteFile(obj) {
			obj.parent().remove();
		}
	</script>
</body>
</html>
