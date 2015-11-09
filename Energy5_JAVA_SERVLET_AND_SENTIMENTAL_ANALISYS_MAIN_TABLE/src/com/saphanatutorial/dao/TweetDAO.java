package com.saphanatutorial.dao;

import com.saphanatutorial.config.*;
import com.saphanatutorial.entity.*;
import com.saphanatutorial.util.*;

import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class TweetDAO {
	private Connection conn = null;

	public static TweetDAO getInstance() {
		TweetDAO tDAO = new TweetDAO();
		tDAO.setConn(HDBConnection.getConnection());
		return tDAO;
	}
	
	public Timestamp getLastRefresh () {
		Timestamp timeStamp = new Timestamp(0);
		if (conn != null) {
			PreparedStatement pstmt;
			try {
				String stmt = "SELECT \"TIMESTAMP\" FROM \"D045495\".\"LAST_REFRESH_NEW\" ORDER BY \"ID\"  DESC LIMIT 1";
				pstmt = conn.prepareStatement(stmt);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					timeStamp = rs.getTimestamp("TIMESTAMP");
				}				

				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return timeStamp;
	}
	
	public void setLastRefresh(Timestamp timestamp) {
		//Calendar cal = Calendar.getInstance(); // creates calendar
	    //cal.setTime(new java.util.Date()); // sets calendar time/date
	    //cal.add(Calendar.HOUR_OF_DAY, -1); // adds one hour
	    //java.util.Date date = cal.getTime(); // returns new date object, one hour in the future
	    
	    java.util.Date date = new java.util.Date(timestamp.getTime());
	    Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(date); // sets calendar time/date
	    //cal.add(Calendar.HOUR_OF_DAY, -1); // adds one hour
	    date = cal.getTime(); // returns new date object, one hour in the future
		if (conn != null) {
			PreparedStatement pstmt;
			try {
				String stmt = "insert into \"" + Configurations.HDB_SCHEMA + "\"." +  
					      "\"LAST_REFRESH_NEW\" values(\"" + Configurations.HDB_SCHEMA + "\"." + 
					      "\"LAST_REFRESH_NEW_SEQ\".NEXTVAL,?)";
				System.out.println(stmt);
				pstmt = conn.prepareStatement(stmt);
				pstmt.setTimestamp(1, new Timestamp(date.getTime()));
				pstmt.execute();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void insert(Tweet tweet){
		if (conn != null) {
			PreparedStatement pstmt;
			try {
				String stmt = "insert into \"" + Configurations.HDB_SCHEMA + "\"." +  
						      "\"TWEETS\" values(\"" + Configurations.HDB_SCHEMA + "\"." + 
						      "\"TWEET_SEQUENCE\".NEXTVAL,?,?,?,?,?,?,?)";
				
				pstmt = conn.prepareStatement(stmt);
				pstmt.setString(1, tweet.getUserName());
				Date sqlDate = new Date(tweet.getCreatedAt().getTime());
				pstmt.setDate(2, sqlDate);
				Time sqlTime = new Time(tweet.getCreatedAt().getTime());
				pstmt.setTime(7, sqlTime);
				pstmt.setString(3, tweet.getText());
				pstmt.setString(4, tweet.getHashTagsString());
				if (tweet.getGeolocalization() != null){
					pstmt.setDouble(5, tweet.getGeolocalization().getLatitude());
					pstmt.setDouble(6, tweet.getGeolocalization().getLongitude());
				} else {
					Random randomGenerator = new Random();
					pstmt.setDouble(5, randomGenerator.nextInt(360)-180);
					pstmt.setDouble(6, randomGenerator.nextInt(180)-90);					
				}
				pstmt.execute();
				
				System.out.println("Insert to HANA successful: " + tweet.getText());
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insert(List<Tweet> tweets){
		for(Tweet t:tweets){
			//t.setText(t.getText().replace("@ComEd","@energy5_innojam"));
			this.insert(t);
		}
	}

	public Connection getConn() {
		return conn;
	}
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public void commitAndClose(){
		try {
			//conn.commit();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
