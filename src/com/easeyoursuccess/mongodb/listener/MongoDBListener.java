package com.easeyoursuccess.mongodb.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;


@WebListener
public class MongoDBListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		MongoClient mongo = (MongoClient) sce.getServletContext().getAttribute("MONG0_CLIENT");
		mongo.close();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext ctx = sce.getServletContext();
		MongoClient mongo = new MongoClient(ctx.getInitParameter("MONGO_HOST"), Integer.parseInt(ctx.getInitParameter("MONGO_PORT")));
		sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
	}

}
