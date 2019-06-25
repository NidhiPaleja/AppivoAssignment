package com.jcg.servlet.async;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

import com.google.gson.Gson;
import com.jcg.dao.DBOperationImpl;
import com.jcg.dao.IDBOperations;
import com.jcg.vo.Custom;
import com.jcg.vo.ResponseVO;

public class AsyncSaveListener implements AsyncListener,Runnable {	

	// Public Constructor Is Required By Servlet Spec
	public AsyncSaveListener() {}
	
	
	private AsyncContext ctx;
	public AsyncSaveListener(AsyncContext async) {
		this.ctx=async;
	}

	private IDBOperations idbOperations=new DBOperationImpl();

	public void onComplete(AsyncEvent ae) throws IOException {
		System.out.println("AsyncListener :: 'onComplete' For Request?= " + ae.getAsyncContext().getRequest().getAttribute("ServletName"));
		System.out.println("AsyncListener :: 'onComplete' For Request?= " + ae.getAsyncContext().getRequest().getAttribute("Custom"));
		
		
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
		System.out.println("-------- "+Thread.currentThread().getName());
		  ResponseVO response=new ResponseVO();
		  Gson gson = new Gson(); 

		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = ctx.getRequest().getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		  try {
			  
			  final Custom cus = gson.fromJson(jb.toString(), Custom.class);
			  
			  idbOperations.saveCustom(cus);
			  
			  response.setMessage("SUCCESS");
			  response.setResponseCode(200);
				ctx.getResponse().getOutputStream().write(gson.toJson(response).getBytes());

		  }
		  catch(Exception e) {
			  e.printStackTrace();
			  response.setMessage("FAIL");
			  response.setResponseCode(400);
				try {
					ctx.getResponse().getOutputStream().write(gson.toJson(response).getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				  }

		finally {
			
			ctx.complete();

		}
	}
}