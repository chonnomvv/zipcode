<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.bit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>전체조회</title>
</head>
<body>

	<%
		ArrayList<MemberVO> list = null;
		list = (ArrayList<MemberVO>)request.getAttribute("list");
		
		out.print(list.size());
		for(int i=0; i<list.size(); i++){
			out.print(list.get(i).getId());
		}
			
	
	%>

</body>
</html>