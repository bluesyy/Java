<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js" integrity="sha256-6XMVI0zB8cRzfZjqKcD01PBsAy3FlDASrlC8SxCpInY=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<script>
	// 페이지 로드 이벤트
	$(document).ready(function(){
		
		$('#targetDt').datepicker({
			showOn: 'both',
			dateFormat: 'yymmdd'  // 실제 생성되는 날짜는 yyyymmdd
		});
		
		// 아래 3개 클릭이벤트는 모두 같은 동작을 합니다.
		$('#btnQuery').on('click', function(){
			fnDailyBoxOffice();
		})
		$('#btnQuery').on('click', ()=>{
			fnDailyBoxOffice();
		})
		$('#btnQuery').on('click', fnDailyBoxOffice)
		
	})
	
	// 함수
	function fnDailyBoxOffice(){
		$.ajax({
			url: '${contextPath}/dailyBoxOffice',
			type: 'get',
			data: 'targetDt=' + $('#targetDt').val(),
			dataType: 'json',
			success: function(result){
				$('#boxOffice').empty();
				$.each(result.boxOfficeResult.dailyBoxOfficeList, function(i, movie){
					$('<tr>')
					.append($('<td>').text(movie.rank + '(' + movie.rankInten + ')'))
					.append($('<td>').text(movie.movieNm))
					.append($('<td>').text(movie.openDt))
					.append($('<td>').text(movie.audiCnt + '명'))
					.append($('<td>').text(movie.audiAcc + '명'))
					.append($('<td>').text(movie.rankOldAndNew == 'NEW' ? '신규진입' : ''))
					.appendTo('#boxOffice');
				})
			},
			error: (jqXHR)=>{  // function(jqXHR){
				alert(jqXHR.status);
			}
		})
	}
</script>
</head>
<body>

	<input type="text" name="targetDt" id="targetDt" placeholder="조회날짜">
	<input type="button" value="조회" id="btnQuery">
	
	<hr>
	
	<table border="1">
		<thead>
			<tr>
				<td>순위</td>
				<td>영화명</td>
				<td>개봉일</td>
				<td>일일관객수</td>
				<td>누적관객수</td>
				<td>비고</td>
			</tr>
		</thead>
		<tbody id="boxOffice"></tbody>
	</table>	

</body>
</html>