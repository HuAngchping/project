package com.npower.dm.soap.service;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.OTAInventory;
import com.npower.cp.db.OTAInventoryImpl;
import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.soap.common.ClientProvService;

public class ClientProvServiceImpl extends BaseService implements ClientProvService {
  private static Log log = LogFactory.getLog(ClientProvServiceImpl.class);

  public String getOMAClientProvDoc(String manufacturerExternalID, String modelExternalID, String categoryName) {
    Properties props = new Properties();
    props.setProperty(OTAInventory.PROPERTY_FACTORY, OTAInventoryImpl.class.getName());
    try {
        OTAInventory inventory = OTAInventory.newInstance(props);
        ClientProvTemplate template = inventory.findTemplate(manufacturerExternalID, modelExternalID, categoryName);
        return template.getContent();
    } catch (Exception ex) {
      log.fatal("Failure in SOAP Service.", ex);
    } finally {
    }
    return null;
  }
	
}