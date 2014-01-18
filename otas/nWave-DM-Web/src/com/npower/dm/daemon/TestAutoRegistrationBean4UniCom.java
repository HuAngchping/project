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
 * �˲��Ը����й���ͨҪ�����ע����Թ淶��д��
 */
public class TestAutoRegistrationBean4UniCom extends TestCase {

  //ŵ����6120c��IMEI����Ϊ�ն�1
  private static String Nokia_6120c_IMEI = "356255011242785";
  //Ħ������L71��IMEI����Ϊ�ն�2
  private static String Nokia_6681_IMEI = "354349002783362";
  
  //�й���ͨ13030065786����ΪUSIM��1
  private static String USIM_Msisdn1 = "13030065786";
  //�й���ͨ13199478079����ΪUSIM��2
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
   * TestCase1:ȡNokia_6120c_IMEI��USIM_Msisdn1����
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

    //����ģ�⿪��
    
    log.debug("�ն�1 Nokia 6120C �״ο�����");
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
        if (device != null) {
          if (device.getSubscriberPhoneNumber().equals(USIM_Msisdn1)) {
            log.debug("���ն�����ע����������Ͷ��ţ�");
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
        log.debug("�ն˹���ƽ̨��ȷ�յ��ն�1 Nokia_6120c_IMEI �ϱ�����ע�����Ϣ����Ϣ����������ȷ���ն���Ԫ�顣�ն˹���ƽ̨�ܹ���ȷ��¼���������Ϣ��");
        /*********************************************************************************/
        log.debug("�ն�1 Nokia 6120C �ػ���");
        log.debug("�ն�1 Nokia 6120C �ٴο�����");
        Device device1 = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
        if (device1 != null) {
          if (device1.getSubscriberPhoneNumber().equals(USIM_Msisdn1)) {
            log.debug("���ն�����ע����������Ͷ��ţ�");
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
   * TestCase2:ȡNokia_6681_IMEI��USIM_Msisdn1��������
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

    //����ģ�⿪��
    
    log.debug("�ն�2 Nokia 6681 ������1�󿪻���");
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6681_IMEI);
        if (device != null) {
          if (device.getSubscriberPhoneNumber().equals(USIM_Msisdn1)) {
            log.debug("���ն�����ע����������Ͷ��ţ�");
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
            log.debug("�ն˹���ƽ̨��ȷ�յ��ն�2 Nokia 6681 �ϱ�����ע�����Ϣ����Ϣ����������ȷ���ն���Ԫ�顣�ն˹���ƽ̨�ܹ���ȷ��¼���������Ϣ��");
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
   * TestCase3:ȡNokia_6120c_IMEI��USIM_Msisdn2��������
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

    //����ģ�⿪��
    
    log.debug("�ն�1 Nokia 6120C ������2�󿪻���");
    try {
        Device device = deviceBean.getDeviceByExternalID("IMEI:" + Nokia_6120c_IMEI);
        if (device != null) {
          if (device.getSubscriberPhoneNumber().equals(USIM_Msisdn2)) {
            log.debug("���ն�����ע����������Ͷ��ţ�");
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
            log.debug("�ն˹���ƽ̨��ȷ�յ��ն�1 Nokia6120c �ϱ�����ע�����Ϣ����Ϣ����������ȷ���ն���Ԫ�顣�ն˹���ƽ̨�ܹ���ȷ��¼���������Ϣ��");
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
