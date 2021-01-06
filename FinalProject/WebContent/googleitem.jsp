<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MovieTime</title>
</head>
<body>
	<%
	String[][] orderList = (String[][]) request.getAttribute("query");
	for (int i = 0; i < orderList.length; i++) {
		if (orderList[i][0] == null || orderList[i][1] == null || orderList[i][2] == null) {
			continue;
		}
	%>
	<a href='http://google.com/url?q=<%=orderList[i][1]%>'><%=orderList[i][0]%></a>
	<br>
	<span style="font-size: 9pt;"><%=orderList[i][1]%></span>
	<br>
	<br>
	<%
}
%>
</body>
</html>