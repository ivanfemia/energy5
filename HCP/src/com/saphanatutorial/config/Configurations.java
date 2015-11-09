package com.saphanatutorial.config;

public interface Configurations {
	
	// Network Proxy - replace with your own network proxy or set the HAS_PROXY as false if you don't need to use proxy
	public static final boolean HAS_PROXY = false;
	public static final String PROXY_HOST = "proxy";
	public static final int PROXY_PORT = 8080;
	
	// HDB Connection Settings - replace with your own HANA connection URL, user, password and schema 
	//JDBC connection url is "jdbc:sap://<host-id>:3 <instance no>15/?autocommit=false"
	//Assume that HANA Host ID is abcd1234 and instance no is 00 then JDBC URL will be "jdbc:sap://abcd1234:30015/?autocommit=false"
	//public static final String HDB_URL = "jdbc:sap://xs01innojam.hana.ondemand.com:30015/?autocommit=false";
	public static final String HDB_URL = "jdbc:sap://localhost:30015/?autocommit=false";
	public static final String HDB_USER = "d045495";
	public static final String HDB_PWD = "InnoJam2015";
	public static final String HDB_SCHEMA = "D045495";
	
	// Twitter Authentication - replace with your own Twitter application consumer key and token
	public static final String OAUTH_CONSUMER_KEY = "rXIOgnuqQo8sB5OVnBzCx2bWe";
	public static final String OAUTH_CONSUMER_SECRET = "mjkXtCVom7Ccs0paKsknq3R60rQ24UrBlZaHNH1hXLWrIW4HyD";
	public static final String OAUTH_ACCESS_TOKEN = "4140019503-WiB6z8m7RA3728PrtfklbQdn2D1DafXMpjskeSt";
	public static final String OAUTH_ACCESS_TOKEN_SECRET = "i25Ied4Sf6nFrIiPgulWBO0Z86MEyTLJHDhc9Ef8M5FJY";
	
	// Search Term and Result Counts - replace with your own search term
	public static final String SEARCH_TERM = "Energy5";
	public static final int SEARCH_RESULT_COUNT = 100; 
}
