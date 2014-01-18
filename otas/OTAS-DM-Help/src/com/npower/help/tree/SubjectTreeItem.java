/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/help/tree/SubjectTreeItem.java,v 1.1 2008/08/13 04:16:35 hcp Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/13 04:16:35 $
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

package com.npower.help.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}ÏÂÎç01:46:56$ com.npower.help.tree
 *          OTAS-DM-Help SubjectTreeItem.java
 */
public class SubjectTreeItem implements Serializable {
  /**
   * 
   */
  private static final long     serialVersionUID = 1L;

  private String                id               = null;

  private String                label            = null;

  private List<SubjectTreeItem> children         = new ArrayList<SubjectTreeItem>();

  /**
   * 
   */
  public SubjectTreeItem() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<SubjectTreeItem> getChildren() {
    return children;
  }

  public void setChildren(List<SubjectTreeItem> children) {
    this.children = children;
  }

}
