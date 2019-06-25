package com.jcg.servlet.async;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

@WebServlet(urlPatterns = "/AsyncListener/*", asyncSupported = true)
public class AsyncListener extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		
		System.out.println("do post");
		  try {
			  System.out.println(Thread.currentThread().getName());
			  AsyncContext asyncCtx = req.startAsync();
				ServletRequest servReq = asyncCtx.getRequest();
				servReq.setAttribute("queryParameters", req.getQueryString());

				/**** Adding 'AsyncListener'  ****/
				AsyncSaveListener asyncSave=new AsyncSaveListener(asyncCtx);
				asyncCtx.addListener(asyncSave);
				asyncCtx.setTimeout(100000);
				
				Thread t=new Thread(asyncSave);

				t.start();

					  } catch (JSONException e) {
						  e.printStackTrace();
		  }
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  System.out.println(Thread.currentThread().getName());

           String servletPath = req.getPathInfo().split("/")[1];
	
		  AsyncContext asyncCtx = req.startAsync();
			ServletRequest servReq = asyncCtx.getRequest();
			servReq.setAttribute("pathParameters", servletPath);

			/**** Adding 'AsyncListener'  ****/
			AsyncFetchListener asyncFetch=new AsyncFetchListener(asyncCtx);
			asyncCtx.addListener(asyncFetch);
			asyncCtx.setTimeout(100000);
			
			Thread t=new Thread(asyncFetch);

			t.start();
			
 
	}
	
		
	
}