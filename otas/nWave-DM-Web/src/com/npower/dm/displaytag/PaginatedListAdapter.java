/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/displaytag/PaginatedListAdapter.java,v 1.2 2007/06/21 11:27:11 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/06/21 11:27:11 $
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
package com.npower.dm.displaytag;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import com.npower.dm.management.PaginatedResult;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/06/21 11:27:11 $
 */
public class PaginatedListAdapter implements PaginatedList {
  private PaginatedResult result = null;

  /**
   * 
   */
  public PaginatedListAdapter() {
    super();
  }

  /**
   * 
   */
  public PaginatedListAdapter(PaginatedResult result) {
    this.result = result;
  }

  /* (non-Javadoc)
   * @see org.displaytag.pagination.PaginatedList#getFullListSize()
   */
  public int getFullListSize() {
    return this.result.getFullListSize();
  }

  /* (non-Javadoc)
   * @see org.displaytag.pagination.PaginatedList#getList()
   */
  public List getList() {
    return this.result.getList();
  }

  /* (non-Javadoc)
   * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
   */
  public int getObjectsPerPage() {
    return this.result.getObjectsPerPage();
  }

  /* (non-Javadoc)
   * @see org.displaytag.pagination.PaginatedList#getPageNumber()
   */
  public int getPageNumber() {
    return this.result.getPageNumber();
  }

  /* (non-Javadoc)
   * @see org.displaytag.pagination.PaginatedList#getSearchId()
   */
  public String getSearchId() {
    return this.result.getSearchId();
  }

  /* (non-Javadoc)
   * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
   */
  public String getSortCriterion() {
    return this.result.getSortCriterion();
  }

  /* (non-Javadoc)
   * @see org.displaytag.pagination.PaginatedList#getSortDirection()
   */
  public SortOrderEnum getSortDirection() {
    return null;
  }

}
