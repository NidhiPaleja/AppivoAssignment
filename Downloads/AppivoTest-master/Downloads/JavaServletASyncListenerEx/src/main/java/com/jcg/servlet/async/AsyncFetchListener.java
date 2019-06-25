package com.jcg.servlet.async;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.bson.Document;

import com.google.gson.Gson;
import com.jcg.dao.DBOperationImpl;
import com.jcg.dao.IDBOperations;
import com.jcg.vo.Custom;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.FindIterable;

public class AsyncFetchListener implements AsyncListener,Runnable {	

	// Public Constructor Is Required By Servlet Spec
	public AsyncFetchListener() {}
	
	private AsyncContext ctx;
	private static final String pathParameterName="_id";
	public AsyncFetchListener(AsyncContext async) {
		this.ctx=async;
	}

	private IDBOperations idbOperations=new DBOperationImpl();


	public void onComplete(AsyncEvent ae) {
		
		System.out.println("Oncomplete method!!!");
	
	}

	public void onTimeout(AsyncEvent ae) {
		System.out.println("AsyncListener :: 'onTimeout' For Request");
	}

	public void onError(AsyncEvent ae) {
		System.out.println("AsyncListener :: 'onError' For Request");
	}

	public void onStartAsync(AsyncEvent ae) {
		System.out.println("AsyncListener :: 'onStartAsync'");
	}

	@Override
	public void run() {
  	  System.out.println("----------------->>> "+ Thread.currentThread().getName());

		// TODO Auto-generated method stub
		String pathParameter=(String) ctx.getRequest().getAttribute("pathParameters");
		//synchr
	    BasicDBObject searchQuery = null; //new BasicDBObject();
	    searchQuery=new BasicDBObject().append(pathParameterName, Integer.parseInt(pathParameter));
	    
	    Document doc=new Document();
	    doc.append("\""+pathParameterName+"\"", Integer.parseInt(pathParameter));
	    
			
		FindIterable<Custom> obj=idbOperations.fetchCustom(searchQuery);
		
		Gson gson = new Gson();

		Block<Custom> printBlock = new Block<Custom>() {
		    @Override
		    public void apply(final Custom cus) {
		        System.out.println("Inside the printBlock");
		        try {
		        	cus.setResponseCode(200);
		        	cus.setMessage("SUCCESS");
					ctx.getResponse().getOutputStream().write(gson.toJson(cus).getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					cus.setResponseCode(400);
		        	cus.setMessage("FAIL");
					
				}
				finally {
					ctx.complete();
				}
				
		    }
		};
		
		SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				System.out.println("result--------------->>"+result);
				
			}	
		};

		
			obj.forEach(printBlock, callbackWhenFinished);
			System.out.println("End of method");
		
	

	}
}