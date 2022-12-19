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
		PreparedStatement pstmt;
		ResultSet rs;
		ResultSetMetaData rsmd;
		int cnt;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url,user,pass);
		String query;
	%>
	<form method="post" action="login.jsp">
		<%
			JSONObject sendObject = new JSONObject();
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			if(id != null){
				query = "select name from users where user_id=? and password=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, password);
				rs = pstmt.executeQuery();
				if(rs.next()){
					sendObject.put("login", "성공");
				}
				else{
					sendObject.put("login", "실패");
				}
				response.getWriter().write(sendObject.toString());
				rs.close();
				pstmt.close();
			}
		%>
		<%
			conn.close();
		%>		
	</form>
</body>
</html>