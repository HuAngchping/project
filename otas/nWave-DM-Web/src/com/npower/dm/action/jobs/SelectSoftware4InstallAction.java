/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/jobs/SelectSoftware4InstallAction.java,v 1.1 2008/12/12 08:19:53 zhaowx Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/12 08:19:53 $
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

package com.npower.dm.action.jobs;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import com.npower.dm.action.BaseAction;
import com.npower.dm.core.Software;
import com.npower.dm.hibernate.entity.SoftwareEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2008/12/12 08:19:53 $
 */

public class SelectSoftware4InstallAction extends BaseAction {

  /**
   * 
   */
  public SelectSoftware4InstallAction() {
    super();
  }

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaValidatorForm dynaForm = (DynaValidatorForm)rawForm;
    String searchText = (String)dynaForm.get("searchText");
       
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(SoftwareEntity.class);
    if (StringUtils.isNotEmpty(searchText)) {
      criteria.add(Expression.ilike("name", searchText, MatchMode.ANYWHERE));      
    }
    
    // Set Target Devices into request attribute "targetDevices" for JSP
    BaseJobsAction.setTargetDevices(factory, request);
    
    // Set options for Records per page options.
    int recordsPerPage = BaseAction.getRecordsPerPage(request);
    dynaForm.set("recordsPerPage", recordsPerPage);
    BaseAction.setRecordsPerPageOptions(request, dynaForm);  
    
    List<Software> softwares = criteria.list();
    request.setAttribute("softwares", softwares);
    return (mapping.findForward("display"));
  }  
  
}
