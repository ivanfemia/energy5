package com.saphanatutorial.search;

import twitter4j.*;

import com.saphanatutorial.config.Configurations;
import com.saphanatutorial.dao.TweetDAO;
import com.saphanatutorial.entity.Tweet;
import com.saphanatutorial.util.TwitterConnection;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;

public class SearchTweets {
	/**
	 * Invoke Twitter Search API to get the tweets and return as a list of Tweet
	 * 
	 * */
    public List<Tweet> search(String searchTerm) {
    	List<Tweet> tList = new ArrayList<Tweet>();
    	
    	Twitter twitter = TwitterConnection.getInstance();
        try {
        	
        	TweetDAO tDAO = TweetDAO.getInstance();
        	Timestamp lastTimestamp = tDAO.getLastRefresh();
        	//System.out.println("Last timestamp: " + lastTimestamp);
        	
        	
            Query query = new Query(searchTerm);
            QueryResult result;
            
            int index = 0;
            long timestamp = 0;
            do {
            	index++;
            	//System.out.println("I found new data");
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                
                for (Status tweet : tweets) {
                	
                	//Instant instant = Instant.ofEpochMilli ( tweet.getCreatedAt().getTime() );
                	Timestamp zdt = new Timestamp( tweet.getCreatedAt().getTime() );
                	//Timestamp zdt = Timestamp.from(instant);
                	//System.out.println("Tweet: " + zdt);
                	//System.out.println("me: " + lastTimestamp);
                	
                	
                	if ( zdt.after(lastTimestamp) ) {
                		//System.out.println("I got some data");
                		Tweet t = new Tweet();
	                	t.setUserName(tweet.getUser().getName());
	                	t.setCreatedAt(tweet.getCreatedAt());
	                	t.setText(tweet.getText());
	                	t.setHashTags(tweet.getHashtagEntities());
	                	t.setGeolocalization(tweet.getGeoLocation());
	                	tList.add(t);
	                	if (tweet.getCreatedAt().getTime() > timestamp ) {
	                		timestamp = tweet.getCreatedAt().getTime();
	                	}
                	}
                }
            } while ((query = result.nextQuery()) != null && index < Configurations.SEARCH_RESULT_COUNT );
            if (tList.size() > 0 ) {
            	tDAO.setLastRefresh(new Timestamp(timestamp));
            }
            
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return tList;
    }
}
