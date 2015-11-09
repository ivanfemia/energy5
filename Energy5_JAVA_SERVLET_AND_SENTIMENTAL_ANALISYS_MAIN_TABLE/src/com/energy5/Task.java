package com.energy5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.TimerTask;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.saphanatutorial.config.Configurations;
import com.saphanatutorial.dao.TweetDAO;
import com.saphanatutorial.entity.Tweet;
import com.saphanatutorial.search.SearchTweets;

public class Task extends TimerTask {
	
	DataSource ds;
	InitialContext ctx;
	TweetDAO tDAO;
	
	public Task() throws Exception{
		ctx = new InitialContext();
		ds = (DataSource) ctx
				.lookup("java:comp/env/jdbc/DefaultDB");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//Connection conn = null;
		try {
			/*conn = ds.getConnection();
			System.out.println("Connection to HANA successful!");
			PreparedStatement pstmt = conn
					.prepareStatement("Select 'helloworld' from dummy");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String hello = rs.getString(1);
			System.out.println(hello);*/
			
			
			// Search tweets
	    	SearchTweets searchTweets = new SearchTweets();
	    	List<Tweet> tList = searchTweets.search(Configurations.SEARCH_TERM);
	    	//List<Tweet> tList = searchTweets.search("@ComEd");
	    	
	    	// Insert tweets into HANA DB
	    	tDAO = TweetDAO.getInstance();
	    	tDAO.insert(tList);
	    	tDAO.commitAndClose();
	    	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			if (tDAO != null)
				//try {
					tDAO.commitAndClose();
				//} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
		}
	}

}
