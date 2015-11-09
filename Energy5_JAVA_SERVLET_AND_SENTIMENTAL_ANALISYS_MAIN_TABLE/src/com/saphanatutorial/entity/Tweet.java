package com.saphanatutorial.entity;

import java.util.Date;

import twitter4j.*;

public class Tweet {
	private String userName;
	private Date createdAt;
	private String text;
	private HashtagEntity[] hashTags;
	private GeoLocation geolocalization;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public HashtagEntity[] getHashTags() {
		return hashTags;
	}
	public void setHashTags(HashtagEntity[] hashTags) {
		this.hashTags = hashTags;
	}
	
	public String getHashTagsString() {
		StringBuffer sb = new StringBuffer();
		HashtagEntity[] tags = this.getHashTags();
		for(HashtagEntity tag:tags){
			sb.append(tag.getText());
			sb.append(",");
		}
		
		return sb.toString();
	}
	
	public String toString(){
		return 
		"User: " + this.getUserName() + 
		" CreatedAt: " + this.getCreatedAt().toString() + 
		" Text: " + this.getText() + 
		" HashTags: " + this.getHashTagsString();
	}
	public GeoLocation getGeolocalization() {
		return geolocalization;
	}
	public void setGeolocalization(GeoLocation geolocalization) {
		this.geolocalization = geolocalization;
	}

}
