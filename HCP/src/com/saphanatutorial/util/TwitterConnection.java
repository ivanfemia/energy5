package com.saphanatutorial.util;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.saphanatutorial.config.Configurations;

public class TwitterConnection {
	
	public static Twitter getInstance(){
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(Configurations.OAUTH_CONSUMER_KEY)
		  .setOAuthConsumerSecret(Configurations.OAUTH_CONSUMER_SECRET)
		  .setOAuthAccessToken(Configurations.OAUTH_ACCESS_TOKEN)
		  .setOAuthAccessTokenSecret(Configurations.OAUTH_ACCESS_TOKEN_SECRET);
		
		if(Configurations.HAS_PROXY){
			cb.setHttpProxyHost(Configurations.PROXY_HOST).setHttpProxyPort(Configurations.PROXY_PORT);
		}
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		return twitter;
	}
	
	// Test the Connection
	public static void main(String[] argv) throws IllegalStateException, TwitterException {
		
		Twitter twitter = TwitterConnection.getInstance();
		String screenName = twitter.getScreenName();
		System.out.println("Connection to Twitter successfully!" + " My screenName is " + screenName);

		Long id = twitter.getId();
		System.out.println("Connection to Twitter successfully!" + " My ID is " + id);
	}

}
