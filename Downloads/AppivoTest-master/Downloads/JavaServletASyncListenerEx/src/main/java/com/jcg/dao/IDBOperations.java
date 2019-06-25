package com.jcg.dao;

import java.util.Map;

import org.bson.Document;

import com.jcg.vo.Custom;
import com.mongodb.BasicDBObject;
import com.mongodb.async.client.FindIterable;

public interface IDBOperations {

	public void saveCustom(Custom custom);
	
	public FindIterable<Custom> fetchCustom(BasicDBObject searchQuery);

	
}
