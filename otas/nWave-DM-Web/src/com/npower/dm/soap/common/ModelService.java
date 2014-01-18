package com.npower.dm.soap.common;

import java.util.List;


public interface ModelService {

  /**
   * End point of this service
   */
  public static final String SERVICE_END_POINT = "ModelService";

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
   * Return all of manufacturers
   * @return
   */
  public List<ManufacturerWS> getManufacturers();
  
  /**
   * Return all of decorated manufacturers
   * @param locale
   * @return
   */
  public List<ManufacturerWS> getDecoratorManufacturers(String language, String country);
  
  /**
   * @param manufacturerExternalID
   * @return
   */
  public List<ModelWS> getModels(String manufacturerExternalID);
  
  
  /**
   * @param modelID
   * @param manufacturerExternalID
   * @return
   */
  public ModelWS getModel(String modelID , String manufacturerExternalID);

}