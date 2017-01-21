package org.miguel.pocs.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface MyProducer <K,V>{
	
	public void send(ProducerRecord<K, V> record);

}
