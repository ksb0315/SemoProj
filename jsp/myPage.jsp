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
		String user_id = request.getParameter("id");
		if(user_id != null){
			JSONObject mypage = new JSONObject();
			String query = "select name, region, phone_number from users where user_id=?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			JSONObject userInfo = new JSONObject();
			userInfo.put("name", rs.getString(1));
			userInfo.put("region", rs.getString(2));
			userInfo.put("phone_number", rs.getString(3));
			mypage.put("info", userInfo);
			
			rs.close();
			pstmt.close();
			
			JSONObject snapshot = new JSONObject();
			
			JSONObject likeScore = new JSONObject();
			query = "select like_count, dislike_count from ranking_snapshot where user_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			rs.next();
			likeScore.put("like", rs.getString(1));
			likeScore.put("dislike", rs.getString(2));
			snapshot.put("likeScore", likeScore);
			rs.close();
			pstmt.close();
			
			JSONArray rankList = new JSONArray();
			query = "select ranks, thing_id from thingrank where user_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				JSONObject thing = new JSONObject();
				thing.put("rank", rs.getBigDecimal(1).toString());
				
				String query2 = "select thing_name from thing where thing_id=?";
				PreparedStatement pstmt2 = conn.prepareStatement(query2);
				pstmt2.setString(1, rs.getString(2));
				ResultSet rs2 = pstmt2.executeQuery();
				rs2.next();
				thing.put("thing_name", rs2.getString(1));
				rankList.put(thing);
				
				rs2.close();
				pstmt2.close();
			}
			
			snapshot.put("rankList", rankList);
			
			rs.close();
			pstmt.close();
			
			mypage.put("snapshot", snapshot);
			
			response.getWriter().write(mypage.toString());
		}
	%>
	<%
		conn.close();
	%>
</body>
</html>