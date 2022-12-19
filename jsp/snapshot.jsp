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
		String query = "select user_id, snapshot_id from ranking_snapshot order by update_date desc";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		JSONArray objArr = new JSONArray();
		while(rs.next()){
			JSONObject obj = new JSONObject();
			String uid = rs.getString(1);
			String sid = rs.getString(2);
			String query2 = "select name from users where user_id=?";
			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			pstmt2.setString(1, uid);
			ResultSet rs2 = pstmt2.executeQuery();
			rs2.next();
			obj.put("name", rs2.getString(1));
			rs2.close();
			pstmt2.close();
			
			query2 = "select like_count, dislike_count from ranking_snapshot where snapshot_id=?";
			pstmt2 = conn.prepareStatement(query2);
			pstmt2.setString(1, sid);
			rs2 = pstmt2.executeQuery();
			rs2.next();
			obj.put("like", rs2.getBigDecimal(1).toString());
			obj.put("dislike", rs2.getBigDecimal(2).toString());
			rs2.close();
			pstmt2.close();

			query2 = "select ranks, thing_id from thingrank where user_id=? order by ranks";
			pstmt2 = conn.prepareStatement(query2);
			pstmt2.setString(1, uid);
			rs2 = pstmt2.executeQuery();
			ResultSetMetaData rsmd2 = rs2.getMetaData();
			int cnt = rsmd2.getColumnCount();
			JSONArray table = new JSONArray();
			while(rs2.next()){
				JSONObject row = new JSONObject();
				row.put("rank", rs2.getBigDecimal(1).toString());
				
				String query3 = "select thing_name from thing where thing_id=?";
				PreparedStatement pstmt3 = conn.prepareStatement(query3);
				pstmt3.setString(1, rs2.getString(2));
				ResultSet rs3 = pstmt3.executeQuery();
				rs3.next();
				row.put("thingName", rs3.getString(1));
				rs3.close();
				pstmt3.close();
				
				table.put(row);
			}
			rs2.close();
			pstmt2.close();
			
			obj.put("table", table);
			objArr.put(obj);
		}
		rs.close();
		pstmt.close();
		
		response.getWriter().write(objArr.toString());
	%>
	<%
		conn.close();
	%>
</body>
</html>