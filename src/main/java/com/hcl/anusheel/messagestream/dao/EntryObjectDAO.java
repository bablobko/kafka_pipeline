/**
 * 
 */
package com.hcl.anusheel.messagestream.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hcl.anusheel.messagestream.model.EntryObjectModel;
import com.hcl.anusheel.messagestream.repository.EntryObjectModelRepository;

/**
 * @author anusheel
 *
 */
@Repository
public class EntryObjectDAO {
	
	private final Logger logger = LoggerFactory.getLogger(EntryObjectDAO.class);
	
	@Autowired
	private EntryObjectModelRepository entryObjectModelRepository;
	

	//find if the similar entry is there in the table, if not then persist this one.
	//else reject this incoming one.
	public void persist(EntryObjectModel entryObjectModel) {
		String tradeId = entryObjectModel.getTradeId();
		Integer version = entryObjectModel.getVersion();
		List<EntryObjectModel> entryObjectList = entryObjectModelRepository.findByTradeIdAndVersion(tradeId, version);
		logger.info("The return from the call to repository by findByIDAndVersion is entryObjectList : '{}'", entryObjectList);
		EntryObjectModel savedEntryObjectModel = null;
		if(entryObjectList.size() == 0) {
			savedEntryObjectModel = entryObjectModelRepository.save(entryObjectModel);
			logger.info(String.format("#### -> Saved savedEntryObjectModel"));
		}else {
			logger.info(String.format("#### -> Could not save savedEntryObjectModel"));
		}
		
	}

}
