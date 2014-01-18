/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/TestAutoRegistrationBean.java,v 1.1 2008/08/05 11:23:20 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/08/05 11:23:20 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.daemon;

import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.action.autoreg.AutoRegAction;
import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/05 11:23:20 $
 */
public class TestAutoRegistrationBean extends TestCase {

  /**
   * @throws java.lang.Exception
   */
  protected void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  protected void tearDown() throws Exception {
  }
  
  public void testCase5() throws Exception {
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();
    SubscriberBean subBean = factory.createSubcriberBean();

    String msisdn = "20010001";
    String imei = "004400112233445";
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        if (device != null) {
           factory.beginTransaction();
           deviceBean.delete(device);
           factory.commit();
        }
        List devices = deviceBean.findDevice("from DeviceEntity where subscriberPhoneNumber='" + msisdn + "'");
        for (Object o: devices) {
            factory.beginTransaction();
            deviceBean.delete((Device)o);
            factory.commit();
        }
        
        factory.beginTransaction();
        reg.bind(msisdn, imei);
        factory.commit();
        
        device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        assertNotNull(device);
        assertEquals("IMEI:" + imei, device.getExternalId());
        assertEquals(msisdn, device.getSubscriberPhoneNumber());
        assertEquals(msisdn, device.getSubscriber().getPhoneNumber());
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testCase1() throws Exception {
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();
    SubscriberBean subBean = factory.createSubcriberBean();

    String msisdn = "20010001";
    String imei = "004400112233445";
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        if (device != null) {
           deviceBean.delete(device);
        }
        List devices = deviceBean.findDevice("from DeviceEntity where subscriberPhoneNumber='" + msisdn + "'");
        for (Object o: devices) {
            deviceBean.delete((Device)o);
        }
        
        factory.beginTransaction();
        reg.bind(msisdn, imei);
        factory.commit();
        
        device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        assertNotNull(device);
        assertEquals("IMEI:" + imei, device.getExternalId());
        assertEquals(msisdn, device.getSubscriberPhoneNumber());
        assertEquals(msisdn, device.getSubscriber().getPhoneNumber());
        
        // Rebind
        factory.beginTransaction();
        reg.bind(msisdn, imei);
        factory.commit();
        
        device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        assertNotNull(device);
        assertEquals("IMEI:" + imei, device.getExternalId());
        assertEquals(msisdn, device.getSubscriberPhoneNumber());
        assertEquals(msisdn, device.getSubscriber().getPhoneNumber());
        
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testCase3() throws Exception {
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();
    SubscriberBean subBean = factory.createSubcriberBean();

    String msisdn = "20010002";
    String imei = "004400112233445";
    try {
        
        factory.beginTransaction();
        reg.bind(msisdn, imei);
        factory.commit();
        
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        assertNotNull(device);
        assertEquals("IMEI:" + imei, device.getExternalId());
        assertEquals(msisdn, device.getSubscriberPhoneNumber());
        assertEquals(msisdn, device.getSubscriber().getPhoneNumber());
        
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void testCase4() throws Exception {
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();
    SubscriberBean subBean = factory.createSubcriberBean();

    String msisdn = "555555";
    String imei = "350149112233446";
    try {
        
        factory.beginTransaction();
        reg.bind(msisdn, imei);
        factory.commit();
        
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        assertNotNull(device);
        assertEquals("IMEI:" + imei, device.getExternalId());
        assertEquals(msisdn, device.getSubscriberPhoneNumber());
        assertEquals(msisdn, device.getSubscriber().getPhoneNumber());
        
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void testCase2() throws Exception {
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();
    SubscriberBean subBean = factory.createSubcriberBean();

    String msisdn = "555555";
    String imei = "004400112233446";
    try {
        
        factory.beginTransaction();
        reg.bind(msisdn, imei);
        factory.commit();
        
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + imei);
        assertNotNull(device);
        assertEquals("IMEI:" + imei, device.getExternalId());
        assertEquals(msisdn, device.getSubscriberPhoneNumber());
        assertEquals(msisdn, device.getSubscriber().getPhoneNumber());
        
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void testParseImei() throws Exception {
    AutoRegAction action = new AutoRegAction();
    String rawdata = "494D45490A40047014676701FFFFFFFFFFFFFFFFFFFFFFFF00";
    String imei = action.parseImei(rawdata);
    assertNotNull(imei);
    assertEquals("004400741767618", imei);
  }
  
}
