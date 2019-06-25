package com.jcg.dao;

import com.jcg.vo.Custom;
import com.mongodb.BasicDBObject;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoCollection;

public class DBOperationImpl implements IDBOperations{

	
	
	public void saveCustom(Custom c) {

		MongoCollection<Custom>  collection=MongoConnection.getInstance().getClient().getCollection("user",Custom.class);
		
		//synch
		//collection.save(custom);
	/*	Document doc = new org.bson.Document();
		doc.put("id", c.getId());
		doc.put("name", c.getName());
*/
		
		
		collection.insertOne(c, new SingleResultCallback<Void>() {
		    @Override
		    public void onResult(final Void result, final Throwable t) {
		        System.out.println("Inserted!");
		    }
		});
		
	}

	
	public FindIterable<Custom> fetchCustom(BasicDBObject searchQuery) {
		 FindIterable<Custom> listOfCustom=null;
		 try {
			MongoCollection<Custom>  collection=MongoConnection.getInstance().getClient().getCollection("user",Custom.class);
					listOfCustom=	collection.find(searchQuery); 
								
				/* synch
			//query.put("id", "123");
			DBCursor cursor = collection.find(query);
			
		    try {
		       while(cursor.hasNext()) {
		          DBObject dbobj = cursor.next();
		        //Converting BasicDBObject to a custom Class(Employee)
		           custom = (new Gson()).fromJson(dbobj.toString(), Custom.class);
		          System.out.println(custom.getName());
		       }
		    } finally {
		       cursor.close();
		    }
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		return listOfCustom;
	}
		catch(Exception e) {
			e.printStackTrace();
		}
		return listOfCustom;
	}
}
