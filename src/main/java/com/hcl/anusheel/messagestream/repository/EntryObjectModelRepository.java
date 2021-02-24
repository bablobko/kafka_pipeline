/**
 * 
 */
package com.hcl.anusheel.messagestream.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.anusheel.messagestream.model.EntryObjectModel;

/**
 * @author anusheel
 *
 */
@Repository("entryObjectModelRepository")
public interface EntryObjectModelRepository extends JpaRepository<EntryObjectModel, Long> {
	
	List<EntryObjectModel> findByTradeIdAndVersion(String tradeId, Integer version);

}
