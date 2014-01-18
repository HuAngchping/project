package com.npower.dm.action.common;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.hibernate.entity.DeviceEntity;
import com.npower.dm.hibernate.management.EmptyPaginatedResult;
import com.npower.dm.hibernate.management.PaginatedResultImpl4Criteria;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.PaginatedResult;
import com.npower.dm.management.SearchBean;

public abstract class AbstractTargetDeviceCaculator extends BaseTargetDevicesCaculator {

  public AbstractTargetDeviceCaculator() {
    super();
  }

  /**
   * @param factory
   */
  public AbstractTargetDeviceCaculator(ManagementBeanFactory factory) {
    super(factory);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.BaseTargetDevicesCaculator#isEmpty()
   */
  public boolean isEmpty() throws DMException {
    return this.getTotalDevices() == 0;
  }

  /**
   * @return
   * @throws DMException
   */
  private Criteria getDevicesCriteria() throws DMException {
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(DeviceEntity.class);
    criteria.addOrder(Order.asc("externalId"));
    criteria.createAlias("model", "model");
    criteria.createAlias("model.manufacturer", "manufacturer");
    criteria.createAlias("subscriber", "subscriber");
    criteria.createAlias("subscriber.carrier", "carrier");
    return criteria;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.BaseTargetDevicesCaculator#getPaginatedDevices(int, int)
   */
  public PaginatedResult getPaginatedDevices(int pageNumber, int objectsPerPage) throws DMException {
    Criteria criteria = this.getDevicesCriteria();
    Criterion expression = this.getExpression(criteria);
    if (expression != null) {
       criteria.add(expression);
       return new PaginatedResultImpl4Criteria(criteria, pageNumber, objectsPerPage);
    } else {
      return new EmptyPaginatedResult(pageNumber, objectsPerPage);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.common.BaseTargetDevicesCaculator#getTotalDevices()
   */
  public int getTotalDevices() throws DMException {
    PaginatedResult result = this.getPaginatedDevices(1, 1);
    return result.getFullListSize();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.action.common.TargetDevicesCaculator#getDeviceGroup()
   */
  public DeviceGroup getDeviceGroup() throws DMException {
    DeviceBean deviceBean = this.getFactory().createDeviceBean();
    DeviceGroup group = deviceBean.newDeviceGroup();
    PaginatedResult result = this.getPaginatedDevices(1, Integer.MAX_VALUE);
    for (Device device: (List<Device>)result.getList()) {
        deviceBean.add(group, device);
    }
    deviceBean.update(group);
    return group;
  }

  /**
   * @param criteria
   * @return
   */
  protected abstract Criterion getExpression(Criteria criteria) throws DMException;

}