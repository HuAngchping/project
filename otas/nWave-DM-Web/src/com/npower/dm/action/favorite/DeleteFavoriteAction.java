/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.npower.dm.action.favorite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.npower.dm.action.BaseDispatchAction;
import com.npower.dm.core.Favorite;
import com.npower.dm.hibernate.dao.FavoriteBeanImpl;
import com.npower.dm.management.FavoriteBean;
import com.npower.dm.management.ManagementBeanFactory;

/** 
 * MyEclipse Struts
 * Creation date: 12-19-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DeleteFavoriteAction extends BaseDispatchAction {
  /*
   * Generated Methods
   */

  /** 
   * Method execute
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return ActionForward
   */
  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    DynaActionForm favoriteForm = (DynaActionForm) form;
    String favoriteId = favoriteForm.getString("favoriteId");
    if (StringUtils.isEmpty(favoriteId)) {
      favoriteId = request.getParameter("favoriteId");
    }
    ManagementBeanFactory factory = null;
    try {
      factory = this.getManagementBeanFactory(request);
      FavoriteBean favoriteBean = (FavoriteBean)factory.createBean(FavoriteBeanImpl.class);
      Favorite favorite = favoriteBean.findById(Long.parseLong(favoriteId));
      factory.beginTransaction();
      favoriteBean.delete(favorite);
      factory.commit();
    } catch (Exception e) {
      throw e;
    }
    return mapping.findForward("delete");
  }
}