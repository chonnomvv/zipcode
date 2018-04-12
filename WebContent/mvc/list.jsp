<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.bit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>

<script type="text/javascript">
	function send_remove(id){
		var frm = document.getElementById("frm");
		frm.action = "command?cmd=remove&id="+id;
		frm.onsubmit();
	}
</script>

</head>
<body>
	<%
	ArrayList<MemberVO> list = (ArrayList<MemberVO>)request.getAttribute("list");
	StringBuffer sb = new StringBuffer();
	//MemberVO vo = null;
	System.out.println(list.size());
	for(MemberVO vo : list){
		sb.append(vo.getId());
		sb.append(" <a href='command?cmd=updateReady&id="+vo.getId()+"'>"+vo.getName()+"</a>");
		sb.append(" "+vo.getAddr1()+"<a href='command?cmd=delete&id="+vo.getId()+"'>" + "삭제" +vo.getName()+"</a>"+"<br>");
		
	}
	out.println(sb.toString());
	
	//out.print();
	
	%>
		
</body>
</html>