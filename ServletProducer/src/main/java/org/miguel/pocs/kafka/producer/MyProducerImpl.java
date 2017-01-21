package org.miguel.pocs.kafka.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Wrapping class for KafkaProducer
 * */
public class MyProducerImpl implements MyProducer<String,String>{
	
	private static final Logger logger = LoggerFactory.getLogger(MyProducerImpl.class);
	private static final String TOPIC_NAME = "Hello-kafka";
	private static final Properties props = new Properties();
	
	private static Producer<String,String> producer = null;
	
	
	
	
	public MyProducerImpl(){setProperties();}
	
	
	/**
	 * Single Broker constructor
	 * */
	public MyProducerImpl(String localhostId){
		setProperties();
		props.put("bootstrap.servers", localhostId);
		props.put("advertised.host.name", localhostId);
	
		producer = new KafkaProducer<String, String>(props);
	}
	
	
	public void send(ProducerRecord<String, String> record) {
		Future<RecordMetadata> future =null;
		
		try{
			future = producer.send(record);	
		}finally{
			producer.flush();
		}
		
		if(future.isDone()) {
			try{
				logger.info(future.get().toString());
			}catch(InterruptedException interrupted){
				logger.error(interrupted.getMessage());
			}catch (ExecutionException executing){
				logger.error(null, executing.getCause());
			}
			
		}
		
//		producer.close();
	}
	
	
	/**
	 * Default config by tutorialspoint.com
	 * */
	private final void  setProperties(){
		//Set acknowledgements for producer requests.
		props.put("acks", "all");
		
		//If the request fails, the producer can automatically retry,
		props.put("retries", 0);
		
		//Specify buffer size in config
//	      props.put("batch.size", 16384);
		
		
		//broker public IP address
//		props.put("advertised.host.name", "192.168.50.4");
		
		
	    //Reduce the no of requests less than 0   
	      props.put("linger.ms", 1);
	      
	    //The buffer.memory controls the total amount of memory available to the producer for buffering.   
	      props.put("buffer.memory", 33554432);
	      
	      props.put("key.serializer", 
	    	         "org.apache.kafka.common.serialization.StringSerializer");
	      
	      props.put("value.serializer", 
	    	         "org.apache.kafka.common.serialization.StringSerializer");
	}

}
