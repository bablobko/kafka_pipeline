/**
 * 
 */
package com.hcl.anusheel.messagestream.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.anusheel.messagestream.request.dto.EntryObject;
import com.hcl.anusheel.messagestream.service.KafkaProducer;

/**
 * @author anusheel
 *
 */
@RestController
public class KafkaController {
	
	private final Logger logger = LoggerFactory.getLogger(KafkaController.class);
	
	@Autowired
	private KafkaProducer producer;
	
	
	@GetMapping()
	public ResponseEntity<?> hello(){
		logger.info("Get hello called, Hello World!! would be printed.");
		return ResponseEntity.ok("<h1> Hello World!!</h1>");
	}
	
	@GetMapping("/entry")
	public ResponseEntity<?> getEntry(){
		logger.info("Get entry called, Hello World!! would be printed.");
		return ResponseEntity.ok(new EntryObject());
	}
	
	@PostMapping("entry/string")
	public ResponseEntity<?> writeStringMessageToTopic(@RequestBody String message){
		logger.info("writeStringMessageToTopic called.");
		logger.info("recieved string message = '{}'", message);
		this.producer.writeStringMessage(message);
		return new ResponseEntity<>("Data sent to Kafka", HttpStatus.OK);
	}
	
	
	
	@PostMapping("/entry")
	public ResponseEntity<?> writeMessageToTopic(@RequestBody EntryObject entry) {
		logger.info("Post writeMessageToTopic method called of KafkaController");
		logger.info("received entryObject = '{}'", entry.toString());
		this.producer.writeMessage(entry);
		return new ResponseEntity<>("Data sent to Kafka", HttpStatus.OK);
	}

}
