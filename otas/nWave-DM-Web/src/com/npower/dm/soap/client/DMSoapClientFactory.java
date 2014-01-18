/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/soap/client/DMSoapClientFactory.java,v 1.4 2008/04/27 02:38:20 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/04/27 02:38:20 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.soap.client;

import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import com.npower.dm.core.DMException;
import com.npower.dm.soap.common.ClientProvService;
import com.npower.dm.soap.common.ModelService;
import com.npower.dm.soap.common.ProvisionJobDispatcherService;
import com.npower.sms.SmsException;

/**
 * <pre>
 *   提供SOAP Client的Factory模式.
 *   必须提供soap server url, 
 *   例如: http://127.0.0.1:8080/OTAS-DM-Web/soap/
 *   
 * </pre>
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/04/27 02:38:20 $
 */
public class DMSoapClientFactory {

  public static final String PROP_NAME_SOAP_BASE_URL = "com.npower.dm.soap.client.url";
  
  private Properties properties = new Properties();

  /**
   * 
   */
  private DMSoapClientFactory() {
    super();
  }

  /**
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
  
  // Private methods -------------------------------------------------------------------
  private String getSoapBaseUrl() throws DMException {
    String url = this.properties.getProperty(PROP_NAME_SOAP_BASE_URL, System.getProperty(PROP_NAME_SOAP_BASE_URL));
    if (StringUtils.isEmpty(url)) {
       throw new DMException("Invalidate DM SOAP URL: " + url);
    }
    if (url.endsWith("/")) {
       url += "/";
    }
    return url;
  }
  
  /**
   * @param serviceClass
   * @param endPoint
   * @return
   * @throws SmsException
   */
  private Object getXFireSOAPClient(Class<?> serviceClass, String endPoint) throws DMException {
    String serviceUrl = this.getSoapBaseUrl() + endPoint;
    //Create a metadata of the service      
    Service serviceModel = new ObjectServiceFactory().create(serviceClass);        
    // To improve performance, avoid following messages:
    //   Discarding unexpected response: HTTP/1.1 100 Continue
    //   org.apache.commons.httpclient.HttpMethodBase writeRequest
    //   100 (continue) read timeout. Resume sending the request
    serviceModel.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");

    //Create a proxy for the deployed service
    XFire xfire = XFireFactory.newInstance().getXFire();
    XFireProxyFactory factory = new XFireProxyFactory(xfire);      
    
    Object client = null;
    try {
        client = factory.create(serviceModel, serviceUrl);
    } catch (MalformedURLException e) {
      throw new DMException("WsClient.callWebService(): EXCEPTION: " + e.toString(), e);
    }
    return client;
  }
  
  // Public methods -----------------------------------------------------------------------------
  
  /**
   * Instance a DMSoapClientFactory, required following properties: <br/>
   * com.npower.dm.soap.client.url     http://127.0.0.1:8080/OTAS-DM-Web/soap/ <br/>
   * @param properties
   * @return
   * @throws DMException
   */
  public static DMSoapClientFactory newInstance(Properties properties) throws DMException {
    DMSoapClientFactory factory = new DMSoapClientFactory();
    factory.setProperties(properties);
    return factory;
    
  }
  
  /**
   * Get ModelService SOAP client
   * @return
   * @throws SmsException
   */
  public ModelService getModelService() throws DMException {
    Class<?> serviceClass = ModelService.class;
    String endPoint = ModelService.SERVICE_END_POINT;

    Object client = getXFireSOAPClient(serviceClass, endPoint);
    return (ModelService)client;
  }

  /**
   * Get ModelService SOAP client
   * @return
   * @throws SmsException
   */
  public ClientProvService getClientProvService() throws DMException {
    Class<?> serviceClass = ClientProvService.class;
    String endPoint = ClientProvService.SERVICE_END_POINT;

    Object client = getXFireSOAPClient(serviceClass, endPoint);
    return (ClientProvService)client;
  }

  /**
   * Get Job Dispatcher SOAP client
   * @return
   * @throws DMException
   */
  public ProvisionJobDispatcherService getProvisionJobDispatcherService() throws DMException {
    Class<?> serviceClass = ProvisionJobDispatcherService.class;
    String endPoint = ProvisionJobDispatcherService.SERVICE_END_POINT;

    Object client = getXFireSOAPClient(serviceClass, endPoint);
    return (ProvisionJobDispatcherService)client;
  }

}
