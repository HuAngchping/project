/**
 * 
 */
package com.npower.dm.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;

/**
 * @author Administrator
 * 
 */
public class DisplayTagPerPage implements DisplayTag {
  
  private static final String regExpage = "d-[0-9]+-p";

  private static final String regExsort = "d-[0-9]+-s";

  private static final String regExorder = "d-[0-9]+-o";

  private ManagementBeanFactory factory = null;

  private String[] colnumber = new String[20];

  private String pagevalue = "";

  private String sortvalue = "";

  private String ordervalue = "";

  private int count = 0;

  private Class classname = null;

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#getOrdervalue()
   */
  public String getOrdervalue() {
    return ordervalue;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#setOrdervalue(java.lang.String)
   */
  public void setOrdervalue(String ordervalue) {
    this.ordervalue = ordervalue;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#getSortvalue()
   */
  public String getSortvalue() {
    return sortvalue;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#setSortvalue(java.lang.String)
   */
  public void setSortvalue(String sortvalue) {
    this.sortvalue = sortvalue;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#getPagevalue()
   */
  public String getPagevalue() {
    return pagevalue;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#setPagevalue(java.lang.String)
   */
  public void setPagevalue(String pagevalue) {
    this.pagevalue = pagevalue;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#getCount()
   */
  public int getCount() {
    return count;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#setCount(int)
   */
  public void setCount(int count) {
    this.count = count;
  }

  DisplayTagPerPage() {
    super();
  }

  public DisplayTagPerPage(ManagementBeanFactory factory, Class className, HttpServletRequest request, String col[]) {

    this.factory = factory;
    this.classname = className;
    this.colnumber = col;

    Enumeration test = request.getParameterNames();
    while (test.hasMoreElements()) {

      String parname = (String) test.nextElement();
      String parvalue = request.getParameter(parname);

      Pattern ppage = Pattern.compile(regExpage);
      Matcher mpage = null;
      mpage = ppage.matcher(parname);
      boolean urlpage = mpage.find();

      if (urlpage) {
        this.setPagevalue(parvalue);
      }

      Pattern psort = Pattern.compile(regExsort);
      Matcher msort = null;
      msort = psort.matcher(parname);
      boolean urlsort = msort.find();

      if (urlsort) {
        this.setSortvalue(parvalue);
      }

      Pattern porder = Pattern.compile(regExorder);
      Matcher morder = null;
      morder = porder.matcher(parname);
      boolean urlorder = morder.find();

      if (urlorder) {
        this.setOrdervalue(parvalue);
      }
    }

  }

  /* (non-Javadoc)
   * @see com.npower.dm.action.DisplayTag#newResult()
   */
  public List findAll() throws DMException {

    List newresult = new ArrayList();
    int begin = 1;

    try {

      if (pagevalue.trim().length() != 0) {
        begin = (Integer.valueOf(pagevalue) - 1) * 10 + 1;
      }

      Criteria criteria = getCriteria();

      criteria.setFirstResult(0);
      int allnumber = criteria.setMaxResults(Integer.MAX_VALUE).list().size();
      this.setCount(allnumber);

      criteria.setFirstResult(begin - 1);
      criteria.setMaxResults(10);

      if (ordervalue.trim().length() != 0) {

        if (ordervalue.equals("1")) {
          criteria.addOrder(Order.desc(colnumber[Integer.valueOf(sortvalue)]));
        } else if (ordervalue.equals("2")) {
          criteria.addOrder(Order.asc(colnumber[Integer.valueOf(sortvalue)]));
        }

      }

      newresult = criteria.list();

    } catch (DMException e) {
      throw e;
    }

    return newresult;
  }

  public List find(Criteria criteria) throws DMException {

    List newresult = new ArrayList();
    int begin = 1;

    try {

      if (pagevalue.trim().length() != 0) {
        begin = (Integer.valueOf(pagevalue) - 1) * 10 + 1;
      }

      criteria.setFirstResult(0);
      int allnumber = criteria.setMaxResults(Integer.MAX_VALUE).list().size();
      this.setCount(allnumber);

      criteria.setFirstResult(begin - 1);
      criteria.setMaxResults(10);

      if (ordervalue.trim().length() != 0) {

        if (ordervalue.equals("1")) {
          criteria.addOrder(Order.desc(colnumber[Integer.valueOf(sortvalue)]));
        } else if (ordervalue.equals("2")) {
          criteria.addOrder(Order.asc(colnumber[Integer.valueOf(sortvalue)]));
        }

      }

      newresult = criteria.list();

    } catch (Exception e) {
      throw new DMException(e);
    }

    return newresult;
  }
  /**
   * @param searchBean
   * @return
   * @throws DMException
   */
  public Criteria getCriteria() throws DMException {
    SearchBean searchBean = this.factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(this.classname);
    return criteria;
  }

}
