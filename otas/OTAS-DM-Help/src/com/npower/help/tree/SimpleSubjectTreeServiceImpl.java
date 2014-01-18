/**
 * $Header: /home/master/OTAS-DM-Help/src/com/npower/help/tree/SimpleSubjectTreeServiceImpl.java,v 1.2 2008/08/13 09:16:18 hcp Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/13 09:16:18 $
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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.help.core.Subject;
import com.npower.help.dao.HelpDAOFactory;
import com.npower.help.dao.SubjectDAO;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.2 $ ${date}ÏÂÎç01:41:04$ com.npower.help.tree
 *          OTAS-DM-Help SubjectTreeServiceImpl.java
 */
public class SimpleSubjectTreeServiceImpl implements SubjectTreeService {

  private static Log  log     = LogFactory.getLog(SimpleSubjectTreeServiceImpl.class);

  private SubjectTree tree    = null;

  HelpDAOFactory      factory = null;

  /**
   * 
   */
  public SimpleSubjectTreeServiceImpl() {
    super();
  }

  public synchronized SubjectTree getTree() {
    return this.tree;
  }

  public synchronized void reload() {
    this.tree = load();
  }

  private SubjectTree load() {
    SubjectTree tree = new SubjectTree();
    try {
      factory = HelpDAOFactory.newInstance();
      SubjectDAO subjectDAO = factory.getSubjectDAO();

      List<Subject> subjects = subjectDAO.findRootSubjects();
      List<SubjectTreeItem> rootsSet = tree.getRootNodes();
      fullFill(subjects, rootsSet);

    } catch (Exception e) {
      log.error("Failure to load subject tree.", e);
    }
    tree.setLastLoadedTime(new Date());
    return tree;
  }

  /**
   * @param subjects
   * @param rootsSet
   */
  private void fullFill(Collection<Subject> subjects, Collection<SubjectTreeItem> rootsSet) {
    for (Subject subject : subjects) {
      SubjectTreeItem item = new SubjectTreeItem();
      item.setId(Long.toString(subject.getSubjectId()));
      item.setLabel(subject.getExternalId());
      rootsSet.add(item);

      this.fullFill(subject.getChildren(), item.getChildren());
    }
  }
}
