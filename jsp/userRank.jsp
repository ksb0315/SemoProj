<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page language="java" import="java.text.*,java.sql.*,java.math.BigDecimal,org.json.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		String serverIP = "localhost";
		String strSID = "orcl";
		String portNum = "1521";
		String user = "university";
		String pass = "comp322";
		String url = "jdbc:oracle:thin:@"+serverIP+":"+portNum+":"+strSID;
		Connection conn = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url,user,pass);
	%>
	<%
		String query = "select U.name, (RS.like_count - RS.dislike_count) Likes from users U, ranking_snapshot RS where U.user_id = RS.user_id order by (RS.like_count - RS.dislike_count) desc";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		JSONArray userRankList = new JSONArray();
		while(rs.next()){
			JSONObject row = new JSONObject();
			row.put("name", rs.getString(1));
			row.put("like", rs.getBigDecimal(2).toString());
			userRankList.put(row);
		}
		rs.close();
		pstmt.close();
		
		response.getWriter().write(userRankList.toString());
	%>
	<%
		conn.close();
	%>
</body>
</html>