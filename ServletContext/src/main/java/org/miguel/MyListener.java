package org.miguel;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class MyListener implements ServletContextListener{
	
	ServletContext context;
	Connection con;
	Statement s;
	PreparedStatement precompileStatement;
	ResultSet rs;
	int count;
	

	public void contextDestroyed(ServletContextEvent arg0) {
		try{
			context = arg0.getServletContext();
			
			count = (Integer) context.getAttribute("pcount");
			
			precompileStatement = con.prepareStatement("update counter set pcount='" +count+"'");
			
			precompileStatement.executeUpdate();
		}catch(SQLException sqle){
			
			sqle.printStackTrace();
			
		}
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.50.4:3306/test","root","12345");
			
			s = con.createStatement();
			
			rs = s.executeQuery("select pageview from counter");
			
			while(rs.next()){
				count=rs.getInt(1);
			}
			
			context = arg0.getServletContext();
			
			String params = context.getInitParameter("arquitectura");
			
			context.setAttribute("pcount", count);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
	}
	
	
}
