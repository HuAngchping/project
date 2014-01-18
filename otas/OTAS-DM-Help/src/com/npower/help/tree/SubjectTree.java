/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/help/tree/SubjectTree.java,v 1.1 2008/08/13 04:16:35 hcp Exp $
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
import java.util.Date;
import java.util.List;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}ÏÂÎç01:38:50$ com.npower.help.tree
 *          OTAS-DM-Help SubjectTree.java
 */
public class SubjectTree implements Serializable {

  /**
   * 
   */
  private static final long     serialVersionUID = 1L;

  private Date                  lastLoadedTime   = new Date();

  private List<SubjectTreeItem> rootNodes        = new ArrayList<SubjectTreeItem>();

  /**
   * 
   */
  public SubjectTree() {
    super();
  }

  public List<SubjectTreeItem> getRootNodes() {
    return rootNodes;
  }

  public void setRootNodes(List<SubjectTreeItem> rootNodes) {
    this.rootNodes = rootNodes;
  }

  public Date getLastLoadedTime() {
    return lastLoadedTime;
  }

  public void setLastLoadedTime(Date lastLoadedTime) {
    this.lastLoadedTime = lastLoadedTime;
  }

}
