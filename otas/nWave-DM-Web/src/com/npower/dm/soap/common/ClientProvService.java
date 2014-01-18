package com.npower.dm.soap.common;


public interface ClientProvService {
	
  /**
   * End point of this service
   */
  public static final String SERVICE_END_POINT = "ClientProvService";

  /**
   * Testing
   * 
   * @param message
   * @return
   */
  public String echo(String message);

  /**
   * Return Version of DM Server
   * 
   * @return
   */
  public String version();

  /**
   * Generate a OMAClientProvDoc.
   * @param manufacturerExternalID
   *        Manufacturer ExternalID
   * @param modelExternalID
   *        Model External ID
   * @param categoryName
   *        Name of ProfileCategory
   * @return
   */
  public String getOMAClientProvDoc(String manufacturerExternalID, String modelExternalID, String categoryName);	
}