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
		String query = "select thing_id, ranks, count, user_id from thingrank natural join (select user_id, count(*) count from thingrank group by user_id) order by thing_id";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		String before_tid = "";
		double count = 0;
		double sum = 0;
		JSONArray thingList = new JSONArray();
		while(rs.next()){
			if(!before_tid.equals(rs.getString(1))){
				if(count > 0){
					JSONObject thing = new JSONObject();
					String query2 = "select thing_name from thing where thing_id=?";
					PreparedStatement pstmt2 = conn.prepareStatement(query2);
					pstmt2.setString(1, before_tid);
					ResultSet rs2 = pstmt2.executeQuery();
					rs2.next();
					thing.put("name", rs2.getString(1));
					thing.put("score", sum/count);
					thingList.put(thing);
					rs2.close();
					pstmt2.close();
					count = 0;
					sum = 0;
				}
				before_tid = rs.getString(1);
			}
			double ranks = rs.getBigDecimal(2).doubleValue();
			double amount = rs.getBigDecimal(3).doubleValue();
			double percent = (amount+1-ranks)/(amount+1);
			
			if(amount < 4){
				sum += (1+4*percent)*0.8;
				count += 0.8;
			}
			else if(amount < 6){
				sum += (1+4*percent)*0.9;
				count += 0.9;
			}
			else{
				sum += (1+4*percent);
				count += 1;
			}
		}
		if(count > 0){
			JSONObject thing = new JSONObject();
			String query2 = "select thing_name from thing where thing_id=?";
			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			pstmt2.setString(1, before_tid);
			ResultSet rs2 = pstmt2.executeQuery();
			rs2.next();
			thing.put("name", rs2.getString(1));
			thing.put("score", sum/count);
			thingList.put(thing);
			rs2.close();
			pstmt2.close();
		}
		rs.close();
		pstmt.close();
		response.getWriter().write(thingList.toString());
	%>
	<%
		conn.close();
	%>
	
</body>
</html>