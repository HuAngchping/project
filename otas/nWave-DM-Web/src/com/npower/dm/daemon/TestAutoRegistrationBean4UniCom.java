/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/daemon/TestAutoRegistrationBean4UniCom.java,v 1.2 2008/10/31 08:52:11 chenlei Exp $
 * $Revision: 1.2 $
 * $Date: 2008/10/31 08:52:11 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/10/31 08:52:11 $
 */

/*
 * 此测试根据中国联通要求的自注册测试规范而写。
 */
public class TestAutoRegistrationBean4UniCom extends TestCase {

  //诺基亚6120c的IMEI号作为终端1
  private static String Nokia_6120c_IMEI = "356255011242785";
  //摩托罗拉L71的IMEI号作为终端2
  private static String Nokia_6681_IMEI = "354349002783362";
  
  //中国联通13030065786号作为USIM卡1
  private static String USIM_Msisdn1 = "13030065786";
  //中国联通13199478079号作为USIM卡2
  private static String USIM_Msisdn2 = "13199478079";
  
  private static Log log = LogFactory.getLog(TestAutoRegistrationBean4UniCom.class);
  
  /**
   * @param name
   */
  public TestAutoRegistrationBean4UniCom(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  
  /*
   * TestCase1:取Nokia_6120c_IMEI与USIM_Msisdn1测试
   */
  public void testCase1() throws DMException {
    System.setProperty("otas.dm.home", "D:/OTASDM");
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();

    //以下模拟开机
    
    log.debug("终端1 Nokia 6120C 首次开机！");
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
        if (device != null) {
          if (device.getSubscriberPhoneNumber().equals(USIM_Msisdn1)) {
            log.debug("此终端已自注册过，不发送短信！");
          }
        }
        factory.beginTransaction();
        reg.bind(USIM_Msisdn1, Nokia_6120c_IMEI);
        factory.commit();
        
        device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
        assertNotNull(device);
        assertEquals("IMEI:" + Nokia_6120c_IMEI, device.getExternalId());
        assertEquals(USIM_Msisdn1, device.getSubscriberPhoneNumber());
        assertEquals(USIM_Msisdn1, device.getSubscriber().getPhoneNumber());
        assertEquals(null, device.getModel().getOperatingSystem());
        log.debug("终端管理平台正确收到终端1 Nokia_6120c_IMEI 上报的自注册短消息，消息中所包含正确的终端四元组。终端管理平台能够正确记录机卡配对信息。");
        /*********************************************************************************/
        log.debug("终端1 Nokia 6120C 关机！");
        log.debug("终端1 Nokia 6120C 再次开机！");
        Device device1 = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
        if (device1 != null) {
          if (device1.getSubscriberPhoneNumber().equals(USIM_Msisdn1)) {
            log.debug("此终端已自注册过，不发送短信！");
          }
        }        
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  
  /*
   * TestCase2:取Nokia_6681_IMEI与USIM_Msisdn1交换测试
   */
  public void testCase2() throws DMException {
    System.setProperty("otas.dm.home", "D:/OTASDM");
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();

    //以下模拟开机
    
    log.debug("终端2 Nokia 6681 交换卡1后开机！");
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6681_IMEI);
        if (device != null) {
          if (device.getSubscriberPhoneNumber().equals(USIM_Msisdn1)) {
            log.debug("此终端已自注册过，不发送短信！");
          } else {
            factory.beginTransaction();
            reg.bind(USIM_Msisdn1, Nokia_6681_IMEI);
            factory.commit();
            
            device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6681_IMEI);
            assertNotNull(device);
            assertEquals("IMEI:" + Nokia_6681_IMEI, device.getExternalId());
            assertEquals(USIM_Msisdn1, device.getSubscriberPhoneNumber());
            assertEquals(USIM_Msisdn1, device.getSubscriber().getPhoneNumber());
            assertEquals(null,device.getModel().getOperatingSystem());
            log.debug("终端管理平台正确收到终端2 Nokia 6681 上报的自注册短消息，消息中所包含正确的终端四元组。终端管理平台能够正确记录机卡配对信息。");
          }
        }
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  
  /*
   * TestCase3:取Nokia_6120c_IMEI与USIM_Msisdn2交换测试
   */
  public void testCase3() throws DMException {
    System.setProperty("otas.dm.home", "D:/OTASDM");
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    AutoRegistrationBean reg = new AutoRegistrationBean();
    reg.setFactory(factory);
    reg.setCarrierExternalID("OTAS");
    reg.setClientPassword("otasdm");
    reg.setClientUsername("otasdm");
    reg.setServerPassword("otasdm");
    
    DeviceBean deviceBean = factory.createDeviceBean();

    //以下模拟开机
    
    log.debug("终端1 Nokia 6120C 交换卡2后开机！");
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
        if (device != null) {
          if (device.getSubscriberPhoneNumber().equals(USIM_Msisdn2)) {
            log.debug("此终端已自注册过，不发送短信！");
          } else {
            factory.beginTransaction();
            reg.bind(USIM_Msisdn2, Nokia_6120c_IMEI);
            factory.commit();
            
            device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
            assertNotNull(device);
            assertEquals("IMEI:" + Nokia_6120c_IMEI, device.getExternalId());
            assertEquals(USIM_Msisdn2, device.getSubscriberPhoneNumber());
            assertEquals(USIM_Msisdn2, device.getSubscriber().getPhoneNumber());
            assertEquals(null, device.getModel().getOperatingSystem());
            log.debug("终端管理平台正确收到终端1 Nokia6120c 上报的自注册短消息，消息中所包含正确的终端四元组。终端管理平台能够正确记录机卡配对信息。");
          }
        }
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  
  
}
