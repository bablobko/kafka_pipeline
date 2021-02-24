/**
 * 
 */
package com.hcl.anusheel.messagestream.service;

import org.springframework.stereotype.Service;

import com.hcl.anusheel.messagestream.request.dto.EntryObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author anusheel
 *
 */
@Service
public class KafkaProducer {
	
	private static final String TOPIC= "myTopic";
	
	@Autowired
	private KafkaTemplate<String, String> stringObjectKafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, EntryObject> entryObjectKafkaTemplate;
	
	public void writeStringMessage(String strMessage) {
		this.stringObjectKafkaTemplate.send(TOPIC, strMessage);
	}
	
	public void writeMessage(EntryObject entryMsg) {
		this.entryObjectKafkaTemplate.send(TOPIC, entryMsg);
	}

}