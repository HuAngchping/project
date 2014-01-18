/**
  * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/action/BaseAction.java,v 1.11 2007/04/11 10:40:50 zhao Exp $
  * $Revision: 1.11 $
  * $Date: 2007/04/11 10:40:50 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.npower.dm.core.DMException;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.security.RolesStrutsMenuPermissionsAdapter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2007/04/11 10:40:50 $
 */
public class BaseAction extends Action {
  
  private static Log log = LogFactory.getLog(BaseAction.class);
  
  /**
   * 
   */
  public static final String PARAMETER_RECORDS_PER_PAGE = "recordsPerPage";
  
  /**
   * Parameter name of tile.template. the template is difined in tils-defs.xml
   */
  public static final String TILES_TEMPLATE_NAME = "tiles.template.name";

  public ManagementBeanFactory getManagementBeanFactory(HttpServletRequest request) throws DMException {
    ManagementBeanFactory factory = PersistentManager.getManagementBeanFactory(request);
    return factory;
  }

  /**
   * Find default menu item on the menuName.
   * The method will find the menu item based on the roles owned by current user.
   * @param request
   *        HttpServletRequest
   * @param menuName
   *        The name of menu which included items.
   * @return
   *        Default menu item.
   */
  protected ActionForward getDefaultMenuItem(HttpServletRequest request, String menuName) {
    ActionForward forward = null;
    MenuRepository repository = (MenuRepository)this.getServlet().getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
    MenuComponent mainMenu = repository.getMenu(menuName);
    RolesStrutsMenuPermissionsAdapter applicationMenuPermission = (RolesStrutsMenuPermissionsAdapter)request.getAttribute("applicationMenuPermission");
    for (Object item: mainMenu.getComponents()) {
        MenuComponent menu = (MenuComponent)item;
        if (applicationMenuPermission.isAllowed(menu)) {
           // First permitted menu
           String firstAction = menu.getAction();
           firstAction = (firstAction.startsWith("/"))?firstAction:"/" + firstAction;
           firstAction = (firstAction.endsWith(".do"))?firstAction:firstAction + ".do";
           forward = new ActionForward(firstAction, false);
           break;
        }
    }
    return forward;
  }

  /**
   * Set options for Records per page
   * @param request
   * @param searchForm
   * @throws DMException
   */
  public static void setRecordsPerPageOptions(HttpServletRequest request, DynaValidatorForm searchForm) throws DMException {
    request.setAttribute("recordsPerPageOptions", getRecordsPerPageOptions(request, searchForm));
  }

  /**
   * Set options for Records per page
   * @param request
   * @param searchForm
   * @throws DMException
   */
  public static void setRecordsPerPageOptions(HttpServletRequest request, ActionForm searchForm) throws DMException {
    request.setAttribute("recordsPerPageOptions", getRecordsPerPageOptions(request, searchForm));
  }

  /**
   * Generate Option list for Records of per page.
   * @return
   * @throws DMException
   */
  protected static Collection<LabelValueBean> getRecordsPerPageOptions(HttpServletRequest request, ActionForm form) throws DMException {
    Collection<LabelValueBean> result = new ArrayList<LabelValueBean>();
    //result.add(new LabelValueBean("2", "2"));
    result.add(new LabelValueBean("5", "5"));
    result.add(new LabelValueBean("10", "10"));
    result.add(new LabelValueBean("15", "15"));
    result.add(new LabelValueBean("25", "25"));
    result.add(new LabelValueBean("50", "50"));
    result.add(new LabelValueBean("100", "100"));
    result.add(new LabelValueBean("All", "" + Integer.MAX_VALUE));
    
    int recordsPerPage = getRecordsPerPage(request);
    if (form instanceof DynaValidatorForm) {
       ((DynaValidatorForm)form).set(BaseAction.PARAMETER_RECORDS_PER_PAGE, recordsPerPage);
    }
    
    return result;
  }
  
  /**
   * Get parameter fro request.
   * @return
   * @throws DMException
   */
  public static int getRecordsPerPage(HttpServletRequest request) throws DMException {
    // Default is 15
    int result = 15;
    String t = request.getParameter(BaseAction.PARAMETER_RECORDS_PER_PAGE);
    if (StringUtils.isEmpty(t)) {
       // Map backed Form
       t = request.getParameter("value(" + BaseAction.PARAMETER_RECORDS_PER_PAGE + ")");
    }
    if (StringUtils.isNotEmpty(t)) {
       try {
           result = Integer.parseInt(t);
       } catch (Exception ex) {
         
       }
    } else {
      Enumeration names = request.getAttributeNames();
      while (names.hasMoreElements()) {
            String name = (String)names.nextElement();
            Object object = request.getAttribute(name);
            if (object != null && object instanceof DynaValidatorForm) {
               DynaValidatorForm form = (DynaValidatorForm)object;
               try {
                   Object v = form.get("recordsPerPage");
                   if (v != null && v instanceof Integer) {
                      return ((Integer)v).intValue();
                   }
               } catch (IllegalArgumentException ex) {
                 // If DynaFomr don't contains the "recordsPerPage" will throw this exception
                 
               } catch (Exception ex) {
                 // Unknow error!
                 log.error("Error in finding recordsPerPage.", ex);
               }
            }
      }
    }
    return result;
  }

}
