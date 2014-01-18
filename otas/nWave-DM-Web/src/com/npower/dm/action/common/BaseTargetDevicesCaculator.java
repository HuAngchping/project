package com.npower.dm.action.common;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;

public abstract class BaseTargetDevicesCaculator implements TargetDevicesCaculator {

  private transient ManagementBeanFactory factory = null;

  /**
   * 
   */
  public BaseTargetDevicesCaculator() {
    super();
  }

  /**
   * @param factory
   */
  public BaseTargetDevicesCaculator(ManagementBeanFactory factory) {
    super();
    this.factory = factory;
  }

  /**
   * @return
   */
  public ManagementBeanFactory getFactory() {
    return factory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.TargetDevicesCaculator#setFactory(com.npower.dm.management.ManagementBeanFactory)
   */
  public void setFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.TargetDevicesCaculator#getPaginatedDevices(int, int)
   */
  public abstract PaginatedResult getPaginatedDevices(int pageNumber, int objectsPerPage) throws DMException ;

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.TargetDevicesCaculator#getTotalDevices()
   */
  public abstract int getTotalDevices() throws DMException;

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.TargetDevicesCaculator#isEmpty()
   */
  public abstract boolean isEmpty() throws DMException;

}