/**
 * 
 */
package com.hcl.anusheel.messagestream.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hcl.anusheel.messagestream.dao.EntryObjectDAO;
import com.hcl.anusheel.messagestream.model.EntryObjectModel;
import com.hcl.anusheel.messagestream.request.dto.EntryObject;

@Service
public class KafkaConsumer {

	private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	@Autowired
	private EntryObjectDAO entryObjectDAO;
	
	/******
	 * @KafkaListener(topics = "myTopic", groupId = "foo", containerFactory =
	 *                       "fooKafkaListenerContainerFactory") public void
	 *                       receive(String message) throws IOException {
	 *                       logger.info(String.format("#### -> Consumed message ->
	 *                       %s", message)); }
	 *******/

	@KafkaListener(topics = "myTopic", containerFactory = "entryObjectKafkaListenerContainerFactory")
	public void receive(EntryObject entryObject) throws IOException {
		logger.info(String.format("#### -> Consumed entryObject"));
		logger.info("received entryObject = '{}'", entryObject.toString());
		// Convert incoming entryObject to entryObjectModel
		EntryObjectModel entryObjectModel = convertEntryObjectToModel(entryObject);
        //find if the similar entry is there in the table, if not then persist this one.
		//else reject this incoming one.
		logger.info("Calling the persist on entryObjectDAO on '{}'", entryObjectModel);
		entryObjectDAO.persist(entryObjectModel);
		logger.info("Called the persist on entryObjectDAO on '{}'", entryObjectModel);
	}

	private EntryObjectModel convertEntryObjectToModel(EntryObject entryObject) {
		logger.info("convertEntryObjectToModel called.");
		EntryObjectModel entryObjectModel = new EntryObjectModel();
		entryObjectModel.setTradeId(entryObject.getTradeId());
		entryObjectModel.setBookId(entryObject.getBookId());
		entryObjectModel.setCounterPartyId(entryObject.getCounterPartyId());
		entryObjectModel.setExpired(entryObject.getExpired());
		entryObjectModel.setVersion(entryObject.getVersion());
		//set times here. With proper conversion.
		Date createdDate = localDateConverter(entryObject.getCreatedDate());
		entryObjectModel.setCreatedDate(createdDate);
		Date maturityDate = localDateConverter(entryObject.getMaturityDate());
		entryObjectModel.setMaturityDate(maturityDate);
		return entryObjectModel;
	}

	private Date localDateConverter(LocalDate createdDate) {

		Date date = Date.from(createdDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return date;
	}
}
