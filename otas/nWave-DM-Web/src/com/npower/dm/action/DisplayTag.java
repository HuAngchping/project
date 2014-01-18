package com.npower.dm.action;

import java.util.List;

import org.hibernate.Criteria;

import com.npower.dm.core.DMException;

public interface DisplayTag {

  public abstract String getOrdervalue();

  public abstract void setOrdervalue(String ordervalue);

  public abstract String getSortvalue();

  public abstract void setSortvalue(String sortvalue);

  public abstract String getPagevalue();

  public abstract void setPagevalue(String pagevalue);

  public abstract int getCount();

  public abstract void setCount(int count);

  public Criteria getCriteria() throws DMException;
  
  public abstract List findAll() throws DMException;

  public abstract List find(Criteria criteria) throws DMException;
}