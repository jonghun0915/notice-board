<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<script type="text/javascript">
    function validateForm(form) {  // 필수 항목 입력 확인
        if (form.nasjh.value == "") {
            alert("작성자를 입력하세요.");
            form.nasjh.focus();
            return false;
        }
        if (form.tisjh.value == "") {
            alert("제목을 입력하세요.");
            form.tisjh.focus();
            return false;
        }
        if (form.consjh.value == "") {
            alert("내용을 입력하세요.");
            form.consjh.focus();
            return false;
        }
        if (form.pwsjh.value == "") {
            alert("비밀번호를 입력하세요.");
            form.pwsjh.focus();
            return false;
        }
    }
</script>
</head>
<h2>파일 첨부형 게시판 - 글쓰기(Write)</h2>
<form name="writeFrm" method="post" enctype="multipart/form-data"
      action="write.do" onsubmit="return validateForm(this);">
<table border="1" width="90%">
    <tr>
        <td>작성자</td>
        <td>
            <input type="text" name="nasjh" style="width:150px;" />
        </td>
    </tr>
    <tr>
        <td>제목</td>
        <td>
            <input type="text" name="tisjh" style="width:90%;" />
        </td>
    </tr>
    <tr>
        <td>내용</td>
        <td>
            <textarea name="consjh" style="width:90%;height:100px;"></textarea>
        </td>
    </tr>
    <tr>
        <td>첨부 파일</td>
        <td>
            <input type="file" name="ofsjh" />
        </td>
    </tr>
    <tr>
        <td>비밀번호</td>
        <td>
            <input type="password" name="pwsjh" style="width:100px;" />
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button type="submit">작성 완료</button>
            <button type="reset">RESET</button>
            <button type="button" onclick="location.href='list.do';">
                목록 바로가기
            </button>
        </td>
    </tr>
</table>    
</form>
</body>
</html>