/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/help/action/FullTextSearchExpression.java,v 1.1 2008/08/07 10:35:34 hcp Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/07 10:35:34 $
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

package com.npower.help.action;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}ÏÂÎç04:38:14$ com.npower.help.action
 *          OTAS-DM-Help FullTextSearchExpression.java
 */
public class FullTextSearchExpression implements Criterion {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private final String      propertyName;

  private final String      value;

  public FullTextSearchExpression(String propertyName, String value) {
    this.propertyName = propertyName;
    this.value = '%' + value + '%';
  }

  public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
    if (columns.length != 1)
      throw new HibernateException("ilike may only be used with single-column properties");
    int index = genIndexId(columns);
    return " contains(" + columns[0] + ", ?, " + index + ")>0";
  }

  /**
   * @param columns
   */
  private int genIndexId(String[] columns) {
    int index = columns[0].hashCode();
    index = (index <0)?(Integer.MAX_VALUE + index):index;
    return index;
  }

  public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    return new TypedValue[] { criteriaQuery.getTypedValue(criteria, propertyName, value.toString().toLowerCase()) };
  }

  public String toString() {
    return " contains(" + propertyName + ", ?, 1)>0";
  }
}
