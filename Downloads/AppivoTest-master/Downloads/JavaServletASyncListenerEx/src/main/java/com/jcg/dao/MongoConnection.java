package com.jcg.dao;

import java.net.UnknownHostException;
import java.util.ResourceBundle;
/*
import com.mongodb.DB;
import com.mongodb.MongoClient;*/

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnection {
	
	
	private static MongoConnection instance;
	private static ResourceBundle bundle=ResourceBundle.getBundle("config");
	
	private MongoConnection() {
		
	}
	
	public static MongoConnection getInstance() {
		
		if(instance==null) {
			instance=new MongoConnection();
		}
		
		return instance;
		
	}
	
	
	// create codec registry for POJOs
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClients.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

	
	public MongoDatabase getClient() {
		// To directly connect to the default server localhost on port 27017
	MongoClient mongoClient = MongoClients.create(bundle.getString("dbUrl"));

		
		MongoClientSettings settings = MongoClientSettings.builder()
		        .codecRegistry(pojoCodecRegistry).build();
	//	MongoClient mongoClient = MongoClients.create(settings);

				
	  /*  MongoClient mongoClient1= MongoClients.create(MongoClientSettings.builder().build().applyToClusterSettings(builder ->Builder.hosts(Arrays.asList(
	                                    new ServerAddress(bundle.getString("dbUrl")))).build()));
*/
		
		// Use a Connection String

		/*ClusterSettings clusterSettings = ClusterSettings.builder().hosts(asList(new ServerAddress(bundle.getString("dbUrl")))).build();	
		MongoClientSettings settings = MongoClientSettings.builder().clusterSettings(clusterSettings).build();
		MongoClient mongoClient = MongoClients.create(settings);
*/
		MongoDatabase database = mongoClient.getDatabase(bundle.getString("dbName")).withCodecRegistry(pojoCodecRegistry);
		return database;

		
	}
	
/*	public DB getClient() throws UnknownHostException {
		MongoClient client=new MongoClient(bundle.getString("dbUrl"), Integer.parseInt(bundle.getString("dbPort").toString()));
		return client.getDB(bundle.getString("dbName"));
		
	}
	

*/
}
