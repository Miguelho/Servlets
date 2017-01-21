package org.miguel;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Counter extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6940749730998483347L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter printOut = resp.getWriter();
		
		ServletContext servletContext = getServletContext();
		
		Integer count = (Integer) servletContext.getAttribute("pcount");
		
		printOut.println(count+": pageview");

		servletContext.setAttribute("pcount", ++count);
	}

}
