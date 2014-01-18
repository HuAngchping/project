/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/common/TestTargetDeviceCaculator.java,v 1.1 2008/07/30 10:21:27 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/07/30 10:21:27 $
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
package com.npower.dm.action.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/07/30 10:21:27 $
 */
public class TestTargetDeviceCaculator extends TestCase {

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
  
  public void testCase1() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      CarrierBean carrierBean = factory.createCarrierBean();
      List<Carrier> carriers = carrierBean.getAllCarriers();
      List<Long> carrierIDs = new ArrayList<Long>();
      for (Carrier carrier: carriers) {
          carrierIDs.add(carrier.getID());
      }
      TargetDevicesCaculator caculator = new CarrierIDTargetDeviceCaculator(factory, carrierIDs.toArray(new Long[]{}));
      
      assertFalse(caculator.isEmpty());
      assertEquals(12152, caculator.getTotalDevices());
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testCase2() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean bean = factory.createModelBean();
      Manufacturer manufacturer = bean.getManufacturerByExternalID("NOKIA");
      Set<Model> models = manufacturer.getModels();
      List<Long> modelIDs = new ArrayList<Long>();
      for (Model model: models) {
          modelIDs.add(model.getID());
      }
      TargetDevicesCaculator caculator = new ModelIDTargetDeviceCaculator(factory, modelIDs.toArray(new Long[]{}));
      
      assertFalse(caculator.isEmpty());
      assertEquals(12013, caculator.getTotalDevices());
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testCase3() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean bean = factory.createModelBean();
      List<Manufacturer> manufacturers = bean.getAllManufacturers();
      List<Long> manufacturerIDs = new ArrayList<Long>();
      for (Manufacturer e: manufacturers) {
        manufacturerIDs.add(e.getID());
      }
      TargetDevicesCaculator caculator = new ManufacturerIDTargetDeviceCaculator(factory, manufacturerIDs.toArray(new Long[]{}));
      
      assertFalse(caculator.isEmpty());
      assertEquals(12152, caculator.getTotalDevices());
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testCase4() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      MultipleTargetDevicesCaculator caculator = new MultipleTargetDevicesCaculator(factory);
      {
        ModelBean bean = factory.createModelBean();
        List<Manufacturer> manufacturers = bean.getAllManufacturers();
        List<Long> manufacturerIDs = new ArrayList<Long>();
        for (Manufacturer e: manufacturers) {
          manufacturerIDs.add(e.getID());
        }
        caculator.addCaculator(new ManufacturerIDTargetDeviceCaculator(factory, manufacturerIDs.toArray(new Long[]{})));
      }
      {
        ModelBean bean = factory.createModelBean();
        Manufacturer manufacturer = bean.getManufacturerByExternalID("NOKIA");
        Set<Model> models = manufacturer.getModels();
        List<Long> modelIDs = new ArrayList<Long>();
        for (Model model: models) {
            modelIDs.add(model.getID());
        }
        caculator.addCaculator(new ModelIDTargetDeviceCaculator(factory, modelIDs.toArray(new Long[]{})));
      }

      {
        CarrierBean carrierBean = factory.createCarrierBean();
        List<Carrier> carriers = carrierBean.getAllCarriers();
        List<Long> carrierIDs = new ArrayList<Long>();
        for (Carrier carrier: carriers) {
            carrierIDs.add(carrier.getID());
        }
        caculator.addCaculator(new CarrierIDTargetDeviceCaculator(factory, carrierIDs.toArray(new Long[]{})));        
      }
      
      assertFalse(caculator.isEmpty());
      assertEquals(12152, caculator.getTotalDevices());
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

}
