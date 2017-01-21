package org.miguel.pocs.kafka.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.miguel.pocs.kafka.producer.MyProducer;
import org.miguel.pocs.kafka.producer.MyProducerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2771470657712482636L;
	
	private static final Logger logger = LoggerFactory.getLogger(MyServlet.class);
	MyProducer<String, String> myProducer;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		myProducer = new MyProducerImpl("192.168.50.4:9092");
		
		logger.info(this.getClass().getTypeName() + " initialized!!!");
		
		logger.info(myProducer.toString() + " is alive ready to send Messages!!");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		String topic = req.getParameter("topic");
		String message = req.getParameter("value");
		myProducer.send(new ProducerRecord<String, String>(topic,message));
	}

}
