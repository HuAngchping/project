package com.npower.dm.action.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.action.BaseAction;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SearchBean;

public class SearchModels extends BaseAction {

  private List getModels(HttpServletRequest request, String manufacturerid) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    ModelBean modelBean = factory.createModelBean();
    List molist = new ArrayList();

    if (manufacturerid != null) {
      molist = modelBean.findManufacturers("from ModelEntity where manufacturer='" + manufacturerid + "'");
    } else {
      molist = modelBean.findManufacturers("from ModelEntity");
    }

    return molist;
  }

  private List getManufacturer(HttpServletRequest request, String manufacturername) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory(request);
    SearchBean searchBean = factory.createSearchBean();
    Criteria criteria = searchBean.newCriteriaInstance(Manufacturer.class);
    List mlist = new ArrayList();

    criteria.add(Expression.eq("name", manufacturername));
    mlist = criteria.list();

    return mlist;
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    DynaActionForm searchmodelform = (DynaActionForm) form;
    String manufacturerName = searchmodelform.getString("manufacturerName");

    if (manufacturerName != null) {

      List manlist = this.getManufacturer(request, manufacturerName);

      String manid = null;
      if (manlist.size() > 0) {
        Manufacturer manufacturer = (Manufacturer) manlist.get(0);
        manid = String.valueOf(manufacturer.getID());
      }
      List result = this.getModels(request, manid);
      request.setAttribute("models", result);

    }

    return (mapping.findForward("searchsuccess"));

  }
}